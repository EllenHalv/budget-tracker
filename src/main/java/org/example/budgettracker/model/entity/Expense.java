package org.example.budgettracker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    //private Long budgetId;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Budget budget;


}
