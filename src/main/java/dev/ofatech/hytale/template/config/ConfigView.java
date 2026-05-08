package dev.ofatech.hytale.template.config;

public interface ConfigView {
    boolean debug();

    String language();

    String storageType();

    String storageDirectory();

    boolean apiEnabled();

    String apiHost();

    int apiPort();

    boolean apiAllowReloadEndpoint();

    boolean exampleCommandEnabled();

    boolean playerWelcomeMessageEnabled();

    boolean restApiEnabled();
}

