package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Budget;
import org.example.budgettracker.model.entity.Expense;
import org.example.budgettracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public Budget save(Budget budget) {
        validateBudget(budget);
            return budgetRepository.save(budget);
    }

    public Budget findById(Long id) {
        validateId(id);
        return budgetRepository.findById(id).orElseThrow(() -> new NoResultException("No budget found with id: " + id));
    }

    public List<Budget> findAll() {
            return budgetRepository.findAll();
            //return BudgetListDTO.fromBudgetList(budgetsList);
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
        validateBudget(updateBudget);
        
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

    public void addToBudgetSpending(Long budgetId, Double expenseAmount) {
        Budget budget = findById(budgetId);
        budget.setAmountSpent(budget.getAmountSpent() + expenseAmount);
        budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());
        save(budget);
    }

    public void subtractFromBudgetSpending(Long budgetId, Double expenseAmount) {
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

    private void validateBudget(Budget budget) {
        if (budget.getName().isEmpty()) {
            throw new IllegalArgumentException("Budget name cannot be empty");
        }
        if (budget.getAmount() <= 0) {
            throw new IllegalArgumentException("Budget amount cannot be negative or zero");
        }
        if (budget.getStartDate().isEmpty()) {
            throw new IllegalArgumentException("Start date cannot be empty");
        }
        if (budget.getEndDate().isEmpty()) {
            throw new IllegalArgumentException("End date cannot be empty");
        }
        if (budget.getEndDate().compareTo(budget.getStartDate()) < 0) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }
}
