package yufei.store.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yufei.store.domain.User;
import yufei.store.service.UserService;
import yufei.store.service.impl.UserServiceImpl;
import yufei.store.utils.CookUtils;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpServletRequest req = (HttpServletRequest)request;
		String url = req.getServletPath();
		if(url.contains("/UserServlet")) {
			String med = req.getParameter("method");
			if("loginUI".equals(med)) {
				chain.doFilter(request, response);
				return;
			}
			
		}
		if(req.getSession().getAttribute("user")!=null) {
			chain.doFilter(request, response);
			return;
		}
		Cookie cookie = CookUtils.getCookieByName("auto_login",req.getCookies());
		if(cookie==null) {
			chain.doFilter(request, response);
			return;
		}
		String username = cookie.getValue().split("=")[0];
		String password = cookie.getValue().split("=")[1];
		UserService us = new UserServiceImpl();
		try {
			User u =us.login(username, password);
			if(u==null) {
				chain.doFilter(request, response);
			}
			req.getSession().setAttribute("user", u);
			chain.doFilter(request, response);
		} catch (SQLException e) {
			
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
