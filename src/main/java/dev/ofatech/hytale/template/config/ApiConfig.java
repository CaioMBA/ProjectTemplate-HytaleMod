package dev.ofatech.hytale.template.config;

public record ApiConfig(
    boolean enabled,
    String host,
    int port,
    String token,
    boolean allowReloadEndpoint
) {
}

