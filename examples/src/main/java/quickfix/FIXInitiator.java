package org.quickfix;

import java.io.IOException;
import java.io.InputStream;

import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

public class FIXInitiator {

	private SocketInitiator socketInitiator;

	public static void main(String[] args) throws ConfigError, InterruptedException, IOException {

	  InputStream inputStream = FIXAcceptorExecutor.class.getResourceAsStream("initiator.cfg"); 
	  startInitiator(inputStream);
	}

	private static void startInitiator(InputStream inputStream) throws ConfigError,
	InterruptedException, IOException {

	  FIXInitiator fixIniator = new FIXInitiator();
	  SessionSettings sessionSettings = new SessionSettings(inputStream);
	  FIXInitiatorApplication application = new FIXInitiatorApplication();
	  FileStoreFactory fileStoreFactory = new FileStoreFactory(sessionSettings);
	  LogFactory logFactory = new FileLogFactory(sessionSettings);
	  MessageFactory messageFactory = new DefaultMessageFactory();
	  fixIniator.socketInitiator = new SocketInitiator(application, fileStoreFactory,
	                                     sessionSettings, logFactory, messageFactory);
	  fixIniator.socketInitiator.start();
	  System.out.println("press to quit");
	  System.in.read();
	  fixIniator.socketInitiator.stop();
	}
}
