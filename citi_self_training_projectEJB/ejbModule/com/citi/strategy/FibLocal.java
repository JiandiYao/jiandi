package com.citi.strategy;


import javax.ejb.Local;

import com.citi.ejb.stock.StockMapLocal;

@Local
public interface FibLocal extends Strategy, Observer {
	public void init(StockMapLocal map);
}
