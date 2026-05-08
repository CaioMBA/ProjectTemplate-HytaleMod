package dev.ofatech.hytale.template.commands;

import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.config.ConfigManager;
import dev.ofatech.hytale.template.config.PluginConfig;
import dev.ofatech.hytale.template.events.ConfigReloadedEvent;
import dev.ofatech.hytale.template.messages.Messages;

import java.util.Map;

public final class ReloadCommand extends BaseCommand {
    public ReloadCommand(PluginContext pluginContext) {
        super(pluginContext, "templatereload", "Reload config and messages", CommandPermission.none());
    }

    @Override
    protected CommandResult handle(CommandContext context) {
        ConfigManager configManager = context.pluginContext().configManager();
        PluginConfig oldConfig = configManager.config();

        try {
            PluginConfig newConfig = configManager.reload();
            context.pluginContext().messageManager().load();
            context.pluginContext().featureManager().refresh(context.pluginContext());
            context.pluginContext().eventBus().publish(new ConfigReloadedEvent(oldConfig, newConfig));
            context.sendMessage(Messages.CONFIG_RELOAD_SUCCESS, Map.of());
            return CommandResult.success();
        } catch (Exception ex) {
            context.sendMessage(
                Messages.CONFIG_RELOAD_FAILURE,
                Map.of("error", ex.getMessage() == null ? "unknown" : ex.getMessage())
            );
            return CommandResult.failure();
        }
    }
}

