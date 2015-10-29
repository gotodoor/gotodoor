package com.gotodoor.svc.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.gotodoor.svc.model.Categories;

/**
 * 
 * @author nabbeher
 *
 */
@Repository
public class CategoryTemplate {

	private static final Logger logger = Logger.getLogger(CategoryTemplate.class);
	

	@Autowired
	@Qualifier("mongoTemplate")
	MongoTemplate mongoTemplate;
	
	public List<Categories> getCategories(){
		
	
		//List<Categories> list = mongoTemplate.find(new Query(Criteria.where("categories")), Categories.class);
		System.out.println("list mongoTemplate" );
		List<Categories> list = mongoTemplate.findAll(Categories.class);
		System.out.println("list :;" + list.size());
		logger.info(list.size());
		return list;
	}
	
}
