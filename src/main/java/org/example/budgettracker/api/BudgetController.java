package org.example.budgettracker.api;

import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO should call the service layer
@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public Budget save(@RequestBody Budget budget) {
        return budgetService.save(budget);
    }

    @GetMapping
    public List<Budget> findAll() {
        return budgetService.findAll();
    }

    @GetMapping("/{id}")
    public Budget findById(@PathVariable Long id) {
        return budgetService.findById(id);
    }

    // for updating info about the budget or its expenses. not for adding expenses
    @PutMapping("/{id}") // spara expense i budget och spara expense i db
    public Budget update(@PathVariable Long id, @RequestBody Budget budget) {
        return budgetService.updateBudget(id, budget);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        budgetService.deleteById(id);
    }

    @GetMapping("/current")
    public Budget getCurrentBudget() {
        return budgetService.findCurrent();
    }

}
