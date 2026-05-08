package dev.ofatech.hytale.template.integration.rest;

import com.sun.net.httpserver.HttpExchange;
import dev.ofatech.hytale.template.config.ConfigView;
import dev.ofatech.hytale.template.features.FeatureManager;
import dev.ofatech.hytale.template.features.FeatureStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ConfigController implements Route {
    private final ConfigView configView;
    private final FeatureManager featureManager;

    public ConfigController(ConfigView configView, FeatureManager featureManager) {
        this.configView = configView;
        this.featureManager = featureManager;
    }

    @Override
    public String method() {
        return "GET";
    }

    @Override
    public String path() {
        return "/config/public";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void handle(HttpExchange exchange) {
        List<Map<String, Object>> featureStatuses = new ArrayList<>();
        for (FeatureStatus status : featureManager.statuses()) {
            featureStatuses.add(
                Map.of(
                    "id", status.id(),
                    "enabled", status.enabled(),
                    "defaultEnabled", status.defaultEnabled(),
                    "error", status.error() == null ? "" : status.error()
                )
            );
        }

        RestApiServer.writeJson(
            exchange,
            200,
            ApiResponse.success(
                Map.of(
                    "debug", configView.debug(),
                    "language", configView.language(),
                    "storageType", configView.storageType(),
                    "apiEnabled", configView.apiEnabled(),
                    "exampleCommandEnabled", configView.exampleCommandEnabled(),
                    "playerWelcomeMessageEnabled", configView.playerWelcomeMessageEnabled(),
                    "restApiEnabled", configView.restApiEnabled(),
                    "featureStatus", featureStatuses
                )
            )
        );
    }
}

