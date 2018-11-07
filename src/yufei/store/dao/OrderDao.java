package yufei.store.dao;

import java.sql.Connection;
import java.sql.SQLException;

import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Page;

public interface OrderDao {
	void addOrder(Order o,Connection con)throws SQLException;
	
	void addOrderItem(OrderItem oi,Connection con)throws SQLException;
	
	Page findByUid(String uid,int pageNum,int everyPage)throws Exception;
	
	int findMaxPage(String uid,int everyPage)throws Exception;
	
	int findMaxCount(String uid)throws Exception;
	
	Order findOne(String id)throws Exception;
	
	void update(Order o)throws Exception;
	
	Order findOrderByOid(String oid)throws Exception;
}
