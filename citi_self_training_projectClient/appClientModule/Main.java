import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.citi.bean.display.ISubscribedStock;
import com.citi.jms.stock.LiveStockPublisher;

import yahoofinance.Stock;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		LiveStockPublisher publisher = new LiveStockPublisher(); 
		try {
			InitialContext context = new InitialContext();
			ISubscribedStock subStock = (ISubscribedStock) context.lookup("com.citi.bean.display.ISubscribedStock");
			subStock.register(publisher);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Map<String, Stock> map;
		while(true){
			
			publisher.update();
			map = publisher.getStockFromYahoo();
			publisher.pub(map);
			Thread.sleep(10000);
		}
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}