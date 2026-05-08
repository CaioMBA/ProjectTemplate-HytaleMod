package dev.ofatech.hytale.template.integration.rest;

public class RestApiException extends RuntimeException {
    private final int status;

    public RestApiException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int status() {
        return status;
    }
}

