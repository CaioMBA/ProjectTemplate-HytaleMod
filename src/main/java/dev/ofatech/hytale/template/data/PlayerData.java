package dev.ofatech.hytale.template.data;

import java.time.Instant;
import java.util.Map;

public record PlayerData(
    int schemaVersion,
    String playerId,
    Instant firstJoin,
    Instant lastJoin,
    Map<String, String> flags
) implements SchemaVersioned {
}

