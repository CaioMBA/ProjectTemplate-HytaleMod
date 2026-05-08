package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.config.PluginConfig;
import dev.ofatech.hytale.template.events.ConfigReloadedEvent;

import java.util.Map;

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
        PluginConfig oldConfig = context.configManager().config();
        PluginConfig newConfig = context.configManager().reload();
        context.messageManager().load();
        context.featureManager().refresh(context);
        context.eventBus().publish(new ConfigReloadedEvent(oldConfig, newConfig));

        RestApiServer.writeJson(exchange, 200, ApiResponse.success(Map.of("reloaded", true)));
    }
}

