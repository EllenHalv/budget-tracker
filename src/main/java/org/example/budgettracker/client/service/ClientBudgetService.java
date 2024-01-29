package org.example.budgettracker.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
            String url = "http://localhost:8080/api/budget";

            // Serialize the Budget object to JSON
            Gson gson = new Gson();
            String jsonBudget = gson.toJson(budget);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBudget))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Budget created successfully: " + response.body());
            } else {
                System.out.println("Failed to create budget. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateBudget(Budget budget) {
        /*try {
            String url = "http://localhost:8080/api/budget/" + budget.getId();
        }*/
    }

    public Budget getCurrentBudget() {
        try {
            String url = "http://localhost:8080/api/budget/current";

            // Create an HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response status code
            if (response.statusCode() == 200) {
                // De-serialize the JSON response into a Budget object
                Gson gson = new Gson();
                Budget budget = gson.fromJson(response.body(), Budget.class);
                return budget;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}