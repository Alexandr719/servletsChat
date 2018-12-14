package filtres;

import dao.DAOFactory;
import dao.UserDAO;
import entity.User;
import lombok.extern.log4j.Log4j2;
import mapper.EntityMapper;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Entity LoginFilter
 * @author Alexander_Filatov
 */
@Log4j2
@WebFilter(filterName = "LoginFilter", servletNames = "controllers.LoginController")
public class LoginFilter implements Filter {

    private UserDAO userDAO;


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);

        if (validateUser(user) && userDAO.isLogged(user) && userDAO.checkLogIn(user)) {
            log.info("User login and password are right");
            request.setAttribute("user", user);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("User login and password  are wrong");
        }
        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
        log.info("Init method was start");
    }


    private boolean validateUser(User user) {
        return true;
    }

    public void destroy() {

    }

}
