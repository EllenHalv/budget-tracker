package org.example.budgettracker.api;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Budget;
import org.example.budgettracker.model.response.BudgetDTO;
import org.example.budgettracker.service.BudgetService;
import org.example.budgettracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    public ResponseEntity<BudgetDTO> save(@RequestBody BudgetDTO budget, Authentication auth) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(!isAdminOrLoggedInUser(budget.getUserId(), auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            return ResponseEntity.ok(budgetService.save(budget));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // admin finds all budgets
    @GetMapping
    public ResponseEntity<List<BudgetDTO>> findAll(Authentication auth) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(!isAdmin(auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try{
            return ResponseEntity.ok(budgetService.findAll());
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // finds all budgets by userId for the logged in user or admin
    @GetMapping("/user/{id}")
    public ResponseEntity<List<BudgetDTO>> findAllByUserId(@PathVariable Long id, Authentication auth) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(!isAdminOrLoggedInUser(id, auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try{
            return ResponseEntity.ok(budgetService.findAllById(id));
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // finds a budget by id for the logged in user or admin
    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> findById(@PathVariable Long id, Authentication auth) {
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!isBudgetIdLoggedInUserOrAdmin(id, auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            return ResponseEntity.ok(budgetService.findById(id));
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // for updating info about the budget. not for expense objects
    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> update(@PathVariable Long id, @RequestBody BudgetDTO budget, Authentication auth) {
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!isBudgetIdLoggedInUserOrAdmin(id, auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            return ResponseEntity.ok(budgetService.updateBudget(id, budget));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, Authentication auth) {
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!isBudgetIdLoggedInUserOrAdmin(id, auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            budgetService.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // finds the current (latest) budget for the logged in user
    @GetMapping("/current/{id}")
    public ResponseEntity<BudgetDTO> getCurrentBudget(@PathVariable Long id, Authentication auth) {
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!isLoggedInUser(id, auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try{
            return ResponseEntity.ok(budgetService.findCurrent(id));
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // check if user is logged in user
    private boolean isLoggedInUser(Long id, Authentication auth) {
        return userService.findById(id).getUsername().equals(auth.getName());
    }

    private boolean isAdminOrLoggedInUser(Long id, Authentication authentication) {
        return isAdmin(authentication) || isLoggedInUser(id, authentication);
    }
    private boolean isAdmin(Authentication authentication) {
        for(GrantedAuthority authority : authentication.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private boolean isBudgetIdLoggedInUserOrAdmin(Long budgetId, Authentication auth) {
        return isAdmin(auth) || isLoggedInUser(budgetService.findById(budgetId).getUserId(), auth);
    }
}
