package dev.ofatech.hytale.template.commands;

public record CommandResult(CommandResultType type) {
    public static CommandResult success() {
        return new CommandResult(CommandResultType.SUCCESS);
    }

    public static CommandResult failure() {
        return new CommandResult(CommandResultType.FAILURE);
    }

    public static CommandResult usage() {
        return new CommandResult(CommandResultType.USAGE);
    }

    public enum CommandResultType {
        SUCCESS,
        FAILURE,
        USAGE
    }
}

