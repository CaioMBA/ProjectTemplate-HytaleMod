# Hytale Plugin Template

This repository is a ready-to-reuse template for Hytale server plugins using Java and the
ScaffoldIt runtime. It includes a small, testable core (config, data, messages, event bus, public
API) plus optional integrations like a local REST API.

## Requirements

- Java: JDK 25 is recommended by the current ScaffoldIt/Hytale tooling. If your environment uses a
  different version, update the project to match your setup.
- Gradle: the wrapper scripts (`gradlew`, `gradlew.bat`) are included.
- Hytale/ScaffoldIt: this template assumes the ScaffoldIt plugin runtime and Hytale server assets.

## Quick start

1. Use GitHub "Use this template" or clone the repo.
2. Open the project in your IDE (IntelliJ IDEA is a common choice).
3. Run a local build to verify the setup.

```bash
./gradlew build
```

On Windows, use `gradlew.bat`:

```powershell
.\gradlew.bat build
```

## Run the dev server

If ScaffoldIt provides the `devServer` task in your environment, you can run it to launch a local
server with hot-reload. On Windows, use `gradlew.bat`.

```bash
./gradlew devServer
```

## Build the plugin jar

The build task produces a jar under `build/libs`.

```bash
./gradlew build
```

## Rename the template

Follow the checklist in `docs/using-this-template.md` to rename the package, update metadata, and
replace the example modules.

## Included features

- JSON config with validation and reload support.
- JSON data store with path safety and schema versioning.
- Message localization with placeholders.
- Internal event bus.
- Public Java API (`dev.ofatech.hytale.template.api`).
- Lightweight command framework and example commands.
- Integration registry + webhook client (optional integrations).
- Optional REST API (disabled by default, bound to `127.0.0.1`).
- Feature toggles to enable/disable example behaviors.
- Unit tests and a CI workflow.

## Current limitations

- Storage is JSON-only by default (no database adapters yet).
- The REST API is local-only unless you explicitly change the host.
- Command registration uses direct command names by default; adapt to a subcommand tree if needed.
- Hytale server APIs are evolving; keep direct calls isolated in modules/bootstrap.

## Notes

- This is a server-side Hytale Java plugin template.
- Hytale modding APIs may evolve; keep integrations isolated and update as needed.
- The REST API is optional and disabled by default.
- Other plugins should prefer the Java API integration (`dev.ofatech.hytale.template.api`).

## Resources

- [Hytale Modding Guides](https://hytalemodding.dev)
- [Hytale Modding Discord](https://discord.gg/hytalemodding)
- [ScaffoldIt Plugin Docs](https://scaffoldit.dev)

