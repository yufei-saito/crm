package yufei.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.PortableServer.RequestProcessingPolicyOperations;

import yufei.store.domain.User;
import yufei.store.service.UserService;
import yufei.store.service.impl.UserServiceImpl;
import yufei.store.utils.MailUtils;
import yufei.store.utils.MyBeanUtils;
import yufei.store.utils.UUIDUtils;
import yufei.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//创新一个新的User对象
		User u = new User();
		//从请求体中获取所有提交的参数,并设置到u对象中
		MyBeanUtils.populate(u, request.getParameterMap());
		u.setUid(UUIDUtils.getId().toString());
		u.setCode(UUIDUtils.getCode().toString());
		//System.out.println(u);
		UserService us = new UserServiceImpl();
		try {
			us.regist(u);
			MailUtils.sendMail(u.getEmail(), u.getCode());
			request.setAttribute("msg", "注册成功,请到邮箱中点击激活邮件!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "注册失败,请重新尝试!");
		}
		
		return "/jsp/info.jsp";
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code=request.getParameter("code");
		UserService us = new UserServiceImpl();
		try {
			User u =us.activeUser(code);
			if(u==null) {
				throw new SQLException();
			}else {
				request.setAttribute("msg", "激活成功!你的账号已经可以使用了");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("msg", "激活失败,你的激活码可能有误或者已过期!");
		}
		
		return "/jsp/info.jsp";
	}
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String auto_login = request.getParameter("auto_login");
		String username_on = request.getParameter("username_on");
		UserService us = new UserServiceImpl();
		if("1".equals(auto_login)){
			Cookie auto_cookie = new Cookie("auto_login",username+"="+password);
			auto_cookie.setPath("/");
			auto_cookie.setMaxAge(60*60*24*7);
			response.addCookie(auto_cookie);
		}else {
			Cookie auto_cookie = new Cookie("auto_login","");
			auto_cookie.setPath("/");
			auto_cookie.setMaxAge(0);
			response.addCookie(auto_cookie);
		}
		if("1".equals(username_on)) {
			Cookie username_cookie = new Cookie("username_on",username);
			username_cookie.setPath("/");
			username_cookie.setMaxAge(60*60*24*7);
			response.addCookie(username_cookie);
		}else {
			Cookie username_cookie = new Cookie("username_on","");
			username_cookie.setPath("/");
			username_cookie.setMaxAge(0);
			response.addCookie(username_cookie);
		}
		try {
			User u =us.login(username,password);
			HttpSession session = request.getSession();
			session.setAttribute("user", u);
			response.sendRedirect(request.getContextPath()+"/");
			return null;
		} catch (Exception e) {
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
		
	
	}

	public String exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = new Cookie("auto_login", "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
        response.addCookie(cookie);

		HttpSession session = request.getSession();
		session.removeAttribute("user");
		
		response.sendRedirect(request.getContextPath()+"/");
		
		return null;
	}


}
