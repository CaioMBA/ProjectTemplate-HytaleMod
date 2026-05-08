# 13 — Add Template Usage Documentation

Goal: make the repository easy to reuse as a real plugin/mod template.

Update root `README.md`.

Add or update:

```txt
docs/
  using-this-template.md
  architecture.md
  configuration.md
  api.md
  integrations.md
  rest-api.md
  data-storage.md
  commands.md
```

## README.md

Should include:
1. What this template is.
2. Requirements:
   - Java version
   - Gradle wrapper
   - Hytale/ScaffoldIt assumptions
3. Quick start.
4. How to run dev server.
5. How to build plugin jar.
6. How to rename the template.
7. What features are included.
8. Current limitations.

## docs/using-this-template.md

Add checklist:

```txt
1. Rename rootProject.name.
2. Rename Java package.
3. Update manifest group/name/main.
4. Update config.default.json.
5. Update messages.default.json.
6. Replace TemplatePlugin constants.
7. Remove example features you do not need.
8. Run ./gradlew build.
9. Run ./gradlew devServer if available.
```

## docs/architecture.md

Explain:
- `TemplatePlugin`
- bootstrap
- modules
- plugin context
- config
- data store
- event bus
- public API
- integrations
- REST API
- features

Include a simple diagram:

```txt
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

## docs/configuration.md

Document:
- config location
- default config
- each config key
- API token warning
- reload behavior

## docs/api.md

Document:
- `TemplateApi`
- `TemplateApiProvider`
- safe usage by other plugins
- what is stable and what is internal

## docs/integrations.md

Document:
- integration registry
- optional integrations
- webhook client
- future economy/permissions integrations

## docs/rest-api.md

Document:
- disabled by default
- auth header
- endpoints
- example curl commands
- security warning

Example curl:

```bash
curl -H "Authorization: Bearer TOKEN" http://127.0.0.1:8080/version
```

## docs/data-storage.md

Document:
- JSON data store
- namespaces
- path safety
- schema versions
- future database options

## docs/commands.md

Document:
- `/template version`
- `/template reload`
- `/template debug`
- `/template api status`

Implementation rules:
- Keep docs accurate to the code.
- Do not document features that were not implemented.
- Mark unstable/future items clearly.
- No marketing fluff.

Acceptance criteria:
- README explains how to reuse the repo.
- Docs match actual package/class names.
- Security warnings exist for REST API.
- `./gradlew build` still succeeds.
