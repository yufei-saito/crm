package yufei.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import yufei.store.dao.ProductDao;
import yufei.store.domain.Page;
import yufei.store.domain.Product;
import yufei.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findNews() throws Exception {
		String sql="SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list =qr.query(sql, new BeanListHandler<Product>(Product.class));
		return list;
	}

	@Override
	public List<Product> findHots() throws Exception {
		String sql="SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list =qr.query(sql, new BeanListHandler<Product>(Product.class));
		return list;
	}

	@Override
	public Product findById(String pid) throws Exception {
		String sql="SELECT * FROM product WHERE pid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<Product>(Product.class),pid);
		 
	}

	@Override
	public Page findByCid(String cid,int pageNum,int everyPage) throws Exception {
		String sql="SELECT * FROM product WHERE cid=? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),cid,(pageNum-1)*everyPage,everyPage);
		int maxPage = findMaxPage(cid,everyPage);
		Page p = new Page(pageNum,maxPage);
		p.setList(list);
		return p;

	}

	@Override
	public int findMaxPage(String cid,int everyPage) throws Exception {
		String sql="SELECT * FROM product WHERE cid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),cid);
		return (int)Math.ceil((list.size()*1.0/everyPage));
	}

	@Override
	public int findMaxCount(String cid) throws Exception {
		String sql="SELECT * FROM product WHERE cid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),cid);
		return list.size();
	}

}
