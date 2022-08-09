package Filter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modal.User;

/**
 *
 * @author window
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    public AuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String path = req.getRequestURI().substring(req.getContextPath().length());

        boolean isLoggedIn = (session != null && session.getAttribute("account") != null);
        boolean isAdminLoggedIn = false;
        if (isLoggedIn) {
            isAdminLoggedIn = ((User) session.getAttribute("account")).isAdmin();
        }
        String loginURI = "/Login";

        boolean isLoginRequest = path.contains(loginURI);

        if (path.startsWith("/admin")) {
            if (isAdminLoggedIn) {
                chain.doFilter(request, response);
            } else if (isLoggedIn && !isAdminLoggedIn) {
                req.getRequestDispatcher("/viewNewsfeed").forward(request, response);

            } else {
                req.getRequestDispatcher("/Login").forward(request, response);
            }
        } else {
            if (isAdminLoggedIn && isLoginRequest) {
                request.getRequestDispatcher("/admin").forward(request, response);
            } else if (isLoggedIn && isLoginRequest) {

                req.getRequestDispatcher("/viewNewsfeed").forward(request, response);

            } else if (!isLoggedIn && isLoginRequired(req)) {

                req.getRequestDispatcher("/Login").forward(request, response);
            } else {

                chain.doFilter(request, response);
            }
        }

    }

    private boolean isLoginRequired(HttpServletRequest httpRequest) {
        String requestURL = httpRequest.getRequestURL().toString();
        String[] loginRequiredURLs = {
            "/chat", "/viewNewsfeed", "/viewpurchase", "/viewProfile","/product","/post","/SearchUser","/SearchPost","/SearchProduct"
        };
        for (String loginRequiredURL : loginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {

    }

}
