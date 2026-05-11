package dev.ofatech.hytale.template.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void loadCreatesDefaultConfigAndView() throws Exception {
        String originalUserDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        try {
            ConfigManager manager = new ConfigManager(
                ConfigManagerTest.class.getClassLoader(),
                "TemplatePlugin"
            );

            PluginConfig config = manager.load();

            assertNotNull(config);
            assertTrue(Files.exists(manager.configFile()));
            assertNotNull(manager.view());
            assertEquals("en_us", config.language());
        } finally {
            System.setProperty("user.dir", originalUserDir);
        }
    }
}


