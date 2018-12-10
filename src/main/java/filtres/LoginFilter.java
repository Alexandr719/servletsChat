package filtres;

import dao.DAOFactory;
import dao.UserDAO;
import entity.User;
import lombok.extern.log4j.Log4j2;
import mapper.EntityMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
@WebFilter(filterName = "LoginFilter", servletNames = "controllers.LoginController")
public class LoginFilter implements Filter {

    private UserDAO userDAO;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request);
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request);
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);

        } catch (Exception e) {
            //TODO log

        }

      // EntityMapper mapper = new EntityMapper();
      // User user = mapper.getUser(wrapper);

       // if (userDAO.checkLogIn(user)) {
            chain.doFilter(wrapper, resp);
       // } else {
       //     response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      //  }


    }

    public void init(FilterConfig config) throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }

}
