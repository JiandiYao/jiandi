package com.citi.ejb;

import java.util.List;

import com.citi.ejb.stock.StockInfo;

public interface Observer {
	public void update(List<StockInfo> stocks);
}
