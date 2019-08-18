package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	/**
	 * 
	 */
	private CategoryRepository catRepo;

	/**
	 * 
	 * @param catRepo
	 */
	@Autowired
	public CategoryServiceImpl(CategoryRepository catRepo) {
		this.catRepo = catRepo;
	}

	/**
	 * 
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {

		Category catExists = null;
		try {
			catExists = getCategoryById(category.getId());
		} catch (Exception e) {
		}

		if (catExists == null) {
			category.setCategoryCreationDate(new Date());
			catExists = this.catRepo.insert(category);
		} else {
			throw new CategoryNotCreatedException("category already exists");
		}

		if (catExists == null) {
			throw new CategoryNotCreatedException("category creation failed");
		}

		return category;
	}

	/**
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {

		Optional<Category> catExists = this.catRepo.findById(categoryId);

		if (!catExists.isPresent() || (catExists.isPresent() && catExists.get() == null)) {
			throw new CategoryDoesNoteExistsException("category not found");
		}
		this.catRepo.deleteById(categoryId);
		return true;
	}

	/**
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {

		Optional<Category> catExists = this.catRepo.findById(categoryId);
		if (catExists.isPresent()) {
			category.setId(categoryId);
			category.setCategoryCreationDate(new Date());
			this.catRepo.save(category);
		}

		return category;
	}

	/**
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
		Category cat = null;
		try {
			cat = this.catRepo.findById(categoryId).get();

			if (cat == null) {
				throw new CategoryNotFoundException("Category not found");
			}

		} catch (Exception e) {
			throw new CategoryNotFoundException("Category not found");
		}
		return cat;
	}

	/**
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {

		return catRepo.findAllCategoryByCategoryCreatedBy(userId);
	}

}
