package yufei.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;
import yufei.store.domain.Category;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Page;
import yufei.store.domain.Product;
import yufei.store.service.AdminService;
import yufei.store.service.CategoryService;
import yufei.store.service.OrderService;
import yufei.store.service.impl.AdminServiceImpl;
import yufei.store.service.impl.CategoryServiceImpl;
import yufei.store.service.impl.OrderServiceImpl;
import yufei.store.utils.JedisUtils;
import yufei.store.utils.UUIDUtils;
import yufei.store.web.base.BaseServlet;

public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String showCates(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService as = new AdminServiceImpl();
		try {
			List<Category> list = as.showAllCates();
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/category/list.jsp";
	}

	public String addCateUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		return "/admin/category/add.jsp";
	}

	public String addCate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cname = request.getParameter("cname");
		String cid = UUIDUtils.getCode();
		Category c = new Category();
		c.setCid(cid);
		c.setCname(cname);
		AdminService as = new AdminServiceImpl();
		try {
			as.addCate(c);
			Jedis j = JedisUtils.getJedis();
			j.del("allCates");
			JedisUtils.closeJedis(j);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/AdminServlet?method=showCates");
		return null;
	}

	public String showProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService as = new AdminServiceImpl();
		int pageNum;
		String _pageNum = request.getParameter("pageNum");
		if (null == _pageNum) {
			pageNum = 1;
		} else {
			pageNum = Integer.parseInt(_pageNum);
		}

		try {
			Page p = as.showAllProduct(pageNum, 5);
			request.setAttribute("page", p);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/admin/product/list.jsp";

	}

	public String addProductUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService  cs = new CategoryServiceImpl();
		try {
			List<Category> list = cs.findAll();
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}

	public String addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			DiskFileItemFactory dff = new DiskFileItemFactory();
			ServletFileUpload sf = new ServletFileUpload(dff);
			List<FileItem> list= sf.parseRequest(request);
			Map<String,String> map = new HashMap<String,String>();
			for (FileItem item : list) {
				if(item.isFormField()) {
					map.put(item.getFieldName(), item.getString("utf-8"));
				}else {
					String realName = item.getName();
					String fileName = UUIDUtils.getId()+realName.substring(realName.lastIndexOf("."));
					InputStream is = item.getInputStream();
					File f = new File(getServletContext().getRealPath("/products/hao//")+fileName);
					System.out.println(f.getPath());
					OutputStream os = new FileOutputStream(f);
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					map.put(item.getFieldName(), "products/hao/"+fileName);
				}
				
			}
			
			Product p = new Product();
			BeanUtils.populate(p, map);
			p.setPid(UUIDUtils.getCode());
			p.setPflag(0);
			p.setPdate(new Date());
			AdminService as = new AdminServiceImpl();
			as.addProduct(p);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		response.sendRedirect(request.getContextPath() + "/AdminServlet?method=showProduct");
		return null;
	}
	
	
	
	public String updateCategoryUI (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		AdminService as = new AdminServiceImpl();
		Category c;
		try {
			c = as.findByCid(cid);
			request.setAttribute("c", c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/category/edit.jsp";
	}
	
	
	public String updateCategory (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		
		Category c = new Category();
		c.setCid(cid);
		c.setCname(cname);
		
		AdminService as = new AdminServiceImpl();
		try {
			as.updateCategory(c);
			Jedis j = JedisUtils.getJedis();
			j.del("allCates");
			JedisUtils.closeJedis(j);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/AdminServlet?method=showCates");
		return null;
	}
	
	
	public String showOrder (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String state = request.getParameter("state");
		AdminService as = new AdminServiceImpl();
		try {
			if(null==state||"".equals(state)) {
				List<Order> list = as.showOrder();
				request.setAttribute("list", list);
			}else {
				List<Order> list = as.showOrder(state);
				request.setAttribute("list", list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		return "/admin/order/list.jsp";
	}
	
	
	
	public String showOrderItems (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		AdminService as = new AdminServiceImpl();
		try {
			OrderService os = new OrderServiceImpl();
			Order order = os.findOrderByOid(oid);
			String str= JSONArray.fromObject(order.getList()).toString();
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	
	public String updateState (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid =request.getParameter("oid");
		OrderService os = new OrderServiceImpl();
		try {
			Order order = os.findOrderByOid(oid);
			order.setState(3);
			os.update(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/AdminServlet?method=showOrder&state=3");
		return null;
	}
	
}
