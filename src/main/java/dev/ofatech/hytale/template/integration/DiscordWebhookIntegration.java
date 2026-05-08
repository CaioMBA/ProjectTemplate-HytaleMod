package dev.ofatech.hytale.template.integration;

import dev.ofatech.hytale.template.bootstrap.PluginContext;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

public final class DiscordWebhookIntegration extends OptionalPluginIntegration {
    private static final String ID = "discord-webhook";

    @Override
    public String id() {
        return ID;
    }

    @Override
    public boolean isAvailable(PluginContext context) {
        return false;
    }

    @Override
    protected void onEnable(PluginContext context) {
        // Disabled by default; configure a real endpoint before enabling.
        WebhookClient client = new WebhookClient(Duration.ofSeconds(5));
        client.sendJson(URI.create("http://127.0.0.1"), Map.of("content", "Template integration enabled"));
    }

    @Override
    protected void onDisable() {
    }
}

