package com.citi.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.citi.ejb.stock.Trade;

@Remote
public interface ControlManagerRemote {

	public boolean subscribeStock(String stock, String user);
	public boolean unSubscribeStock(String stock, String user);
	public boolean authenticateUserLogin(String user, String password);
	public Map<String, List<Trade>> updateTrades();
}
