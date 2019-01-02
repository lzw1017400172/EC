package com.deehow.core.support.mq;

import java.io.IOException;
import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import com.alibaba.dubbo.common.json.JSON;
import com.deehow.core.support.jpush.model.CommMessageCollect;
/**
 * 队列消息发送类
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
//@Component
public class QueueSender {
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate topic;

	/**
	 * 发送一条消息到指定的队列（目标）
	 * 
	 * @param queueName 队列名称
	 * @param message 消息内容
	 */
	public void send(String queueName, final Serializable message) {
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject(message);
				return objectMessage;
			}
		});
	}
	
	/**
	 * 发送一条消息到指定的订阅者（目标）
	 * 
	 * @param topicName 订阅者名称
	 * @param message 消息内容
	 */
	public void sendMessage(String queueName, CommMessageCollect message) {
		topic.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = null;
				try {
					objectMessage = session.createObjectMessage(JSON.json(message));
					objectMessage.setObject(message);
					objectMessage.setStringProperty("title", message.getMesTitle());
					objectMessage.setStringProperty("desc", message.getMesDesc());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return objectMessage;
			}
		});
	}
}
