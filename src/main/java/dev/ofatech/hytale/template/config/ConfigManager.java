package dev.ofatech.hytale.template.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.ofatech.hytale.template.TemplatePlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class ConfigManager {
    private static final String DEFAULT_RESOURCE = "config.default.json";

    private final ClassLoader classLoader;
    private final String pluginName;
    private final Gson gson;
    private final Path configDirectory;
    private final Path configFile;

    private PluginConfig config;
    private ConfigView view;

    public ConfigManager(TemplatePlugin plugin) {
        this(
            Objects.requireNonNull(plugin, "plugin").getClass().getClassLoader(),
            plugin.getClass().getSimpleName()
        );
    }

    ConfigManager(ClassLoader classLoader, String pluginName) {
        this.classLoader = Objects.requireNonNull(classLoader, "classLoader");
        this.pluginName = Objects.requireNonNull(pluginName, "pluginName");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.configDirectory = Paths.get("config").resolve(pluginName);
        this.configFile = configDirectory.resolve("config.json");
    }

    public Path configFile() {
        return configFile;
    }

    public Path configDirectory() {
        return configDirectory;
    }

    public PluginConfig config() {
        if (config == null) {
            throw new ConfigException("Config has not been loaded yet");
        }
        return config;
    }

    public ConfigView view() {
        if (view == null) {
            throw new ConfigException("Config view has not been loaded yet");
        }
        return view;
    }

    public PluginConfig load() {
        return loadInternal();
    }

    public PluginConfig reload() {
        return loadInternal();
    }

    private PluginConfig loadInternal() {
        try {
            Files.createDirectories(configDirectory);
        } catch (IOException ex) {
            throw new ConfigException("Failed to create config directory: " + configDirectory, ex);
        }

        if (Files.notExists(configFile)) {
            copyDefaultConfig();
        }

        PluginConfig loaded = readConfig();
        ConfigValidator.validate(loaded);

        this.config = loaded;
        this.view = new DefaultConfigView(
            loaded.debug(),
            loaded.language(),
            loaded.storage().type(),
            loaded.storage().directory(),
            loaded.api().enabled(),
            loaded.api().host(),
            loaded.api().port(),
            loaded.api().allowReloadEndpoint(),
            loaded.features().exampleCommand(),
            loaded.features().playerWelcomeMessage(),
            loaded.features().restApi()
        );

        return loaded;
    }

    private void copyDefaultConfig() {
        try (InputStream input = classLoader.getResourceAsStream(DEFAULT_RESOURCE)) {
            if (input == null) {
                throw new ConfigException("Missing default config resource: " + DEFAULT_RESOURCE);
            }
            Files.copy(input, configFile);
        } catch (IOException ex) {
            throw new ConfigException("Failed to write default config to " + configFile, ex);
        }
    }

    private PluginConfig readConfig() {
        try (Reader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            PluginConfig loaded = gson.fromJson(reader, PluginConfig.class);
            if (loaded == null) {
                throw new ConfigException("Config file is empty or invalid: " + configFile);
            }
            return loaded;
        } catch (IOException ex) {
            throw new ConfigException("Failed to read config file: " + configFile, ex);
        }
    }

    private record DefaultConfigView(
        boolean debug,
        String language,
        String storageType,
        String storageDirectory,
        boolean apiEnabled,
        String apiHost,
        int apiPort,
        boolean apiAllowReloadEndpoint,
        boolean exampleCommandEnabled,
        boolean playerWelcomeMessageEnabled,
        boolean restApiEnabled
    ) implements ConfigView {
    }
}

