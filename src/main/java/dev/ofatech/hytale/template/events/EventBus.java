package dev.ofatech.hytale.template.events;

import java.util.function.Consumer;

public interface EventBus {
    <T> EventSubscription subscribe(Class<T> eventType, Consumer<T> handler);

    <T> void publish(T event);
}

