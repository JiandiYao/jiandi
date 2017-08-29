package com.citi.ejb;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.ejb.Remote;

import com.citi.ejb.stock.Trade;

@Remote
public interface LiveTradesRemote {
	public Queue<Map<String, List<Trade>>> getTrades();
}
