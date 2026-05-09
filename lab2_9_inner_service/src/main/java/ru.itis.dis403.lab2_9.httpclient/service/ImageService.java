package ru.itis.dis403.lab2_9.httpclient.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class ImageService {

    private final List<String> imgList = Collections.synchronizedList(new ArrayList<>());
    private final HttpClient httpClient;
    private final String serverUrl = "http://127.0.0.1:5000/resize";

    public ImageService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<String> getImgList() {
        return new ArrayList<>(imgList);
    }

    public void processImage(byte[] imageBytes) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/octet-stream")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(imageBytes))
                    .build();

            CompletableFuture<HttpResponse<byte[]>> futureResponse =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());

            futureResponse
                    .thenAccept(response -> {
                        int statusCode = response.statusCode();
                        System.out.println("HTTP Статус: " + statusCode);
                        if (statusCode == 200) {
                            imgList.add(Base64.getEncoder().encodeToString(response.body()));
                        } else {
                            String errorBody = new String(response.body());
                            System.err.println("Ошибка сервера: " + errorBody);
                        }
                    })
                    .exceptionally(ex -> {
                        System.err.println("HTTP ошибка: " + ex.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Синхронный метод для замера времени
    public byte[] processImageSync(byte[] imageBytes) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/octet-stream")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(imageBytes))
                    .build();

            long startTime = System.currentTimeMillis();
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            long endTime = System.currentTimeMillis();

            System.out.println("HTTP запрос выполнен за: " + (endTime - startTime) + " ms");
            System.out.println("HTTP Статус: " + response.statusCode());

            if (response.statusCode() == 200) {
                String imgBase64 = Base64.getEncoder().encodeToString(response.body());
                imgList.add(imgBase64);
                return response.body();
            } else {
                String errorBody = new String(response.body());
                System.err.println("Ошибка сервера: " + errorBody);
            }
        } catch (Exception e) {
            System.err.println("HTTP ошибка: " + e.getMessage());
        }
        return null;
    }
}