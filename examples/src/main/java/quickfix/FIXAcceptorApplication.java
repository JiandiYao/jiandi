package org.quickfix;

import quickfix.Acceptor;
import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.MessageCracker;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.field.AvgPx;
import quickfix.field.CumQty;
import quickfix.field.ExecTransType;
import quickfix.field.ExecType;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.OrdStatus;
import quickfix.field.OrderQty;
import quickfix.field.Price;
import quickfix.fix50sp2.ExecutionReport;
import quickfix.fix50sp2.NewOrderSingle;

public class FIXAcceptorApplication extends MessageCracker implements Application {

    @Override
    public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,  IncorrectDataFormat, IncorrectTagValue, RejectLogon {
    }

    @Override
    public void fromApp(Message arg0, SessionID arg1) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("Acceptor received new message..  ");
        crack(arg0, arg1);
    }

    @Override
    public void onCreate(SessionID arg0) {
    }

    @Override
    public void onLogon(SessionID arg0) {
        System.out.println("Acceptor logged on.........");
    }

    @Override
    public void onLogout(SessionID arg0) {
    }

    @Override
    public void toAdmin(Message arg0, SessionID arg1) {
        // TODO

    }

    @Override
    public void toApp(Message arg0, SessionID arg1) throws DoNotSend {

    }

    public void onMessage(NewOrderSingle order, SessionID sessionID) throws FieldNotFound,  UnsupportedMessageType,  IncorrectTagValue {
        OrderQty orderQty = new OrderQty(10.0);
        Price price = new Price(10.0);
        ExecutionReport executionReport =
                                          new ExecutionReport(getOrderIDCounter(),
                                                              getExecutionIDCounter(),
                                                              new ExecTransType(ExecTransType.NEW),
                                                              new ExecType(ExecType.FILL),
                                                              new OrdStatus(OrdStatus.FILLED),
                                                              order.getSymbol(), order.getSide(),
                                                              new LeavesQty(0),
                                                              new CumQty(orderQty.getValue()),
                                                              new AvgPx(price.getValue()));

        executionReport.set(order.getClOrdID());
        executionReport.set(orderQty);
        executionReport.set(new LastShares(orderQty.getValue()));
        executionReport.set
        executionReport.set(new LastPx(price.getValue()));   

        try {
            Session session = Session.lookupSession(sessionID);
            Session.sendToTarget(executionReport, sessionID); 
            System.out.println("NewOrderSingle Execution  Completed-----");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error during order execution" + ex.getMessage());
        }
    }
}