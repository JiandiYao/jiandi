package com.citi.strategy;

import com.citi.ejb.stock.Observable;

public interface Observer {
	public void update(Observable o);
}
