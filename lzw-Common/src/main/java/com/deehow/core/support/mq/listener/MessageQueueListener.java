package com.deehow.core.support.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 发送消息
 */
//@Component
public class MessageQueueListener implements MessageListener {
	private final Logger logger = LogManager.getLogger();

	public void onMessage(Message message) {
		try {
			System.out.println(((ObjectMessage) message).getObject());
		} catch (JMSException e) {
			logger.error(e);
		}
	}
}
