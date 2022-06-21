package com.anup.blog.services;

import java.util.List;

import com.anup.blog.payloads.CategoryDTO;

public interface CategoryService {
	
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
	void deleteCategory(Integer categoryId);
	CategoryDTO getCategory(Integer categoryId);
	List<CategoryDTO> getCategories();
	
}
