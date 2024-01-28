package org.example.budgettracker.api;

import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.repository.BudgetRepository;
import org.example.budgettracker.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetRepository budgetRepository;

    @PostMapping
    public Budget save(@RequestBody Budget budget) {
        return budgetRepository.save(budget);
    }

    @GetMapping
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Budget> findById(@PathVariable Long id) {
        return budgetRepository.findById(id);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody Budget budget) {
        Optional<Budget> dbBudget = budgetRepository.findById(id);
        if (dbBudget.isEmpty()) {
            return "Budget not found";
        } else {
            budget.setId(id);
            return budgetRepository.save(budget).toString();
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        budgetRepository.deleteById(id);
        return "Budget deleted";
    }

    @GetMapping("/current")
    public Budget getCurrentBudget() {
        return budgetRepository.findFirstByOrderByIdDesc();
    }
}
