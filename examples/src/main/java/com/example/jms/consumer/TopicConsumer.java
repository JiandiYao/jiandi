package com.example.jms.consumer;

import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TopicConsumer implements MessageListener{

	public static void main(String[] args) throws NamingException, JMSException {
		// TODO Auto-generated method stub
		System.out.println("-------starting JMS Example Topic Consumer-------");
		Context context = TopicConsumer.getInitialContext();
		TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
		Topic topic = (Topic) context.lookup("topic/jms_topic");
		TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
		TopicSession topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		topicSession.createSubscriber(topic).setMessageListener(new TopicConsumer());
		topicConnection.start();
		
		System.out.println("-------stopping JMS Example Topic Consumer--------");
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Incoming messages: "+((TextMessage)message).getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Context getInitialContext() throws NamingException {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		props.setProperty("java.naming.provider.url", "localhost:8023");
		Context context = (Context) new InitialContext(props);
		return context;
	}
}
