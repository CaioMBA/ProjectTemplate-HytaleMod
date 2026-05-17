package net.ofatech.hytale.template.api.registry;

import java.util.Optional;

public interface ApiRegistry {
    <T> void register(Class<T> apiType, T implementation);

    <T> Optional<T> resolve(Class<T> apiType);

    <T> T require(Class<T> apiType);
}
