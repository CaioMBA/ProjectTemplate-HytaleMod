package dev.ofatech.hytale.template.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonDataStoreTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadRoundTrip() throws Exception {
        JsonDataStore store = new JsonDataStore(tempDir);

        SampleData input = new SampleData("alpha", 42);
        store.save("samples", "one", input);

        SampleData output = store.load("samples", "one", SampleData.class).orElseThrow();
        assertEquals(input.name(), output.name());
        assertEquals(input.value(), output.value());
    }

    @Test
    void missingObjectReturnsEmpty() {
        JsonDataStore store = new JsonDataStore(tempDir);
        assertTrue(store.load("samples", "missing", SampleData.class).isEmpty());
    }

    @Test
    void deleteRemovesObject() {
        JsonDataStore store = new JsonDataStore(tempDir);
        SampleData input = new SampleData("alpha", 42);
        store.save("samples", "one", input);

        store.delete("samples", "one");

        assertFalse(store.exists("samples", "one"));
        assertTrue(store.load("samples", "one", SampleData.class).isEmpty());
    }

    @Test
    void rejectsPathTraversal() {
        JsonDataStore store = new JsonDataStore(tempDir);
        SampleData input = new SampleData("alpha", 42);

        assertThrows(DataException.class, () -> store.save("../bad", "one", input));
        assertThrows(DataException.class, () -> store.save("good", "..", input));
    }


    private record SampleData(String name, int value) {
    }
}

