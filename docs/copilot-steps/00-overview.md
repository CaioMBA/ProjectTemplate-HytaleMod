# 00 — Repository Upgrade Overview

You are working inside the current `CaioMBA/ProjectTemplate-HytaleMod` repository.

Goal: convert this minimal Hytale Java plugin template into a reusable, production-oriented plugin foundation.

Current repo assumptions:
- Java plugin template for Hytale.
- Uses Gradle Kotlin DSL.
- Uses ScaffoldIt / Hytale plugin tooling.
- Has a minimal plugin class, command registration, event registration, and manifest.
- The template should stay lightweight and not become a huge framework.

Main design goals:
1. Keep Hytale API usage isolated behind thin internal abstractions.
2. Make the template easy to rename and reuse.
3. Add a clean lifecycle/bootstrap/module system.
4. Add reusable utilities:
   - settings/config
   - JSON data parsing
   - persistence abstraction
   - messages/localization
   - result/error helpers
5. Add integration points:
   - public Java API for other plugins
   - optional REST API for external tools
   - optional plugin integration registry
6. Add testing and CI.
7. Keep examples useful but removable.

Preferred package:

```txt
dev.ofatech.hytale.template
```

If the repository already uses a different package, migrate it consistently.

Expected final source layout:

```txt
src/main/java/dev/ofatech/hytale/template/
  TemplatePlugin.java

  bootstrap/
    PluginBootstrap.java
    PluginContext.java
    PluginModule.java
    LifecycleHooks.java

  api/
    TemplateApi.java
    TemplateApiProvider.java
    ConfigView.java
    events/
    services/
    dto/

  commands/
    BaseCommand.java
    CommandModule.java
    ReloadCommand.java
    VersionCommand.java
    DebugCommand.java

  config/
    PluginConfig.java
    ApiConfig.java
    StorageConfig.java
    FeatureConfig.java
    ConfigManager.java
    ConfigValidator.java
    ConfigException.java

  data/
    DataStore.java
    JsonDataStore.java
    PlayerData.java
    PlayerDataService.java
    Repository.java

  events/
    EventBus.java
    SimpleEventBus.java
    EventModule.java
    PlayerJoinListener.java
    TemplatePluginReadyEvent.java
    ConfigReloadedEvent.java
    PlayerDataLoadedEvent.java

  integration/
    IntegrationRegistry.java
    PluginIntegration.java
    OptionalPluginIntegration.java
    WebhookClient.java
    rest/

  messages/
    Messages.java
    MessageManager.java
    MessageFormatter.java

  scheduler/
    TaskScheduler.java
    CooldownService.java

  util/
    Identifiers.java
    JsonUtil.java
    Log.java
    Preconditions.java
    Result.java
    TimeUtil.java
```

Rules:
- Do not add unnecessary heavy dependencies.
- Keep REST API disabled by default.
- Bind REST API to `127.0.0.1` by default.
- Do not expose secrets or full config through endpoints.
- Prefer interfaces in `api/` and `data/`.
- Keep Hytale-specific calls close to the plugin/bootstrap/event/command modules.
- Add tests where possible without requiring a real Hytale server.
- Keep the project buildable after each step.

Acceptance criteria:
- `./gradlew build` succeeds.
- Template can still run as a basic Hytale plugin.
- The README explains how to rename and reuse the template.
