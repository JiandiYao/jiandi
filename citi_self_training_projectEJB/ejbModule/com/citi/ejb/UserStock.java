package com.citi.ejb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class UserStock
 */
@Singleton
@LocalBean
public class UserStock implements UserStockLocal {

    private Map<String, List<String>> userStockMap;
   
    public UserStock() {
        // TODO Auto-generated constructor stub
    	userStockMap = new HashMap<String, List<String>>();
    	
    }
	public Map<String, List<String>> getUserStockMap() {
		return userStockMap;
	}
	public void setUserStockMap(Map<String, List<String>> userStockMap) {
		this.userStockMap = userStockMap;
	}
    
}
