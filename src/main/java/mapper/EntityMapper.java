package mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Message;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


//Todo javaDoc
public class EntityMapper {

    public User getUser(HttpServletRequest req) {
        ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
            user = om.readValue(req.getInputStream(), User.class);
        } catch (IOException e) {
            //TODO log
            e.printStackTrace();
        }
        return user;
    }


    public Message getMessage(HttpServletRequest req) {
        ObjectMapper om = new ObjectMapper();
        Message message = null;
        try {
            message = om.readValue(req.getInputStream(), Message.class);

        } catch (IOException e) {
            //TODO log
            e.printStackTrace();
        }
        return message;
    }

    public String objectToJSON(Object o){
        ObjectMapper om = new ObjectMapper();
        String json = null;
        try {
            json = om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            //TODO log
            e.printStackTrace();
        }
        return json;
    }





}



