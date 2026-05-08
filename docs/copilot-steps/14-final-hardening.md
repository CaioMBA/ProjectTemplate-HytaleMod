# 14 — Final Hardening and Cleanup

Goal: review the template for correctness, safety, and reuse quality.

Tasks:

## 1. Build verification

Run:

```bash
./gradlew clean build
```

Fix all compilation/test issues.

## 2. Package consistency

Verify:
- all Java files use the intended package
- manifest main class points to real class
- README class/package names are correct
- docs class/package names are correct

## 3. Secret safety

Search for:
- `token`
- `secret`
- `password`
- `webhook`

Ensure:
- no default real secrets
- no secrets printed in logs
- no secrets exposed by REST API
- default REST token is rejected if API is enabled

## 4. REST safety

Verify:
- disabled by default
- binds to `127.0.0.1` by default
- auth required except `/health`
- `/reload` disabled unless explicitly enabled
- responses use consistent JSON

## 5. Data safety

Verify:
- JSON store prevents path traversal
- directories are created safely
- IO errors have useful messages
- schema version field exists on player data or versioned data

## 6. Template usability

Verify:
- user can rename package without hunting through too many files
- docs explain all required replacement points
- example functionality is isolated and removable
- feature toggles can disable demo behavior

## 7. API stability

Verify:
- public `api/` package does not expose internal mutable classes
- provider lifecycle is safe
- config view does not expose secrets
- DTOs are immutable where practical

## 8. Logging

Verify:
- startup logs module setup
- reload logs result
- optional integration failure is visible but not fatal
- debug logs respect debug config where practical

## 9. Tests

Run:

```bash
./gradlew test
```

Add missing tests for any critical pure-Java utility.

## 10. Final README polish

README should clearly say:
- this is a Hytale Java plugin template
- current Hytale modding APIs may evolve
- server-side plugin first
- REST API is optional and disabled by default
- other plugins should prefer Java API integration

Acceptance criteria:
- clean build passes
- tests pass
- docs match code
- no secrets exposed
- template is reusable without deleting half the project
