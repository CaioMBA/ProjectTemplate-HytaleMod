package dev.ofatech.hytale.template.api;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public final class TemplateApiProvider {
    private static final AtomicReference<TemplateApi> API = new AtomicReference<>();

    private TemplateApiProvider() {
    }

    public static void register(TemplateApi instance) {
        Objects.requireNonNull(instance, "instance");
        if (!API.compareAndSet(null, instance)) {
            throw new IllegalStateException("TemplateApi is already registered.");
        }
    }

    public static TemplateApi get() {
        TemplateApi api = API.get();
        if (api == null) {
            throw new IllegalStateException("TemplateApi is not available.");
        }
        return api;
    }

    public static boolean isAvailable() {
        return API.get() != null;
    }

    public static void unregister() {
        API.set(null);
    }
}

