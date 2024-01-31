package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.Budget;
import org.example.budgettracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO should call the repository layer
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public Budget save(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget findById(Long id) {
        return budgetRepository.findById(id).orElseThrow(() -> new NoResultException("No budget found with id: " + id));
    }

    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    public void deleteById(Long id) {
        if(!budgetRepository.existsById(id)) {
            throw new NoResultException("Budget with id : " + id + " not found");
        }
        budgetRepository.deleteById(id);
    }

    public Budget updateBudget(Long id, Budget updateBudget) {
        Budget budget = findById(id);

        budget.setName(updateBudget.getName());
        budget.setAmount(updateBudget.getAmount());
        budget.setStartDate(updateBudget.getStartDate());
        budget.setEndDate(updateBudget.getEndDate());
        budget.setExpenses(updateBudget.getExpenses());
        budget.setAmountSpent(updateBudget.getAmountSpent());
        budget.setRemainingAmount(updateBudget.getRemainingAmount());

        return save(budget);
    }


    public Budget findCurrent() {
        return budgetRepository.findFirstByOrderByIdDesc();
    }
}
