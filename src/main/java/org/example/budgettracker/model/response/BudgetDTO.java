package org.example.budgettracker.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.budgettracker.model.entity.Budget;

@AllArgsConstructor
@Data
public class BudgetDTO {
    private String name;
    private String startDate;
    private String endDate;
    private Double amount;
    private double remainingAmount;
    private double amountSpent;
    private Long userId;

    public static BudgetDTO fromBudget(Budget budget) {
        return new BudgetDTO(budget.getName(), budget.getStartDate(), budget.getEndDate(), budget.getAmount(), budget.getRemainingAmount(), budget.getAmountSpent(), budget.getUser().getId());
    }
}
