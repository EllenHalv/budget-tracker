package org.example.budgettracker.client.consoleMenu;

import org.example.budgettracker.client.service.ClientBudgetService;
import org.example.budgettracker.client.service.ClientExpenseService;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        ClientBudgetService cbs = new ClientBudgetService();
        ClientExpenseService ces = new ClientExpenseService();
        Budget currentBudget = cbs.getCurrentBudget();
        List<Expense> currentExpenses = currentBudget.getExpenses();

        while (true) {
            // print the current budget
            System.out.println("\nWelcome to Budget Tracker!");
            System.out.println();
            System.out.println(
                    "\nYour current Budget:"
                            + "\n----------------------------------"
                            + "\n" + currentBudget
                            + "\n----------------------------------"
            );

            System.out.println();
            System.out.println("==== Budget Menu ====");
            System.out.println("1. Create new budget");
            System.out.println("2. Add new expense to current budget");
            System.out.println("3. Update information of current budget");
            System.out.println("4. Delete a budget");
            System.out.println("5. View all budgets"); // TODO show in a list, + clickable for more details or editing of budget
            System.out.println("6. Go to ==== Expense Menu ==== --->");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (choice) { // TODO create input validation methods for name, amount, startDate, endDate
                // Create a new budget
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

                    // Add new expense to current budget
                case 2:
                    System.out.println("Enter expense details:");
                    System.out.print("Name: ");
                    String expenseName = sc.nextLine();
                    System.out.print("Amount: ");
                    double expenseAmount = Double.parseDouble(sc.nextLine());
                    System.out.println("Date (YYYY-MM-DD): ");
                    String expenseDate = sc.nextLine();

                    // just create the expense object. send the expense and budget ID!!!

                    // Create an Expense object
                    Expense expense = Expense.builder()
                            .name(expenseName)
                            .amount(expenseAmount)
                            .date(expenseDate)
                            .budgetId(currentBudget.getId())
                            .build();

                    // send HTTP request to add an expense to the current budget
                    ces.addExpenseToCurrentBudget(expense);

                    break;

                    // Update information of current budget
                case 3:
                    System.out.println("1. Update name");
                    System.out.println("2. Update amount");
                    System.out.println("3. Update start date");
                    System.out.println("4. Update end date");
                    System.out.println("5. Update expense");
                    System.out.println("0. Back to main menu");
                    System.out.println();
                    System.out.print("Enter your choice: ");
                    int updateChoice = Integer.parseInt(sc.nextLine());

                    switch (updateChoice) {
                        case 1:
                            System.out.print("Enter new name: ");
                            String newName = sc.nextLine();
                            cbs.updateBudgetName(currentBudget, newName);
                            break;
                        case 2:
                            System.out.print("Enter new amount: ");
                            double newAmount = Double.parseDouble(sc.nextLine());
                            cbs.updateBudgetAmount(currentBudget, newAmount);
                            break;
                        case 3:
                            System.out.print("Enter new start date (YYYY-MM-DD): ");
                            String newStartDate = sc.nextLine();
                            cbs.updateBudgetStartDate(currentBudget, newStartDate);
                            break;
                        case 4:
                            System.out.print("Enter new end date (YYYY-MM-DD): ");
                            String newEndDate = sc.nextLine();
                            cbs.updateBudgetEndDate(currentBudget, newEndDate);
                            break;
                        case 5:
                            for (Expense e : currentExpenses) {
                                System.out.println(e.getId() + "." + e);
                            }
                            System.out.println("Enter the expense ID to update:");
                            int expID = Integer.parseInt(sc.nextLine());

                            for (Expense e : currentExpenses) {
                                if (e.getId() == expID) {
                                    System.out.println(e);
                                    System.out.println("Enter new expense details:");
                                    System.out.print("Name: ");
                                    String updatedExpenseName = sc.nextLine();
                                    System.out.print("Amount: ");
                                    double updatedExpenseAmount = Double.parseDouble(sc.nextLine());
                                    System.out.println("Date (YYYY-MM-DD): ");
                                    String updatedExpenseDate = sc.nextLine();
                                    Expense updatedExpense = Expense.builder()
                                            .name(updatedExpenseName)
                                            .amount(updatedExpenseAmount)
                                            .date(updatedExpenseDate)
                                            .budgetId(e.getBudgetId())
                                            .id(e.getId())
                                            .build();

                                    currentExpenses.remove(e);
                                    currentExpenses.add(updatedExpense);
                                    currentBudget.setExpenses(currentExpenses);
                                    ces.updateExpense(updatedExpense);
                                }
                            }
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                            break;
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
