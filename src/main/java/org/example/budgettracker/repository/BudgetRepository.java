package org.example.budgettracker.repository;

import org.example.budgettracker.model.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    //Budget findFirstByOrderByIdDesc();

    //find latest budget by user id
    Budget findFirstByUserIdOrderByIdDesc(Long id);

    List<Budget> findAllBudgetsByUserId(Long id);
}
