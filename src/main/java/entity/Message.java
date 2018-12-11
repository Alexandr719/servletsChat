package entity;

import lombok.Data;
import lombok.ToString;

//Todo javaDoc
@Data
@ToString
public class Message {
    private int id;
    private User user;
    private String message;
}