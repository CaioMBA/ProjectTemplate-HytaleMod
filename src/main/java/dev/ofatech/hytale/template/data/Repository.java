package dev.ofatech.hytale.template.data;

import java.util.Objects;
import java.util.Optional;

public final class Repository<T> {
    private final String namespace;
    private final Class<T> type;
    private final DataStore store;

    public Repository(String namespace, Class<T> type, DataStore store) {
        this.namespace = Objects.requireNonNull(namespace, "namespace");
        this.type = Objects.requireNonNull(type, "type");
        this.store = Objects.requireNonNull(store, "store");
    }

    public Optional<T> load(String key) {
        return store.load(namespace, key, type);
    }

    public void save(String key, T value) {
        store.save(namespace, key, value);
    }

    public boolean exists(String key) {
        return store.exists(namespace, key);
    }

    public void delete(String key) {
        store.delete(namespace, key);
    }
}

