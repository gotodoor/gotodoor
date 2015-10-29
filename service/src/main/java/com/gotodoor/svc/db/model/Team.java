package com.gotodoor.svc.db.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author nabbeher
 *
 */
@Document
public class Team {

	private String country;
	private String GroupName;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGroupname() {
		return GroupName;
	}
	public void setGroupname(String groupname) {
		this.GroupName = groupname;
	}
	
}
