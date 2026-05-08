# 09 — Add Optional Plugin Integration Registry

Goal: create a clean pattern for optional integrations with other plugins/services.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/integration/
```

Add:

```txt
PluginIntegration.java
OptionalPluginIntegration.java
IntegrationRegistry.java
IntegrationStatus.java
WebhookClient.java
DiscordWebhookIntegration.java
```

## PluginIntegration

Design:

```java
public interface PluginIntegration {
    String id();
    boolean isAvailable(PluginContext context);
    void enable(PluginContext context);
    void disable();
}
```

## OptionalPluginIntegration

Abstract base class:
- stores enabled status
- common logging
- catches enable/disable errors

## IntegrationRegistry

Responsibilities:
- register integrations
- enable available integrations
- disable integrations on shutdown if lifecycle exists
- list statuses for debug command/API

## IntegrationStatus

Fields:
- id
- available
- enabled
- error message, if failed

## WebhookClient

Reusable outgoing webhook helper:
- send JSON payload
- configurable timeout
- no hard dependency on Discord
- use Java built-in HTTP client if available

## DiscordWebhookIntegration

Example optional integration:
- disabled by default
- configured through config later if desired
- demonstrate how integrations should be written
- do not ship with a real webhook URL

Implementation rules:
- Do not hardcode dependency on any real external plugin yet.
- Do not block plugin startup if optional integration fails.
- Do not log secrets.
- Keep network integrations disabled by default.
- Avoid heavy HTTP libraries unless already present.

Acceptance criteria:
- Integration registry exists and is available from `PluginContext`.
- Debug command can list integration statuses.
- Webhook client compiles.
- `./gradlew build` succeeds.
