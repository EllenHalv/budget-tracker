package org.example.budgettracker.client.consoleMenu;

import org.example.budgettracker.client.service.ClientBudgetService;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ClientBudgetService clientBudgetService = new ClientBudgetService();

        while (true) {
            System.out.println("==== Console Menu ====");
            System.out.println("1. Create Budget");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter budget details:");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Amount: ");
                    //double amount = scanner.nextDouble();
                    double amount = Double.parseDouble(scanner.nextLine());
                    System.out.println("Start Date (in format '2021-01-01'): ");
                    String startDate = scanner.nextLine();
                    System.out.println("End Date (in format '2021-01-01'): ");
                    String endDate = scanner.nextLine();


                    // Create a Budget object
                    Budget budget = Budget.builder()
                            .name(name)
                            .amount(amount)
                            .amountSpent(0)
                            .remainingAmount(amount)
                            .startDate(startDate)
                            .endDate(endDate)
                            .expenses(new ArrayList<>())
                            .build();

                    // Send HTTP request to create a budget
                    clientBudgetService.createBudget(budget);
                    break;

                case 2:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }
}
