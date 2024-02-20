package org.example.budgettracker;

import org.example.budgettracker.client.consoleMenu.ConsoleMenu;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.repository.BudgetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class BudgetTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetTrackerApplication.class, args);

        // Start console menu
        ConsoleMenu.main(args);
    }

    /*@Bean
    CommandLineRunner run(BudgetRepository budgetRepository) {
        return args -> {
            Budget budget = Budget.builder()
            .name("My budget")
            .amount(1000.0)
            .startDate("2021-01-01")
            .endDate("2021-12-31")
            .expenses(new ArrayList<Expense>())
            .remainingAmount(1000.0)
            .amountSpent(0.0)
            .build();

            budgetRepository.save(budget);

            Expense expense = Expense.builder()
            .name("My expense")
            .amount(100.0)
            .date("2021-01-01")
            .budgetId(budget.getId())
            .build();

            budget.getExpenses().add(expense);
            budget.setAmountSpent(budget.getAmountSpent() + expense.getAmount());
            budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());

            budgetRepository.save(budget);
        };
    }*/
}
