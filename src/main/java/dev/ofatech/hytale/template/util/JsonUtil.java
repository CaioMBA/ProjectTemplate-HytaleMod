package dev.ofatech.hytale.template.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.Instant;
import java.lang.reflect.Type;

import java.io.Reader;
import java.io.Writer;

public final class JsonUtil {
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(
            Instant.class,
            (JsonSerializer<Instant>) (value, type, context) -> new JsonPrimitive(value.toString())
        )
        .registerTypeAdapter(
            Instant.class,
            (JsonDeserializer<Instant>) (json, type, context) -> Instant.parse(json.getAsString())
        )
        .setPrettyPrinting()
        .create();

    private JsonUtil() {
    }

    public static String toJson(Object value) {
        return GSON.toJson(value);
    }

    public static void toJson(Writer writer, Object value) {
        GSON.toJson(value, writer);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(Reader reader, Class<T> type) {
        return GSON.fromJson(reader, type);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(Reader reader, Type type) {
        return GSON.fromJson(reader, type);
    }
}


