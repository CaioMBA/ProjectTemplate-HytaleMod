package net.ofatech.hytale.template;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import net.ofatech.hytale.template.platform.command.ExampleCommand;
import net.ofatech.hytale.template.platform.event.ExampleEvent;

import javax.annotation.Nonnull;

public class TemplatePlugin extends JavaPlugin {

    public TemplatePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);
    }
}
