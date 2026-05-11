# Commands

The template registers example commands via the command module. Permission checks are currently
no-ops; add your own permission nodes if needed.

## Commands

- `/template version` (registered as `templateversion` by default)
- `/template reload` (registered as `templatereload` by default)
- `/template debug` (registered as `templatedebug` by default)
- `/template api status` (registered as `templateapistatus` by default)

If your command system supports subcommands, you can map these into a single `/template` tree while
reusing the same command handlers.

