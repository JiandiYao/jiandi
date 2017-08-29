package org.quickfix;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FieldConvertError;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RuntimeError;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider;

public class FIXAcceptorExecutor {
    private final SocketAcceptor acceptor;
    private final static Map<SocketAddress, SessionID>  dynamicSessionMappings =   new HashMap<SocketAddress, SessionID>();

    public FIXAcceptorExecutor(SessionSettings settings) throws ConfigError,  FieldConvertError {
        Application application = (Application) new FIXAcceptorApplication();
        MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();

        acceptor =  new SocketAcceptor(application, messageStoreFactory, settings, logFactory, messageFactory);

        configureDynamicSessions(settings, application, messageStoreFactory, logFactory,
messageFactory);

    }

    private void configureDynamicSessions(SessionSettings settings, Application application,
  MessageStoreFactory messageStoreFactory,  LogFactory logFactory, MessageFactory messageFactory) throws ConfigError, FieldConvertError {

        Iterator sectionIterator = settings.sectionIterator();

        while (sectionIterator.hasNext()) {
            SessionID sessionID = (SessionID) sectionIterator.next();       
            if (isSessionTemplate(settings, sessionID)) {
                InetSocketAddress address = getAcceptorSocketAddress(settings, sessionID);
                getMappings(address).add(new TemplateMapping(sessionID, sessionID));
            }
        }

        for (Map.Entry> entry : dynamicSessionMappings.entrySet()) {
            acceptor.setSessionProvider((SocketAddress) entry.getKey(),
                                        new DynamicAcceptorSessionProvider(settings,
                                                                           (SessionID) entry.getValue(),
                                                                           application,
                                                                           messageStoreFactory,
                                                                           logFactory,
                                                                           messageFactory));
        }
    }

    private List getMappings(InetSocketAddress address) {
        List mappings = (List) dynamicSessionMappings.get(address);
        if (mappings == null) {
            mappings = new ArrayList();
            dynamicSessionMappings.put(address, (SessionID) mappings);
        }
        return mappings;
    }

    private InetSocketAddress getAcceptorSocketAddress(SessionSettings settings, SessionID sessionID)    throws ConfigError,  FieldConvertError {
        String acceptorHost = "0.0.0.0";
        if (settings.isSetting(sessionID, SETTING_SOCKET_ACCEPT_ADDRESS)) {
            acceptorHost = settings.getString(sessionID, SETTING_SOCKET_ACCEPT_ADDRESS);           
        }
       
        int acceptorPort = (int) settings.getLong(sessionID, SETTING_SOCKET_ACCEPT_PORT);

        InetSocketAddress address = new InetSocketAddress(acceptorHost, acceptorPort);
        return address;
    }

    private boolean isSessionTemplate(SessionSettings settings, SessionID sessionID)
                                                                                    throws ConfigError,
                                                                                    FieldConvertError {
        return settings.isSetting(sessionID, SETTING_ACCEPTOR_TEMPLATE) &&
               settings.getBool(sessionID, SETTING_ACCEPTOR_TEMPLATE);
    }

    private void start() throws RuntimeError, ConfigError {
        acceptor.start();
    }

    private void stop() {
        acceptor.stop();
    }

    public static void main(String args[]) throws Exception {
        InputStream inputStream = null;
        if (args.length == 0) {
            inputStream = FIXAcceptorExecutor.class.getResourceAsStream("executor.cfg");
        } else if (args.length == 1) {
            inputStream = new FileInputStream(args[0]);
        }

        SessionSettings settings = new SessionSettings(inputStream);
        FIXAcceptorExecutor executor = new FIXAcceptorExecutor(settings);

        executor.start();
        System.out.println("press to quit");
        System.in.read();
        executor.stop();
    }
}
