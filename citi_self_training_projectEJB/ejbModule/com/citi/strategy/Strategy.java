package com.citi.strategy;

import java.util.List;

import com.citi.ejb.stock.StockInfo;
import com.citi.ejb.stock.Trade;

public interface Strategy {

	public void setUp();
	public List<Trade> evluate(StockInfo stock);
	public List<Trade> evaluate(List<StockInfo> stocks);
	public String getId();
}
