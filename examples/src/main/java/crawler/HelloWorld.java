package crawler;

import java.util.logging.Logger;

public class HelloWorld {

	private static Logger logger = 
			Logger.getLogger(HelloWorld.class.getName());
	public HelloWorld() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true){
			logger.info("This is a class");
			logger.warning("THis is a warningj");
			logger.severe("Server");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
