package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;

public interface Route {
    String method();

    String path();

    boolean requiresAuth();

    void handle(HttpExchange exchange) throws Exception;
}

