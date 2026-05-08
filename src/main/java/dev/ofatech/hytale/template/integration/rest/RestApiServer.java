package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dev.ofatech.hytale.template.config.ConfigManager;
import dev.ofatech.hytale.template.config.PluginConfig;
import dev.ofatech.hytale.template.data.PlayerDataService;
import dev.ofatech.hytale.template.events.EventBus;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.util.JsonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class RestApiServer {
    private final ConfigManager configManager;
    private final PlayerDataService playerDataService;
    private final EventBus eventBus;
    private final Object logger;
    private PluginContext context;
    private final List<Route> routes = new ArrayList<>();

    private HttpServer server;

    public RestApiServer(
        ConfigManager configManager,
        PlayerDataService playerDataService,
        EventBus eventBus,
        Object logger
    ) {
        this.configManager = Objects.requireNonNull(configManager, "configManager");
        this.playerDataService = Objects.requireNonNull(playerDataService, "playerDataService");
        this.eventBus = Objects.requireNonNull(eventBus, "eventBus");
        this.logger = logger;
    }

    public void bindContext(PluginContext context) {
        this.context = Objects.requireNonNull(context, "context");
    }

    public boolean start() {
        if (server != null) {
            return true;
        }
        PluginConfig config = configManager.config();
        if (context == null) {
            throw new RestApiException(500, "REST API context is not ready");
        }
        if (!config.api().enabled() || !config.features().restApi()) {
            return false;
        }

        try {
            InetSocketAddress address = new InetSocketAddress(config.api().host(), config.api().port());
            server = HttpServer.create(address, 0);
        } catch (IOException ex) {
            throw new RestApiException(500, "Failed to start REST API server: " + ex.getMessage());
        }

        registerRoutes(config);
        server.start();
        logInfo("REST API listening on " + config.api().host() + ":" + config.api().port());
        return true;
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    public boolean isRunning() {
        return server != null;
    }

    public int routeCount() {
        return routes.size();
    }

    private void registerRoutes(PluginConfig config) {
        AuthFilter authFilter = new AuthFilter(() -> configManager.config().api().token());
        routes.clear();
        routes.add(new HealthController());
        routes.add(new VersionController());
        routes.add(new ConfigController(configManager.view(), context.featureManager()));
        routes.add(new PlayerController(playerDataService));

        if (config.api().allowReloadEndpoint()) {
            routes.add(new AdminController(context));
        }

        for (Route route : routes) {
            server.createContext(route.path(), new RouteHandler(route, authFilter));
        }
    }

    private final class RouteHandler implements HttpHandler {
        private final Route route;
        private final AuthFilter authFilter;

        private RouteHandler(Route route, AuthFilter authFilter) {
            this.route = route;
            this.authFilter = authFilter;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if (!route.method().equalsIgnoreCase(exchange.getRequestMethod())) {
                    writeJson(exchange, 405, ApiResponse.failure("Method not allowed"));
                    return;
                }

                if (route.requiresAuth() && !authFilter.authorize(exchange)) {
                    writeJson(exchange, 401, ApiResponse.failure("Unauthorized"));
                    return;
                }

                route.handle(exchange);
            } catch (RestApiException ex) {
                writeJson(exchange, ex.status(), ApiResponse.failure(ex.getMessage()));
            } catch (Exception ex) {
                logError("REST API handler failed", ex);
                writeJson(exchange, 500, ApiResponse.failure("Internal error"));
            }
        }
    }

    static void writeJson(HttpExchange exchange, int status, ApiResponse response) {
        try {
            byte[] bytes = JsonUtil.toJson(response).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(bytes);
            }
        } catch (IOException ex) {
            exchange.close();
        }
    }

    private void logInfo(String message) {
        if (logger instanceof Logger) {
            ((Logger) logger).info(message);
            return;
        }
        System.out.println(message);
    }

    private void logError(String message, Throwable error) {
        if (logger instanceof Logger) {
            ((Logger) logger).severe(message + " - " + error.getMessage());
            return;
        }
        System.err.println(message);
        error.printStackTrace(System.err);
    }
}

