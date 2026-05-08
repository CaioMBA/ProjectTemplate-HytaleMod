# 08 — Add Command Framework and Template Commands

Goal: improve the command system so future plugins can add commands cleanly.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/commands/
```

Add:

```txt
CommandModule.java
BaseCommand.java
SubCommand.java
CommandResult.java
CommandContext.java
CommandPermission.java
VersionCommand.java
ReloadCommand.java
DebugCommand.java
ApiStatusCommand.java
```

Use the actual Hytale command APIs already present in the repo. Do not invent incompatible command registration if the template already has working command code.

## CommandModule

Responsibilities:
- register root `/template` command or closest supported equivalent
- register subcommands:
  - `version`
  - `reload`
  - `debug`
  - `api status`

If Hytale command API does not support subcommands cleanly yet, register separate commands temporarily:

```txt
/templateversion
/templatereload
/templatedebug
/templateapistatus
```

But prefer one root command if supported.

## BaseCommand

Common helper:
- access `PluginContext`
- access messages
- format errors
- check permissions through wrapper

## CommandResult

Represent:
- success
- failure
- usage error

## VersionCommand

Output:
- plugin name
- plugin version
- Java version if easy
- debug enabled/disabled

## ReloadCommand

Reload:
- config
- messages
- feature toggles where safe

Publish `ConfigReloadedEvent` if event bus exists.

Do not reload unsafe things like REST server unless the relevant service supports restart.

## DebugCommand

Output useful dev information:
- plugin id/version
- enabled features
- storage type
- API enabled/listening status
- loaded integrations

## ApiStatusCommand

Output:
- REST API enabled/disabled
- host/port, but no token
- route count if available

Implementation rules:
- Keep commands safe.
- Never print secrets.
- Do not crash on missing optional modules.
- Prefer message keys over hardcoded strings.
- Keep Hytale command API usage localized.

Acceptance criteria:
- User can run basic template command(s).
- Reload command reloads config/messages safely.
- Debug command does not expose secrets.
- `./gradlew build` succeeds.
