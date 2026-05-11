# Architecture

This template keeps core plugin logic small and modular, with explicit boundaries between
Hytale runtime APIs and testable services.

```
Hytale Plugin Runtime
		↓
TemplatePlugin
		↓
PluginBootstrap
		↓
PluginContext
		↓
Modules / Features / Services
		↓
Public Java API + Optional REST API
```

## Core components

- `TemplatePlugin`: entry point provided to the Hytale runtime.
- Bootstrap: `PluginBootstrap` builds the `PluginContext`, loads config/messages, and installs
  modules and services.
- Modules: `PluginModule` implementations register commands, events, and integrations.
- Plugin context: `PluginContext` holds shared services (config, messages, data, event bus, API).
- Config: `ConfigManager` loads `config.default.json`, validates, and exposes a safe `ConfigView`.
- Data store: `JsonDataStore` provides JSON persistence with path safety and schema versions.
- Event bus: `SimpleEventBus` lets internal services publish/subscribe without Hytale dependencies.
- Public API: `TemplateApi` exposes safe, read-only access for other plugins.
- Integrations: `IntegrationRegistry` and optional integrations (for example, webhook-based).
- REST API: `RestApiServer` is optional, disabled by default, and bound to `127.0.0.1` by default.
- Features: `FeatureManager` toggles optional behaviors based on config.

