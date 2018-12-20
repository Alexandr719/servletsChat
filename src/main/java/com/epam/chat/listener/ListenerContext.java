package com.epam.chat.listener;

import com.epam.chat.ChatConstants;
import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.dao.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class ListenerContext implements ServletContextListener{

    // Public constructor is required by servlet spec
    public ListenerContext() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        ServletContext sc = sce.getServletContext();
        DAOFactory dao = DAOFactory.getDAOFactory();
        UserDAO userDAO = dao.getUserDAO();
        MessageDAO messageDAO = dao.getMessageDAO();
        sc.setAttribute(ChatConstants.USER_DAO,userDAO);
        sc.setAttribute(ChatConstants.MESSAGE_DAO,messageDAO);




    }


}
