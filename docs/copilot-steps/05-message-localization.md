# 05 — Add Messages and Localization Utilities

Goal: centralize plugin messages and make output configurable.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/messages/
```

Add:

```txt
Messages.java
MessageManager.java
MessageFormatter.java
MessageException.java
```

Add default messages resource:

```txt
src/main/resources/messages.default.json
```

Suggested default content:

```json
{
  "prefix": "[Template]",
  "plugin.loaded": "Plugin loaded successfully.",
  "plugin.reloaded": "Plugin reloaded.",
  "plugin.version": "Running {name} version {version}.",
  "command.no_permission": "You do not have permission.",
  "command.invalid_argument": "Invalid argument: {argument}",
  "config.reload.success": "Configuration reloaded.",
  "config.reload.failure": "Configuration reload failed: {error}",
  "api.disabled": "REST API is disabled.",
  "api.enabled": "REST API is listening on {host}:{port}."
}
```

## MessageManager

Responsibilities:
- copy `messages.default.json` to a runtime messages file if missing
- load messages from JSON
- fallback to default messages if key is missing
- expose `get(key)`
- expose `format(key, placeholders)`

## MessageFormatter

Support simple placeholders:

```txt
{name}
{version}
{error}
```

Do not add a heavy templating engine.

## Messages

Optional constants:

```java
public final class Messages {
    public static final String PLUGIN_LOADED = "plugin.loaded";
    public static final String PLUGIN_RELOADED = "plugin.reloaded";
}
```

Integrate:
- Store `MessageManager` in `PluginContext`.
- Use it in command output where possible.
- Use it in reload/version commands once those exist.

Implementation rules:
- Do not crash the plugin because one message is missing.
- Do not allow formatting to throw for missing placeholder; leave placeholder unchanged.
- Keep this independent from Hytale chat APIs as much as possible.
- Hytale-specific sending should be wrapped separately.

Acceptance criteria:
- Messages load from JSON.
- Placeholder replacement works.
- Missing message keys return a visible fallback like `missing.message.key`.
- `./gradlew build` succeeds.
