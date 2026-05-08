package dev.ofatech.hytale.template.features;

import dev.ofatech.hytale.template.bootstrap.PluginContext;

public interface Feature {
    String id();

    boolean enabledByDefault();

    void enable(PluginContext context);

    void disable(PluginContext context);
}

