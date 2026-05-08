package dev.ofatech.hytale.template.commands;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.messages.Messages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class BaseCommand extends AbstractCommand {
    private final PluginContext pluginContext;
    private final CommandPermission permission;

    protected BaseCommand(
        PluginContext pluginContext,
        String name,
        String description,
        CommandPermission permission
    ) {
        super(name, description);
        this.pluginContext = Objects.requireNonNull(pluginContext, "pluginContext");
        this.permission = Objects.requireNonNull(permission, "permission");
    }

    @Nullable
    @Override
    protected final CompletableFuture<Void> execute(@Nonnull com.hypixel.hytale.server.core.command.system.CommandContext context) {
        CommandContext wrapped = new CommandContext(context, pluginContext);
        if (!hasPermission(wrapped)) {
            wrapped.sendMessage(Messages.COMMAND_NO_PERMISSION, Map.of());
            return CompletableFuture.completedFuture(null);
        }
        handle(wrapped);
        return CompletableFuture.completedFuture(null);
    }

    protected abstract CommandResult handle(CommandContext context);

    protected boolean hasPermission(CommandContext context) {
        return permission.node() == null || permission.node().isBlank();
    }
}

