package com.epam.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.epam.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


//Todo javaDoc
@Log4j2
public class EntityMapper {

    public User getUser(HttpServletRequest req) {
        ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
            user = om.readValue(req.getInputStream(), User.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return user;
    }


    public String objectToJSON(Object object) {
        ObjectMapper om = new ObjectMapper();
        String objectInJson = null;
        try {
            objectInJson = om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return objectInJson;
    }


}



