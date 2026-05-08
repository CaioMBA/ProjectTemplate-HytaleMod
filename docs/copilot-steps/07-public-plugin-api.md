# 07 — Add Public Java API for Other Plugins

Goal: expose a stable Java API that other Hytale plugins can use.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/api/
```

Add:

```txt
TemplateApi.java
TemplateApiProvider.java
ConfigView.java
ApiException.java
services/
  PlayerDataApi.java
dto/
  PlayerDataDto.java
events/
  ApiReadyEvent.java
```

## TemplateApi

Design:

```java
public interface TemplateApi {
    String pluginId();
    String version();

    ConfigView config();
    PlayerDataApi players();
}
```

If your internal event bus is stable enough, optionally expose it through a limited API interface, not the raw internal implementation.

## TemplateApiProvider

Design:

```java
public final class TemplateApiProvider {
    private static TemplateApi api;

    public static void register(TemplateApi instance) {
        if (api != null) {
            throw new IllegalStateException("TemplateApi is already registered.");
        }
        api = Objects.requireNonNull(instance, "instance");
    }

    public static TemplateApi get() {
        if (api == null) {
            throw new IllegalStateException("TemplateApi is not available.");
        }
        return api;
    }

    public static boolean isAvailable() {
        return api != null;
    }

    public static void unregister() {
        api = null;
    }
}
```

Use synchronization or `AtomicReference` if needed.

## ConfigView

Expose only safe config values:
- debug
- language
- storage type
- API enabled flag
- enabled features

Do not expose:
- API token
- internal filesystem paths unless safe
- secrets

## PlayerDataApi

Public-facing player data service:
- avoid exposing mutable internal models
- return DTOs
- use optional/result types

Example:

```java
Optional<PlayerDataDto> findPlayerData(String playerId);
```

## TemplateApi implementation

Create an internal implementation:

```txt
api/internal/TemplateApiImpl.java
```

or:

```txt
internal/TemplateApiImpl.java
```

Keep public interfaces separate from implementation.

Register API during bootstrap after config/data services are available.

Implementation rules:
- Classes under `api/` should be stable and clean.
- Avoid leaking internal Hytale-specific or implementation-specific types unless necessary.
- Do not expose mutable collections directly.
- Do not expose secrets.
- Other plugins should be able to call `TemplateApiProvider.isAvailable()` safely.

Acceptance criteria:
- `TemplateApiProvider` can register and return the API.
- API exposes version/config/player service.
- Internal implementation delegates to real services.
- `./gradlew build` succeeds.
