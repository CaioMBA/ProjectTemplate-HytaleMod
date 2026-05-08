package dev.ofatech.hytale.template.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import dev.ofatech.hytale.template.messages.MessageManager;
import dev.ofatech.hytale.template.messages.Messages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.Map;

public class TemplateCommand extends AbstractCommand {

    private final MessageManager messageManager;
    private final String pluginName;

    public TemplateCommand(String name, String description, MessageManager messageManager, String pluginName) {
        super(name, description);
        this.messageManager = messageManager;
        this.pluginName = pluginName;
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        String message = messageManager.formatWithPrefix(
            Messages.COMMAND_TEMPLATE_HELLO,
            Map.of("name", pluginName)
        );
        context.sendMessage(Message.raw(message));
        return CompletableFuture.completedFuture(null);
    }

}

