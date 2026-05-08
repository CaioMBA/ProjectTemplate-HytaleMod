# 04 — Add Data Parsing and Persistence Utilities

Goal: create reusable data parsing and persistence utilities without locking the template into a database.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/data/
```

Add:

```txt
DataStore.java
JsonDataStore.java
Repository.java
PlayerData.java
PlayerDataService.java
DataException.java
SchemaVersioned.java
```

Create utility package if missing:

```txt
src/main/java/dev/ofatech/hytale/template/util/
```

Add:

```txt
JsonUtil.java
Result.java
Preconditions.java
TimeUtil.java
Identifiers.java
```

## DataStore

Design:

```java
public interface DataStore {
    <T> Optional<T> load(String namespace, String key, Class<T> type);
    <T> void save(String namespace, String key, T value);
    boolean exists(String namespace, String key);
    void delete(String namespace, String key);
}
```

## JsonDataStore

Implement file-based JSON storage:

```txt
<data-directory>/<namespace>/<key>.json
```

Requirements:
- create directories as needed
- sanitize namespace/key path segments
- prevent path traversal
- write atomically if practical:
  - write to temp file
  - move/replace target file
- use UTF-8
- wrap IO failures in `DataException`

## PlayerData

Example versioned player data:

```java
public record PlayerData(
    int schemaVersion,
    UUID playerId,
    Instant firstJoin,
    Instant lastJoin,
    Map<String, String> flags
) {}
```

Adjust fields if Hytale player IDs are not UUID-based. Keep the service decoupled enough to change ID type later.

## PlayerDataService

Responsibilities:
- load player data
- create default player data
- update last join timestamp
- save player data
- publish player data loaded event later if event bus exists

## Repository<T>

Generic helper around `DataStore`:

```java
public final class Repository<T> {
    // namespace + class type + store
}
```

Keep it simple.

## JsonUtil

Central wrapper around the chosen JSON library:
- serialize
- deserialize
- pretty print
- consistent exception messages

## Result<T>

Small success/failure wrapper:

```java
public record Result<T>(boolean success, T value, String error) {}
```

Or use a class with static factories:

```java
Result.success(value)
Result.failure(message)
```

Implementation rules:
- No database dependency yet.
- No async complexity yet.
- No Hytale hard dependency inside `JsonDataStore`.
- Path safety is mandatory.
- Keep data layer testable.

Acceptance criteria:
- Can save and load a sample object as JSON.
- Path traversal attempts are rejected.
- `PlayerDataService` can create default player data.
- `./gradlew build` succeeds.
