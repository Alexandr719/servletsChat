package entity;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class user
 */
//TODO javadoc
@Data
@ToString
public class User {

    private int id;

    @Size(min = 1, max = 20, message= "Login length must be between 1 and 20 characters")
    private String login;

    @Size(min = 1, max = 40, message= "FirstName length must be between 1 and 20 characters")
    private String firstName;

    @Size(min = 1, max = 40, message= "LastName length must be between 1 and 20 characters")
    private String lastName;

    @Size(min = 1, max = 40, message= "Email length must be between 1 and 40 characters")
    @Email(message = "Email will be has specialize symbols")
    private String email;

    @Size(min = 1, max = 20, message= "Password length must be between 1 and 20 characters")
    private String password;
    private String role;


}

