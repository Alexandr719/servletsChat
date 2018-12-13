package filtres;

import dao.DAOFactory;
import dao.UserDAO;
import entity.User;
import entity.InputsValidator;
import lombok.extern.log4j.Log4j2;
import mapper.EntityMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebFilter(filterName = "RegistrationFilter", servletNames = "controllers.RegistrationController")
public class RegistrationFilter implements Filter {
    private UserDAO userDAO;
    private static final int PASS_LOGIN_MAX_LENGTH = 20;
    private static final int ANOTHER_MAX_LENGTH = 40;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);
        validateUser(user);
        if (!userDAO.isLogged(user)) {
            log.info("User with this login doesn't exist");
            userDAO.login(user);
            request.setAttribute("regUser", user);
            log.info("Success registration");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();

    }

    private boolean validateUser(User user) {
        new InputsValidator().validateUser(user, log);
         return true;
    }



}
