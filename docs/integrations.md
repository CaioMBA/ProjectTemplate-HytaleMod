# Integrations

Integrations are optional and should be easy to enable/disable without affecting core gameplay.

## Guidelines
- Keep integrations behind interfaces.
- Do not expose secrets or internal config.

## Integration registry

`IntegrationRegistry` manages integration lifecycles and reports status for debug output. Each
integration implements `PluginIntegration` or `OptionalPluginIntegration` and can be toggled on
or off at runtime based on config.

## Webhook client

`WebhookClient` is a small HTTP client that sends JSON payloads to a configured URL. It is used by
optional integrations and does not log sensitive data.

## Optional integrations

- `DiscordWebhookIntegration`: a disabled-by-default example that posts to a Discord webhook when
  configured.

## Future integrations (not implemented yet)

- Economy providers.
- Permission systems.
- External analytics.

For REST API usage, see `docs/rest-api.md`.

