package filtres;

import dao.DAOFactory;
import dao.UserDAO;
import entity.User;
import mapper.EntityMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", servletNames = "controllers.LoginController")
public class LoginFilter implements Filter {

    private UserDAO userDAO;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);
        if (userDAO.checkLogIn(user)) {
            chain.doFilter(req, resp);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }


    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }

}
