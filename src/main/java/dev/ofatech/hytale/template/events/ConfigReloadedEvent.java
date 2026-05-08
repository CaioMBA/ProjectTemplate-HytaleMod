package dev.ofatech.hytale.template.events;

import dev.ofatech.hytale.template.config.PluginConfig;

public record ConfigReloadedEvent(PluginConfig oldConfig, PluginConfig newConfig) {
}

