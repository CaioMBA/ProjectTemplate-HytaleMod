package dev.ofatech.hytale.template.events;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.features.PlayerWelcomeFeature;

import java.util.Objects;

public final class PlayerJoinListener {
    private final PluginContext context;

    public PlayerJoinListener(PluginContext context) {
        this.context = Objects.requireNonNull(context, "context");
    }

    public void onPlayerReady(PlayerReadyEvent event) {
        if (!context.featureManager().isEnabled(PlayerWelcomeFeature.ID)) {
            return;
        }
        Player player = event.getPlayer();
        player.sendMessage(Message.raw("Welcome " + player.getDisplayName()));
    }
}

