# Data Storage

The template uses a JSON data store by default. It is designed to be easy to replace with another
backend later.

## JSON data store

- `JsonDataStore` persists records as JSON files under the configured `storage.directory`.
- Each record lives under a namespace folder (for example, `players/player-123.json`).
- The data store uses atomic writes and never overwrites files in place.

## Path safety

Namespace and key segments are validated to prevent path traversal. Segments may not contain `..` or
path separators.

## Schema versions

`PlayerData` includes a `schemaVersion` field to support future migrations. Keep schema changes
backward compatible or add migration logic in your services.

## Future storage options

Relational databases or key/value stores are possible future replacements, but they are not
implemented in this template.

