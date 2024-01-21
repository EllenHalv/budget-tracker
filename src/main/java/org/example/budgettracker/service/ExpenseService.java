package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Expense;
import org.example.budgettracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense findById(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new NoResultException("No expense found with id: " + id));
    }

    public Iterable<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public void deleteById(Long id) {
        if(!expenseRepository.existsById(id)) throw new NoResultException("Expense with id : " + id + " not found");
        expenseRepository.deleteById(id);
    }

    public Expense updateExpense(Long id, Expense updateExpense) {
            Expense expense = findById(id);
            expense.setName(updateExpense.getName());
            expense.setAmount(updateExpense.getAmount());
            expense.setDate(updateExpense.getDate());
            expense.setBudget(updateExpense.getBudget());
            return save(expense);
    }
}
