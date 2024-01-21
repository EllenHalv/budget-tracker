package org.example.budgettracker.api;

import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.service.BudgetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public String createBudget(@RequestBody Budget budget) {
        return budgetService.save(budget).toString();
    }

    @GetMapping
    public String getAllBudgets() {
        return budgetService.findAll().toString();
    }

    @GetMapping("/{id}")
    public String getBudgetById(@PathVariable Long id) {
        return budgetService.findById(id).toString();
    }

    @PutMapping("/{id}")
    public String updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        return budgetService.updateBudget(id, budget).toString();
    }

    @DeleteMapping("/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgetService.deleteById(id);
        return "Budget deleted";
    }
}
