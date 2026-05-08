package dev.ofatech.hytale.template.messages;

import java.util.Map;

public final class MessageFormatter {
    private MessageFormatter() {
    }

    public static String format(String template, Map<String, String> placeholders) {
        if (template == null) {
            return "";
        }

        if (placeholders == null || placeholders.isEmpty()) {
            return template;
        }

        String result = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null || value == null) {
                continue;
            }
            result = result.replace("{" + key + "}", value);
        }
        return result;
    }
}

