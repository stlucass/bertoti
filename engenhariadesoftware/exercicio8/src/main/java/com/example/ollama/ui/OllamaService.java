package com.example.ollama.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OllamaService {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String askOnce(String baseUrl, String model, String userQuestion) throws IOException, InterruptedException {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Modelo vazio. Informe o modelo (ex.: qwen3:1.7b).");
        }
        String host = normalizeHost(baseUrl);
        String url = host.endsWith("/") ? host + "api/chat" : host + "/api/chat";

        Map<String, Object> body = Map.of(
                "model", model,
                "stream", false,
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant. Answer in Portuguese when the user writes in Portuguese."),
                        Map.of("role", "user", "content", userQuestion)
                )
        );

        String json;
        try {
            json = MAPPER.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao serializar JSON", e);
        }

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(60))
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int sc = response.statusCode();
        if (sc >= 200 && sc < 300) {
            OllamaChatResponse chatResponse = MAPPER.readValue(response.body(), OllamaChatResponse.class);
            if (chatResponse != null && chatResponse.message != null) {
                String content = chatResponse.message.content == null ? "" : chatResponse.message.content.trim();
                return content;
            } else {
                return response.body();
            }
        } else {
            String bodyText = response.body();
            throw new IOException("HTTP " + sc + " - " + (bodyText==null? "(sem corpo)" : bodyText));
        }
    }

    private static String normalizeHost(String baseUrl) {
        String h = baseUrl == null ? "" : baseUrl.trim();
        if (h.isEmpty()) h = "http://localhost:11434";
        if (!h.startsWith("http://") && !h.startsWith("https://")) {
            h = "http://" + h;
        }
        return h;
    }
}
