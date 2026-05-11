package dev.ofatech.hytale.template.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigValidatorTest {

    @Test
    void validConfigPasses() {
        PluginConfig config = baseConfig();
        assertDoesNotThrow(() -> ConfigValidator.validate(config));
    }

    @Test
    void enabledApiWithDefaultTokenFails() {
        PluginConfig config = new PluginConfig(
            false,
            "en_us",
            new StorageConfig("json", "data"),
            new ApiConfig(true, "127.0.0.1", 8080, "change-me", false),
            new FeatureConfig(true, true, false)
        );

        assertThrows(ConfigException.class, () -> ConfigValidator.validate(config));
    }

    @Test
    void invalidPortFails() {
        PluginConfig config = new PluginConfig(
            false,
            "en_us",
            new StorageConfig("json", "data"),
            new ApiConfig(false, "127.0.0.1", 70000, "token", false),
            new FeatureConfig(true, true, false)
        );

        assertThrows(ConfigException.class, () -> ConfigValidator.validate(config));
    }

    @Test
    void blankLanguageFails() {
        PluginConfig config = new PluginConfig(
            false,
            "   ",
            new StorageConfig("json", "data"),
            new ApiConfig(false, "127.0.0.1", 8080, "token", false),
            new FeatureConfig(true, true, false)
        );

        assertThrows(ConfigException.class, () -> ConfigValidator.validate(config));
    }

    private PluginConfig baseConfig() {
        return new PluginConfig(
            false,
            "en_us",
            new StorageConfig("json", "data"),
            new ApiConfig(false, "127.0.0.1", 8080, "change-me", false),
            new FeatureConfig(true, true, false)
        );
    }
}

