/*
package org.example.budgettracker.validator;

import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Collections;

import java.util.Set;

*/
/**
 * This class is used to validate objects.
 *//*

@Component
public class BudgetValidator {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public Set<String> validate(BudgetDTO budgetDTO) {
        Set<ConstraintValidator<BudgetDTO>> violations = validator.validate(budgetDTO);
        if (!violations.isEmpty()) {
            return violations
                    .stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
*/
