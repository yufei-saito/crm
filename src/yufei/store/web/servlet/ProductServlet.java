package yufei.store.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yufei.store.domain.Page;
import yufei.store.domain.Product;
import yufei.store.service.ProductService;
import yufei.store.service.impl.ProductServiceImpl;
import yufei.store.web.base.BaseServlet;


public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService ps = new ProductServiceImpl();
		String pid = request.getParameter("pid");
		try {
			Product p =ps.findById(pid);
			if(p!=null) {
				request.setAttribute("product", p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "/jsp/product_info.jsp";
	}
	public String findByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService ps = new ProductServiceImpl();
		String cid = request.getParameter("cid");
		int pageNum =1;
		int everyPage =10;
		if(request.getParameter("pageNum")!=null) {
			 pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		if(request.getParameter("everyPage")!=null) {
			everyPage = Integer.parseInt(request.getParameter("everyPage"));
		}
		

		
		try {
			Page p  = ps.findByCid(cid,pageNum,everyPage);
			if(p!=null) {
				request.setAttribute("page", p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "/jsp/product_list.jsp";
	}

}
