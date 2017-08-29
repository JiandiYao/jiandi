package com.citi.bean.display;

import java.util.List;

import javax.ejb.Local;

import yahoofinance.Stock;

@Local
public interface SubscribedStockListLocal {

	public void addStock(Stock stock);
	public void removeStock(Stock stock);
	public List<Stock> getStock();
}
