package org.example.budgettracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expenses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Expense {
    private String name;
    private double amount;
    private String date;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    public Expense(String name, double amount, String date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

}
