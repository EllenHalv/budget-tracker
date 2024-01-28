package org.example.budgettracker.repository;

import org.example.budgettracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findFirstByOrderByIdDesc();
}
