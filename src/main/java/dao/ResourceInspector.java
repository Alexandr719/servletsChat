package dao;

import java.util.ResourceBundle;

public class ResourceInspector {
    private static ResourceBundle instance = null;

    private ResourceInspector() {
    }

    public static synchronized ResourceBundle getInstance() {
        if (instance == null)
            instance = ResourceBundle.getBundle("propretydata");
        return instance;
    }


}