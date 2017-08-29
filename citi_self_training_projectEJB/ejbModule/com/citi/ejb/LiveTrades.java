package com.citi.ejb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import com.citi.ejb.stock.Trade;

/**
 * Session Bean implementation class LiveTrades
 */
@Singleton
@LocalBean
public class LiveTrades implements LiveTradesRemote, LiveTradesLocal {

	Queue<Map<String, List<Trade>>> trades;
    /**
     * Default constructor. 
     */
    public LiveTrades() {
        // TODO Auto-generated constructor stub
    	trades = new LinkedList<Map<String, List<Trade>>>();
    }

	@Override
	public Queue<Map<String, List<Trade>>> getTrades() {
		// TODO Auto-generated method stub
		return trades;
	}

	public void setTrades(Map<String, List<Trade>> trade) {
		this.trades.add(trade);
	}


}
