package dev.ofatech.hytale.template.features;

import dev.ofatech.hytale.template.bootstrap.PluginContext;

public final class PlayerWelcomeFeature implements Feature {
    public static final String ID = "player-welcome";

    @Override
    public String id() {
        return ID;
    }

    @Override
    public boolean enabledByDefault() {
        return true;
    }

    @Override
    public void enable(PluginContext context) {
        // Handled by listener checks.
    }

    @Override
    public void disable(PluginContext context) {
        // Handled by listener checks.
    }
}

