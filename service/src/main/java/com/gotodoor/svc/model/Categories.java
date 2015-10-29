package com.gotodoor.svc.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection="categories")

public class Categories {
//gotodoor.categories
	private String category;

	private String _id;
	
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	private String sub_category;

	public String getSub_category() {
		return sub_category;
	}

	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}
	
	public Categories(String category, String sub_category){
		this.category = category;
		this.sub_category = sub_category;
	}

	
	
}
