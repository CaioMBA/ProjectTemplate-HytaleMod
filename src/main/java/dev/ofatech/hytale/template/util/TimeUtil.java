package dev.ofatech.hytale.template.util;

import java.time.Instant;

public final class TimeUtil {
    private TimeUtil() {
    }

    public static Instant now() {
        return Instant.now();
    }
}

