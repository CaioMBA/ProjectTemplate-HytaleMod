package dev.ofatech.hytale.template.commands;

public record CommandPermission(String node) {
    public static CommandPermission none() {
        return new CommandPermission("");
    }
}

