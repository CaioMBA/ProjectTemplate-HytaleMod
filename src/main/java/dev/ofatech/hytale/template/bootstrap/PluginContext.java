package dev.ofatech.hytale.template.bootstrap;

import dev.ofatech.hytale.template.TemplatePlugin;
import dev.ofatech.hytale.template.config.ConfigManager;
import dev.ofatech.hytale.template.data.PlayerDataService;
import dev.ofatech.hytale.template.events.EventBus;
import dev.ofatech.hytale.template.features.FeatureManager;
import dev.ofatech.hytale.template.integration.IntegrationRegistry;
import dev.ofatech.hytale.template.integration.rest.RestApiServer;
import dev.ofatech.hytale.template.messages.MessageManager;

import java.util.Objects;

public final class PluginContext {
    private final TemplatePlugin plugin;
    private final Object logger;
    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final EventBus eventBus;
    private final PlayerDataService playerDataService;
    private final IntegrationRegistry integrationRegistry;
    private final RestApiServer restApiServer;
    private final FeatureManager featureManager;

    public PluginContext(
        TemplatePlugin plugin,
        ConfigManager configManager,
        MessageManager messageManager,
        EventBus eventBus,
        PlayerDataService playerDataService,
        IntegrationRegistry integrationRegistry,
        RestApiServer restApiServer,
        FeatureManager featureManager
    ) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.logger = Objects.requireNonNull(plugin.getLogger(), "logger");
        this.configManager = Objects.requireNonNull(configManager, "configManager");
        this.messageManager = Objects.requireNonNull(messageManager, "messageManager");
        this.eventBus = Objects.requireNonNull(eventBus, "eventBus");
        this.playerDataService = Objects.requireNonNull(playerDataService, "playerDataService");
        this.integrationRegistry = Objects.requireNonNull(integrationRegistry, "integrationRegistry");
        this.restApiServer = Objects.requireNonNull(restApiServer, "restApiServer");
        this.featureManager = Objects.requireNonNull(featureManager, "featureManager");
    }

    public TemplatePlugin plugin() {
        return plugin;
    }

    public Object logger() {
        return logger;
    }

    public ConfigManager configManager() {
        return configManager;
    }

    public MessageManager messageManager() {
        return messageManager;
    }

    public EventBus eventBus() {
        return eventBus;
    }

    public PlayerDataService playerDataService() {
        return playerDataService;
    }

    public IntegrationRegistry integrationRegistry() {
        return integrationRegistry;
    }

    public RestApiServer restApiServer() {
        return restApiServer;
    }

    public FeatureManager featureManager() {
        return featureManager;
    }
}



