package com.citi.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface UserStockLocal {
	public Map<String, List<String>> getUserStockMap();
	public void setUserStockMap(Map<String, List<String>> userStockMap);
}
