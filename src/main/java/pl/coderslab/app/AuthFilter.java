package pl.coderslab.app;

import pl.coderslab.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletReq, ServletResponse servletResp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletReq;
        HttpServletResponse httpResp = (HttpServletResponse) servletResp;

        HttpSession sess = httpReq.getSession(false);
        String url = httpReq.getRequestURI();

        if (sess == null || sess.getAttribute("user") == null) {
            if (!url.equals("/") && !url.startsWith("/login") && !url.equals("/about") && !url.equals("/register")) {
                httpResp.sendRedirect("/");
                return;
            }
        } else {
            User user = (User) sess.getAttribute("user");
            if (user.getRole() != 1L && url.startsWith("/admin")) {
                httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        }
        chain.doFilter(httpReq, httpResp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}