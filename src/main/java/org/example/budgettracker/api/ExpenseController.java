package org.example.budgettracker.api;


import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public String createExpense(@RequestBody Expense expense) {
        return expenseService.save(expense).toString();
    }

    @GetMapping
    public String getAllExpenses() {
        return expenseService.findAll().toString();
    }

    @GetMapping("/{id}")
    public String getExpenseById(@PathVariable Long id) {
        return expenseService.findById(id).toString();
    }

    @PutMapping("/{id}")
    public String updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense).toString();
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteById(id);
        return "Expense deleted";
    }
}

