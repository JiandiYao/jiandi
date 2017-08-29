package com.citi.ejb;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.ejb.Local;

import com.citi.ejb.stock.Trade;

@Local
public interface LiveTradesLocal {
	public Queue<Map<String, List<Trade>>> getTrades();
	public void setTrades(Map<String, List<Trade>> trade);
}
