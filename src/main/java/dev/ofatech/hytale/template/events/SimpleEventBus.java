package dev.ofatech.hytale.template.events;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.logging.Logger;

public final class SimpleEventBus implements EventBus {
    private final Map<Class<?>, List<Consumer<?>>> handlers = new ConcurrentHashMap<>();
    private final Object logger;

    public SimpleEventBus(Object logger) {
        this.logger = logger;
    }

    @Override
    public <T> EventSubscription subscribe(Class<T> eventType, Consumer<T> handler) {
        Objects.requireNonNull(eventType, "eventType");
        Objects.requireNonNull(handler, "handler");
        List<Consumer<?>> list = handlers.computeIfAbsent(eventType, key -> new CopyOnWriteArrayList<>());
        list.add(handler);
        return () -> list.remove(handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void publish(T event) {
        if (event == null) {
            return;
        }
        List<Consumer<?>> list = handlers.get(event.getClass());
        if (list == null || list.isEmpty()) {
            return;
        }
        for (Consumer<?> consumer : list) {
            Consumer<T> handler = (Consumer<T>) consumer;
            try {
                handler.accept(event);
            } catch (Exception ex) {
                logError("Event handler failed for " + event.getClass().getSimpleName(), ex);
            }
        }
    }

    private void logError(String message, Throwable error) {
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

