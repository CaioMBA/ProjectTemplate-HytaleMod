package dev.ofatech.hytale.template.commands;

public interface SubCommand {
    String name();

    CommandResult handle(CommandContext context);
}

