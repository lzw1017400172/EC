package com.deehow.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deehow.core.util.PropertiesUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deehow.core.Constants;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.config.Resources;
import com.deehow.core.exception.LoginException;
import com.deehow.core.shiro.Realm;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.support.cache.shiro.RedisSessionDao;
import com.deehow.core.support.login.LoginHelper;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.model.Login;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户登录
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:11:21
 */
@RestController
@Api(value = "APP登录接口", description = "APP登录接口")
public class AppLoginController extends AbstractController<ICmsProvider> {
	
	private static final Logger log = LoggerFactory.getLogger(AppLoginController.class);
    
	public String getService() {
        return "sysUserService";
    }

	@Autowired
	@Qualifier("sessionDAO")
	private RedisSessionDao redisSessionDao;
	
	@Autowired
	@Qualifier("realm")
	private Realm realm;
	
    // 登录
    @ApiOperation(value = "app用户登录")
    @PostMapping("/app/login")
    public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody Login user, ModelMap modelMap,
                        HttpServletRequest request,HttpServletResponse response) {
        Assert.notNull(user.getAccount(), "ACCOUNT");
        Assert.notNull(user.getPassword(), "PASSWORD");
        Assert.notNull(user.getTenantId(), "TENANTID");

    	//判断是否已经登陆
		if(SecurityUtils.getSubject().isAuthenticated()){
			modelMap.put("msg", "已经登陆，请不要重复登陆!!");
			return setModelMap(modelMap, HttpCode.CONFLICT);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enable", 1);
		params.put("account", user.getAccount());
		params.put("tenantId", user.getTenantId());
		Parameter parameterU = new Parameter("sysUserService", "queryList").setMap(params);
		logger.info("{} execute sysUserService.queryList start...", parameterU.getNo());
		List<SysUser> uList = (List<SysUser>) provider.execute(parameterU).getList();
		logger.info("{} execute sysUserService.queryList end.", parameterU.getNo());
		if(uList.size() == 0){
			modelMap.put("msg", "账号不存在!!");
			return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
		}
		if(uList.get(0).getLogin() == 2){
			modelMap.put("msg", "账号被禁止登录!!");
			return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
		}
	 	if (LoginHelper.login(user.getAccount(), user.getPassword(),user.getTenantId())) {
 			request.setAttribute("msg", "[" + user.getAccount() + "]登录成功.");
 			
			SysUser sysUser = uList.get(0);
			
            modelMap.put("userId", sysUser.getId());
            modelMap.put("deptId", sysUser.getDeptId() != null ? sysUser.getDeptId().toString() : "");
            modelMap.put("tenantId", sysUser.getTenantId() != null ? sysUser.getTenantId().toString() : "");
            modelMap.put("ip", PropertiesUtil.getString("deehow.address"));
            modelMap.put("account", user.getAccount());
            modelMap.put("deptId", sysUser.getDeptId());
            modelMap.put("deptName", sysUser.getDeptName());
            modelMap.put("phone", sysUser.getPhone());
            modelMap.put("sex", sysUser.getSex());
            modelMap.put("userName", sysUser.getUserName());
            
    		Parameter parameterRole = new Parameter("sysUserRoleService", "queryRoleIdListByUserId").setId(sysUser.getId());
    		List<Long> list = (List<Long>) provider.execute(parameterRole).getList();
    		List<String> roles = new ArrayList<>();
    		for(Long roleId:list){
				roles.add(roleId.toString());
    		}
    		modelMap.put("roleId", roles);
 			return setSuccessModelMap(modelMap);
 			
 		}
 		request.setAttribute("msg", "[" + user.getAccount() + "]登录失败.");
 		throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }
    
    /**	此接口必须手机端登陆。 app登陆和web登陆  凭证的区别就是  sessionId不同，	将手机端当前的session拷贝一份，更换成浏览器的sessionId	*/
    @ApiOperation(value = "app扫码登录")
    @PostMapping("/app/qrcode")
    public Object appLoginQrcode(@RequestBody Map<String,Object> param, ModelMap modelMap,
                        HttpServletRequest request,HttpServletResponse response) {
        Assert.notNull(param.get("sessionId"), "sessionId");
        //获取web将要登陆的session
        Session webSession = getSession(param.get("sessionId").toString());
		if(webSession == null){
			modelMap.put("msg", "二维码已经过期，请重新获取。");
			return setModelMap(modelMap, HttpCode.GONE);
		}
		boolean authenticated = RedisSessionDao.isAuthenticated(webSession);
		if(!authenticated){//若还没有登陆，才去登陆
			//获取手机登录的session
			SimpleSession appSession =  (SimpleSession)getSession(SecurityUtils.getSubject().getSession().getId().toString());
			SimpleSession newSession = InstanceUtil.to(appSession, SimpleSession.class);
			newSession.setAttribute("aw_type",Constants.LOGIN_MODE_JSESSIONID);
			newSession.setId(webSession.getId());
			newSession.setStartTimestamp(new Date());
			redisSessionDao.update(newSession);
			//挤掉 web端 上一次登陆的	
			SysUser user = new SysUser();
			user.setId(getCurrUser());
			user.setTenantId(getCurrTenant());
			realm.saveSession(newSession, user);
			
		}
		//WebSocket  通知客户端，已经登陆成功,基于redis的订阅发布模式
		CacheUtil.getRedisHelper().sendMessage(PropertiesUtil.getString("ws.topic.channel"), param.get("sessionId").toString());
		return setSuccessModelMap(modelMap);
    }
    
    /**	获取session	*/
    private Session getSession(String sessionId){
        Session session = null;
    	try {
			Object webSessionOb = CacheUtil.getCache().getFinal(sessionId);
			if (webSessionOb != null) {
				session = (Session) CacheUtil.getRedisHelper().deserialize(webSessionOb.toString());
			} else {
				log.debug("获取seesion 为空!,id=[{}]", sessionId);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    	return session;
    }
    
//	@ApiOperation(value = "test")
//	@RequestMapping(value = "/lab/anon/test", method = { RequestMethod.GET })
//	public void test(HttpServletResponse response,String content){
//		//WebSocket  通知客户端，已经登陆成功，刷新页面
//		CacheUtil.getRedisHelper().sendMessage(PropertiesUtil.getString("ws.topic.channel"), SecurityUtils.getSubject().getSession().getId().toString());
//	}
}
