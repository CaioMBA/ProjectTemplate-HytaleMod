# Public API

The template exposes a stable Java API under `dev.ofatech.hytale.template.api`.

## Usage

```java
if (TemplateApiProvider.isAvailable()) {
	TemplateApi api = TemplateApiProvider.get();
	String version = api.version();
}
```

## What is stable

- `TemplateApi` and its sub-interfaces (for example, `PlayerDataApi`).
- DTOs in `dev.ofatech.hytale.template.api.dto`.
- Events in `dev.ofatech.hytale.template.api.events`.

## What is internal

- Classes under `dev.ofatech.hytale.template.api.internal`.
- `TemplateApiImpl` and internal service wiring.

Treat internal classes as implementation details; they can change without notice.

## Guidelines

- Prefer interfaces and immutable DTOs.
- Avoid exposing internal implementation classes.
- Do not expose secrets or internal filesystem paths.

