package yufei.store.service;

import yufei.store.domain.Order;
import yufei.store.domain.Page;

public interface OrderService {
		void addOrder(Order o);

		Page findByUid(String uid, int pageNum, int everyPage) throws Exception;

		Order findOne(String id)throws Exception;


		void update(Order o)throws Exception;
		
		Order findOrderByOid(String oid) throws Exception;
}
