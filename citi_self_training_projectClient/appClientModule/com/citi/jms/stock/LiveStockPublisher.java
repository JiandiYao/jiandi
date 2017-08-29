package com.citi.jms.stock;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.citi.bean.display.ISubscribedStock;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/*
 * Get realtime data from Yahoo API and publish to JMS topic
 */
public class LiveStockPublisher implements IObserver {

	private List<String> stock;
	private ISubscribedStock subStock;
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		List<String> stocks = (List<String>) subStock.getUpdate(this);
	}
	@Override
	public void setStock(List<String> stock) {
		// TODO Auto-generated method stub
		this.stock = stock;
	} 

	public Map<String, Stock> getStockFromYahoo(){
		String[] symbols = (String[]) stock.toArray();
		try {
			return YahooFinance.get(symbols);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;

	}
	public void pub(Map<String, Stock> map){
		try {
			InitialContext context = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) context.lookup("connection");
			Topic topic = (Topic) context.lookup("topic");
			Connection conn;
			try {
				conn=factory.createConnection();
				Session session = conn.createSession();
				MessageProducer producer = session.createProducer(topic);
				TextMessage message = session.createTextMessage();
				message.setText(map.toString());
				producer.send(message);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
