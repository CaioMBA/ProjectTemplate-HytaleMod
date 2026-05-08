package dev.ofatech.hytale.template.bootstrap;

public interface LifecycleHooks {
    default void onShutdown(PluginContext context) {
    }

    default void onReload(PluginContext context) {
    }
}

