package org.example.budgettracker;

import org.example.budgettracker.model.Budget;
import org.example.budgettracker.service.BudgetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BudgetServiceTest {

    @Autowired
    private BudgetService budgetService;

    @MockBean
    private BudgetService mockBudgetService;

    @Test
    public void testSave() {
        // Create a budget object
        Budget budget = Budget.builder()
                .name("Test Budget")
                .amount(1000.00)
                .startDate("2021-01-01")
                .endDate("2021-12-31")
                .expenses(new ArrayList<>())
                .amountSpent(0.00)
                .remainingAmount(1000.00)
                .build();

        // Mock the behavior of the budgetService.save method
        when(mockBudgetService.save(budget)).thenReturn(budget);

        // Call the save method and assert the results
        Budget savedBudget = mockBudgetService.save(budget);

        assertEquals(budget.getName(), savedBudget.getName());
        assertEquals(budget.getAmount(), savedBudget.getAmount());
        assertEquals(budget.getStartDate(), savedBudget.getStartDate());
        assertEquals(budget.getEndDate(), savedBudget.getEndDate());
        assertEquals(budget.getExpenses().size(), savedBudget.getExpenses().size());
        assertEquals(budget.getAmountSpent(), savedBudget.getAmountSpent());
        assertEquals(budget.getRemainingAmount(), savedBudget.getRemainingAmount());
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
