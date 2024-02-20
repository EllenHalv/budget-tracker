package org.example.budgettracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.lang.reflect.Type;

@Entity
@Table(name = "expenses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Expense {
    private String name;
    private Double amount;
    private String date;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long id;
    private Long budgetId;

    public Expense(String name, Double amount, String date, Long budgetId) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.budgetId = budgetId;
    }

}
