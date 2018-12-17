package com.epam.mapper;

import lombok.extern.log4j.Log4j2;

import java.util.ResourceBundle;

/**
 * ResourceInspector
 * Singleton  class, who works with .properties file
 *
 * @author Alexander_Filatov
 */
@Log4j2
public class ResourceInspector {
    private static ResourceBundle instance = null;

    private ResourceInspector() {
    }

    public static synchronized ResourceBundle getInstance() {
        if (instance == null)
            instance = ResourceBundle.getBundle("propretydata");
        log.debug("Create instance of ResourceBundle");
        return instance;
    }


}