package dev.ofatech.hytale.template.util;

public final class Identifiers {
    private Identifiers() {
    }

    public static String normalize(String value) {
        Preconditions.checkNotBlank(value, "identifier");
        return value.trim().toLowerCase();
    }
}

