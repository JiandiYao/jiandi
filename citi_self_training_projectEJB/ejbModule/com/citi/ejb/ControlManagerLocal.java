package com.citi.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.citi.ejb.stock.StockInfo;
import com.citi.ejb.stock.Trade;

@Local
public interface ControlManagerLocal extends Observer{
	public Map<String, List<Trade>> updateTrades();
//	public void updateUserList(String user);
	
}
