package com.epam.entity;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Log4j2
public class InputsValidator {
    public boolean validateUser(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        //Validate bean
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        //Wrong validation
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<User> violation : constraintViolations) {
                log.info(violation.getMessage());
            }
            return false;
        } else {
            log.info(user.getLogin() + " successfully passed validation");
            return true;
        }
    }
}