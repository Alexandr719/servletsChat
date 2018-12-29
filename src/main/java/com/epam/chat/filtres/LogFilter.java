package com.epam.chat.filtres;

import com.epam.chat.ChatConstants;
import com.epam.chat.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", servletNames = {"MessageListController", "UserListController"})
public class LogFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute(ChatConstants.SESSION_USER);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        chain.doFilter(req, resp);
    }



}
