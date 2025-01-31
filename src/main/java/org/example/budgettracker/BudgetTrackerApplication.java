package org.example.budgettracker;

import org.example.budgettracker.client.consoleMenu.ConsoleMenu;
import org.example.budgettracker.model.entity.Role;
import org.example.budgettracker.model.entity.User;
import org.example.budgettracker.repository.BudgetRepository;
import org.example.budgettracker.repository.RoleRepository;
import org.example.budgettracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BudgetTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetTrackerApplication.class, args);

        // Start console menu
//        ConsoleMenu.main(args);
    }

    /*@Bean
    CommandLineRunner run(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // create admin and user
            Optional<Role> adminRoleOptional = roleRepository.findByAuthority("ADMIN");
            Optional<Role> userRoleOptional = roleRepository.findByAuthority("USER");

            if (adminRoleOptional.isPresent() && userRoleOptional.isPresent()) {
                return; // Both roles are already present
            }

            Role adminRole = adminRoleOptional.orElseGet(() -> roleRepository.save(new Role("ADMIN")));
            Role userRole = userRoleOptional.orElseGet(() -> roleRepository.save(new Role("USER")));

        // create an admin user
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("*****"))
                    .roles(new HashSet<>(List.of(adminRole)))
                    .build();

            userRepository.save(admin);

            // create a regular user
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("*****"))
                    .roles(new HashSet<>(List.of(userRole)))
                    .build();

            userRepository.save(user);*/

          /*  Budget budget = Budget.builder()
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
        };*/
}
