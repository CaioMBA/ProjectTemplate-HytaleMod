package dev.ofatech.hytale.template.api.internal;

import dev.ofatech.hytale.template.api.ConfigView;
import dev.ofatech.hytale.template.api.TemplateApi;
import dev.ofatech.hytale.template.api.dto.PlayerDataDto;
import dev.ofatech.hytale.template.api.services.PlayerDataApi;
import dev.ofatech.hytale.template.config.ConfigManager;
import dev.ofatech.hytale.template.data.PlayerData;
import dev.ofatech.hytale.template.data.PlayerDataService;

import java.util.Objects;
import java.util.Optional;

public final class TemplateApiImpl implements TemplateApi {
    private final String pluginId;
    private final String version;
    private final ConfigView configView;
    private final PlayerDataApi playerDataApi;

    public TemplateApiImpl(
        String pluginId,
        String version,
        ConfigManager configManager,
        PlayerDataService playerDataService
    ) {
        this.pluginId = Objects.requireNonNull(pluginId, "pluginId");
        this.version = Objects.requireNonNull(version, "version");
        this.configView = new SafeConfigView(configManager.view());
        this.playerDataApi = new PlayerDataApiImpl(playerDataService);
    }

    @Override
    public String pluginId() {
        return pluginId;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public ConfigView config() {
        return configView;
    }

    @Override
    public PlayerDataApi players() {
        return playerDataApi;
    }

    private static final class SafeConfigView implements ConfigView {
        private final dev.ofatech.hytale.template.config.ConfigView internal;

        private SafeConfigView(dev.ofatech.hytale.template.config.ConfigView internal) {
            this.internal = Objects.requireNonNull(internal, "internal");
        }

        @Override
        public boolean debug() {
            return internal.debug();
        }

        @Override
        public String language() {
            return internal.language();
        }

        @Override
        public String storageType() {
            return internal.storageType();
        }

        @Override
        public boolean apiEnabled() {
            return internal.apiEnabled();
        }

        @Override
        public boolean exampleCommandEnabled() {
            return internal.exampleCommandEnabled();
        }

        @Override
        public boolean playerWelcomeMessageEnabled() {
            return internal.playerWelcomeMessageEnabled();
        }

        @Override
        public boolean restApiEnabled() {
            return internal.restApiEnabled();
        }
    }

    private static final class PlayerDataApiImpl implements PlayerDataApi {
        private final PlayerDataService playerDataService;

        private PlayerDataApiImpl(PlayerDataService playerDataService) {
            this.playerDataService = Objects.requireNonNull(playerDataService, "playerDataService");
        }

        @Override
        public Optional<PlayerDataDto> findPlayerData(String playerId) {
            return playerDataService.find(playerId).map(PlayerDataApiImpl::toDto);
        }

        private static PlayerDataDto toDto(PlayerData data) {
            return new PlayerDataDto(
                data.schemaVersion(),
                data.playerId(),
                data.firstJoin(),
                data.lastJoin(),
                data.flags()
            );
        }
    }
}

