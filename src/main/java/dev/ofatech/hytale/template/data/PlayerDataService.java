package dev.ofatech.hytale.template.data;

import dev.ofatech.hytale.template.util.Preconditions;
import dev.ofatech.hytale.template.util.TimeUtil;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class PlayerDataService {
    private static final int CURRENT_SCHEMA = 1;

    private final Repository<PlayerData> repository;

    public PlayerDataService(Repository<PlayerData> repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public PlayerData loadOrCreate(String playerId) {
        Preconditions.checkNotBlank(playerId, "playerId");

        PlayerData existing = repository.load(playerId).orElse(null);
        if (existing == null) {
            PlayerData created = createDefault(playerId, TimeUtil.now());
            repository.save(playerId, created);
            return created;
        }

        PlayerData updated = updateLastJoin(existing, TimeUtil.now());
        repository.save(playerId, updated);
        return updated;
    }

    public Optional<PlayerData> find(String playerId) {
        Preconditions.checkNotBlank(playerId, "playerId");
        return repository.load(playerId);
    }

    private PlayerData createDefault(String playerId, Instant now) {
        return new PlayerData(
            CURRENT_SCHEMA,
            playerId,
            now,
            now,
            Map.of()
        );
    }

    private PlayerData updateLastJoin(PlayerData existing, Instant now) {
        Map<String, String> flags = existing.flags() == null
            ? Map.of()
            : Map.copyOf(existing.flags());

        return new PlayerData(
            existing.schemaVersion(),
            existing.playerId(),
            existing.firstJoin(),
            now,
            flags
        );
    }
}

