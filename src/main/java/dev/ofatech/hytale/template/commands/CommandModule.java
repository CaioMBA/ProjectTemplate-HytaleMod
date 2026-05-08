package dev.ofatech.hytale.template.commands;

import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.bootstrap.PluginModule;

public class CommandModule implements PluginModule {
    @Override
    public String name() {
        return "commands";
    }

    @Override
    public void setup(PluginContext context) {
        context.plugin().getCommandRegistry().registerCommand(new VersionCommand(context));
        context.plugin().getCommandRegistry().registerCommand(new ReloadCommand(context));
        context.plugin().getCommandRegistry().registerCommand(new DebugCommand(context));
        context.plugin().getCommandRegistry().registerCommand(new ApiStatusCommand(context));
    }
}

