package yufei.store.service.impl;

import java.util.List;

import yufei.store.dao.AdminDao;
import yufei.store.dao.impl.AdminDaoImpl;
import yufei.store.domain.Category;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Page;
import yufei.store.domain.Product;
import yufei.store.service.AdminService;
import yufei.store.utils.BeanFactory;

public class AdminServiceImpl implements AdminService {
	AdminDao dao = (AdminDao)BeanFactory.getBean("AdminDao");
	@Override
	public List<Category> showAllCates() throws Exception {
		return dao.showAllCates();
	}
	@Override
	public void addCate(Category c) throws Exception {
		dao.addCate(c);

	}
	@Override
	public Page showAllProduct(int pageNum, int everyPage) throws Exception {
		Page p = new Page(pageNum,dao.findMaxPage(everyPage));
		p.setMaxCount(dao.findMaxCount());
		List<Product> list = dao.showAllProduct(pageNum,everyPage);
		p.setList(list);
		p.setEveryPage(everyPage);
		p.setUrl("AdminServlet?method=showProduct");
		
		return p;
	}
	@Override
	public void addProduct(Product p) throws Exception {
		dao.addProduct(p);
		
	}
	@Override
	public Category findByCid(String cid) throws Exception {

		return dao.findByCid(cid);
	}
	@Override
	public void updateCategory(Category c) throws Exception {
		dao.updateCategory(c);
		
	}
	@Override
	public List<Order> showOrder() throws Exception {
		// TODO Auto-generated method stub
		return dao.showOrder();
	}
	@Override
	public List<Order> showOrder(String state) throws Exception {
		// TODO Auto-generated method stub
		return dao.showOrder(state);
	}
	@Override
	public List<OrderItem> showOrderItems(String oid) throws Exception {
		return dao.showOrderItems(oid);
	}

}
