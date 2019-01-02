package com.deehow.core.config;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deehow.core.Constants;
import com.deehow.core.util.DataUtil;

public class SessionManager extends DefaultWebSessionManager {
	private static final Logger log = LoggerFactory.getLogger(SessionManager.class);

	//从客户端获取凭证seesionId;区分TOKEN和 JSESSIONID和C_TOKEN方式携带的凭证
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		if (!isSessionIdCookieEnabled()) {
			log.debug("Session ID cookie is disabled - session id will not be acquired from a request cookie.");
			return null;
		}
		if (!(request instanceof HttpServletRequest)) {
			log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
			return null;
		}
		
		String id = null; 
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String name = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;
		String appValue = httpRequest.getHeader(Constants.TOKEN);
		String cValue = httpRequest.getHeader(Constants.C_TOKEN);
		
		if(appValue != null){
			if(Constants.FIRST_VISIT.equals(appValue)){
				log.debug("当前客户端，第一次访问，创建新的session");
				return null;
			} else {
				id = appValue;
				log.debug("Found '{}' cookie value [{}]", "token", appValue);	
			}
		} else {
			if(cValue != null){
				if(Constants.FIRST_VISIT.equals(cValue)){
					log.debug("当前客户端，第一次访问，创建新的session");
					return null;
				} else {
					id = cValue;
					log.debug("Found '{}' cookie value [{}]", "c-token", cValue);	
				}
			} else {
				javax.servlet.http.Cookie cookie = null;
				javax.servlet.http.Cookie cookies[] = httpRequest.getCookies();
				if (cookies != null) {
					for (javax.servlet.http.Cookie cook : cookies) {
						if (name.equals(cook.getName())) {
							cookie = cook;
						}
					}
				}
				if (cookie != null) {
					id = cookie.getValue();
					log.debug("Found '{}' cookie value [{}]", name, id);
				} else {
					log.trace("No '{}' cookie value", name);
				}
			}
		}
		
		if (id != null) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
					ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
		} else {
			// not in a cookie, or cookie is disabled - try the request URI as a
			// fallback (i.e. due to URL rewriting):

			// try the URI path segment parameters first:
			String paramName = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;
			if (!(request instanceof HttpServletRequest)) {
				return null;
			}
			String uri = httpRequest.getRequestURI();
			if (uri == null) {
				return null;
			}

			int queryStartIndex = uri.indexOf('?');
			if (queryStartIndex >= 0) { // get rid of the query string
				uri = uri.substring(0, queryStartIndex);
			}

			int index = uri.indexOf(';'); // now check for path segment
											// parameters:
			if (index < 0) {
				// no path segment params - return:
				return null;
			}

			// there are path segment params, let's get the last one that may
			// exist:

			final String TOKEN = paramName + "=";

			uri = uri.substring(index + 1); // uri now contains only the path
											// segment params

			// we only care about the last JSESSIONID param:
			index = uri.lastIndexOf(TOKEN);
			if (index < 0) {
				// no segment param:
				return null;
			}

			uri = uri.substring(index + TOKEN.length());

			index = uri.indexOf(';'); // strip off any remaining segment params:
			if (index >= 0) {
				uri = uri.substring(0, index);
			}
			id = uri;

			if (id == null) {
				// not a URI path segment parameter, try the query parameters:
				id = request.getParameter(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
				if (id == null) {
					// try lowercase:
					id = request.getParameter(name.toLowerCase());
				}
			}
			if (id != null) {
				request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
						ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
			}
		}
		if (id != null) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			// automatically mark it valid here. If it is invalid, the
			// onUnknownSession method below will be invoked and we'll remove
			// the attribute at that time.
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
		}
		return id;
	}

	//创建session对象；凭证不存在时触发，创建新的
    protected Session doCreateSession(SessionContext context) {
        Session s = newSessionInstance(context);
        if (log.isTraceEnabled()) {
            log.trace("Creating session for host {}", s.getHost());
        }
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        
		String appValue = request.getHeader(Constants.TOKEN);
		String cValue = request.getHeader(Constants.C_TOKEN);
		
		if(appValue != null){
			s.setAttribute(Constants.AW_TYPE, Constants.LOGIN_MODE_TOKEN);
		} else {
			if(cValue != null){
				s.setAttribute(Constants.AW_TYPE, Constants.LOGIN_MODE_C_TOKEN);
			} else {
				s.setAttribute(Constants.AW_TYPE, Constants.LOGIN_MODE_JSESSIONID);
			}
		}
        create(s);
        return s;
    }
	
	
	
    /**
     * 创建了新的session时才触发，将新的凭证给客户端
     * Stores the Session's ID, usually as a Cookie, to associate with future requests.
     *
     * @param session the session that was just {@link #createSession created}.
     */
    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);

        if (!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response " +
                    "pair. No session ID cookie will be set.");
            return;
        }
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        HttpServletResponse response = WebUtils.getHttpResponse(context);

        if (isSessionIdCookieEnabled()) {
            storeSessionId(session, request, response);
        } else {
            log.debug("Session ID cookie is disabled.  No cookie has been set for new session with id {}", session.getId());
        }

        request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
    }
	
    /**
     * 将凭证写入响应头
     * token方式/cookie方式
     * @param session
     * @param request
     * @param response
     */
    private void storeSessionId(Session session, HttpServletRequest request, HttpServletResponse response) {
    	Serializable currentId = session.getId();
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        }
		Object aw_type = session.getAttribute(Constants.AW_TYPE);
		if(DataUtil.isNotEmpty(aw_type)){
			if(Constants.LOGIN_MODE_TOKEN.equals(aw_type.toString())){
				//token方式写入header
				response.addHeader(Constants.TOKEN, currentId.toString());
			} else if(Constants.LOGIN_MODE_C_TOKEN.equals(aw_type.toString())){
				//c-token方式写入header
				response.addHeader(Constants.C_TOKEN, currentId.toString());
			} else if(Constants.LOGIN_MODE_JSESSIONID.equals(aw_type.toString())){
				//cookie方式写入header
				Cookie template = getSessionIdCookie();
		        Cookie cookie = new SimpleCookie(template);
		        String idString = currentId.toString();
		        cookie.setValue(idString);
		        cookie.saveTo(request, response);
		        log.trace("Set session ID cookie for session with id {}", idString);
			} else {
				throw new IllegalArgumentException();
			}
		}
    }

}
