# Architecture

This repository is a **cloneable Hytale plugin architecture template**. It is a project starter, not a shared runtime framework.

## Layers

1. **Root plugin entrypoint** (`TemplatePlugin`)
   - Bootstraps the plugin.
   - Registers platform commands/events.
   - Should remain thin.

2. **API** (`api`)
   - Public-facing contracts for the real mod you create from this template.
   - Placeholder only in this template.

3. **Core Domain** (`core/domain`)
   - Pure business/domain concepts.
   - No Hytale API imports.

4. **Core Service** (`core/service`)
   - Use-cases, orchestration, validation, algorithms.
   - Can depend on domain.

5. **Core Infrastructure** (`core/infrastructure`)
   - Technical adapters: file IO, API clients, persistence implementations, logging, etc.

6. **Platform** (`platform`)
   - Hytale-specific integration.
   - Commands, event handlers, lifecycle hooks, registries, screen integration.

## Dependency rules

- `platform` may call `core/service` and `core/domain`.
- `core/service` may use `core/domain`.
- `core/domain` must not depend on `platform` or Hytale APIs.
- Any Hytale-specific import belongs in `platform`.
