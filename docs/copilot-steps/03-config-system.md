# 03 — Add Config and Settings System

Goal: add a reusable JSON-based config/settings layer.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/config/
```

Add:

```txt
PluginConfig.java
ApiConfig.java
StorageConfig.java
FeatureConfig.java
ConfigManager.java
ConfigValidator.java
ConfigException.java
ConfigView.java
```

Also add resources:

```txt
src/main/resources/config.default.json
```

Suggested default config:

```json
{
  "debug": false,
  "language": "en_us",
  "storage": {
    "type": "json",
    "directory": "data"
  },
  "api": {
    "enabled": false,
    "host": "127.0.0.1",
    "port": 8080,
    "token": "change-me",
    "allowReloadEndpoint": false
  },
  "features": {
    "exampleCommand": true,
    "playerWelcomeMessage": true,
    "restApi": false
  }
}
```

Use Java records where reasonable:

```java
public record PluginConfig(
    boolean debug,
    String language,
    StorageConfig storage,
    ApiConfig api,
    FeatureConfig features
) {}
```

`ConfigManager` responsibilities:
- locate plugin config file
- copy `config.default.json` to config location if missing
- load config
- validate config
- expose current config
- reload config
- avoid returning mutable internal state

`ConfigValidator` responsibilities:
- reject invalid storage directory
- reject invalid API port
- reject enabled API with default token
- reject blank language
- reject null nested configs

`ConfigException`:
- runtime exception or checked exception; choose whatever fits the repo style
- message should explain exactly what is wrong

`ConfigView`:
- read-only interface intended for public API
- expose only safe values
- do not expose secrets such as API token

Integrate into bootstrap:
- create/load config before command/event modules
- store `ConfigManager` in `PluginContext`
- use config debug flag for logging decisions where practical

Implementation rules:
- Prefer an existing JSON library if the project already has one.
- If no JSON library exists, add a lightweight one such as Gson or Jackson.
- Do not store generated config in `src/main/resources`.
- Do not expose API token through public config views.
- Build must succeed without a real Hytale server.

Acceptance criteria:
- Missing config gets created from default config.
- Invalid config fails with useful error.
- `PluginContext` exposes config manager.
- `./gradlew build` succeeds.
