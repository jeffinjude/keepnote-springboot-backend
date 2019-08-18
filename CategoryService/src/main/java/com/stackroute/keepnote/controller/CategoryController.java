package com.stackroute.keepnote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.CategoryService;

@RestController
public class CategoryController {

	/**
	 * 
	 */
	private CategoryService catSer;

	/**
	 * 
	 * @param catSer
	 */
	@Autowired
	public CategoryController(CategoryService catSer) {
		this.catSer = catSer;
	}

	/**
	 * 
	 * @param cat
	 * @return
	 */
	@PostMapping("/api/v1/category")
	public ResponseEntity<Category> createCat(@RequestBody Category cat) {
		boolean status = false;
		try {
			this.catSer.createCategory(cat);
			status = true;
		} catch (CategoryNotCreatedException e) {
		}
		return status ? new ResponseEntity<>(cat, HttpStatus.CREATED) : new ResponseEntity<>(cat, HttpStatus.CONFLICT);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/api/v1/category/{id}")
	public ResponseEntity<String> removeCategory(@PathVariable("id") String id) {
		boolean status = false;
		try {
			if (this.catSer.deleteCategory(id)) {
				status = true;
			}
		} catch (CategoryDoesNoteExistsException e) {
		}
		return status ? ResponseEntity.status(HttpStatus.OK).body("deleted category successfully")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("category not found ");
	}

	/**
	 * 
	 * @param cat
	 * @param id
	 * @return
	 */
	@PutMapping("/api/v1/category/{id}")
	public ResponseEntity<Category> modifyCategory(@RequestBody Category cat, @PathVariable("id") String id) {
		boolean status = false;

		if (this.catSer.updateCategory(cat, id) != null) {
			status = true;
		}

		return status ? new ResponseEntity<>(cat, HttpStatus.OK) : new ResponseEntity<>(cat, HttpStatus.CONFLICT);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/api/v1/category/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") String id) {
		Category cat = null;
		try {
			cat = this.catSer.getCategoryById(id);
		} catch (Exception e) {
		}

		return cat != null ? new ResponseEntity<>(cat, HttpStatus.OK) : new ResponseEntity<>(cat, HttpStatus.NOT_FOUND);
	}

}
