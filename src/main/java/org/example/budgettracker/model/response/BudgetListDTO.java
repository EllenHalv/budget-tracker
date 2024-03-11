/*
package org.example.budgettracker.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.budgettracker.model.entity.Budget;

import java.util.List;

@AllArgsConstructor
@Data
public class BudgetListDTO {
    private List<BudgetDTO> budgets;

    public static List<BudgetListDTO> fromBudgetList(List<Budget> budgetsList) {
        return List.of(new BudgetListDTO(budgetsList.stream().map(BudgetDTO::fromBudget).toList()));
    }
}
*/
