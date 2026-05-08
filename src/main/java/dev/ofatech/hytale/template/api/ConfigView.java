package dev.ofatech.hytale.template.api;

public interface ConfigView {
    boolean debug();

    String language();

    String storageType();

    boolean apiEnabled();

    boolean exampleCommandEnabled();

    boolean playerWelcomeMessageEnabled();

    boolean restApiEnabled();
}

