package dev.ofatech.hytale.template.messages;

import com.google.gson.reflect.TypeToken;
import dev.ofatech.hytale.template.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public final class MessageManager {
    private static final String DEFAULT_RESOURCE = "messages.default.json";
    private static final String DEFAULT_LANGUAGE = "en_us";

    private final Path baseDirectory;
    private final String language;
    private final Path messagesFile;

    private Map<String, String> defaults = Collections.emptyMap();
    private Map<String, String> messages = Collections.emptyMap();

    public MessageManager(Path baseDirectory, String language) {
        this.baseDirectory = Objects.requireNonNull(baseDirectory, "baseDirectory");
        this.language = normalizeLanguage(language);
        this.messagesFile = baseDirectory.resolve("messages." + this.language + ".json");
    }

    public void load() {
        try {
            Files.createDirectories(baseDirectory);
        } catch (IOException ex) {
            throw new MessageException("Failed to create messages directory: " + baseDirectory, ex);
        }

        if (Files.notExists(messagesFile)) {
            copyDefaultMessages();
        }

        this.defaults = readDefaults();
        this.messages = readMessagesFile();
    }

    public String get(String key) {
        if (key == null) {
            return "missing.null";
        }
        String value = messages.get(key);
        if (value != null) {
            return value;
        }
        value = defaults.get(key);
        if (value != null) {
            return value;
        }
        return "missing." + key;
    }

    public String format(String key, Map<String, String> placeholders) {
        return MessageFormatter.format(get(key), placeholders);
    }

    public String formatWithPrefix(String key, Map<String, String> placeholders) {
        String prefix = get(Messages.PREFIX);
        String message = MessageFormatter.format(get(key), placeholders);
        if (prefix == null || prefix.isBlank()) {
            return message;
        }
        return prefix + " " + message;
    }

    private Map<String, String> readDefaults() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(DEFAULT_RESOURCE)) {
            if (input == null) {
                return Collections.emptyMap();
            }
            return JsonUtil.fromJson(
                new String(input.readAllBytes(), StandardCharsets.UTF_8),
                new TypeToken<Map<String, String>>() {
                }.getType()
            );
        } catch (IOException ex) {
            throw new MessageException("Failed to read default messages", ex);
        }
    }

    private Map<String, String> readMessagesFile() {
        try (Reader reader = Files.newBufferedReader(messagesFile, StandardCharsets.UTF_8)) {
            Map<String, String> loaded = JsonUtil.fromJson(
                reader,
                new TypeToken<Map<String, String>>() {
                }.getType()
            );
            return loaded == null ? Collections.emptyMap() : loaded;
        } catch (IOException ex) {
            throw new MessageException("Failed to read messages file: " + messagesFile, ex);
        }
    }

    private void copyDefaultMessages() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(DEFAULT_RESOURCE)) {
            if (input == null) {
                throw new MessageException("Missing default messages resource: " + DEFAULT_RESOURCE);
            }
            Files.copy(input, messagesFile);
        } catch (IOException ex) {
            throw new MessageException("Failed to write default messages to " + messagesFile, ex);
        }
    }

    private String normalizeLanguage(String value) {
        if (value == null || value.trim().isEmpty()) {
            return DEFAULT_LANGUAGE;
        }
        String normalized = value.trim().toLowerCase();
        if (!normalized.matches("[a-z0-9_-]+")) {
            return DEFAULT_LANGUAGE;
        }
        return normalized;
    }
}


