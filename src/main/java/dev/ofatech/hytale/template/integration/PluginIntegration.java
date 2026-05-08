package dev.ofatech.hytale.template.integration;

import dev.ofatech.hytale.template.bootstrap.PluginContext;

public interface PluginIntegration {
    String id();

    boolean isAvailable(PluginContext context);

    void enable(PluginContext context);

    void disable();
}

