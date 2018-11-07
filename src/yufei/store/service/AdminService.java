package yufei.store.service;

import java.util.List;

import yufei.store.domain.Category;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Page;
import yufei.store.domain.Product;

public interface AdminService {
	List<Category> showAllCates()throws Exception;

	void addCate(Category c)throws Exception;

	Page showAllProduct(int pageNum, int i)throws Exception;

	void addProduct(Product p)throws Exception;

	Category findByCid(String cid)throws Exception;

	void updateCategory(Category c)throws Exception;

	List<Order> showOrder()throws Exception;

	List<Order> showOrder(String state)throws Exception;

	List<OrderItem> showOrderItems(String oid)throws Exception;
}
