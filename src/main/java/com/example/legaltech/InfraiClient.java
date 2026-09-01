package com.example.legaltech;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class InfraiClient {
    private final HttpClient http = HttpClient.newHttpClient();
    private final String key;
    private final String baseUrl;

    public InfraiClient(String baseUrl, String key) {
        this.baseUrl = baseUrl;
        this.key = key;
    }

    public String send(String to, String subject, String html) throws IOException, InterruptedException {
        String body = "{\"to\":\"" + esc(to) + "\",\"subject\":\"" + esc(subject)
                + "\",\"html\":\"" + esc(html) + "\"}";
        HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/v1/email/send"))
                .timeout(Duration.ofSeconds(20)).header("Authorization", "Bearer " + key)
                .header("Content-Type", "application/json").method("POST", HttpRequest.BodyPublishers.ofString(body)).build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        String envelope = response.body();
        if (!envelope.contains("\"ok\":true")) throw new IOException("Infrai request rejected: " + envelope);
        return envelope;
    }

    private static String esc(String value) { return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " "); }
}
