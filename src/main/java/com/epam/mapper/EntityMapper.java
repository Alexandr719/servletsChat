package com.epam.mapper;


import com.epam.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * EntityMapper
 * Get user class from json/gets json back
 * * @author Alexander_Filatov
 */
@Log4j2
public class EntityMapper {
    /**
     * Method returns User class from request
     *
     * @return user
     */
    public User getUser(HttpServletRequest req) {
        ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
            user = om.readValue(req.getInputStream(), User.class);
            log.debug("Get user from request" + user);
        } catch (IOException e) {
            log.error(e);
        }
        return user;
    }

    /**
     * Convert java object into Json string
     *
     * @return string
     */
    public String convertObjectToJSON(Object object) {
        ObjectMapper om = new ObjectMapper();
        String objectInJson = null;
        try {
            objectInJson = om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e);
        }
        log.debug("Put java object into json format: " + objectInJson);
        return objectInJson;
    }
}



