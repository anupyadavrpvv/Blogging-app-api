package com.anup.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anup.blog.entities.Category;
import com.anup.blog.exceptions.ResourceNotFoundException;
import com.anup.blog.payloads.CategoryDTO;
import com.anup.blog.repositories.CategoryRepository;
import com.anup.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category= this.mapper.map(categoryDTO, Category.class);
		Category addedCategory= this.categoryRepo.save(category);
		
		return this.mapper.map(addedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		
		Category cat= this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		cat.setCategoryDescription(categoryDTO.getCategoryDescription());
		
		Category updatedCategory=  this.categoryRepo.save(cat);
		
		return this.mapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat= this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "CAtegory Id", categoryId));
		
		return this.mapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCategories() {
		List<Category> categories=this.categoryRepo.findAll();
		
		List<CategoryDTO> categoryDTOs= categories.stream()
												.map((cat) -> this.mapper.map(cat, CategoryDTO.class))
												.collect(Collectors.toList());
		
		return categoryDTOs;
	}
	
	
}
