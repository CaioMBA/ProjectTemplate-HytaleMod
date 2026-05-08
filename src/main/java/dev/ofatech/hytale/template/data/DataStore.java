package dev.ofatech.hytale.template.data;

import java.util.Optional;

public interface DataStore {
    <T> Optional<T> load(String namespace, String key, Class<T> type);

    <T> void save(String namespace, String key, T value);

    boolean exists(String namespace, String key);

    void delete(String namespace, String key);
}

