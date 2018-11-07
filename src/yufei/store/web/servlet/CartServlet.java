package yufei.store.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yufei.store.domain.Cart;
import yufei.store.domain.CartItem;
import yufei.store.domain.Product;
import yufei.store.domain.User;
import yufei.store.service.ProductService;
import yufei.store.service.impl.ProductServiceImpl;
import yufei.store.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public String addCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User u	 = (User)session.getAttribute("user");
		if(null==u) {
			request.setAttribute("msg", "请先登录账号!");
			return "/jsp/info.jsp";
		}
		Cart cart = (Cart)session.getAttribute("cart");
		System.out.println(cart);
		if(null==cart) {
			cart = new Cart();
		}
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("quantity"));
		ProductService ps = new ProductServiceImpl();
		try {
			Product p = ps.findById(pid);
			CartItem ci = new CartItem();
			ci.setNum(num);
			ci.setP(p);
			cart.addItem(ci);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			session.setAttribute("cart", cart);
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	public String removeAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.removeAll();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	public String removeById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		String pid = request.getParameter("id");
		cart.removeItem(pid);
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
}
