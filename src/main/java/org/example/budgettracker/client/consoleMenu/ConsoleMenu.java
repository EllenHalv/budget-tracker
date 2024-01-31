package org.example.budgettracker.client.consoleMenu;

import org.example.budgettracker.client.service.ClientBudgetService;
import org.example.budgettracker.client.service.ClientExpenseService;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMenu {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ClientBudgetService cbs = new ClientBudgetService();
        ClientExpenseService ces = new ClientExpenseService();

        while (true) {
            // print the current budget
            System.out.println(
                    "\nCurrent Budget:"
                            + "\n----------------------------------"
                            + "\n" + cbs.getCurrentBudget()
                            + "\n----------------------------------"
            );

            System.out.println();
            System.out.println("==== Budget Tracker Menu ====");
            System.out.println("1. Create new budget");
            System.out.println("2. Add new expense to current budget");
            System.out.println("3. Update information of current budget");
            System.out.println("4. Delete a budget");
            System.out.println("5. View all budgets"); // TODO show in a list, clickable for more details or editing of budget
            System.out.println("6. Go to Expense Menu -->");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (choice) { // TODO create input validation methods for name, amount, startDate, endDate
                case 1:
                    System.out.println("Enter budget details:");
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Amount: ");
                    //double amount = scanner.nextDouble(); buggy
                    double amount = Double.parseDouble(sc.nextLine());
                    System.out.println("Start Date (YYYY-MM-DD): ");
                    String startDate = sc.nextLine();
                    System.out.println("End Date (YYYY-MM-DD): ");
                    String endDate = sc.nextLine();

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
                    cbs.createBudget(budget);
                    break;

                case 2:
                    System.out.println("Enter expense details:");
                    System.out.print("Name: ");
                    String expenseName = sc.nextLine();
                    System.out.print("Amount: ");
                    double expenseAmount = Double.parseDouble(sc.nextLine());
                    System.out.println("Date (YYYY-MM-DD): ");
                    String expenseDate = sc.nextLine();

                    // in the current budget...
                    Budget currentBudget = cbs.getCurrentBudget();

                    // TODO just create the expense object. send the expense and budget ID!!!

                    // Create an Expense object
                    if (currentBudget!=null) {
                    Expense expense = Expense.builder()
                            .name(expenseName)
                            .amount(expenseAmount)
                            .date(expenseDate)
                            .budgetId(currentBudget.getId())
                            .build();

                    // send HTTP request to add an expense to the current budget
                    ces.addExpenseToCurrentBudget(expense);

                    } else {
                        System.out.println("No current budget. Please create a budget first.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }
}
