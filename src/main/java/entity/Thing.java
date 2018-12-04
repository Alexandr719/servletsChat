package entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Thing {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        String s = "{\"name\": \"gfdg\"}";
        ObjectMapper om = new ObjectMapper();

        try {
            Thing t = om.readValue(s,Thing.class);
            System.out.println(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

