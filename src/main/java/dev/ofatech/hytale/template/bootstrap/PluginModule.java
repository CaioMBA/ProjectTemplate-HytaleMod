package dev.ofatech.hytale.template.bootstrap;

public interface PluginModule {
    String name();

    void setup(PluginContext context);

    default void shutdown(PluginContext context) {
    }

    default void reload(PluginContext context) {
    }
}

