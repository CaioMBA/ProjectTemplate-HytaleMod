package dev.ofatech.hytale.template.data;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonDataStoreTest {

    @Test
    void saveAndLoadRoundTrip() throws Exception {
        Path tempDir = Files.createTempDirectory("datastore-test");
        JsonDataStore store = new JsonDataStore(tempDir);

        SampleData input = new SampleData("alpha", 42);
        store.save("samples", "one", input);

        SampleData output = store.load("samples", "one", SampleData.class).orElseThrow();
        assertEquals(input.name(), output.name());
        assertEquals(input.value(), output.value());
    }

    @Test
    void rejectsPathTraversal() {
        JsonDataStore store = new JsonDataStore(Path.of("data"));
        SampleData input = new SampleData("alpha", 42);

        assertThrows(DataException.class, () -> store.save("../bad", "one", input));
        assertThrows(DataException.class, () -> store.save("good", "..", input));
    }

    @Test
    void existsReturnsFalseWhenMissing() {
        JsonDataStore store = new JsonDataStore(Path.of("data"));
        assertFalse(store.exists("samples", "missing"));
    }

    private record SampleData(String name, int value) {
    }
}

