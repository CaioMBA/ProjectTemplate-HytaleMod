package dev.ofatech.hytale.template.commands;

import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.config.PluginConfig;
import dev.ofatech.hytale.template.messages.Messages;
import dev.ofatech.hytale.template.integration.rest.RestApiServer;

import java.util.Map;

public final class ApiStatusCommand extends BaseCommand {
    public ApiStatusCommand(PluginContext pluginContext) {
        super(pluginContext, "templateapistatus", "Show API status", CommandPermission.none());
    }

    @Override
    protected CommandResult handle(CommandContext context) {
        PluginConfig config = context.pluginContext().configManager().config();
        RestApiServer server = context.pluginContext().restApiServer();

        if (!config.api().enabled() || !config.features().restApi()) {
            context.sendMessage(Messages.API_DISABLED, Map.of());
            return CommandResult.success();
        }

        if (server.isRunning()) {
            context.sendMessage(
                Messages.API_STATUS_RUNNING,
                Map.of(
                    "host", config.api().host(),
                    "port", String.valueOf(config.api().port()),
                    "routes", String.valueOf(server.routeCount())
                )
            );
        } else {
            context.sendMessage(
                Messages.API_STATUS_STOPPED,
                Map.of("host", config.api().host(), "port", String.valueOf(config.api().port()))
            );
        }
        return CommandResult.success();
    }
}

