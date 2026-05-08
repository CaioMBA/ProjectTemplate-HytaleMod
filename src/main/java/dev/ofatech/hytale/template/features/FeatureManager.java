package dev.ofatech.hytale.template.features;

import dev.ofatech.hytale.template.bootstrap.PluginContext;
import dev.ofatech.hytale.template.config.ConfigManager;
import dev.ofatech.hytale.template.config.FeatureConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class FeatureManager {
    private final ConfigManager configManager;
    private final List<Feature> features = new ArrayList<>();
    private final Map<String, FeatureStatus> statusById = new HashMap<>();

    public FeatureManager(ConfigManager configManager) {
        this.configManager = Objects.requireNonNull(configManager, "configManager");
    }

    public void register(Feature feature) {
        features.add(Objects.requireNonNull(feature, "feature"));
    }

    public void enableConfigured(PluginContext context) {
        statusById.clear();
        FeatureConfig config = configManager.config().features();

        for (Feature feature : features) {
            boolean desired = desiredState(feature, config);
            if (!desired) {
                statusById.put(
                    feature.id(),
                    new FeatureStatus(feature.id(), false, feature.enabledByDefault(), null)
                );
                continue;
            }

            try {
                feature.enable(context);
                statusById.put(
                    feature.id(),
                    new FeatureStatus(feature.id(), true, feature.enabledByDefault(), null)
                );
            } catch (Exception ex) {
                String error = ex.getMessage() == null ? "Enable failed" : ex.getMessage();
                statusById.put(
                    feature.id(),
                    new FeatureStatus(feature.id(), false, feature.enabledByDefault(), error)
                );
            }
        }
    }

    public void disableAll(PluginContext context) {
        for (Feature feature : features) {
            try {
                feature.disable(context);
            } catch (Exception ignored) {
                // Feature disable should not break shutdown.
            }
        }
    }

    public void refresh(PluginContext context) {
        disableAll(context);
        enableConfigured(context);
    }

    public boolean isEnabled(String id) {
        FeatureStatus status = statusById.get(id);
        return status != null && status.enabled();
    }

    public List<FeatureStatus> statuses() {
        return Collections.unmodifiableList(new ArrayList<>(statusById.values()));
    }

    private boolean desiredState(Feature feature, FeatureConfig config) {
        String id = feature.id();
        if ("example-command".equals(id)) {
            return config.exampleCommand();
        }
        if ("player-welcome".equals(id)) {
            return config.playerWelcomeMessage();
        }
        if ("rest-api".equals(id)) {
            return config.restApi();
        }
        return feature.enabledByDefault();
    }
}

