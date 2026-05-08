package dev.ofatech.hytale.template.api.dto;

import java.time.Instant;
import java.util.Map;

public record PlayerDataDto(
    int schemaVersion,
    String playerId,
    Instant firstJoin,
    Instant lastJoin,
    Map<String, String> flags
) {
    public PlayerDataDto {
        flags = flags == null ? Map.of() : Map.copyOf(flags);
    }
}

