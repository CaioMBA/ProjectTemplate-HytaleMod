package dev.ofatech.hytale.template.features;

import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.commands.TemplateCommand;

public final class ExampleCommandFeature implements Feature {
    public static final String ID = "example-command";
    private boolean registered;

    @Override
    public String id() {
        return ID;
    }

    @Override
    public boolean enabledByDefault() {
        return true;
    }

    @Override
    public void enable(PluginContext context) {
        if (registered) {
            return;
        }
        context.plugin().getCommandRegistry().registerCommand(
            new TemplateCommand(
                "template",
                "A template command",
                context.messageManager(),
                context.plugin().getClass().getSimpleName()
            )
        );
        registered = true;
    }

    @Override
    public void disable(PluginContext context) {
        // Command unregister is not supported by the current API.
    }
}


