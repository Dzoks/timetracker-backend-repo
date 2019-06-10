package rs.dzoks.timetracker.session;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/hub/*")
public class HubAccessFilter implements Filter {

    private WebApplicationContext springContext;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        springContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        UserBean userBean = (UserBean) springContext.getBean("userBean");
        if (request.getRequestURI().startsWith("/hub/user/login")||request.getRequestURI().startsWith("/hub/user/state")|| userBean.isAuthorized() ) {
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            response.sendError(401);

        }
        // filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
