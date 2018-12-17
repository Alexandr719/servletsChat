package com.epam.dao;

/**
 * DAOFactory
 *
 * @author Alexander_Filatov
 */
public abstract class DAOFactory {

    public abstract MessageDAO getMessageDAO();

    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory() {

        return new OracleDAOFactory();

    }
}