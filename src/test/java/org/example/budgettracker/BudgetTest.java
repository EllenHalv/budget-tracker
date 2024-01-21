package org.example.budgettracker;

import org.example.budgettracker.service.BudgetService;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest {

    BudgetService budgetService;
    Budget budget;

    // should set the budget object. then call getBudget() and compare the two
    private void saveBudgetTest() {

        // create a budget object
        Budget budget = Budget.builder()
                .name("Test Budget")
                .amount(1000.00)
                .startDate("2021-01-01")
                .endDate("2021-12-31")
                .expenses(new ArrayList<Expense>())
                .amountSpent(0.00)
                .remainingAmount(1000.00)
                .build();

        Budget savedBudget = budgetService.save(budget);

        // assert that budget is equal to budgetService.getBudget()
        Budget serviceBudget = budgetService.findById(savedBudget.getId());
        assertEquals(budget.getName(), serviceBudget.getName());
        assertEquals(budget.getAmount(), serviceBudget.getAmount());
        assertEquals(budget.getStartDate(), serviceBudget.getStartDate());
        assertEquals(budget.getEndDate(), serviceBudget.getEndDate());
        assertEquals(budget.getExpenses().size(), serviceBudget.getExpenses().size());
        assertEquals(budget.getAmountSpent(), serviceBudget.getAmountSpent());
        assertEquals(budget.getRemainingAmount(), serviceBudget.getRemainingAmount());
    }

    /*// should return the budget object toString() TODO client side methods below?
    private void getBudgetTest() {
        String budgetString = budgetService.getBudget();

        // assert that budgetString is equal to the toString() of the budget object
        assertEquals(budgetString, budget.toString());
    }

    // should return the budget amount
    private void getBudgetAmountTest() {
        double budgetAmount = budgetService.getBudgetAmount();

        // assert that budgetAmount is equal to the budget amount
        assertEquals(budgetAmount, budget.getAmount());
    }

    // should set the budget amount. then call getBudgetAmount() and compare the two
    private void setBudgetAmountTest() {
        budgetService.setBudgetAmount(2000.00);

        // assert that budgetService.getBudgetAmount() is equal to 2000.00
        assertEquals(budgetService.getBudgetAmount(budget), 2000.00);
    }

    // should return a toString() of the budget and the current amount spent, as well as the remaining amount to spend
    private void getBudgetStatusTest() {
        String budgetStatus = budgetService.getBudgetStatus(budget);

        // assert that budgetStatus is equal to the toString() of the budget object, the amount spent, and the remaining amount to spend
        assertEquals(budgetStatus, budget.budgetStatusToString() + "\nAmount Spent: " + budgetService.getAmountSpent() + "\nRemaining Amount: " + budgetService.getRemainingAmount());
    }

    // should return the current expenses in the budget
    private void getBudgetExpensesTest() {
        List<Expense> budgetExpenses = budgetService.getBudgetExpenses();

        // assert that budgetExpenses is equal to the list of expenses in the budget object
        assertEquals(budgetExpenses, budget.getExpenses());
    }*/
}
