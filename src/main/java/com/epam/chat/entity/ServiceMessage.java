package com.epam.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ServiceMessage implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean condition;
    private String cause;
}
