package dev.ofatech.hytale.template.api;

import dev.ofatech.hytale.template.api.services.PlayerDataApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateApiProviderTest {

    @AfterEach
    void cleanup() {
        TemplateApiProvider.unregister();
    }

    @Test
    void registerAndGet() {
        assertFalse(TemplateApiProvider.isAvailable());

        TemplateApiProvider.register(new StubApi());
        assertTrue(TemplateApiProvider.isAvailable());
        assertNotNull(TemplateApiProvider.get());
    }

    @Test
    void cannotRegisterTwice() {
        TemplateApiProvider.register(new StubApi());
        assertThrows(IllegalStateException.class, () -> TemplateApiProvider.register(new StubApi()));
    }

    @Test
    void getThrowsWhenUnavailable() {
        TemplateApiProvider.unregister();
        assertThrows(IllegalStateException.class, TemplateApiProvider::get);
    }

    private static final class StubApi implements TemplateApi {
        @Override
        public String pluginId() {
            return "stub";
        }

        @Override
        public String version() {
            return "0.0.0";
        }

        @Override
        public ConfigView config() {
            return new ConfigView() {
                @Override
                public boolean debug() {
                    return false;
                }

                @Override
                public String language() {
                    return "en_us";
                }

                @Override
                public String storageType() {
                    return "json";
                }

                @Override
                public boolean apiEnabled() {
                    return false;
                }

                @Override
                public boolean exampleCommandEnabled() {
                    return true;
                }

                @Override
                public boolean playerWelcomeMessageEnabled() {
                    return true;
                }

                @Override
                public boolean restApiEnabled() {
                    return false;
                }
            };
        }

        @Override
        public PlayerDataApi players() {
            return playerId -> java.util.Optional.empty();
        }
    }
}

