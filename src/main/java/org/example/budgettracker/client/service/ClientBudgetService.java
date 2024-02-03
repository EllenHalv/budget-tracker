package org.example.budgettracker.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.budgettracker.model.Budget;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
// TODO Ccalls the API (gets the data, do whatever you want with it)
public class ClientBudgetService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void createBudget(Budget budget) {
        try {
            String url = "http://localhost:8080/api/budget";

            // Serialize the Budget object to JSON
            HttpResponse<String> response = sendRequest(budget, url);

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

    private HttpResponse<String> sendRequest(Budget budget, String url) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        String jsonBudget = gson.toJson(budget);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBudget))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateBudget(int id) {
        try {
            //convert id to Long
            String url = "http://localhost:8080/api/budget/" + (long) id;

            // Serialize the Budget object to JSON
            Gson gson = new Gson();
            String jsonBudget = gson.toJson(id);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBudget))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Budget updated successfully: " + response.body());
            } else {
                System.out.println("Failed to update budget. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBudget(int id) {
        try {
            String url = "http://localhost:8080/api/budget/" + id;

            // Create an HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response status code
            if (response.statusCode() == 200) {
                System.out.println("Budget deleted successfully");
            } else {
                System.out.println("Failed to delete budget. Status code: " + response.statusCode());
            }
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBudgetName(Budget budget, String newName) {
        budget.setName(newName);

        try {
            String url = "http://localhost:8080/api/budget/" + budget.getId();

            // Serialize the Budget object to JSON
            Gson gson = new Gson();
            String jsonBudget = gson.toJson(budget);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBudget))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Budget updated successfully: " + response.body());
            } else {
                System.out.println("Failed to update budget. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBudgetAmount(Budget budget, double newAmount) {
        budget.setAmount(newAmount);

        try {
            String url = "http://localhost:8080/api/budget/" + budget.getId();

            // Serialize the Budget object to JSON
            Gson gson = new Gson();
            String jsonBudget = gson.toJson(budget);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBudget))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Budget updated successfully: " + response.body());
            } else {
                System.out.println("Failed to update budget. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBudgetStartDate(Budget budget, String newStartDate) {
        budget.setStartDate(newStartDate);

        try {
            String url = "http://localhost:8080/api/budget/" + budget.getId();

            // Serialize the Budget object to JSON
            Gson gson = new Gson();
            String jsonBudget = gson.toJson(budget);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBudget))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Budget updated successfully: " + response.body());
            } else {
                System.out.println("Failed to update budget. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBudgetEndDate(Budget budget, String newEndDate) {
        budget.setEndDate(newEndDate);

        try {
            String url = "http://localhost:8080/api/budget/" + budget.getId();

            // Serialize the Budget object to JSON
            Gson gson = new Gson();
            String jsonBudget = gson.toJson(budget);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBudget))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Budget updated successfully: " + response.body());
            } else {
                System.out.println("Failed to update budget. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
