package dev.ofatech.hytale.template.events;

import dev.ofatech.hytale.template.data.PlayerData;

public record PlayerDataLoadedEvent(String playerId, PlayerData data) {
}

