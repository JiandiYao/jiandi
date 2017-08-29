package com.citi.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.citi.ejb.stock.Observable;
import com.citi.ejb.stock.ProfolioLocal;
import com.citi.ejb.stock.StockInfo;
import com.citi.ejb.stock.StockMapLocal;
import com.citi.ejb.stock.Trade;
import com.citi.ejb.stock.WatchListLocal;

import utilities.AlgorithmToolKit;
import utilities.GeneralToolKit;

/**
 * Session Bean implementation class Strategy1
 */
@Stateless
@Local(Strategy.class)
@LocalBean
public class Fib implements FibLocal, FibRemote {
	private StockMapLocal map;
	private static Logger logger = Logger.getLogger("this");
	private String symbol = "AAPL";

    /**
     * Default constructor. 
     */
	public Fib(){
		
	}
    public void init(StockMapLocal map) {
        // TODO Auto-generated constructor stub
    	/*
    	 * Need to be changed later. For now, it only process AAPL
    	 * In the future, there should be a list/Singleton Bean that maintains which strategy to process which stock
    	 */
    	this.map = map;
    	map.addObserver(this, symbol);
    }

    @EJB
	WatchListLocal watchList;
	@EJB
	ProfolioLocal myPortfolio;
	private List<String> watch;
	// Number of stocks in each group
	private final int specStockNumber = 5;
	private final int smallStockNumber = 10;
	private final int largeStockNumber = 10;
	
	// Indicates the dollar amount for each stock group
	private final double specStockAmount = 350000000;
	private final double smallStockAmount = 2000000000;
	
	// Allocated money for each stock group
	private double allowedSpecMoneyAmount;
	private double allowedSmallMoneyAmount;
	private double allowedLargeMoneyAmount;
	
	private StockInfo info = null;
	
	@Override
	public void setUp(){
		watch = watchList.getWatchList();
		// Sets up the alloted money amount for each group of stocks
		double moneyAmount = myPortfolio.getMoneyAmount();
		int totalStocks = specStockNumber + smallStockNumber + largeStockNumber;
		
		allowedSpecMoneyAmount = moneyAmount * ((float) specStockNumber / totalStocks);
		allowedSmallMoneyAmount = moneyAmount * ((float) smallStockNumber / totalStocks);
		allowedLargeMoneyAmount = moneyAmount * ((float) largeStockNumber / totalStocks);
	}
	
	@Override
	public void update(Observable o) {
		// TODO Auto-generated method stub
		if(map == o){
			info = map.getStock();
			logger.info("stock info is updated: "+info.toString());
			//Need to implment something
			
			
			
			
			
			
			
			evluate(info);
		}
	}

	@Override
	public List<Trade> evluate(StockInfo stock) {
		// TODO Auto-generated method stub
		List<Trade> trades = new ArrayList<Trade>();
		// Makes move for each stock in watch list
		
		for (String s : watch) {
			Trade trade = determineFibonacciMove(s);
			if(trade != null)
				trades.add(trade);
		}

		return trades;
	}

	private Trade determineFibonacciMove(String symbol) {
		System.out.println("\nMaking move for " + symbol);
		Trade move = null;

		// Verification that the Stock Market is open
		if (GeneralToolKit.isMarketOpen()) {
			double daysHigh = info.getDayHigh();
			double daysLow = info.getDayLow(); 
			double curPrice = info.getLastTradePrice();
			
			// Computes Values to be used as the high and low for the Fibonacci Retracement calculation
			// If the days high and low are close, use the averages instead
			String[] fibonacciCompVals;
			if ((daysHigh - daysLow) <= (curPrice * .007)) {
				fibonacciCompVals = AlgorithmToolKit.calculateHighAndLow_HLAVG(symbol);
			} else {
				fibonacciCompVals = AlgorithmToolKit.calculateHighAndLow_HDAILY(symbol);
			}

			double low = Double.parseDouble(fibonacciCompVals[0]);
			double high = Double.parseDouble(fibonacciCompVals[1]);
			double threshold = Double.parseDouble(fibonacciCompVals[2]);

			int sharesToSell = 0;
			int sharesToBuy = 0;
			
			// Calculates Fibonacci Retracement values
			ArrayList<Double> levels = AlgorithmToolKit.calcFibRetrace(high, low);
			double approximateVolume = AlgorithmToolKit.approximateVolume(symbol);
			
			// Retrieves the Stock's average daily volume over a period of 10 days
			double tenDayVolAvg = info.getAverageDailyVolume();
			System.out.println("Current Price: " + curPrice);
			System.out.println("Threshold of " + threshold);

			// For each level, determine if the Stock's current price is within the threshold.
			// If it is, it then determines if it has heavy or light volume by the time the market is closed.
			// It then determines a move based on the volume.
			for (int i = 0; i < levels.size(); i++) {
				// Support Case
				if (curPrice >= levels.get(i)) {

					if (curPrice <= (levels.get(i) + threshold)) {
						System.out.println("Potential Support at: " + levels.get(i) + " within threshold");

						if (approximateVolume >= tenDayVolAvg) {
							System.out.println("Heavy Volume. Should sell.");
							sharesToSell = determineShares(symbol);
						} else {
							System.out.println("Light Volume. Should buy.");
							sharesToBuy = determineShares(symbol);
						}
					} else {
						System.out.println("Potential Support at: " + levels.get(i) + " not within threshold");
						System.out.println("Not doing anythng.");
					}
					// Resistance Case
				} else if (curPrice <= levels.get(i)) {

					if (curPrice >= (levels.get(i) - threshold)) {
						System.out.println("Potential Resistance at: " + levels.get(i) + " within threshold");

						if (approximateVolume >= tenDayVolAvg) {
							System.out.println("Heavy volume. Should buy.");
							sharesToBuy = determineShares(symbol);
						} else {
							System.out.println("Light volume. Should sell.");
							sharesToSell = determineShares(symbol);
						}
					} else {
						System.out.println("Potential Resistance at: " + levels.get(i) + " not within threshold");
						System.out.println("Not doing anything.");
					}
				} else {
					System.out.println("Unknown Level: " + levels.get(i));
				}
			}

			// If there is a trade to be made, construct Trade object
			if (sharesToBuy != 0) {
				move = new MarketTrade("Buy", Stock.get(symbol), sharesToBuy);
			} else if (sharesToSell != 0) {
				move = new MarketTrade("Sell", Stock.get(symbol), sharesToSell);
			}
		}

		// Returns null if there is no move for the Stock
		return move;
	}

	/**
	 * Dynamically determines number of shares to buy based on which
	 * group the stock is in as well as how much money is allocated to that group.
	 * 
	 * @param symbol the stock
	 * @return the number of shares
	 */
	private int determineShares(String symbol) {
		
		int shareAmount = 0;
		double allowedMoneyPerStock = allowedSpecMoneyAmount / specStockNumber;
		double curPrice = info.getLastTradePrice(); 
		
		shareAmount = (int) (allowedMoneyPerStock / curPrice);
		
		return shareAmount;
	}

	
}
