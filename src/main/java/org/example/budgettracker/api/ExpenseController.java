package org.example.budgettracker.api;


import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Expense;
import org.example.budgettracker.service.ExpenseService;
import org.example.budgettracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// TODO should call the service layer

@CrossOrigin("*")
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final UserService userService;
    private final ExpenseService expenseService;

    // create the expense object
    @PostMapping
    public ResponseEntity<Object> addExpenseToBudget(@RequestBody Expense expense) { // decided to save expenses directly to the db instead of in budget obj
        try {
            //return ResponseEntity.status(201).build(); om statuskoden behövs hellre än objektet använd denna
            return ResponseEntity.ok(expenseService.save(expense));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/all/{id}")
    public List<Expense> getAllExpensesFromOneBudget(@PathVariable Long id) {
        return expenseService.findAllById(id);
    }

    @GetMapping("/{id}")
    public Expense getOneExpenseById(@PathVariable Long id) {
        return expenseService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExpenseFromBudget(@PathVariable Long id) {
        expenseService.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOneExpense(@PathVariable Long id, @RequestBody Expense expense) {
        try {
            expenseService.updateExpense(id, expense);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
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
}

