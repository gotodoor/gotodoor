package com.gotodoor.svc.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gotodoor.svc.alf.ICategoryService;
import com.gotodoor.svc.model.Categories;
import com.gotodoor.svc.model.CategoryList;

/**
 * 
 * @author nabbeher
 * 
 */
@Controller
@RequestMapping("/rest/v1/services")
public class CategoryController {
	private static final Logger logger = Logger
			.getLogger(CategoryController.class);

	@Autowired
	private ICategoryService categoryService;



	/*
	 * @Autowired public CategoryController(ICategoryService categoryService,
	 * MongoTemplate mongoTemplate){ this.categoryService = categoryService ;
	 * this.mongoTemplate = mongoTemplate;
	 * 
	 * }
	 */

	@RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
	/**
	 * 
	 * @param category
	 * @return all sub category of a category
	 */
	public ResponseEntity<Categories> getCategory(
			@RequestParam(value = "name", required = true) String category) {

		System.out.println(category);
		Categories cate = categoryService.getCategories(category);

		if (cate != null) {

			return new ResponseEntity<Categories>(cate, HttpStatus.OK);
		} else {
			return new ResponseEntity<Categories>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
	/**
	 * 	
	 * @param category
	 * @return top level category names
	 */
	public ResponseEntity<CategoryList> getCategories(
			@RequestParam(value = "name", required = false) String category) {

		System.out.println(category);
		List<Categories> cate = categoryService.getCategory();
		CategoryList list = new CategoryList();
		list.setCategories(cate);

		if (cate != null) {

			return new ResponseEntity<CategoryList>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<CategoryList>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * @RequestMapping(value = "/teams", method = RequestMethod.GET, produces =
	 * "application/json")
	 * 
	 * public ResponseEntity<List> getTeams(@RequestParam(value = "name",
	 * required = false)String category){
	 * 
	 * 
	 * return new ResponseEntity<List>(teamRepo.getTeams(), HttpStatus.OK); }
	 */

}
