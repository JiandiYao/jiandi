package com.citi.jms.stock;

import java.util.List;

public interface IObserver {

	public void update();
	public void setStock(List<String> stock);
}
