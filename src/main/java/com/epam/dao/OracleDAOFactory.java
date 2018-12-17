package com.epam.dao;

import com.epam.dao.oraclefactory.OracleMessageDAO;
import com.epam.dao.oraclefactory.OracleUserDAO;

/**
 * OracleDAOFactory
 *
 * @author Alexander_Filatov
 */
public class OracleDAOFactory extends DAOFactory {

    @Override
    public MessageDAO getMessageDAO() {
        return new OracleMessageDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new OracleUserDAO();
    }
}