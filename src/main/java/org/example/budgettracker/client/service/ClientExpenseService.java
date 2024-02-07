package org.example.budgettracker.client.service;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ClientExpenseService {

    private final HttpClient httpClient = HttpClient.newHttpClient();


    // TODO just create the expense object. send the expense (and its containing budget ID!!!
    public void addExpenseToCurrentBudget(Expense expense) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
/*
            String url = "http://localhost:8080/api/expense";
*/
            final HttpPost httpPost = new HttpPost("http://localhost:8080/api/expenses");


            Gson gsonExpense = new Gson();
            StringEntity entity = new StringEntity(gsonExpense.toJson(expense));

            String jsonExpense = gsonExpense.toJson(expense);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader("Authorization", "Bearer ");

            /*HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonExpense))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
*/
            httpClient.execute(httpPost);



            // Handle the response
            /*if (response.statusCode() == 200) {
                System.out.println("Expense added successfully: " + response.body());
            } else {
                System.out.println("Failed to add expense. Status code: " + response.statusCode() + " " + response.body());
            }*/
        /*} catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);*/
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

    public void updateExpense(Expense updatedExpense) {
        try {
            String url = "http://localhost:8080/api/expenses/" + updatedExpense.getId();

            // Serialize the Budget object to JSON
            Gson gson = new Gson();
            String jsonExpense = gson.toJson(updatedExpense);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonExpense))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                System.out.println("Expense updated successfully: " + response.body());
            } else {
                System.out.println("Failed to update expense. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
