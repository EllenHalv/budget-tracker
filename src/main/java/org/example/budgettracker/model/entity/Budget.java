package org.example.budgettracker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(unique = true)
    private String name;
    @Setter
    private Double amount;
    private String startDate;
    @Setter
    private String endDate;
    @JsonIgnore
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();
    private double amountSpent;
    private double remainingAmount;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;
    /*@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    public Budget(String name, Double amount, String startDate, String endDate, List<Expense> expenses, double amountSpent, double remainingAmount) {
        this.name = name;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenses = expenses;
        this.amountSpent = amountSpent;
        this.remainingAmount = remainingAmount;
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