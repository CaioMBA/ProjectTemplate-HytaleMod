# 02 — Add Bootstrap, Plugin Context, and Module System

Goal: replace direct all-in-one setup logic with a reusable bootstrap/module architecture.

Create package:

```txt
src/main/java/dev/ofatech/hytale/template/bootstrap/
```

Add:

```txt
PluginModule.java
PluginContext.java
PluginBootstrap.java
LifecycleHooks.java
```

Required design:

## PluginModule

```java
public interface PluginModule {
    String name();
    void setup(PluginContext context);
}
```

Optional lifecycle methods may be default methods:

```java
default void shutdown(PluginContext context) {}
default void reload(PluginContext context) {}
```

## PluginContext

`PluginContext` should hold shared runtime services, for example:
- reference to `TemplatePlugin`
- logger/log wrapper
- config manager, once available
- data store, once available
- event bus, once available
- integration registry, once available

At this step, it may start minimal:

```java
public final class PluginContext {
    private final TemplatePlugin plugin;

    public PluginContext(TemplatePlugin plugin) {
        this.plugin = plugin;
    }

    public TemplatePlugin plugin() {
        return plugin;
    }
}
```

## PluginBootstrap

Create a builder-like class:

```java
PluginBootstrap.create(this)
    .withModule(new CommandModule())
    .withModule(new EventModule())
    .start();
```

It should:
- keep a list of modules
- create `PluginContext`
- setup modules in registration order
- log module setup start/success/failure
- fail fast if a critical module throws during setup

## LifecycleHooks

Add a small utility/interface for future lifecycle support. Keep it simple.

Update `TemplatePlugin.setup()` so it delegates to `PluginBootstrap`.

Example target style:

```java
@Override
protected void setup() {
    this.context = PluginBootstrap.create(this)
        .withModule(new CommandModule())
        .withModule(new EventModule())
        .start();
}
```

If `CommandModule` and `EventModule` do not exist yet, create minimal modules that preserve current behavior.

Implementation rules:
- Do not add dependency injection frameworks.
- Do not over-engineer reflection scanning.
- Registration order should be explicit.
- Keep Hytale API calls inside modules or plugin class.
- Preserve existing example command/event behavior.

Acceptance criteria:
- `TemplatePlugin.setup()` is short and delegates setup.
- Modules are reusable and explicit.
- Existing command/event examples still work.
- `./gradlew build` succeeds.
