package yufei.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;
import yufei.store.domain.Category;
import yufei.store.service.CategoryService;
import yufei.store.service.impl.CategoryServiceImpl;
import yufei.store.utils.JedisUtils;
import yufei.store.web.base.BaseServlet;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService cs = new CategoryServiceImpl();
		Jedis j = JedisUtils.getJedis();
		String jsonStr = j.get("allCates");
		if (jsonStr == null) {
			try {
				List<Category> list = cs.findAll();
				jsonStr = JSONArray.fromObject(list).toString();
				j.set("allCates", jsonStr);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JedisUtils.closeJedis(j);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
		return null;
	}
}
