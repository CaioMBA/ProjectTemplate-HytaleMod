package dev.ofatech.hytale.template.integration;

public record IntegrationStatus(
    String id,
    boolean available,
    boolean enabled,
    String error
) {
}

