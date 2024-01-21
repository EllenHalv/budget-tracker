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

}
