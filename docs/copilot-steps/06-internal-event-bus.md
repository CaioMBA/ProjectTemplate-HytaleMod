# 06 — Add Internal Event Bus

Goal: add a simple internal event bus for template/plugin-specific events.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/events/
```

Add:

```txt
EventBus.java
SimpleEventBus.java
EventSubscription.java
TemplatePluginReadyEvent.java
ConfigReloadedEvent.java
PlayerDataLoadedEvent.java
EventModule.java
PlayerJoinListener.java
```

## EventBus

Design:

```java
public interface EventBus {
    <T> EventSubscription subscribe(Class<T> eventType, Consumer<T> handler);
    <T> void publish(T event);
}
```

## EventSubscription

Design:

```java
public interface EventSubscription {
    void unsubscribe();
}
```

## SimpleEventBus

Requirements:
- store handlers by event class
- publish synchronously
- one failing handler should not prevent all handlers from running
- log handler failures
- allow unsubscribe

## Events

Examples:

```java
public record TemplatePluginReadyEvent(TemplateApi api) {}
```

If `TemplateApi` does not exist yet, temporarily use `Object api` or create the event later. Prefer compiling cleanly.

```java
public record ConfigReloadedEvent(
    PluginConfig oldConfig,
    PluginConfig newConfig
) {}
```

```java
public record PlayerDataLoadedEvent(
    Object playerId,
    PlayerData data
) {}
```

Use better types if already known.

## EventModule

Responsibilities:
- register Hytale global/player events in one place
- bridge Hytale events into internal event bus when useful
- preserve current player-ready example behavior

## PlayerJoinListener

Move player-ready logic out of the main plugin class and into listener/module code.

Implementation rules:
- Do not replace Hytale's event system globally.
- The internal event bus is only for your plugin abstraction.
- Keep it lightweight and testable.
- Avoid reflection scanning.
- Register subscriptions explicitly.

Acceptance criteria:
- `PluginContext` exposes `EventBus`.
- Internal events can be subscribed and published.
- Existing Hytale player-ready behavior still works.
- `./gradlew build` succeeds.
