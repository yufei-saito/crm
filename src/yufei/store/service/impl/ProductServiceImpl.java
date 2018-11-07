package yufei.store.service.impl;

import java.util.List;

import yufei.store.dao.ProductDao;
import yufei.store.dao.impl.ProductDaoImpl;
import yufei.store.domain.Page;
import yufei.store.domain.Product;
import yufei.store.service.ProductService;
import yufei.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
	ProductDao dao = (ProductDao)BeanFactory.getBean("ProductDao");
	@Override
	public List<Product> findNews() throws Exception {

		return dao.findNews();
	}

	@Override
	public List<Product> findHots() throws Exception {

		return dao.findHots();
	}

	@Override
	public Product findById(String pid) throws Exception{
		return dao.findById(pid);
	}

	@Override
	public Page findByCid(String cid,int pageNum,int everyPage) throws Exception {
		Page p = dao.findByCid(cid,pageNum,everyPage);
		p.setEveryPage(everyPage);
		p.setMaxCount(dao.findMaxCount(cid));
		//System.out.println(p);
		p.setUrl("ProductServlet?method=findByCid&cid="+cid);
		return p;
	}

}
