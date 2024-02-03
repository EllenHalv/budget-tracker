package org.example.budgettracker.api;


import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.repository.BudgetRepository;
import org.example.budgettracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// TODO should call the service layer

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final BudgetRepository budgetRepository;

    // create the expense object. send the expense and budget ID!!!
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
    public List<Expense> getAllExpensesFromOneBudget() {
        return expenseService.findAll();
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.findById(id);
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

    @PutMapping("/{id}")
    public Expense updateOneExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Expense dbExpense = expenseService.findById(id);
        dbExpense.setName(expense.getName());
        dbExpense.setAmount(expense.getAmount());
        dbExpense.setDate(expense.getDate());
        dbExpense.setBudgetId(expense.getBudgetId());
        return expenseService.saveExpense(dbExpense);
    }
}

