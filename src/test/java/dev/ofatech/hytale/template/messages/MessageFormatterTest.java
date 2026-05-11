package dev.ofatech.hytale.template.messages;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageFormatterTest {

    @Test
    void replacesPlaceholders() {
        String template = "Hello {name}, welcome to {world}!";
        String result = MessageFormatter.format(template, Map.of("name", "Ava", "world", "Hytale"));

        assertEquals("Hello Ava, welcome to Hytale!", result);
    }

    @Test
    void leavesMissingPlaceholdersUnchanged() {
        String template = "Hello {name}, welcome to {world}!";
        String result = MessageFormatter.format(template, Map.of("name", "Ava"));

        assertEquals("Hello Ava, welcome to {world}!", result);
    }

    @Test
    void handlesEmptyPlaceholderMap() {
        String template = "Hello {name}!";
        String result = MessageFormatter.format(template, Map.of());

        assertEquals(template, result);
    }
}

