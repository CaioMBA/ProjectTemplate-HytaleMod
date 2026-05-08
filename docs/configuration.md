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

## Security

If you enable the API, change the default token first. Avoid logging secrets
or returning them from commands or API responses.

