package com.deehow.core.config;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionContext;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.TokenUtil;

public class AppSubject extends DelegatingSubject {
	private static final Logger log = LoggerFactory.getLogger(AppSubject.class);

	public AppSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
			boolean sessionCreationEnabled, SecurityManager securityManager) {
		super(principals, authenticated, host, session, sessionCreationEnabled, securityManager);
		// TODO Auto-generated constructor stub
	}

	public AppSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
			SecurityManager securityManager) {
		super(principals, authenticated, host, session, securityManager);
		// TODO Auto-generated constructor stub
	}

	public AppSubject(SecurityManager securityManager) {
		super(securityManager);
		// TODO Auto-generated constructor stub
	}
	public void appLoginss(AuthenticationToken token, String tokenId, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String host = SecurityUtils.getSubject().getSession().getHost();
		log.info("APP SecurityUtils.getSubject().getSession().getHost()"+SecurityUtils.getSubject().getSession().getHost());
//	 	Session session = getSession(false);
//        if (session != null) {
//            session.removeAttribute(DelegatingSubject.class.getName() + ".RUN_AS_PRINCIPALS_SESSION_KEY");
//            log.info("app 验证 前 session.getId()"+session.getId());
////            session.stop();
//        }
		
        Subject subject = securityManager.login(this, token);
//		 Subject subject =null;
        PrincipalCollection principals;


        if (subject instanceof DelegatingSubject) {
            DelegatingSubject delegating = (DelegatingSubject) subject;
            //we have to do this in case there are assumed identities - we don't want to lose the 'real' principals:
            principals = delegating.getPrincipals();
//            host = delegating.getHost();
            log.info("APP host"+host);
        } else {
            principals = subject.getPrincipals();
        }

        if (principals == null || principals.isEmpty()) {
            String msg = "Principals returned from securityManager.login( token ) returned a null or " +
                    "empty value.  This value must be non null and populated with one or more elements.";
            throw new IllegalStateException(msg);
        }
        this.principals = principals;
        this.authenticated = true;
        if (token instanceof HostAuthenticationToken) {
//            host = ((HostAuthenticationToken) token).getHost();
        }
        if (host != null) {
            this.host = host;
        }
        log.info("token"+token);
        log.info("tokenId"+tokenId);
        
//        log.info("app 创建  前   SecurityUtils.getSubject().getSession().getId()"+SecurityUtils.getSubject().getSession().getId());
//        Subject currentUser = SecurityUtils.getSubject();
//        if (null != currentUser) {
//            Session sessionBefore = currentUser.getSession();
//            if (null != sessionBefore) {
//            	sessionBefore.stop();
//            }
//        }
        log.info("app 验证 中   SecurityUtils.getSubject().getSession().getId()"+SecurityUtils.getSubject().getSession().getId());
		
        SessionContext sessionContext = new DefaultWebSessionContext();
        log.info("app 验证 中   host"+host);
        sessionContext.setHost(host);
        ExecutorServiceSessionValidationScheduler scheduler;
                if (log.isDebugEnabled()) {
                    log.debug("APP No sessionValidationScheduler set.  Attempting to create default instance.");
                }
                scheduler = new ExecutorServiceSessionValidationScheduler(new SessionManager());
                scheduler.setInterval(30*60*60*24*1000);
                if (log.isTraceEnabled()) {
                    log.trace("APP Created default SessionValidationScheduler instance of type [" + scheduler.getClass().getName() + "].");
                }
//                new TokenUtil().setSessionValidationScheduler(scheduler);
                
            if (log.isInfoEnabled()) {
                log.info("APP Enabling session validation scheduler...");
            }
            scheduler.enableSessionValidation();
            Session s = new SimpleSessionFactory().createSession(sessionContext);
            if (log.isTraceEnabled()) {
                log.trace("APP Creating session for host {}", s.getHost());
            }
            ((SimpleSession) s).setId(tokenId);
            log.info("APP 创建seesion,id=[{}]",s.getId().toString());
            try {
              CacheUtil.getCache().setForEver(s.getId().toString(),  CacheUtil.getRedisHelper().serialize(s));
            } catch (Exception e) {
              log.error(e.getMessage(), e);
            }
        
        s.setTimeout(30*60*60*24*1000);
        log.info("APP 更新seesion,id=[{}]",s.getId().toString());
        log.info("app 创建  后   SecurityUtils.getSubject().getSession().getId()"+SecurityUtils.getSubject().getSession().getId());
		
//        if (!WebUtils.isHttp(sessionContext)) {
//            log.debug("APP SessionContext argument is not HTTP compatible or does not have an HTTP request/response " +
//                    "pair. No session ID cookie will be set.");
//            return;
//
//        }
//        SessionManager manager = getSessionManager();
//        if(manager != null) {
//          SessionKey key = new DefaultSessionKey(sessionId);
//          try {
//            return manager.getSession(key);
//          } catch(SessionException e) {
//            // Means that the session does not exist or has expired.
//          }
//        }
        
//        HttpServletRequest request = WebUtils.getHttpRequest(sessionContext);
//        HttpServletResponse response = WebUtils.getHttpResponse(sessionContext);

            Serializable sessionId = s.getId();
            
            Cookie template = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
            template.setHttpOnly(true); //more secure, protects against XSS attacks
            Cookie cookie = new SimpleCookie(template);
            String idString = sessionId.toString();
            cookie.setValue(idString);
            cookie.saveTo(request, response);
            log.debug("Set session ID cookie for session with id {}", idString);
        request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
        
        //Don't expose the EIS-tier Session object to the client-tier:
        new DelegatingSession(new DefaultSessionManager(), new DefaultSessionKey(s.getId()));
        
        log.info("app 验证  后   SecurityUtils.getSubject().getSession().getId()"+SecurityUtils.getSubject().getSession().getId());
		
        this.session = decorate(s);
        log.info("app 完成那个  后   SecurityUtils.getSubject().getSession().getId()"+SecurityUtils.getSubject().getSession().getId());
		
}

	@Override
	public void login(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		super.login(token);
		Subject currentUser = SecurityUtils.getSubject();
      if (null != currentUser) {
          Session sessionBefore = currentUser.getSession();
          if (null != sessionBefore) {
          	sessionBefore.setTimeout(30*60*60*24*1000);
          	log.info("app sessionBefore.getTimeout()"+sessionBefore.getTimeout());
          	log.info("app sessionBefore.getTimeout()"+sessionBefore.getId());
          }
      }
	}
	

}
