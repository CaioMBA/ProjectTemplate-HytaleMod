# Integrations

Integrations are optional and should be easy to disable.

## Guidelines
- Keep integrations behind interfaces.
- Do not expose secrets or internal config.

## Optional REST API

The REST API is disabled by default. When enabled, it binds to `127.0.0.1`
and requires a token for all endpoints except `/health`.

Endpoints:
- `GET /health`
- `GET /version`
- `GET /config/public`
- `GET /players/{id}`
- `POST /reload` (only if `api.allowReloadEndpoint=true`)

Authentication header:

`Authorization: Bearer <token>`

