package dev.ofatech.hytale.template.api;

import dev.ofatech.hytale.template.api.services.PlayerDataApi;

public interface TemplateApi {
    String pluginId();

    String version();

    ConfigView config();

    PlayerDataApi players();
}

