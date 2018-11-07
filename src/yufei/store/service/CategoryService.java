package yufei.store.service;

import java.util.List;

import yufei.store.domain.Category;


public interface CategoryService {

	List<Category> findAll()throws Exception;

}
