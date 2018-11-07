package yufei.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yufei.store.domain.User;

/**
 * Servlet Filter implementation class UserFilter
 */
public class UserFilter implements Filter {

    public UserFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		User u = (User)req.getSession().getAttribute("user");
		if(null!=u) {
			chain.doFilter(request, response);
		}else {
			req.setAttribute("msg", "请先登录后操作!");
			req.getRequestDispatcher("/jsp/info.jsp").forward(req, resp);
		}
		
		//user
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
