package com.example.jms.producer;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jms.consumer.TopicConsumer;

public class TopicProducer {
	public static void main(String[] args) throws NamingException, JMSException {
		// TODO Auto-generated method stub
		System.out.println("-------starting JMS Example Topic Producer-------");
		Context context = TopicConsumer.getInitialContext();
		TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("ConnecitonFactory");
		Topic topic = (Topic) context.lookup("topic/jms_topic");
		TopicConnection topicConnection = (TopicConnection) topicConnectionFactory.createConnection();
		TopicSession topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		topicConnection.start();
		TopicProducer topicProducer = new TopicProducer();
		topicProducer.sendMessage("Message 1 from topic producer", topicSession, topic);
		
		System.out.println("-------finishing JMS Example Topic Producer-------");
	}
	
	public void sendMessage(String text, TopicSession topicSession, Topic topic) throws JMSException{
		TopicPublisher topicPublisher = topicSession.createPublisher(topic);
		TextMessage textMessage = topicSession.createTextMessage(text);
		topicPublisher.publish(textMessage);
		topicPublisher.close();
	}
	
}
