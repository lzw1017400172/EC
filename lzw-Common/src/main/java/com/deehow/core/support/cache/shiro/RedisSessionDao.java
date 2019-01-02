package com.deehow.core.support.cache.shiro;

import com.deehow.core.Constants;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.DataUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import com.deehow.core.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;

/**
 * Created by zhaozhiliang on 17/8/13.
 */
public class RedisSessionDao extends AbstractSessionDAO {

	protected final org.apache.logging.log4j.Logger log = LogManager.getLogger(this.getClass());

	private Integer JSESSIONID_EXPIRE = PropertiesUtil.getInt("redis.jsessionid.session.expiration");
	private Integer TOKEN_EXPIRE = PropertiesUtil.getInt("redis.token.session.expiration");
	private Integer NOTAUTHENTICATED_EXPIRE = PropertiesUtil.getInt("redis.notAuthenticated.session.expiration");
	
	/**
	 * 更新session，每次请求--无论是否登陆都会执行的操作
	 * 重置缓存的过期时间
	 */
	@Override
	public void update(Session session) throws UnknownSessionException {
		if(isAuthenticated(session)){
			//已经登陆的session
			Object aw_type = session.getAttribute(Constants.AW_TYPE);
			if(DataUtil.isNotEmpty(aw_type)){
				if(Constants.LOGIN_MODE_TOKEN.equals(aw_type.toString()) || Constants.LOGIN_MODE_C_TOKEN.equals(aw_type.toString())){
					//token或c-token方式登陆的
					try {
						CacheUtil.getCache().set(session.getId().toString(), CacheUtil.getRedisHelper().serialize(session),TOKEN_EXPIRE);
						log.debug("已经登陆，TOKEN 形式 更新 session,id=[{}]", session.getId().toString());
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				} else if(Constants.LOGIN_MODE_JSESSIONID.equals(aw_type.toString())){
					//JSESSIONID方式登陆的
					try {
						CacheUtil.getCache().set(session.getId().toString(), CacheUtil.getRedisHelper().serialize(session),JSESSIONID_EXPIRE);
						log.debug("已经登陆，JSESSIONID 形式 更新 session,id=[{}]", session.getId().toString());
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		} else {
			//未登录的session
			try {
				CacheUtil.getCache().set(session.getId().toString(), CacheUtil.getRedisHelper().serialize(session),NOTAUTHENTICATED_EXPIRE);
				log.debug(" 未登录 更新 session,id=[{}]", session.getId().toString());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void delete(Session session) {
		log.debug("删除seesion,id=[{}]", session.getId().toString());
		try {
			CacheUtil.getCache().del(session.getId().toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		log.debug("获取存活的session");
		return Collections.emptySet();
	}

	/**
	 * 若 没有从客户端获取到seesionId或者没有从缓存中获取到session都会触发这个创建一个新的session
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		log.debug("创建seesion,id=[{}]", session.getId().toString());
		return sessionId;
	}

	/**
	 * 从缓存中获取session
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId != null){
			Session session = null;
			try {
				Object sessions = CacheUtil.getCache().getFinal(sessionId.toString());
				if (sessions != null) {
					session = (Session) CacheUtil.getRedisHelper().deserialize(sessions.toString());
				} else {
					log.debug("获取seesion 为空!,id=[{}]", sessionId.toString());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return session;
		}
		return null;
	}
	
	/**
	 * 判断session是否已经登陆过
	 * @param session
	 * @return
	 */
	public static boolean isAuthenticated(Session session) {
		boolean status = false;
        try{
            Object obj = session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);//此值为true表示已经登陆
            if(obj != null){
                status = (Boolean) obj;
            }
        } catch (Exception e){
            e.printStackTrace();
        } 
        return status;
	}
}
