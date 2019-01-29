package com.epam.chat.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Entity Message
 * @author Alexander_Filatov
 */
@Data
@ToString
public class Message {
    /** Field id  creating by data base*/
    private int id;

    private User user;

    @Size(min = 1, max = 255,
            message= "Message length must be between 1 and 255 characters")
    private String message;

    private Date timeStamp;

}