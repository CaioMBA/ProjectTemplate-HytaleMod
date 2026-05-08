package dev.ofatech.hytale.template.bootstrap;

import dev.ofatech.hytale.template.TemplatePlugin;
import dev.ofatech.hytale.template.api.TemplateApi;
import dev.ofatech.hytale.template.api.TemplateApiProvider;
import dev.ofatech.hytale.template.api.events.ApiReadyEvent;
import dev.ofatech.hytale.template.api.internal.TemplateApiImpl;
import dev.ofatech.hytale.template.config.ConfigManager;
import dev.ofatech.hytale.template.config.PluginConfig;
import dev.ofatech.hytale.template.data.JsonDataStore;
import dev.ofatech.hytale.template.data.PlayerData;
import dev.ofatech.hytale.template.data.PlayerDataService;
import dev.ofatech.hytale.template.data.Repository;
import dev.ofatech.hytale.template.events.EventBus;
import dev.ofatech.hytale.template.events.SimpleEventBus;
import dev.ofatech.hytale.template.events.TemplatePluginReadyEvent;
import dev.ofatech.hytale.template.features.ExampleCommandFeature;
import dev.ofatech.hytale.template.features.FeatureManager;
import dev.ofatech.hytale.template.features.PlayerWelcomeFeature;
import dev.ofatech.hytale.template.features.RestApiFeature;
import dev.ofatech.hytale.template.integration.DiscordWebhookIntegration;
import dev.ofatech.hytale.template.integration.IntegrationRegistry;
import dev.ofatech.hytale.template.integration.rest.RestApiServer;
import dev.ofatech.hytale.template.messages.MessageManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class PluginBootstrap {
    private final TemplatePlugin plugin;
    private final List<PluginModule> modules = new ArrayList<>();

    private PluginBootstrap(TemplatePlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
    }

    public static PluginBootstrap create(TemplatePlugin plugin) {
        return new PluginBootstrap(plugin);
    }

    public PluginBootstrap withModule(PluginModule module) {
        modules.add(Objects.requireNonNull(module, "module"));
        return this;
    }

    public PluginContext start() {
        ConfigManager configManager = new ConfigManager(plugin);
        PluginConfig config = configManager.load();
        boolean debug = config.debug();

        JsonDataStore dataStore = new JsonDataStore(
            configManager.configDirectory().resolve(config.storage().directory())
        );
        Repository<PlayerData> playerRepository = new Repository<>("players", PlayerData.class, dataStore);
        PlayerDataService playerDataService = new PlayerDataService(playerRepository);

        MessageManager messageManager = new MessageManager(configManager.configDirectory(), config.language());
        messageManager.load();

        EventBus eventBus = new SimpleEventBus(plugin.getLogger());

        IntegrationRegistry integrationRegistry = new IntegrationRegistry();
        integrationRegistry.register(new DiscordWebhookIntegration());

        FeatureManager featureManager = new FeatureManager(configManager);
        featureManager.register(new ExampleCommandFeature());
        featureManager.register(new PlayerWelcomeFeature());
        featureManager.register(new RestApiFeature());

        RestApiServer restApiServer = new RestApiServer(
            configManager,
            playerDataService,
            eventBus,
            plugin.getLogger()
        );

        PluginContext context = new PluginContext(
            plugin,
            configManager,
            messageManager,
            eventBus,
            playerDataService,
            integrationRegistry,
            restApiServer,
            featureManager
        );

        restApiServer.bindContext(context);

        featureManager.enableConfigured(context);

        for (PluginModule module : modules) {
            if (debug) {
                logInfo(context, "Setting up module: " + module.name());
            }
            try {
                module.setup(context);
                if (debug) {
                    logInfo(context, "Module ready: " + module.name());
                }
            } catch (RuntimeException ex) {
                logError(context, "Module failed: " + module.name(), ex);
                throw ex;
            } catch (Exception ex) {
                logError(context, "Module failed: " + module.name(), ex);
                throw new IllegalStateException("Module failed: " + module.name(), ex);
            }
        }

        integrationRegistry.enableAvailable(context);

        // REST API startup is controlled by feature toggles.

        TemplateApi api = new TemplateApiImpl(
            plugin.getClass().getSimpleName(),
            "0.1.0",
            configManager,
            playerDataService
        );
        TemplateApiProvider.register(api);
        eventBus.publish(new ApiReadyEvent(api));
        eventBus.publish(new TemplatePluginReadyEvent(api));

        return context;
    }

    private static void logInfo(PluginContext context, String message) {
        Object logger = context.logger();
        if (logger instanceof Logger) {
            ((Logger) logger).info(message);
            return;
        }

        if (!tryInvoke(logger, "info", new Class<?>[]{String.class}, new Object[]{message})) {
            System.out.println(message);
        }
    }

    private static void logError(PluginContext context, String message, Throwable error) {
        Object logger = context.logger();
        if (logger instanceof Logger) {
            ((Logger) logger).severe(message + " - " + error.getMessage());
            return;
        }

        if (tryInvoke(logger, "error", new Class<?>[]{String.class, Throwable.class}, new Object[]{message, error})) {
            return;
        }

        if (tryInvoke(logger, "error", new Class<?>[]{String.class}, new Object[]{message})) {
            return;
        }

        System.err.println(message);
        error.printStackTrace(System.err);
    }

    private static boolean tryInvoke(Object target, String methodName, Class<?>[] paramTypes, Object[] args) {
        if (target == null) {
            return false;
        }

        try {
            Method method = target.getClass().getMethod(methodName, paramTypes);
            method.invoke(target, args);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}



