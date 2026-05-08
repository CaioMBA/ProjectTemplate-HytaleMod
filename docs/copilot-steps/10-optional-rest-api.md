# 10 — Add Optional REST API for External Integrations

Goal: add an optional local REST API that can be used by dashboards, bots, launchers, or external tools.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/integration/rest/
```

Add:

```txt
RestApiServer.java
ApiResponse.java
AuthFilter.java
Route.java
HealthController.java
VersionController.java
ConfigController.java
PlayerController.java
AdminController.java
RestApiException.java
```

Use Java built-in HTTP server if available and appropriate:

```java
com.sun.net.httpserver.HttpServer
```

Avoid heavy frameworks.

## Config behavior

REST API must be disabled by default.

Config:

```json
"api": {
  "enabled": false,
  "host": "127.0.0.1",
  "port": 8080,
  "token": "change-me",
  "allowReloadEndpoint": false
}
```

Rules:
- If `api.enabled=false`, do not start server.
- If `api.enabled=true` and token is `change-me`, fail validation.
- Bind to `127.0.0.1` by default.
- Never print token in logs.
- Never expose full config.

## Endpoints

Add:

```txt
GET /health
GET /version
GET /config/public
GET /players/{id}
POST /reload
```

`POST /reload` must only be registered or allowed if:

```txt
api.allowReloadEndpoint=true
```

## Authentication

Require token for anything except `/health`.

Suggested header:

```txt
Authorization: Bearer <token>
```

Return:
- `401` for missing/invalid token
- JSON response body

## ApiResponse

Use consistent JSON:

```json
{
  "success": true,
  "data": {},
  "error": null
}
```

Failure:

```json
{
  "success": false,
  "data": null,
  "error": "message"
}
```

## RestApiServer

Responsibilities:
- start
- stop
- expose status
- register controllers/routes
- log host/port without token

Integrate:
- add `RestApiServer` to `PluginContext`
- start from integration/module layer only if enabled
- expose status in debug/api status command

Implementation rules:
- Disabled by default.
- Auth required except health.
- No secrets in responses.
- No admin reload unless explicitly enabled.
- Keep route code small and testable.
- Do not make REST the primary plugin-to-plugin integration path; Java API is primary.

Acceptance criteria:
- API disabled by default.
- Health route works when enabled.
- Version route works with auth.
- Invalid/missing auth returns 401.
- `./gradlew build` succeeds.
