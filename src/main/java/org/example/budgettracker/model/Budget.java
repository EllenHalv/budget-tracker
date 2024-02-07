package org.example.budgettracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budgets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Budget {
    @NotNull
    @NotEmpty
    private String name;
    private double amount;
    private String startDate;
    private String endDate;
    @OneToMany(mappedBy = "budgetId", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();
    private double amountSpent;
    private double remainingAmount;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;

    public Budget(String name, double amount, String startDate, String endDate, List<Expense> expenses, double amountSpent, double remainingAmount) {
        this.name = name;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenses = expenses;
        this.amountSpent = amountSpent;
        this.remainingAmount = remainingAmount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Budget amount cannot be negative");
        }
        this.amount = amount;
    }

    public void setEndDate(String endDate) {
        if (endDate.compareTo(this.startDate) < 0) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Budget: " + this.name + "\n" +
                "Amount: " + this.amount + "\n" +
                "Start Date: " + this.startDate + "\n" +
                "End Date: " + this.endDate + "\n" +
                "Expenses: " + this.expenses + "\n" +
                "Amount Spent: " + this.amountSpent + "\n" +
                "Remaining Amount: " + this.remainingAmount;
    }

    /*public int getBudgetDaysLeft() {
        LocalDate today = LocalDate.now();
        LocalDate endDate = LocalDate.parse(this.endDate);
        Period period = Period.between(today, endDate);
        return period.getDays();
    }*/

    public String budgetStatusToString() {
        return "Budget: " + this.name + "\n" +
                "Amount: " + this.amount + "\n" +
                /*"Days left: " + this.getBudgetDaysLeft() + "\n" +*/
                "Amount spent so far: " + this.amountSpent + "\n" +
                "Remaining Amount: " + this.remainingAmount;
    }
}