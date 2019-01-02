package com.deehow.core.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.deehow.core.Constants;

public class WebSocketHander implements WebSocketHandler {

    private static final Logger logger = Logger.getLogger(WebSocketHander.class);
    
    //redis发布订阅，解决多个 WebSocket通道问题
    
    /**	未登录的浏览器session，主要用来解决 二维码长连接 */
    private static final ConcurrentHashMap<String,WebSocketSession> anonUsers = new ConcurrentHashMap<String,WebSocketSession>();
    
    /**	已经登录的浏览器session，主要用来存储用户，针对用户推送消息 */
    private static final ConcurrentHashMap<Long,WebSocketSession> authcUsers = new ConcurrentHashMap<Long,WebSocketSession>();

    
    // 初次链接成功执行
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
    	logger.debug("链接成功......");
        Map<String,Object> map = webSocketSession.getAttributes();
        Object ob = map.get(Constants.WSTYPE.WS_TYPE);
        String type = ob == null ? "" : ob.toString();
        switch (type) {
			case Constants.WSTYPE.WS_TYPE_QRCODE:
				String key = map.get(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME).toString();
				anonUsers.put(key,webSocketSession);
				break;
				
			default:
				break;
		}
    }

    // 接受消息处理消息
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
            throws Exception {

    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
    	if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        logger.debug("链接出错，关闭链接......");
        Map<String,Object> map = webSocketSession.getAttributes();
        Object ob = map.get(Constants.WSTYPE.WS_TYPE);
        String type = ob == null ? "" : ob.toString();
        switch (type) {
			case Constants.WSTYPE.WS_TYPE_QRCODE:
				String key = map.get(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME).toString();
				anonUsers.remove(key);
				break;
				
			default:
				break;
		}
    }

    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
    	logger.debug("链接关闭......" + closeStatus.toString());
        Map<String,Object> map = webSocketSession.getAttributes();
        Object ob = map.get(Constants.WSTYPE.WS_TYPE);
        String type = ob == null ? "" : ob.toString();
        switch (type) {
			case Constants.WSTYPE.WS_TYPE_QRCODE:
				String key = map.get(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME).toString();
				anonUsers.remove(key);
				break;
				
			default:
				break;
		}
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    
    /**
     * 给所有在线用户发送消息
     */
    public void sendMessageToUsers(TextMessage message) {
    	for(Map.Entry<Long, WebSocketSession> entry:authcUsers.entrySet()){
    		WebSocketSession user = entry.getValue();
    		if(null != user){
	            try {
	                if (user.isOpen()) {
	                    user.sendMessage(message);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
    		}
        }
    }

    /**
     * 给某个用户发送消息
     */
    public void sendMessageToAuthcUser(Long userId, TextMessage message) {
    	WebSocketSession user = authcUsers.get(userId);
    	if(null != user){
	        try {
	            if (user.isOpen()) {
	                user.sendMessage(message);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    /**
     * 获取二维码，给某个；浏览器发送消息
     */
    public static void sendMessageToAnonUser(String sessionId, TextMessage message) {
		WebSocketSession user = anonUsers.get(sessionId);
		if(null != user){
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
