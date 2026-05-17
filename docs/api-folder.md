# API Folder Guide

The `api` folder is a placeholder for the **public API surface** of the real mod you create from this template.

## Key rules

- `api` is not a runtime integration framework by itself.
- Do not treat `api` as a global registry/discovery system.
- Internal classes in `core` and `platform` are not public API.

## When to add files

- Add files under `api/contracts` when you intentionally expose stable contracts for other plugins.
- Add files under `api/integration` when you publish documented integration-facing contracts.
- Keep APIs minimal and stable; hide implementation details in `core` or `platform`.
