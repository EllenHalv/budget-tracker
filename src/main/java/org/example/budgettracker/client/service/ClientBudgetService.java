package org.example.budgettracker.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.budgettracker.model.Budget;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientBudgetService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void createBudget(Budget budget) {
        try {
            String apiUrl = "http://localhost:8080/api/budget"; // Adjust the URL based on your API endpoint
            String requestBody = objectMapper.writeValueAsString(budget);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Budget created successfully: " + response.body());
            } else {
                System.out.println("Failed to create budget. Status code: " + response.statusCode());
                System.out.println("Response body: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
