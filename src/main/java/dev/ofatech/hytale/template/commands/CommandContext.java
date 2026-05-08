package dev.ofatech.hytale.template.commands;

import com.hypixel.hytale.server.core.Message;
import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.messages.MessageManager;

import java.util.Map;
import java.util.Objects;

public final class CommandContext {
    private final com.hypixel.hytale.server.core.command.system.CommandContext handle;
    private final PluginContext pluginContext;

    public CommandContext(
        com.hypixel.hytale.server.core.command.system.CommandContext handle,
        PluginContext pluginContext
    ) {
        this.handle = Objects.requireNonNull(handle, "handle");
        this.pluginContext = Objects.requireNonNull(pluginContext, "pluginContext");
    }

    public PluginContext pluginContext() {
        return pluginContext;
    }

    public MessageManager messages() {
        return pluginContext.messageManager();
    }

    public void sendMessage(String key, Map<String, String> placeholders) {
        String message = messages().formatWithPrefix(key, placeholders);
        handle.sendMessage(Message.raw(message));
    }

    public void sendRaw(String message) {
        handle.sendMessage(Message.raw(message));
    }
}

