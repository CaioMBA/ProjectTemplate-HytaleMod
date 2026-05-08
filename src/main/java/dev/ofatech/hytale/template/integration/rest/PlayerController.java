package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;
import dev.ofatech.hytale.template.api.dto.PlayerDataDto;
import dev.ofatech.hytale.template.data.PlayerData;
import dev.ofatech.hytale.template.data.PlayerDataService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class PlayerController implements Route {
    private final PlayerDataService playerDataService;

    public PlayerController(PlayerDataService playerDataService) {
        this.playerDataService = playerDataService;
    }

    @Override
    public String method() {
        return "GET";
    }

    @Override
    public String path() {
        return "/players";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        if (path == null || !path.startsWith("/players/")) {
            throw new RestApiException(400, "Player id is required");
        }

        String rawId = path.substring("/players/".length());
        if (rawId.isBlank()) {
            throw new RestApiException(400, "Player id is required");
        }

        String playerId = URLDecoder.decode(rawId, StandardCharsets.UTF_8);
        PlayerData data = playerDataService.find(playerId).orElse(null);
        if (data == null) {
            throw new RestApiException(404, "Player not found");
        }

        PlayerDataDto dto = new PlayerDataDto(
            data.schemaVersion(),
            data.playerId(),
            data.firstJoin(),
            data.lastJoin(),
            data.flags()
        );

        RestApiServer.writeJson(exchange, 200, ApiResponse.success(dto));
    }
}

