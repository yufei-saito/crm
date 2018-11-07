package yufei.store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import yufei.store.dao.OrderDao;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Page;
import yufei.store.domain.Product;
import yufei.store.utils.JDBCUtils;
import yufei.store.utils.MyBeanUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOrder(Order o,Connection con) throws SQLException {
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		qr.update(con, sql,o.getOid(),o.getOrdertime(),o.getTotal(),o.getState(),o.getAddress(),o.getName(),o.getTelephone(),o.getUser().getUid());
		
	}

	@Override
	public void addOrderItem(OrderItem oi,Connection con) throws SQLException {
		String sql="insert into orderitem values(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		qr.update(con, sql,oi.getItemid(),oi.getQuantity(),oi.getTotal(),oi.getP().getPid(),oi.getO().getOid());
	}

	@Override
	public Page findByUid(String uid, int pageNum, int everyPage) throws Exception {
		String sql="SELECT * FROM orders WHERE uid=? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] o = {uid, (pageNum-1)*everyPage, everyPage};
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),o);
		
		for (Order order : list) {
			String oid = order.getOid();
			String sql2="SELECT * FROM orderitem o, product p WHERE o.pid=p.pid and oid=?";
			List<Map<String, Object>> list2 = qr.query(sql2, new MapListHandler(),oid);
			for (Map map : list2) {
				OrderItem oi = new OrderItem();
				Product p = new Product();
				MyBeanUtils.populate(oi, map);
				MyBeanUtils.populate(p, map);
				oi.setP(p);
				oi.setO(order);
				order.getList().add(oi);
			}
		}
		int maxPage = this.findMaxPage(uid, everyPage);
		Page p = new Page(pageNum,maxPage);
		p.setList(list);
		return p;
	}

	@Override
	public int findMaxPage(String uid, int everyPage) throws Exception {
		String sql="SELECT * FROM orders WHERE uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
		return (int)Math.ceil((list.size()*1.0/everyPage));
	}

	@Override
	public int findMaxCount(String uid) throws Exception {
		String sql="SELECT * FROM orders WHERE uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
		return list.size();
	}

	@Override
	public Order findOne(String id) throws Exception {
		String sql="select * from orders where oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order o = qr.query(sql, new BeanHandler<Order>(Order.class),id);
		String sql2="SELECT * FROM orderitem o, product p WHERE o.pid=p.pid and oid=?";
		List<Map<String, Object>> list = qr.query(sql2, new MapListHandler(),id);
		for (Map map : list) {
			OrderItem oi = new OrderItem();
			Product p = new Product();
			MyBeanUtils.populate(oi, map);
			MyBeanUtils.populate(p, map);
			oi.setO(o);
			oi.setP(p);
			o.getList().add(oi);
		}
		
		return o;
	}



	@Override
	public void update(Order o) throws Exception {
		String sql="UPDATE orders SET  ordertime=?,total=? ,state=?,address=? ,NAME=? ,telephone=?  WHERE oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,o.getOrdertime(),o.getTotal(),o.getState(),o.getAddress(),o.getName(),o.getTelephone(),o.getOid());
		
	}
	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql="select * from orders where oid= ?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		Order order=qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		
		//根据订单id查询订单下所有的订单项以及订单项对应的商品信息
		sql="select * from orderitem o, product p where o.pid=p.pid and oid=?";
		List<Map<String, Object>> list02 = qr.query(sql, new MapListHandler(),oid);
		//遍历list
		for (Map map : list02) {
			OrderItem orderItem=new OrderItem();
			Product product=new Product();
			MyBeanUtils.populate(orderItem, map);
			//将map中属于product的数据自动填充到product对象上
			MyBeanUtils.populate(product, map);
			
			//让每个订单项和商品发生关联关系
			orderItem.setP(product);
			//将每个订单项存入订单下的集合中
			order.getList().add(orderItem);
		}
		return order;
	}
		
}
