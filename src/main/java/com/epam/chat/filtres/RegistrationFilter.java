package com.epam.chat.filtres;

import com.epam.chat.ChatConstants;
import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.EntityMapper;
import com.epam.chat.validation.InputsValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Entity RegistrationFilter
 * @author Alexander_Filatov
 */
@Log4j2
@WebFilter(filterName = "RegistrationFilter", servletNames = "RegistrationController")
public class RegistrationFilter implements Filter {
    private UserDAO userDAO;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp
            , FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);

        if (validateUser(user) && !userDAO.isLogged(user)) {
            log.debug("User with this login doesn't exist");
            userDAO.login(user);
            request.setAttribute(ChatConstants.REG_USER, user);
            log.debug("Success registration");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
        log.debug("Init method was start");

    }

    private boolean validateUser(User user) {
        return new InputsValidator().validateUser(user);
    }
}
