package com.epam.entity;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Entity user
 * @author Alexander_Filatov
 */
//TODO javadoc
@Data
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1;
    /** Field id  creating by data base*/
    private int id;

    /** Field login (unique) */
    @NotNull(message = "Login will be not null")
    @Size(min = 1, max = 20,
            message = "Login length must be between 1 and 20 characters")
    private String login;

    @Size(min = 1, max = 40,
            message = "FirstName length must be between 1 and 20 characters")
    private String firstName;

    @Size(min = 1, max = 40,
            message = "LastName length must be between 1 and 20 characters")
    private String lastName;

    @Size(min = 1, max = 40,
            message = "Email length must be between 1 and 40 characters")
    @Email(message = "Email will be have specialize symbols")
    private String email;

    @NotNull(message = "Password will be not null")
    @Size(min = 1, max = 20,
            message= "Password length must be between 1 and 20 characters")
    private String password;

    /** Field role (ADMIN or USER) */
    private String role;


}

