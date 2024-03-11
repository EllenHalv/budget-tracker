package org.example.budgettracker.api;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Budget;
import org.example.budgettracker.service.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    // saves budget to the logged in user
    @PostMapping
    public ResponseEntity<Budget> save(@RequestBody Budget budget) {
        try {
            return ResponseEntity.ok(budgetService.save(budget));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    //finds all budgets for the logged in user
    /*@GetMapping
    public List<Budget> findAll() {
        return budgetService.findAll();
    }*/

    // finds all budgets for the logged in user. returns a list of BudgetDTO objects
    @GetMapping
    public ResponseEntity<List<Budget>> findAll() {
        try{
            return ResponseEntity.ok(budgetService.findAll());
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public Budget findById(@PathVariable Long id) {
        return budgetService.findById(id);
    }

    // for updating info about the budget. not for expense objects
    @PutMapping("/{id}")
    public Budget update(@PathVariable Long id, @RequestBody Budget budget) {
        return budgetService.updateBudget(id, budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        budgetService.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    // finds the current (latest) budget for the logged in user
    @GetMapping("/current")
    public Budget getCurrentBudget() {
        return budgetService.findCurrent();
    }
}
