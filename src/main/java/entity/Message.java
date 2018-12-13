package entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Size;

//Todo javaDoc
@Data
@ToString
public class Message {

    private int id;
    private User user;
    @Size(min = 1, max = 255, message= "Message length must be between 1 and 255 characters")
    private String message;
}