package yufei.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import yufei.store.dao.AdminDao;
import yufei.store.domain.Category;
import yufei.store.domain.Order;
import yufei.store.domain.OrderItem;
import yufei.store.domain.Product;
import yufei.store.utils.JDBCUtils;

public class AdminDaoImpl implements AdminDao {

	@Override
	public List<Category> showAllCates() throws Exception {
		String sql = "select * from category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Category>(Category.class)); 
	}

	@Override
	public void addCate(Category c) throws Exception {
		String sql = "insert into category values(?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,c.getCid(),c.getCname());
	}

	@Override
	public List<Product> showAllProduct(int pageNum, int everyPage) throws Exception {
		String sql = "SELECT * FROM product limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),(pageNum-1)*everyPage,everyPage);
		return list;
	}
	
	@Override
	public int findMaxPage(int everyPage) throws Exception {
		String sql="SELECT * FROM product ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return (int)Math.ceil((list.size()*1.0/everyPage));
	}

	@Override
	public int findMaxCount() throws Exception {
		String sql="SELECT * FROM product ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return list.size();
	}

	@Override
	public void addProduct(Product p) throws Exception {
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] o = {p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCid()};
		qr.update(sql,o);
	}

	@Override
	public Category findByCid(String cid) throws Exception {
		String sql = "select * from category where cid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<Category>(Category.class),cid); 
	}

	@Override
	public void updateCategory(Category c) throws Exception {
		String sql = "update category set cname =? where cid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,c.getCname(),c.getCid());
		
	}

	@Override
	public List<Order> showOrder() throws Exception {
		String sql="select * from orders";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class));
		return list;
	}

	@Override
	public List<Order> showOrder(String state) throws Exception {
		String sql="select * from orders where state=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),state);
		return list;
	}

	@Override
	public List<OrderItem> showOrderItems(String oid) throws Exception {
		String sql="select * from orderitem where oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<OrderItem> list = qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class),oid);
		return list;
	}

}
