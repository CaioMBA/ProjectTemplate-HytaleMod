# 01 — Clean Base, Naming, Metadata, and Project Hygiene

You are working inside the current Hytale Java plugin template repository.

Goal: clean the base repository so it is reusable and easier to rename.

Tasks:
1. Rename the main plugin class to:

```txt
TemplatePlugin
```

2. Use this base package unless the existing project has a stronger convention:

```txt
dev.ofatech.hytale.template
```

3. Rename/migrate existing example classes from the current package into the new package.

4. Update `manifest.json` so the plugin metadata matches the new plugin class.

Suggested metadata:
```json
{
  "name": "ProjectTemplateHytaleMod",
  "group": "dev.ofatech",
  "version": "0.1.0",
  "main": "dev.ofatech.hytale.template.TemplatePlugin"
}
```

Adjust fields to the real manifest schema already used by the repository. Do not invent unsupported manifest fields.

5. Clean Gradle files:
   - format `settings.gradle.kts`
   - format `build.gradle.kts`
   - format `gradle.properties`
   - centralize plugin name, group, version, and main class where practical

Suggested `gradle.properties` values:

```properties
plugin_group=dev.ofatech
plugin_name=ProjectTemplateHytaleMod
plugin_version=0.1.0
plugin_main=dev.ofatech.hytale.template.TemplatePlugin
```

Only wire these into Gradle if it fits the current build tooling.

6. Add repository hygiene files if missing:
   - `.editorconfig`
   - `.gitattributes`
   - `LICENSE`
   - `CHANGELOG.md`
   - `CONTRIBUTING.md`
   - `SECURITY.md`

7. Add documentation folder:

```txt
docs/
  architecture.md
  configuration.md
  api.md
  integrations.md
  using-this-template.md
```

8. Replace old example names with neutral template names:
   - `ExamplePlugin` -> `TemplatePlugin`
   - example command names should become `/template` or equivalent
   - comments should explain what users should replace when creating a real plugin

Implementation rules:
- Preserve existing Hytale/ScaffoldIt setup.
- Do not break `./gradlew build`.
- Do not remove working example functionality yet.
- Keep changes small and mechanical in this step.

Acceptance criteria:
- Package names are consistent.
- Main class in manifest points to the real plugin class.
- `./gradlew build` succeeds.
- Repository has basic docs and hygiene files.
