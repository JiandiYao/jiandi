package com.citi.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.citi.ejb.stock.StockInfo;
import com.citi.ejb.stock.Trade;
import com.citi.strategy.FibLocal;
import com.citi.strategy.Strategy;

/**
 * Session Bean implementation class Processor
 */
@Stateless
@LocalBean
public class Processor implements ProcessorLocal {
	@EJB
	UserStockLocal userStock;
	@EJB
	UserStrategyLocal userStrategy;
	@EJB
	UserListLocal userList;
	@EJB
	FibLocal fib;
    /**
     * Default constructor. 
     */
    public Processor() {
        // TODO Auto-generated constructor stub
    }


	@Override
	public Map<String, List<Trade>> getTrades(List<StockInfo> stocks) {
		// TODO Auto-generated method stub
		/*
		 * <strategy, List<StockInfo>>
		 */
		Map<String, List<StockInfo>> strategyStockMap = getStrategyStockMap(stocks);
		/*
		 * <Strategy, List<trade>>
		 */
		Map<String, List<Trade>> trades = new HashMap<String, List<Trade>>();
		
		for(Entry entry: strategyStockMap.entrySet()){
			String strategy = (String) entry.getKey();
			List<Trade> tradeList = fib.evaluate((List<StockInfo>) entry.getValue());
			trades.put(strategy, tradeList);
		}
		
		return getUserTradeMap(trades);
	}
    
    /*
	 * Get all the stock list that each strategy needs to evaluate()
	 */
	private Map<String, List<StockInfo>> getStrategyStockMap(List<StockInfo> stocks) {
		// TODO Auto-generated method stub
		
		Map<String, List<StockInfo>> strategyStockMap = new HashMap<String, List<StockInfo>>();
		
		/*
		 * user-List<Strategy>
		 * user-List<Stock>
		 */
		/*
		 * stockSet is used to put all the interested stocks without considering the users
		 */
		
		
		for(StockInfo stockInfo: stocks){
			/*
			 * Then use the userStock and userStrategy to decide which map to put into
			 */
			for(String user: userList.getUserList()){
				List<String> strategies = userStrategy.getUserStrategyMap().get(user);
				List<String> stock = userStock.getUserStockMap().get(user);
				/*
				 * Loop throught all the strategies
				 */
				for(String s: strategies){
					/*
					 * If this StockInfo happens to be in a user's interested stocks
					 * this user's interested strategies need to evaluate this stock
					 */
					if(stock.contains(stockInfo.getSymbol())){
						if(!strategyStockMap.containsKey(s)){
							List<StockInfo> i = new ArrayList<StockInfo>();
							strategyStockMap.put(s, i);
						}else{
							strategyStockMap.get(s).add(stockInfo);
						}
					}
					
				}
			}
			
		}
		return strategyStockMap;
	}




	/*
	 * Input: <Strategy, List<trade>>
	 * <User,  List<Trade>>
	 * A user has multiple Trades which are evaluated by different strategies
	 */
	private Map<String, List<Trade>> getUserTradeMap(Map<String, List<Trade>> trades) {
		Map<String, List<Trade>> userTrades = new HashMap<String, List<Trade>>();
		
		for(String user: userList.getUserList()){
			
		}
		return userTrades;
	}

	/*
	 * Update the trades in database
	 */
	private void updateDBTrade(Map<String, List<Trade>> map) {
		// TODO Auto-generated method stub
		
	}




}
