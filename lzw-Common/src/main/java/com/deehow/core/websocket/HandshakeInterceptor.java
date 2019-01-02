package com.deehow.core.websocket;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.deehow.core.Constants;

/**
 * 创建握手（handshake）接口
 * 拦截器
 * @version argType
 */

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{
    
	private Logger logger=Logger.getLogger(HandshakeInterceptor.class);
	
	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		logger.debug("创建握手后...");
		super.afterHandshake(request, response, wsHandler, ex);
	}
 
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		logger.debug("握手完成前...");
		HttpSession session = getSession(request);
		if (session != null) {
			if (isCopyHttpSessionId()) {
				attributes.put(HTTP_SESSION_ID_ATTR_NAME, session.getId());
			}
			Enumeration<String> names = session.getAttributeNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				if (isCopyAllAttributes() || getAttributeNames().contains(name)) {
					attributes.put(name, session.getAttribute(name));
				}
			}
			//通过request获取请求的参数，保存到session
			String sid = ((ServletServerHttpRequest) request).getServletRequest().getParameter("sid");
			attributes.put(Constants.WSTYPE.WS_TYPE, sid);
		}
		return true;
	}
	
	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(isCreateSession());
		}
		return null;
	}
}