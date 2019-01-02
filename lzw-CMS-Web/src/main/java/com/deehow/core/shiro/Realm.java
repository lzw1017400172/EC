package com.deehow.core.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deehow.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.deehow.core.Constants;
import com.deehow.core.base.BaseProvider;
import com.deehow.core.base.Parameter;
import com.deehow.core.exception.DataParseException;
import com.deehow.core.support.login.UsernamePasswordTokenExt;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.DataUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.core.util.SecurityUtil;
import com.deehow.core.util.WebUtil;
import com.deehow.provider.ICmsProvider;
import com.deehow.util.ModelMenuUtils;

/**
 * 权限检查类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:44:45
 */
public class Realm extends AuthorizingRealm {
	private final Logger logger = LogManager.getLogger();
	@Autowired
	@Qualifier("cmsProvider")
	protected BaseProvider provider;
//	@Autowired
//	private RedisOperationsSessionRepository sessionRepository;

	// 权限
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Long userId = WebUtil.getCurrentUser();
		Long tenantId = WebUtil.getCurrentTenant();
		int userType = WebUtil.getCurrentUserType();

		List<Long> modelIds = ModelMenuUtils.getTenantModelUndueCache(tenantId,(ICmsProvider)provider);
		if (Constants.ADMINISTRATOR == userType) {
			/**	1 Permissions权限 */
			if(modelIds != null && modelIds.size()>0){
				Parameter menuParam = new Parameter("sysModelMenuService", "queryListByModelIds").setList(modelIds);
				List<SysModelMenu> list = (List<SysModelMenu>) provider.execute(menuParam).getList();
				
				Map<String, Object> dicParam = InstanceUtil.newHashMap();
	            dicParam.put("type", "CRUD");
	            Parameter dicParameter = new Parameter("sysDicService", "queryList").setMap(dicParam);
	            List<SysDic> sysDics = (List<SysDic>) provider.execute(dicParameter).getList();
		         
				for (int i = 0; i < list.size(); i++) {
					if (StringUtils.isNotBlank(list.get(i).getPermission())) {
						// 添加基于Permission的权限信息
					    for (SysDic sysDic : sysDics) {
					    	info.addStringPermission(list.get(i).getPermission()+"."+sysDic.getCode());
			            }
						logger.debug("管理员：" + list.get(i).getPermission());
					}
				}
			}
			/**	2 rule权限 */
			Parameter roleAllParam = new Parameter("sysRoleService", "queryList").setMap(InstanceUtil.newHashMap("tenantId",tenantId));
			List<SysRole> roleAllList = (List<SysRole>) provider.execute(roleAllParam).getList();
			for(SysRole role:roleAllList){
				info.addRole(role.getRoleName());
			}

		}else{
			/**	1 Permissions权限 */
			Parameter parameterOther = new Parameter("sysAuthorizeService", "newQueryUserPermissions").setId(userId);
			List<ModelPermissionDTO> list = (List<ModelPermissionDTO>) provider.execute(parameterOther).getList();
			for (ModelPermissionDTO dto : list) {
				if (StringUtils.isNotBlank(dto.getPermission()) && modelIds.contains(dto.getModelId())) {
					// 添加基于Permission的权限信息
					info.addStringPermission(dto.getPermission());
					logger.debug("普通员："+ dto.getPermission());
				}
			}

			/**	2 rule权限 */
			Parameter roleByMeParam = new Parameter("sysAuthorizeService", "selectRolesByUserId").setId(userId);
			List<SysRole> roleByMeList = (List<SysRole>) provider.execute(roleByMeParam).getList();
			for(SysRole role:roleByMeList){
				if(role != null) {
					info.addRole(role.getRoleName());
				}
			}
		}
		// 添加用户权限
		info.addStringPermission("user");
		logger.debug("权限管理完成。");
		return info;
	}

	// 登录验证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordTokenExt token = (UsernamePasswordTokenExt) authcToken;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enable", 1);
		params.put("account", token.getUsername());
		if(token.getTenantId() != null){
			params.put("tenantId", token.getTenantId());
		}
		
		Parameter parameter = new Parameter("sysUserService", "queryList").setMap(params);
		logger.info("{} execute sysUserService.queryList start...", parameter.getNo());
		List<?> list = provider.execute(parameter).getList();
		logger.info("{} execute sysUserService.queryList end.", parameter.getNo());
		if (list.size() == 1) {
			SysUser user = (SysUser) list.get(0);
			if(user.getLogin() == 2){
				logger.warn("此账号已经被禁止登陆。", token.getUsername());
				return null;
			}
			String password = null;
			if(Constants.LOGINTYPE_PASSWORD.equals(token.getLoginType())){//账号密码登录
				StringBuilder sb = new StringBuilder(100);
				for (int i = 0; i < token.getPassword().length; i++) {
					sb.append(token.getPassword()[i]);
				}
				password = sb.toString();
				if (!SecurityUtil.validatePassword(password, user.getPassword())) {
					logger.warn("USER [{}] PASSWORD IS WRONG: {}", token.getUsername(), password);
					return null;
				}
			} else {//无密码登录
				//随便一个密码即可
				password = Constants.LOGINTYPE_NOPASSWD;
				token.setPassword(password.toCharArray());
			}
			
			saveData(user);
			AuthenticationInfo authcInfo = 
					new SimpleAuthenticationInfo(
							new StringBuilder(user.getTenantId().toString()).append(":").append(user.getId()).toString(), 
								password,user.getUserName());
			//第一个参数，会作为权限缓存的键  Constants.CACHE_NAMESPACE + "shiro_redis_cache:" + 第一个参数
			//第二个参数 是真实密码
			//无密码登录，只需要将AuthenticationInfo和AuthenticationToken设置两个相同的密码即可。
			return authcInfo;
		} else {
			logger.warn("No user: {}", token.getUsername());
			return null;
		}
	}
	
	/** 保存数据	*/
	private void saveData(SysUser user) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute(Constants.CURRENT_USER, user.getId());
		session.setAttribute(Constants.CURRENT_TENANT, user.getTenantId());
		session.setAttribute(Constants.CURRENT_USER_TYPE, user.getUserType());
		saveSession(session,user);
	}
	
	/** 保存session */
	/**
	 * @param session	登陆人的session
	 * @param user		登陆人sysUser
	 */
	public void saveSession(Session session,SysUser user) {
		// 踢出用户
		Object aw_type = session.getAttribute(Constants.AW_TYPE);
		int type = 1;
		if(DataUtil.isNotEmpty(aw_type)){
			switch (aw_type.toString()) {
				case Constants.LOGIN_MODE_JSESSIONID:
					type = 1;
					break;
				case Constants.LOGIN_MODE_TOKEN:
					type = 2;
					break;
				case Constants.LOGIN_MODE_C_TOKEN:
					type = 3;
					break;
				default:
					break;
			}
		} else {
			throw new DataParseException();
		}
		
		SysSession record = new SysSession();
		record.setAccount(user.getId());
		record.setType(type);
		Parameter parameter = new Parameter("sysSessionService", "querySessionIdByAccount").setModel(record);
		logger.info("{} execute querySessionIdByAccount start...", parameter.getNo());
		List<?> sessionIds = provider.execute(parameter).getList();
		logger.info("{} execute querySessionIdByAccount end.", parameter.getNo());
		
		String currentSessionId= session.getId().toString();
		if (sessionIds.size() > 0) {
			parameter = new Parameter("sysSessionService", "deleteBySessionIdAndType").setModel(record);	
			logger.info("{} execute deleteBySessionIdAndType start...", parameter.getNo());
			provider.execute(parameter);//删除数据库中已经登陆的用户
			logger.info("{} execute deleteBySessionIdAndType end.", parameter.getNo());
			try {
				for (Object sessionId : sessionIds) {
					if (!currentSessionId.equals(sessionId)) {//清除缓存中已经登陆的用户
						CacheUtil.getCache().del(sessionId.toString());
						//sessionRepository.delete((String) sessionId);
						//sessionRepository.cleanupExpiredSessions();
					}
				}
			} catch (Exception e) {
				logger.error(Constants.Exception_Head, e);
			}
		}
		// 保存用户
		record.setTenantId(user.getTenantId());
		record.setSessionId(currentSessionId);
		String host = (String) session.getAttribute("HOST");
		record.setIp(StringUtils.isBlank(host) ? session.getHost() : host);
		record.setStartTime(session.getStartTimestamp());
		parameter = new Parameter("sysSessionService", "update").setModel(record);
		logger.info("{} execute sysSessionService.update start...", parameter.getNo());
		provider.execute(parameter);
		logger.info("{} execute sysSessionService.update end.", parameter.getNo());
	}
	
}
