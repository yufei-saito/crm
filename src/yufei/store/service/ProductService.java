package yufei.store.service;

import java.util.List;

import yufei.store.domain.Page;
import yufei.store.domain.Product;

public interface ProductService {

	List<Product> findNews()throws Exception;

	List<Product> findHots()throws Exception;

	Product findById(String pid)throws Exception;

	Page findByCid(String cid,int pageNum,int everyPage)throws Exception;
	
	

}
