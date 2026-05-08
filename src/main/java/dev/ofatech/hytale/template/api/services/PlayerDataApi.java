package dev.ofatech.hytale.template.api.services;

import dev.ofatech.hytale.template.api.dto.PlayerDataDto;

import java.util.Optional;

public interface PlayerDataApi {
    Optional<PlayerDataDto> findPlayerData(String playerId);
}

