package dev.ofatech.hytale.template.config;

import java.util.Objects;

public final class ConfigValidator {
    private ConfigValidator() {
    }

    public static void validate(PluginConfig config) {
        if (config == null) {
            throw new ConfigException("Config is missing or unreadable");
        }

        if (isBlank(config.language())) {
            throw new ConfigException("Language must not be blank");
        }

        StorageConfig storage = Objects.requireNonNull(config.storage(), "Storage config is required");
        if (isBlank(storage.type())) {
            throw new ConfigException("Storage type must not be blank");
        }

        if (isInvalidDirectory(storage.directory())) {
            throw new ConfigException("Storage directory must be a relative path without '..'");
        }

        ApiConfig api = Objects.requireNonNull(config.api(), "API config is required");
        if (api.port() < 1 || api.port() > 65535) {
            throw new ConfigException("API port must be between 1 and 65535");
        }

        if (api.enabled() && isDefaultToken(api.token())) {
            throw new ConfigException("API token must be changed before enabling the API");
        }

        Objects.requireNonNull(config.features(), "Feature config is required");
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static boolean isDefaultToken(String token) {
        return isBlank(token) || "change-me".equalsIgnoreCase(token.trim());
    }

    private static boolean isInvalidDirectory(String directory) {
        if (isBlank(directory)) {
            return true;
        }

        String trimmed = directory.trim();
        return trimmed.contains("..") || trimmed.startsWith("/") || trimmed.startsWith("\\");
    }
}

