package com.epam.filtres;

import com.epam.dao.DAOFactory;
import com.epam.dao.UserDAO;
import com.epam.entity.InputsValidator;
import com.epam.entity.User;
import lombok.extern.log4j.Log4j2;
import com.epam.mapper.EntityMapper;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

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

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);

        if (validateUser(user) && !userDAO.isLogged(user)) {
            log.debug("User with this login doesn't exist");
            userDAO.login(user);
            request.setAttribute("regUser", user);
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
