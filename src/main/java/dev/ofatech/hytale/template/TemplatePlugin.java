package dev.ofatech.hytale.template;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.ofatech.hytale.template.bootstrap.PluginBootstrap;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.commands.CommandModule;
import dev.ofatech.hytale.template.events.EventModule;

import javax.annotation.Nonnull;

public class TemplatePlugin extends JavaPlugin {

    private PluginContext context;

    public TemplatePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Replace these example modules with your plugin-specific features.
        this.context = PluginBootstrap.create(this)
            .withModule(new CommandModule())
            .withModule(new EventModule())
            .start();
    }
}
