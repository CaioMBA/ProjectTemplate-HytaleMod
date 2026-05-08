package dev.ofatech.hytale.template.data;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerDataServiceTest {

    @Test
    void createsDefaultPlayerData() throws Exception {
        Path tempDir = Files.createTempDirectory("playerdata-test");
        JsonDataStore store = new JsonDataStore(tempDir);
        Repository<PlayerData> repository = new Repository<>("players", PlayerData.class, store);
        PlayerDataService service = new PlayerDataService(repository);

        PlayerData data = service.loadOrCreate("player-1");

        assertEquals("player-1", data.playerId());
        assertEquals(1, data.schemaVersion());
        assertNotNull(data.firstJoin());
        assertNotNull(data.lastJoin());
        assertTrue(data.flags().isEmpty());
    }
}

