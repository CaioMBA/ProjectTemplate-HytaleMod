package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public final class HealthController implements Route {
    @Override
    public String method() {
        return "GET";
    }

    @Override
    public String path() {
        return "/health";
    }

    @Override
    public boolean requiresAuth() {
        return false;
    }

    @Override
    public void handle(HttpExchange exchange) {
        RestApiServer.writeJson(exchange, 200, ApiResponse.success(Map.of("status", "ok")));
    }
}

