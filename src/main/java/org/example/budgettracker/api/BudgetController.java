package org.example.budgettracker.api;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Budget;
import org.example.budgettracker.model.response.BudgetListDTO;
import org.example.budgettracker.service.BudgetService;
import org.example.budgettracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    // saves budget to the logged in user
    @PostMapping
    public ResponseEntity<Budget> save(@RequestBody Budget budget, Authentication auth) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            if(!isCorrectUser(budget.getUser().getUsername(), auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            return ResponseEntity.ok(budgetService.save(budget));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean isCorrectUser(String username, Authentication authentication) {
        return userService.loadUserByUsername(username).getUsername().equals(authentication.getName());
    }

    //finds all budgets for the logged in user
    /*@GetMapping
    public List<Budget> findAll() {
        return budgetService.findAll();
    }*/

    // finds all budgets for the logged in user. returns a list of BudgetDTO objects
    @GetMapping
    public ResponseEntity<List<BudgetListDTO>> findAll(Authentication auth) {
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try{
            return ResponseEntity.ok(budgetService.findAll(auth));
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
