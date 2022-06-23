package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) /*throws IOException, ServletException*/ {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (uri.endsWith("loginPage")
                || uri.endsWith("login")
                || uri.endsWith("index")
                || uri.endsWith("formRegistration")
                || uri.endsWith("registration")
                || uri.endsWith("success")
                || uri.endsWith("fail")) {
            try {
                filterChain.doFilter(req, res);
            } catch (IOException | ServletException e) {
                LOG.error("Exception in AuthFilter#doFilter direct way", e);
            }
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            try {
                res.sendRedirect(req.getContextPath() + "/loginPage");
            } catch (IOException e) {
                LOG.error("Exception in AuthFilter#doFilter user==null", e);
            }
            return;
        }
        try {
            filterChain.doFilter(req, res);
        } catch (IOException | ServletException e) {
            LOG.error("Exception in AuthFilter#doFilter user != null", e);
        }
    }
}
