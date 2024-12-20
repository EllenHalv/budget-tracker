package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Budget;
import org.example.budgettracker.model.entity.Expense;
import org.example.budgettracker.model.entity.User;
import org.example.budgettracker.model.response.BudgetDTO;
import org.example.budgettracker.repository.BudgetRepository;
import org.example.budgettracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetDTO save(BudgetDTO budgetDTO) {
        validateBudget(budgetDTO);
        // find user
        Optional<User> user = userRepository.findById(budgetDTO.getUserId());
        if (user.isPresent()) {
            Budget budget = Budget.builder()
                    .name(budgetDTO.getName())
                    .amount(budgetDTO.getAmount())
                    .startDate(budgetDTO.getStartDate())
                    .endDate(budgetDTO.getEndDate())
                    .expenses(budgetDTO.getExpenses())
                    .amountSpent(budgetDTO.getAmountSpent())
                    .remainingAmount(budgetDTO.getRemainingAmount())
                    .user(user.get())
                    .build();
            budgetRepository.save(budget);
            return BudgetDTO.fromBudget(budget);
        }
        throw new NoResultException("User not found");
    }

    public BudgetDTO findById(Long id) {
        validateId(id);
        return budgetRepository.findById(id).map(BudgetDTO::fromBudget).orElseThrow(() -> new NoResultException("No budget found with id: " + id));
    }

    public List<BudgetDTO> findAll() {
        List<Budget> budgetsList = budgetRepository.findAll();

        List<BudgetDTO> budgetDTOList = new ArrayList<>();
        for (Budget budget : budgetsList) {
            budgetDTOList.add(BudgetDTO.fromBudget(budget));
        }

        //return BudgetListDTO.fromBudgetList(budgetsList);
        return budgetDTOList;

        //return BudgetDTO.fromBudgetList(budgetsList);
    }

    public List<BudgetDTO> findAllById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User budgetUser = user.get();
            List<Budget> budgetsList = budgetRepository.findAllBudgetsByUserId(budgetUser.getId());

            List<BudgetDTO> budgetDTOList = new ArrayList<>();
            for (Budget budget : budgetsList) {
                budgetDTOList.add(BudgetDTO.fromBudget(budget));
            }

            //return BudgetListDTO.fromBudgetList(budgetsList);
            return budgetDTOList;

        }
        throw new NoResultException("User not found");
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
    public BudgetDTO updateBudget(Long id, BudgetDTO updateBudget) {
        validateId(id);
        validateBudget(updateBudget);
        
        BudgetDTO budget = findById(id);

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
        BudgetDTO budget = findById(budgetId);
        budget.setAmountSpent(budget.getAmountSpent() + expenseAmount);
        budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());
        save(budget);
    }

    public void subtractFromBudgetSpending(Long budgetId, Double expenseAmount) {
        BudgetDTO budget = findById(budgetId);
        budget.setAmountSpent(budget.getAmountSpent() - expenseAmount);
        budget.setRemainingAmount(budget.getAmount() - budget.getAmountSpent());
        save(budget);
    }

    public void updateBudgetSpending(Long budgetId, Expense newExpense) {
        BudgetDTO budget = findById(budgetId);

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

    public BudgetDTO findCurrent(Long userId) {
        validateId(userId);
        //return as BudgetDTO
        Budget budget = budgetRepository.findFirstByUserIdOrderByIdDesc(userId);
        return BudgetDTO.fromBudget(budget);
        //return budgetRepository.findFirstByUserIdOrderByIdDesc(userId);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
    }

    private void validateBudget(BudgetDTO budget) {
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
