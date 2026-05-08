package dev.ofatech.hytale.template.events;

import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.bootstrap.PluginModule;

public class EventModule implements PluginModule {
    @Override
    public String name() {
        return "events";
    }

    @Override
    public void setup(PluginContext context) {
        PlayerJoinListener listener = new PlayerJoinListener(context);
        context.plugin().getEventRegistry().registerGlobal(
            com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent.class,
            listener::onPlayerReady
        );
    }
}

