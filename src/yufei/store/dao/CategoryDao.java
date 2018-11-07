package yufei.store.dao;

import java.util.List;

import yufei.store.domain.Category;


public interface CategoryDao {

	List<Category> finAll()throws Exception;

}
