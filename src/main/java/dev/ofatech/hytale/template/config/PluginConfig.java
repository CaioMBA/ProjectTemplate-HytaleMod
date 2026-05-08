package dev.ofatech.hytale.template.config;

public record PluginConfig(
    boolean debug,
    String language,
    StorageConfig storage,
    ApiConfig api,
    FeatureConfig features
) {
}

