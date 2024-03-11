package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Expense;
import org.example.budgettracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;

    public Expense save(Expense expense) {
        validateExpense(expense);
        budgetService.addToBudgetSpending(expense.getBudget().getId(), expense.getAmount());
        return expenseRepository.save(expense);
    }

    public Expense findById(Long id) {
        validateId(id);
        return expenseRepository.findById(id).orElseThrow(() -> new NoResultException("No expense found with id: " + id));
    }

    public List<Expense> findAllById(Long id) {
        validateId(id);
        return expenseRepository.findAllByBudgetId(id);
    }

    public void deleteById(Long id) {
        validateId(id);
        Expense expense = findById(id);
        expenseRepository.deleteById(id);

        budgetService.subtractFromBudgetSpending(id, expense.getAmount());
    }

    public void updateExpense(Long id, Expense updateExpense) {
        validateId(id);

        Expense expense = findById(id);

        if (updateExpense.getAmount() != expense.getAmount()) {
            Expense newExpense = Expense.builder()
                    .name(updateExpense.getName())
                    .amount(updateExpense.getAmount())
                    .date(updateExpense.getDate())
                    .id(expense.getId())
                    .build();

            budgetService.updateBudgetSpending(expense.getBudget().getId(), newExpense);
        } else {
            updateExpenseWithoutAmountChange(updateExpense, expense.getId());
        }
    }

    private void updateExpenseWithoutAmountChange(Expense updateExpense, Long id) {
        Expense newExpense = Expense.builder()
                .name(updateExpense.getName())
                .amount(updateExpense.getAmount())
                .date(updateExpense.getDate())
                .id(id)
                .build();

        save(newExpense);
    }

    private void validateId(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
    }


    private void validateExpense(Expense expense) {
        if(expense.getName().isEmpty()) {
            throw new IllegalArgumentException("Expense name cannot be empty");
        }
        if(expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Expense amount cannot be negative or zero");
        }
        if(expense.getDate().isEmpty()) {
            throw new IllegalArgumentException("Expense date cannot be empty");
        }
    }
}
