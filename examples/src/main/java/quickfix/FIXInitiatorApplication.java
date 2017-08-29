package org.quickfix;

import java.util.Date;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.UnsupportedMessageType;
import quickfix.field.ClOrdID;
import quickfix.field.HandlInst;
import quickfix.field.OrdType;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TransactTime;
import quickfix.fix50sp2.NewOrderSingle;

public class FIXInitiatorApplication implements Application {
	@Override
	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
	}

	@Override
	public void fromApp(Message message, SessionID arg1) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
	   System.out.println("Received reply from executor"); 
	}

	@Override
	public void onCreate(SessionID arg0) {
	  // TODO Auto-generated method stub
	}

	@Override
	public void onLogon(SessionID sessionId) {
	   System.out.println("Initiator LOGGED ON.......");
	   NewOrderSingle order = new NewOrderSingle(new ClOrdID("MISYS1001"),
	   new   Side(Side.BUY), new TransactTime(new Date()), new OrdType(OrdType.LIMIT));

	   try {
		Session.sendToTarget(order, sessionId);
	} catch (SessionNotFound e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	@Override
	public void onLogout(SessionID arg0) {
	   System.out.println("Session logged out");
	}

	@Override
	public void toAdmin(Message arg0, SessionID arg1) {
	   // TODO Auto-generated method stub
	}

	@Override
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
	   // TODO Auto-generated method stub
	   }
	}
