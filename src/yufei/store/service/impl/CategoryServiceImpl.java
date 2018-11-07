package yufei.store.service.impl;

import java.util.List;

import yufei.store.dao.CategoryDao;
import yufei.store.dao.impl.CategoryDaoImpl;
import yufei.store.domain.Category;
import yufei.store.service.CategoryService;
import yufei.store.utils.BeanFactory;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao dao = (CategoryDao)BeanFactory.getBean("CategoryDao");
	@Override
	public List<Category> findAll() throws Exception {
		return dao.finAll();
	}

}
