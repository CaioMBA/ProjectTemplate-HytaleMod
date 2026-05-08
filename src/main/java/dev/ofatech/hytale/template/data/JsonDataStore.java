package dev.ofatech.hytale.template.data;

import dev.ofatech.hytale.template.util.JsonUtil;
import dev.ofatech.hytale.template.util.Preconditions;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class JsonDataStore implements DataStore {
    private final Path baseDirectory;

    public JsonDataStore(Path baseDirectory) {
        this.baseDirectory = Objects.requireNonNull(baseDirectory, "baseDirectory");
    }

    @Override
    public <T> Optional<T> load(String namespace, String key, Class<T> type) {
        Path target = resolvePath(namespace, key);
        if (Files.notExists(target)) {
            return Optional.empty();
        }

        try (Reader reader = Files.newBufferedReader(target, StandardCharsets.UTF_8)) {
            return Optional.ofNullable(JsonUtil.fromJson(reader, type));
        } catch (IOException ex) {
            throw new DataException("Failed to read data file: " + target, ex);
        } catch (RuntimeException ex) {
            throw new DataException("Failed to parse data file: " + target, ex);
        }
    }

    @Override
    public <T> void save(String namespace, String key, T value) {
        Path target = resolvePath(namespace, key);
        Path directory = target.getParent();
        try {
            Files.createDirectories(directory);
        } catch (IOException ex) {
            throw new DataException("Failed to create data directory: " + directory, ex);
        }

        Path tempFile = directory.resolve(target.getFileName() + "." + UUID.randomUUID() + ".tmp");
        try (Writer writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
            JsonUtil.toJson(writer, value);
        } catch (IOException ex) {
            throw new DataException("Failed to write data file: " + tempFile, ex);
        } catch (RuntimeException ex) {
            throw new DataException("Failed to serialize data file: " + tempFile, ex);
        }

        try {
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ex) {
            try {
                Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException moveEx) {
                throw new DataException("Failed to replace data file: " + target, moveEx);
            }
        } catch (IOException ex) {
            throw new DataException("Failed to replace data file: " + target, ex);
        }
    }

    @Override
    public boolean exists(String namespace, String key) {
        Path target = resolvePath(namespace, key);
        return Files.exists(target);
    }

    @Override
    public void delete(String namespace, String key) {
        Path target = resolvePath(namespace, key);
        try {
            Files.deleteIfExists(target);
        } catch (IOException ex) {
            throw new DataException("Failed to delete data file: " + target, ex);
        }
    }

    private Path resolvePath(String namespace, String key) {
        String safeNamespace = sanitizeSegment(namespace, "namespace");
        String safeKey = sanitizeSegment(key, "key");
        return baseDirectory.resolve(safeNamespace).resolve(safeKey + ".json");
    }

    private String sanitizeSegment(String segment, String label) {
        Preconditions.checkNotBlank(segment, label);
        String trimmed = segment.trim();

        if (trimmed.contains("..") || trimmed.contains("/") || trimmed.contains("\\")) {
            throw new DataException("Invalid " + label + " segment: " + segment);
        }

        return trimmed;
    }
}

