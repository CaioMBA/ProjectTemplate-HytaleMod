package dev.ofatech.hytale.template.integration;

import dev.ofatech.hytale.template.bootstrap.PluginContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class IntegrationRegistry {
    private final List<PluginIntegration> integrations = new ArrayList<>();
    private final List<IntegrationStatus> statuses = new ArrayList<>();

    public void register(PluginIntegration integration) {
        integrations.add(Objects.requireNonNull(integration, "integration"));
    }

    public void enableAvailable(PluginContext context) {
        statuses.clear();
        for (PluginIntegration integration : integrations) {
            boolean available = integration.isAvailable(context);
            if (available) {
                integration.enable(context);
            }

            String error = null;
            boolean enabled = false;
            if (integration instanceof OptionalPluginIntegration optional) {
                enabled = optional.isEnabled();
                error = optional.error();
            }

            statuses.add(new IntegrationStatus(integration.id(), available, enabled, error));
        }
    }

    public void disableAll() {
        for (PluginIntegration integration : integrations) {
            integration.disable();
        }
    }

    public List<IntegrationStatus> statuses() {
        return Collections.unmodifiableList(statuses);
    }
}

