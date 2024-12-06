package org.example.budgettracker.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.budgettracker.model.entity.Budget;
import org.example.budgettracker.model.entity.Expense;

import java.util.List;

@AllArgsConstructor
@Data
public class BudgetDTO {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private Double amount;
    private double remainingAmount;
    private double amountSpent;
    private List<Expense> expenses;
    private Long userId;

    public static BudgetDTO fromBudget(Budget budget) {
        return new BudgetDTO(
                budget.getId(),
                budget.getName(),
                budget.getStartDate(),
                budget.getEndDate(),
                budget.getAmount(),
                budget.getRemainingAmount(),
                budget.getAmountSpent(),
                budget.getExpenses(),
                budget.getUser().getId());
    }
}
