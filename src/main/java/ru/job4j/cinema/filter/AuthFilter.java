package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class.getName());
    private static final Set<String> REG_LOGIN = Set.of(
            "loginPage",
            "login",
            "index",
            "formRegistration",
            "registration",
            "success",
            "fail"
    );

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        int index = uri.lastIndexOf('/') + 1;
        String endUri = uri.substring(index);
        if (REG_LOGIN.contains(endUri)) {
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
