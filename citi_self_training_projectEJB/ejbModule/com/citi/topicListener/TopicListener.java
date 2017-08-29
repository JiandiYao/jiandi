package com.citi.topicListener;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.citi.ejb.ControlManagerLocal;
import com.citi.ejb.LiveTradesLocal;
import com.citi.ejb.UserStockLocal;
import com.citi.ejb.stock.StockInfo;
import com.citi.ejb.stock.Trade;
import com.citi.strategy.FibLocal;

/**
 * Message-Driven Bean implementation class for: TopicListener
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		})
public class TopicListener implements MessageListener {

@EJB
UserStockLocal userStock;
@EJB
ControlManagerLocal manager;
@EJB
LiveTradesLocal liveTrades;
    /**
     * Default constructor. 
     */
    public TopicListener() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    	/*
    	 * filter stocks based on the header.
    	 * Only update the stocks that are in the interest list
    	 */
        List<StockInfo> list = (List<StockInfo>) message;
    	Set<String> interestedStocks = getAllStocks();
    	for(StockInfo info : list){
    		if(!interestedStocks.contains(info.getSymbol())){
    			list.remove(info);
    		}
    	}
    	
    	/*
    	 * Then call the controlManager to updateStocks
    	 */
    	manager.update(list);
    	Map<String, List<Trade>> trade = manager.updateTrades();
    	liveTrades.setTrades(trade);
    }

	private Set<String> getAllStocks() {
		// TODO Auto-generated method stub
		Set<String> stockSet = new HashSet<String>();
		 
		for(Entry entry: userStock.getUserStockMap().entrySet()){
			List<String> stocks = (List<String>) entry.getValue();
			stockSet.addAll(stocks);
		}
		return stockSet;
	}
}
