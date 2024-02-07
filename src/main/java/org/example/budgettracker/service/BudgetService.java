package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public Budget save(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget findById(Long id) {
        validateId(id);
        return budgetRepository.findById(id).orElseThrow(() -> new NoResultException("No budget found with id: " + id));
    }

    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    public void deleteById(Long id) {
        validateId(id);
        if(!budgetRepository.existsById(id)) {
            throw new NoResultException("Budget with id : " + id + " not found");
        }
        budgetRepository.deleteById(id);
    }

    // for updating info about the budget. not for expense objects
    // look for: updated amount
    public Budget updateBudget(Long id, Budget updateBudget) {
        validateId(id);
        
        Budget budget = findById(id);

        budget.setName(updateBudget.getName());
        budget.setAmount(updateBudget.getAmount());
        budget.setStartDate(updateBudget.getStartDate());
        budget.setEndDate(updateBudget.getEndDate());
        budget.setExpenses(updateBudget.getExpenses());
        budget.setAmountSpent(updateBudget.getAmountSpent());
        // update the remaining amount
        budget.setRemainingAmount(updateBudget.getAmount() - updateBudget.getAmountSpent());

        return save(budget);
    }

    public void addToBudgetSpending(Long budgetId, double expenseAmount) {
        Budget budget = findById(budgetId);
        budget.setAmountSpent(budget.getAmountSpent() + expenseAmount);
        budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());
        save(budget);
    }

    public void subtractFromBudgetSpending(Long budgetId, double expenseAmount) {
        Budget budget = findById(budgetId);
        budget.setAmountSpent(budget.getAmountSpent() - expenseAmount);
        budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());
        save(budget);
    }

    public void updateBudgetSpending(Long budgetId, Expense newExpense) {
        Budget budget = findById(budgetId);

        // find the expense and update the amount
        for (Expense e : budget.getExpenses()) {
            if (e.getId().equals(newExpense.getId())) {
                double oldAmount = e.getAmount();
                double newAmount = newExpense.getAmount();
                double newSpent = budget.getAmountSpent() - oldAmount + newAmount;
                budget.setAmountSpent(newSpent);
                budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());
                //save the expenses new amount
                e.setAmount(newAmount);
                save(budget);
                return;
            }
        }
    }

    public Budget findCurrent() {
        return budgetRepository.findFirstByOrderByIdDesc();
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
    }
}
