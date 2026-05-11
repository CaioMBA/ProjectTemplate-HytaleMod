package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.config.PluginConfig;
import dev.ofatech.hytale.template.events.ConfigReloadedEvent;

import java.util.Map;
import java.util.logging.Logger;

public final class AdminController implements Route {
    private final PluginContext context;

    public AdminController(PluginContext context) {
        this.context = context;
    }

    @Override
    public String method() {
        return "POST";
    }

    @Override
    public String path() {
        return "/reload";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            PluginConfig oldConfig = context.configManager().config();
            PluginConfig newConfig = context.configManager().reload();
            context.messageManager().load();
            context.featureManager().refresh(context);
            context.eventBus().publish(new ConfigReloadedEvent(oldConfig, newConfig));
            logInfo(context.logger(), "REST config reload completed");

            RestApiServer.writeJson(exchange, 200, ApiResponse.success(Map.of("reloaded", true)));
        } catch (Exception ex) {
            logError(context.logger(), "REST config reload failed", ex);
            throw ex;
        }
    }

    private void logInfo(Object logger, String message) {
        if (logger instanceof Logger) {
            ((Logger) logger).info(message);
        } else {
            System.out.println(message);
        }
    }

    private void logError(Object logger, String message, Exception ex) {
        if (logger instanceof Logger) {
            ((Logger) logger).severe(message + " - " + ex.getMessage());
        } else {
            System.err.println(message);
        }
    }
}

