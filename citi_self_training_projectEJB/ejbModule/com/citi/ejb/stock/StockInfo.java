package com.citi.ejb.stock;

public class StockInfo {

	private String symbol;
	private double dayHigh;
	private double dayLow;
	private double lastTradePrice;
	private double averageDailyVolume;
	
	public double getAverageDailyVolume() {
		return averageDailyVolume;
	}

	public void setAverageDailyVolume(double averageDailyVolume) {
		this.averageDailyVolume = averageDailyVolume;
	}

	public double getLastTradePrice() {
		return lastTradePrice;
	}

	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}

	public double getDayHigh() {
		return dayHigh;
	}

	public void setDayHigh(double dayHigh) {
		this.dayHigh = dayHigh;
	}

	public double getDayLow() {
		return dayLow;
	}

	public void setDayLow(double dayLow) {
		this.dayLow = dayLow;
	}

	public StockInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
