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
@WebFilter(filterName = "LoginFilter", servletNames = "controllers.LoginController")
public class LoginFilter implements Filter {

    private static final int PASS_LOGIN_MAX_LENGTH = 20;

    private UserDAO userDAO;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);

        if (validateUser(user) && userDAO.isLogged(user) && userDAO.checkLogIn(user)) {
            log.info("User login and password right!!!");
            request.setAttribute("user", user);
            log.info("User with login:" + user.getLogin() + "inter into chat");
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
        return true;
    }


}
