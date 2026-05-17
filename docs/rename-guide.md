# Rename Guide

Use this template as a starting point, then rename package/class/API names to match your real plugin.

## Generic checklist

1. Clone this repository.
2. Replace base package `net.ofatech.hytale.template` with your target package.
3. Rename `TemplatePlugin` to your plugin entrypoint class name.
4. Rename `TemplateApi` to your public API contract name.
5. Update `src/main/resources/manifest.json` plugin metadata, especially `Main`.
6. Update Gradle project name (`settings.gradle.kts`) if desired.
7. Update README title/content for your plugin.

## Example: Server Core

- Replace `net.ofatech.hytale.template` -> `net.ofatech.hytale.servercore`
- Replace `TemplatePlugin` -> `ServerCorePlugin`
- Replace `TemplateApi` -> `ServerCoreApi`
- Update manifest/plugin metadata
- Update Gradle project name if applicable
- Update README title

## Example: Multiversal Tales

- Replace `net.ofatech.hytale.template` -> `net.ofatech.hytale.multiversaltales`
- Replace `TemplatePlugin` -> `MultiversalTalesPlugin`
- Replace `TemplateApi` -> `MultiversalTalesApi`
- Update manifest/plugin metadata
- Update Gradle project name if applicable
- Update README title

## Example: Super Playground

- Replace `net.ofatech.hytale.template` -> `net.ofatech.hytale.superplayground`
- Replace `TemplatePlugin` -> `SuperPlaygroundPlugin`
- Replace `TemplateApi` -> `SuperPlaygroundApi`
- Update manifest/plugin metadata
- Update Gradle project name if applicable
- Update README title
