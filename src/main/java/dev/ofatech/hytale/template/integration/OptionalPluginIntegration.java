package dev.ofatech.hytale.template.integration;

import dev.ofatech.hytale.template.bootstrap.PluginContext;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class OptionalPluginIntegration implements PluginIntegration {
    private boolean enabled;
    private String error;

    @Override
    public final void enable(PluginContext context) {
        Objects.requireNonNull(context, "context");
        if (enabled) {
            return;
        }
        try {
            onEnable(context);
            enabled = true;
            error = null;
        } catch (Exception ex) {
            error = ex.getMessage() == null ? "Enable failed" : ex.getMessage();
            logError(context.logger(), "Integration " + id() + " failed to enable", ex);
        }
    }

    @Override
    public final void disable() {
        if (!enabled) {
            return;
        }
        try {
            onDisable();
        } catch (Exception ex) {
            error = ex.getMessage() == null ? "Disable failed" : ex.getMessage();
        } finally {
            enabled = false;
        }
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final String error() {
        return error;
    }

    protected abstract void onEnable(PluginContext context);

    protected abstract void onDisable();

    protected void logError(Object logger, String message, Throwable error) {
        if (logger instanceof Logger) {
            ((Logger) logger).severe(message + " - " + error.getMessage());
            return;
        }

        if (tryInvoke(logger, "error", new Class<?>[]{String.class, Throwable.class}, new Object[]{message, error})) {
            return;
        }

        System.err.println(message);
        error.printStackTrace(System.err);
    }

    private static boolean tryInvoke(Object target, String methodName, Class<?>[] paramTypes, Object[] args) {
        if (target == null) {
            return false;
        }

        try {
            Method method = target.getClass().getMethod(methodName, paramTypes);
            method.invoke(target, args);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}

