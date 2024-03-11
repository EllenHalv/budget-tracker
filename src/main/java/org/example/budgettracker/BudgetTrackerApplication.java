package org.example.budgettracker;

import org.example.budgettracker.client.consoleMenu.ConsoleMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BudgetTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetTrackerApplication.class, args);

        // Start console menu
        ConsoleMenu.main(args);
    }

    /*@Bean
    CommandLineRunner run(BudgetRepository budgetRepository, UserRepository userRepository) {
        return args -> {
            User user = User.builder()
                    .username("ellen")
                    .password("12345")
                    .role(Role.USER)
                    .build();

            userRepository.save(user);

            Budget budget = Budget.builder()
            .name("My budget")
            .amount(1000.0)
            .startDate("2021-01-01")
            .endDate("2021-12-31")
            .expenses(new ArrayList<Expense>())
            .remainingAmount(1000.0)
            .amountSpent(0.0)
            .user(user)
            .build();

            budgetRepository.save(budget);

            Expense expense = Expense.builder()
            .name("My expense")
            .amount(100.0)
            .date("2021-01-01")
            .budget(budget)
            .build();

            budget.getExpenses().add(expense);
            budget.setAmountSpent(budget.getAmountSpent() + expense.getAmount());
            budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());

            budgetRepository.save(budget);
        };
    }*/
}
