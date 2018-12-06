package mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Message;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

//Todo javaDoc
public class EntityMapper {

    public User getUser(HttpServletRequest req) {
        String userString = parceRequest(req);
        ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
            user = om.readValue(userString, User.class);
        } catch (IOException e) {
            //TODO log
            e.printStackTrace();
        }
        return user;
    }


    public Message getMessage(HttpServletRequest req) {
        String messageString = parceRequest(req);
        ObjectMapper om = new ObjectMapper();
        Message message = null;
        try {
            message = om.readValue(messageString, Message.class);

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
        System.out.println(json);
        return json;
    }

    private String parceRequest(HttpServletRequest req) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            //TODO log
        }

        return jb.toString();
    }





}



