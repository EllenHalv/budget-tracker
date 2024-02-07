package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;

    public Expense save(Expense expense) {
        budgetService.addToBudgetSpending(expense.getBudgetId(), expense.getAmount());
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
                    .budgetId(updateExpense.getBudgetId())
                    .id(expense.getId())
                    .build();

            budgetService.updateBudgetSpending(expense.getBudgetId(), newExpense);
        } else {
            updateExpenseWithoutAmountChange(updateExpense, expense.getId());
        }
    }

    private void updateExpenseWithoutAmountChange(Expense updateExpense, Long id) {
        Expense newExpense = Expense.builder()
                .name(updateExpense.getName())
                .amount(updateExpense.getAmount())
                .date(updateExpense.getDate())
                .budgetId(updateExpense.getBudgetId())
                .id(id)
                .build();

        save(newExpense);
    }

    private void validateId(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
    }
}
