package com.citi.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.citi.ejb.stock.StockInfo;
import com.citi.ejb.stock.Trade;

/**
 * Session Bean implementation class ControlManager
 * Link for session bean/entity bean tutorial
 * http://sqltech.cl/doc/oas10gR31/core.1013/b28764/ejb005.htm
 */
@Stateless
@LocalBean
public class ControlManager implements ControlManagerRemote, ControlManagerLocal {

//	@EJB
//	UserStockLocal userStock;
//	@EJB
//	UserStrategyLocal userStrategy;
	@EJB
	UserListLocal userList;
	@EJB
	ProcessorLocal processor;
	
	private List<StockInfo> stocks;
    /**
     * Default constructor. 
     */
    public ControlManager() {
        // TODO Auto-generated constructor stub
    }


	/*
	 * update stockInfo objects, used by MDB
	 */
	@Override
	public void update(List<StockInfo> stocks) {
		// TODO Auto-generated method stub
		this.stocks = stocks;
	}
	
	/*
	 * Update the trades to Web, used by servlet/jsp
	 */
	@Override
	public Map<String, List<Trade>> updateTrades() {
		// TODO Auto-generated method stub
		return processor.getTrades(stocks);
	}

	/*
	 * Web uses it to subscribe a stock
	 * (non-Javadoc)
	 * @see com.citi.ejb.ControlManagerRemote#subscribeStock(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean subscribeStock(String stock, String user) {
		/*
		 * Write to database and update the UserStock singleton bean
		 */
		boolean success = false;
		
		return success;
	}

	/*
	 * Similar to subscribeStock(), unsubscribeStock() is used to remove a stock symbol from user
	 * (non-Javadoc)
	 * @see com.citi.ejb.ControlManagerRemote#unSubscribeStock(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean unSubscribeStock(String stock, String user) {
		// TODO Auto-generated method stub
		
		boolean success = false;
		
		return success;
	}
	@Override
	public boolean authenticateUserLogin(String user, String password) {
		// TODO Auto-generated method stub
		boolean pass = true;
		/*
		 * query database to confirm correctness
		 */
		
		
		
		
		
		
		
		
		
		if(pass){
			updateUserList(user);
			return true;
		}else
			return false;
			
	}
	
	private void updateUserList(String user) {
		// TODO Auto-generated method stub
		userList.getUserList().add(user);
	}


}
