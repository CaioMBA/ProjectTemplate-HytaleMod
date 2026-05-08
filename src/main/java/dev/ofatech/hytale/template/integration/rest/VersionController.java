package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;
import dev.ofatech.hytale.template.api.TemplateApiProvider;

import java.util.Map;

public final class VersionController implements Route {
    @Override
    public String method() {
        return "GET";
    }

    @Override
    public String path() {
        return "/version";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String name = TemplateApiProvider.isAvailable()
            ? TemplateApiProvider.get().pluginId()
            : "template";
        String version = TemplateApiProvider.isAvailable()
            ? TemplateApiProvider.get().version()
            : "unknown";

        RestApiServer.writeJson(
            exchange,
            200,
            ApiResponse.success(Map.of("name", name, "version", version))
        );
    }
}

