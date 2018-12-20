package com.epam.chat.validation;

import com.epam.chat.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
/**
 * InputsValidator
 * @author Alexander_Filatov
 * validate user's fields use javax.valodation api
 */
@Log4j2
public class InputsValidator {

    /**
     * validate user's fields use javax.valodation api
     * @return true if user passed validation
     */
    public boolean validateUser(User user) {
        boolean validationResult = false;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        //Validate bean
        Set<ConstraintViolation<User>> constraintViolations
                = validator.validate(user);
        //Wrong validation

        //If validations errors > 0  bad validation
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<User> violation : constraintViolations) {
                log.debug(violation.getMessage());
            }
        } else {
            log.debug(user.getLogin() + " successfully passed validation");
            validationResult = true;
        }
        return validationResult;
    }
}