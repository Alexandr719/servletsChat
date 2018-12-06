package entity;

import lombok.Data;

//Todo javaDoc
@Data
public class Message {
    private int id;
    private User user;
    private String message;
}