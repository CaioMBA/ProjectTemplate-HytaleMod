package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Objects;

public final class AuthFilter {
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    public AuthFilter(TokenProvider tokenProvider) {
        this.tokenProvider = Objects.requireNonNull(tokenProvider, "tokenProvider");
    }

    public boolean authorize(HttpExchange exchange) {
        String token = tokenProvider.token();
        if (token == null || token.isBlank()) {
            return false;
        }

        List<String> values = exchange.getRequestHeaders().get(HEADER);
        if (values == null || values.isEmpty()) {
            return false;
        }

        for (String value : values) {
            if (value != null && value.startsWith(PREFIX)) {
                String provided = value.substring(PREFIX.length()).trim();
                return token.equals(provided);
            }
        }

        return false;
    }

    public interface TokenProvider {
        String token();
    }
}

