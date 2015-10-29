package com.gotodoor.svc.alf.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gotodoor.svc.alf.ICategoryService;
import com.gotodoor.svc.model.Categories;
import com.gotodoor.svc.model.CategoryList;

import com.gotodoor.svc.services.CategoryTemplate;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

@Component
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	CategoryTemplate cTemplate;
	

	
	@Override
	public List<Categories> getCategory() {
		
		return cTemplate.getCategories();
		//return cinterface.findAll();
	}

	@Override
	public Categories getCategories(String category) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
