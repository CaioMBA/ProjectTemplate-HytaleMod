# REST API

The REST API is optional, disabled by default, and bound to `127.0.0.1` by default. It is only
started when both `api.enabled` and `features.restApi` are `true`.

## Authentication

All endpoints except `/health` require the header:

`Authorization: Bearer <token>`

The token is configured in `api.token`. Change it before enabling the API.

## Endpoints

- `GET /health` (no auth)
- `GET /version`
- `GET /config/public`
- `GET /players/{id}`
- `POST /reload` (only when `api.allowReloadEndpoint=true`)

## Example curl

```bash
curl -H "Authorization: Bearer TOKEN" http://127.0.0.1:8080/version
```

```bash
curl -H "Authorization: Bearer TOKEN" http://127.0.0.1:8080/config/public
```

## Security warning

Keep the REST API bound to `127.0.0.1` unless you have a trusted reverse proxy in front of it. Do
not log tokens or return them in responses, and avoid exposing internal filesystem paths.

