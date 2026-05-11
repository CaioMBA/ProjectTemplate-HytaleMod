# Configuration

The template uses a JSON configuration file that is created on first run from
`config.default.json`.

## Location

By default, the config file is placed under:

`config/TemplatePlugin/config.json`

This path is relative to the server working directory.

## Defaults

The default config includes:
- debug flag
- language
- storage settings
- optional API settings (disabled by default)
- feature toggles for example behaviors

## Config keys

- `debug`: enables extra debug output in commands and logging.
- `language`: message bundle key to load (for example, `en_us`).
- `storage.type`: storage backend (`json` by default).
- `storage.directory`: relative directory for data files (default `data`).
- `api.enabled`: enables the optional REST API server.
- `api.host`: host/interface to bind the REST API (default `127.0.0.1`).
- `api.port`: port for the REST API (default `8080`).
- `api.token`: bearer token used for API requests.
- `api.allowReloadEndpoint`: exposes `/reload` when `true`.
- `features.exampleCommand`: enables the example command outputs.
- `features.playerWelcomeMessage`: enables the welcome message listener.
- `features.restApi`: enables REST API feature gate (must be `true` with `api.enabled`).

## Security

If you enable the API, change the default token first. Avoid logging secrets
or returning them from commands or API responses.

## Reload behavior

`/templatereload` and `POST /reload` (when enabled) reload the config and messages, refresh
features, and publish a `ConfigReloadedEvent` on the internal event bus. No automatic migration
is performed; keep schema changes backward compatible or handle them in your code.

