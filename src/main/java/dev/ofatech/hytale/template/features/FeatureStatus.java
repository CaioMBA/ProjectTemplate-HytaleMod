package dev.ofatech.hytale.template.features;

public record FeatureStatus(
    String id,
    boolean enabled,
    boolean defaultEnabled,
    String error
) {
}

