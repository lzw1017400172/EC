package com.deehow.core.listener;

import java.io.Serializable;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.TextMessage;

import com.deehow.core.util.CacheUtil;
import com.deehow.core.websocket.WebSocketHander;

/**
 * redis发布订阅的监听器类 
 * 这个类用来解决 二维码 --扫码登录的长链接。多个通道问题。
 * 
 * 若需要实现其他的业务，对其业务单独创建topic频道，创建监听器，不和这个相关联
 *
 * @author LZW
 *
 */

public class WsQrRedisMessageListener implements MessageListener {
	
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {  
		this.redisTemplate = redisTemplate;  
    } 
	
    @Override
    public void onMessage(Message message, byte[] pattern) {
    	
        Object sessionId =  CacheUtil.getRedisHelper().deserialize(message.toString());
        WebSocketHander.sendMessageToAnonUser(sessionId.toString(),new TextMessage("SUCCESS"));
        
    	//byte[] body = message.getBody();
    	//String msgContent = redisTemplate.getStringSerializer().deserialize(body);  
    	//byte[] channel = message.getChannel();
        //String topic = (String) redisTemplate.getStringSerializer().deserialize(channel);
    }
}
