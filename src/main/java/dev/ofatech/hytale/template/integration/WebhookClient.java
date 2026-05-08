package dev.ofatech.hytale.template.integration;

import dev.ofatech.hytale.template.util.JsonUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public final class WebhookClient {
    private final HttpClient client;
    private final Duration timeout;

    public WebhookClient(Duration timeout) {
        this.client = HttpClient.newBuilder().connectTimeout(timeout).build();
        this.timeout = timeout;
    }

    public void sendJson(URI endpoint, Map<String, Object> payload) {
        Objects.requireNonNull(endpoint, "endpoint");
        Objects.requireNonNull(payload, "payload");

        String json = JsonUtil.toJson(payload);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(endpoint)
            .timeout(timeout)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new IllegalStateException("Webhook responded with status " + response.statusCode());
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Webhook request failed", ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Webhook request interrupted", ex);
        }
    }
}


