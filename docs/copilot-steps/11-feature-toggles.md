# 11 — Add Feature Toggles

Goal: make example/template functionality easy to enable/disable without deleting code.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/features/
```

Add:

```txt
Feature.java
FeatureManager.java
FeatureStatus.java
ExampleCommandFeature.java
PlayerWelcomeFeature.java
RestApiFeature.java
```

## Feature

Design:

```java
public interface Feature {
    String id();
    boolean enabledByDefault();
    void enable(PluginContext context);
    void disable(PluginContext context);
}
```

## FeatureManager

Responsibilities:
- register features
- read enabled/disabled state from config
- enable selected features
- disable features on shutdown/reload where safe
- expose statuses for debug command and REST public config

## FeatureStatus

Fields:
- id
- enabled
- defaultEnabled
- error message if enable failed

## Example features

### ExampleCommandFeature
Owns registration of example/demo command behavior if command registration supports late feature registration.

If not practical with current Hytale API, keep command module always enabled and make command behavior check feature state.

### PlayerWelcomeFeature
Controls player welcome message on player ready/join event.

### RestApiFeature
Controls REST API startup if the config also enables API.

REST API should require both:
- `features.restApi=true`
- `api.enabled=true`

Implementation rules:
- Do not over-engineer runtime hot swapping.
- It is acceptable for some features to require server/plugin restart.
- Do not let a non-critical feature failure kill the whole plugin unless marked critical.
- Feature IDs should be stable lowercase strings.

Acceptance criteria:
- Features are registered and listed.
- Config can disable example welcome behavior.
- Debug command can show feature statuses.
- `./gradlew build` succeeds.
