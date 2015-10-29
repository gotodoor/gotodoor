package com.gotodoor.svc.alf;

import java.util.List;

import com.gotodoor.svc.model.Categories;



public interface ICategoryService {

	public List<Categories> getCategory();
	
	public Categories getCategories(String category);
}