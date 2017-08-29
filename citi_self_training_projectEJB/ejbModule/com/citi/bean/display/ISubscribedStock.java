package com.citi.bean.display;

import com.citi.jms.stock.IObserver;

public interface ISubscribedStock {

	public void register(IObserver obj);
	public void unregister(IObserver obj);
	public void notifyObservers();
	public Object getUpdate(IObserver obj);
}
