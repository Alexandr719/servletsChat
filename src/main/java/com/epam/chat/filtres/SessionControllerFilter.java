package com.epam.chat.filtres;



import org.jboss.logging.MDC;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class SessionControllerFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        MDC.put("sessionId", request.getSession().getId().substring(0,7));
        chain.doFilter(req, resp);
    }



}
