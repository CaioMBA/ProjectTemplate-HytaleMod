package dev.ofatech.hytale.template.commands;

import dev.ofatech.hytale.template.api.TemplateApiProvider;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.config.ConfigView;
import dev.ofatech.hytale.template.integration.IntegrationStatus;
import dev.ofatech.hytale.template.integration.rest.RestApiServer;
import dev.ofatech.hytale.template.features.FeatureStatus;
import dev.ofatech.hytale.template.messages.Messages;

import java.util.Map;

public final class DebugCommand extends BaseCommand {
    public DebugCommand(PluginContext pluginContext) {
        super(pluginContext, "templatedebug", "Show debug information", CommandPermission.none());
    }

    @Override
    protected CommandResult handle(CommandContext context) {
        String name = TemplateApiProvider.isAvailable()
            ? TemplateApiProvider.get().pluginId()
            : context.pluginContext().plugin().getClass().getSimpleName();
        String version = TemplateApiProvider.isAvailable()
            ? TemplateApiProvider.get().version()
            : "unknown";

        ConfigView config = context.pluginContext().configManager().view();

        context.sendMessage(
            Messages.COMMAND_DEBUG_HEADER,
            Map.of("name", name, "version", version)
        );
        context.sendMessage(
            Messages.COMMAND_DEBUG_FEATURES,
            Map.of(
                "exampleCommand", String.valueOf(config.exampleCommandEnabled()),
                "playerWelcome", String.valueOf(config.playerWelcomeMessageEnabled()),
                "restApi", String.valueOf(config.restApiEnabled())
            )
        );
        context.sendMessage(
            Messages.COMMAND_DEBUG_STORAGE,
            Map.of("storage", config.storageType())
        );
        RestApiServer server = context.pluginContext().restApiServer();
        context.sendMessage(
            Messages.COMMAND_DEBUG_API,
            Map.of(
                "enabled", String.valueOf(config.apiEnabled()),
                "running", String.valueOf(server.isRunning()),
                "routes", String.valueOf(server.routeCount())
            )
        );
        for (IntegrationStatus status : context.pluginContext().integrationRegistry().statuses()) {
            context.sendMessage(
                Messages.COMMAND_DEBUG_INTEGRATION,
                Map.of(
                    "id", status.id(),
                    "available", String.valueOf(status.available()),
                    "enabled", String.valueOf(status.enabled()),
                    "error", status.error() == null ? "" : status.error()
                )
            );
        }

        for (FeatureStatus status : context.pluginContext().featureManager().statuses()) {
            context.sendMessage(
                Messages.COMMAND_DEBUG_FEATURE_STATUS,
                Map.of(
                    "id", status.id(),
                    "enabled", String.valueOf(status.enabled()),
                    "default", String.valueOf(status.defaultEnabled()),
                    "error", status.error() == null ? "" : status.error()
                )
            );
        }
        return CommandResult.success();
    }
}

