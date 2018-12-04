package entity;

import lombok.Data;
import lombok.ToString;

/**
 * Class user
 */
@Data
@ToString
public class User {

    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}