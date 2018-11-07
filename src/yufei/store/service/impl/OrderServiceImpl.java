package yufei.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import yufei.store.dao.OrderDao;
import yufei.store.dao.impl.OrderDaoImpl;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Page;
import yufei.store.service.OrderService;
import yufei.store.utils.BeanFactory;
import yufei.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {
	OrderDao dao = (OrderDao)BeanFactory.getBean("OrderDao");
	@Override
	public void addOrder(Order o)  {
			Connection con = null;
			try {
				con = JDBCUtils.getConnection();
				con.setAutoCommit(false);
				dao.addOrder(o, con);
				for (OrderItem oi : o.getList()) {
					dao.addOrderItem(oi, con);
				}
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		
	}
	@Override
	public Page findByUid(String uid, int pageNum, int everyPage) throws Exception {
		Page p = dao.findByUid(uid, pageNum, everyPage);
		p.setMaxCount(dao.findMaxCount(uid));
		p.setEveryPage(everyPage);
		p.setUrl("OrderServlet?method=showOrder");
		return p;
	}
	@Override
	public Order findOne(String id) throws Exception {
		return dao.findOne(id);
	}

	@Override
	public void update(Order o) throws Exception {
		dao.update(o);
		
	}
	@Override
	public Order findOrderByOid(String oid) throws Exception {
		// TODO Auto-generated method stub
		return dao.findOrderByOid(oid);
	}

}
