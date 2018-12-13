package entity;

import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class InputsValidator {
    public  void validateUser(User user , Logger log){
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        javax.validation.Validator validator = factory.getValidator();
////        Set<ConstraintViolation<User>> violations = validator.validate(user);
////        for (ConstraintViolation<User> violation : violations) {
////            log.info(violation.getMessage());
//
//        }

    }

}