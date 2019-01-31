package com.epam.chat.validation;

import com.epam.chat.ChatConstants;
import com.epam.chat.entity.Message;
import com.epam.chat.entity.User;
import com.epam.chat.exeptions.ChatExeption;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * InputsValidator
 *
 * @author Alexander_Filatov
 * validate user's fields use javax.valodation api
 */
@Log4j2
public class InputsValidator {

    /**
     * validate user's fields use javax.valodation api
     *
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
            throw new ChatExeption(ChatConstants.NO_VALID_USER);
        } else {
            log.debug("Successfully passed validation", user);
            validationResult = true;
        }
        return validationResult;
    }

    public void validateMessage(Message message) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Message>> constraintViolations
                = validator.validate(message);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Message> violation : constraintViolations) {
                log.debug(violation.getMessage());
            }
            throw new ChatExeption(ChatConstants.NO_VALID_MESSAGE);
        } else {
            log.debug("Successfully passed validation", message);
        }
    }
}

