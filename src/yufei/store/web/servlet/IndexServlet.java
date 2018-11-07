package yufei.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yufei.store.domain.Product;
import yufei.store.service.ProductService;
import yufei.store.service.impl.ProductServiceImpl;
import yufei.store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			ProductService ps = new ProductServiceImpl();
			try {
				List<Product> hots = ps.findHots();
				List<Product> news = ps.findNews();
				request.setAttribute("hots", hots);
				request.setAttribute("news", news);
			} catch (Exception e) {
			}
			
			return "/jsp/index.jsp";
	}



}
