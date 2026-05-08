package dev.ofatech.hytale.template.integration.rest;

public record ApiResponse(boolean success, Object data, String error) {
    public static ApiResponse success(Object data) {
        return new ApiResponse(true, data, null);
    }

    public static ApiResponse failure(String error) {
        return new ApiResponse(false, null, error);
    }
}

