package com.citi.bean.display;

import java.util.List;

import javax.ejb.Remote;

import yahoofinance.Stock;

@Remote
public interface SubscribedStockListRemote {
	public void addStock(Stock stock);
	public void removeStock(Stock stock);
	public List<Stock> getStock();
}
