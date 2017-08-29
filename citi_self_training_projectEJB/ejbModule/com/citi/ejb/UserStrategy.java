package com.citi.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import com.citi.strategy.Strategy;

/**
 * Session Bean implementation class UserStrategy
 */
@Singleton
@LocalBean
public class UserStrategy implements UserStrategyLocal {

	/*
	 * <user, List<Strategy>>
	 */
	Map<String, List<String>> userStrategyMap;
    /**
     * Default constructor. 
     */
    public UserStrategy() {
        // TODO Auto-generated constructor stub
    	userStrategyMap = new HashMap<String, List<String>>();
    }
	public Map<String, List<String>> getUserStrategyMap() {
		return userStrategyMap;
	}
	public void setUserStrategyMap(Map<String, List<String>> userStrategyMap) {
		this.userStrategyMap = userStrategyMap;
	}

}
