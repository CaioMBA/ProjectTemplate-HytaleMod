package dev.ofatech.hytale.template.features;

import dev.ofatech.hytale.template.bootstrap.PluginContext;

public final class RestApiFeature implements Feature {
    public static final String ID = "rest-api";

    @Override
    public String id() {
        return ID;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }

    @Override
    public void enable(PluginContext context) {
        context.restApiServer().start();
    }

    @Override
    public void disable(PluginContext context) {
        context.restApiServer().stop();
    }
}

