package dev.ofatech.hytale.template.commands;

import dev.ofatech.hytale.template.api.TemplateApiProvider;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.messages.Messages;

import java.util.Map;

public final class VersionCommand extends BaseCommand {
    public VersionCommand(PluginContext pluginContext) {
        super(pluginContext, "templateversion", "Show template version", CommandPermission.none());
    }

    @Override
    protected CommandResult handle(CommandContext context) {
        String version = TemplateApiProvider.isAvailable()
            ? TemplateApiProvider.get().version()
            : "unknown";
        String name = TemplateApiProvider.isAvailable()
            ? TemplateApiProvider.get().pluginId()
            : context.pluginContext().plugin().getClass().getSimpleName();

        context.sendMessage(
            Messages.COMMAND_VERSION,
            Map.of(
                "name", name,
                "version", version,
                "java", System.getProperty("java.version", "unknown"),
                "debug", String.valueOf(context.pluginContext().configManager().view().debug())
            )
        );
        return CommandResult.success();
    }
}

