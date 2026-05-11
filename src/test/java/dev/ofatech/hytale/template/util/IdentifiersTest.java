package dev.ofatech.hytale.template.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IdentifiersTest {

    @Test
    void normalizeTrimsAndLowercases() {
        assertEquals("example-id", Identifiers.normalize("  Example-ID  "));
    }

    @Test
    void normalizeRejectsBlank() {
        assertThrows(IllegalArgumentException.class, () -> Identifiers.normalize("  "));
    }
}

