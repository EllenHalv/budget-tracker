package org.example.budgettracker.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/home")
public class HomeController {
    @GetMapping
    public String home() {
        return "Welcome to the Budget Tracker Home Page!";
    }
}
