package yufei.store.dao;

import java.util.List;

import yufei.store.domain.Category;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Product;

public interface AdminDao {
	List<Category> showAllCates()throws Exception;

	void addCate(Category c)throws Exception;

	List<Product> showAllProduct(int pageNum, int everyPage)throws Exception;
	
	int findMaxPage(int everyPage)throws Exception ;
	int findMaxCount() throws Exception;

	void addProduct(Product p)throws Exception;

	Category findByCid(String cid)throws Exception;

	void updateCategory(Category c)throws Exception;

	List<Order> showOrder()throws Exception;

	List<Order> showOrder(String state)throws Exception;

	List<OrderItem> showOrderItems(String oid)throws Exception;
}
