package com.citi.bean.display;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;


import com.citi.jms.stock.IObserver;

import yahoofinance.Stock;

/**
 * Session Bean implementation class SubscribedStockList
 */
@Singleton
@LocalBean
public class SubscribedStockList implements ISubscribedStock, SubscribedStockListRemote, SubscribedStockListLocal {

	List<Stock> list = new ArrayList<Stock>();

	@Override
	public void addStock(Stock stock) {
		// TODO Auto-generated method stub
		list.add(stock);
	}

	@Override
	public void removeStock(Stock stock) {
		// TODO Auto-generated method stub
		list.remove(stock);
	}

	@Override
	public List<Stock> getStock() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public void register(IObserver obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregister(IObserver obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getUpdate(IObserver obj) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
