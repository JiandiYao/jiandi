package com.citi.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.citi.strategy.Strategy;

@Local
public interface UserStrategyLocal {
	public Map<String, List<String>> getUserStrategyMap();
	public void setUserStrategyMap(Map<String, List<String>> userStrategyMap);
}
