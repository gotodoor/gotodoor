package com.gotodoor.svc.security;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
/**
 * 
 * @author nabbeher
 *
 */
public class CacheUserDataManager {

	private static final Logger logger = Logger.getLogger(CacheUserDataManager.class);
	private String localCacheName;
	
	public String getLocalCacheName() {
		return localCacheName;
	}

	public void setLocalCacheName(String localCacheName) {
		this.localCacheName = localCacheName;
	}

	private EhCacheManagerFactoryBean cacheManager;
	
	@Autowired
	public void setCacheManager(EhCacheManagerFactoryBean cacheManager) {
		this.cacheManager = cacheManager;
	}

	private Ehcache userChache;
	
	public CacheUserDataManager(){
		
	}
	
	/**
	 * 
	 * @param userName
	 * @param userData
	 */
	public void addUserData(String userName, String ticket){
		
		userChache = (Ehcache) cacheManager.getObject().getCache(getLocalCacheName());
		
        Element element = new Element( userName, ticket );
        logger.info("add user to Cache::"+ticket);
        userChache.put(element);
	}
	
	
	public String getUserData(String userName){
		userChache = (Ehcache) cacheManager.getObject().getCache(getLocalCacheName());
		if(userChache!= null){
		Element element = userChache.get( userName);
		//
	        if( element != null )
	        {
	        	
	        	logger.info("Total Size:::"+userChache.getSize());
	        	logger.info("total keys:"+ userChache.getKeys());
	            return ( String )element.getValue();
	        }
		}
		return null;	
	}
	
}
