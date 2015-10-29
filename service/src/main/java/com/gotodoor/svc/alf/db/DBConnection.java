package com.gotodoor.svc.alf.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author nabbeher
 *
 */
public class DBConnection {
	
	public static void main(String []args){
		
		//MongoClient mongo = new MongoClient( "alfurdapp-dev-001" , 27017 );
		//50.174.18.42
		

		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
		seeds.add( new ServerAddress( "50.174.18.42", 16017));
		
		//adminuser1   2015!GHANSHYAM  mygo2door  2015ghanshyam
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(
		    MongoCredential.createCredential(
		        "adminuser1",
		        "gotodoor",
		        "2015!GHANSHYAM".toCharArray()
		    )
		);
		MongoClient mongo = new MongoClient( seeds, credentials);
		
		//MongoCredential credential = MongoCredential.createCredential("mygo2door", "gotodoor", "2015ghanshyam".toCharArray());
		
		MongoDatabase db = mongo.getDatabase("gotodoor");
		
		
		System.out.println(db.getCollection("categories").count());
		
		FindIterable<org.bson.Document> d = db.getCollection("categories").find();
		MongoCursor<org.bson.Document> it = d.iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
		
		/*DB db = mongo.getDB("gotomkt");
		
		DBCollection table = db.getCollection("teams");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("country","England");
		
		// for all the colection for this group
		//DBCursor cursor = table.find();

		//predicate
		DBCursor cursor = table.find(searchQuery);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}*/
	}

}
