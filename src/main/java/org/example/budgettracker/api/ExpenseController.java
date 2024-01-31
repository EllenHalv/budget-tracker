package org.example.budgettracker.api;


import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.repository.BudgetRepository;
import org.example.budgettracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
// TODO should call the service layer

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final BudgetRepository budgetRepository;

    // TODO just create the expense object. send the expense and budget ID!!!
    @PostMapping
    public Budget addExpenseToBudget(@RequestBody Expense expense) {
        //get the budget obj from db
        Budget budget = budgetRepository.findById(expense.getBudgetId()).orElseThrow();
        // add the expense to the db
        Expense dbExpense = expenseService.saveExpense(expense);
        //add the expense to the budget
        budget.getExpenses().add(dbExpense);
        //save the budget to db
        return expenseService.save(budget);
    }

    @GetMapping
    public String getAllExpensesFromOneBudget() {
        return expenseService.findAll().toString();
    }

    @GetMapping("/{id}")
    public String getExpenseById(@PathVariable Long id) {
        return expenseService.findById(id).toString();
    }

    /*@PutMapping("/{id}")
    public String updateOneExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense).toString();
    }*/

    @DeleteMapping("/{id}")
    public String deleteExpenseFromBudget(@PathVariable Long id) {
        expenseService.deleteById(id);
        return "Expense deleted";
    }
}

