# Public API

The template exposes a stable Java API under `dev.ofatech.hytale.template.api`.

## Usage

```java
if (TemplateApiProvider.isAvailable()) {
	TemplateApi api = TemplateApiProvider.get();
	String version = api.version();
}
```

## Guidelines

- Prefer interfaces and immutable DTOs.
- Avoid exposing internal implementation classes.
- Do not expose secrets or internal filesystem paths.

