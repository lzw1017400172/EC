package com.deehow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deehow.constant.LoginConstant;
import com.deehow.core.Constants;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.config.Resources;
import com.deehow.core.exception.LoginException;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.support.login.LoginHelper;
import com.deehow.core.util.CheckCodeUtil;
import com.deehow.core.util.SecurityUtil;
import com.deehow.core.util.WebUtil;
import com.deehow.model.Login;
import com.deehow.model.SysSession;
import com.deehow.model.SysUnit;
import com.deehow.model.SysUser;
import com.deehow.model.UserBelongedToUnit;
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
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends AbstractController<ICmsProvider> {

	public String getService() {
		return "sysUserService";
	}
	
	// 登录
	@ApiOperation(value = "用户登录")
	@PostMapping("/login")
	public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody Login user, ModelMap modelMap,
			HttpServletRequest request) {
		
		Assert.isNotBlank(user.getCheckCode(), "checkCode");
		Assert.isNotBlank(user.getAccount(), "ACCOUNT");
		Assert.isNotBlank(user.getPassword(), "PASSWORD");
		Assert.notNull(user.getTenantId(), "TENANTID");
		
		//判断是否已经登陆
		if(SecurityUtils.getSubject().isAuthenticated()){
			modelMap.put("msg", "已经登陆，请不要重复登陆!!");
			return setModelMap(modelMap, HttpCode.CONFLICT);
		}
		
		Boolean bln = CheckCodeUtil.validateCheckCode(user.getCheckCode());
		if(bln == null){
			modelMap.put("msg", "验证码已经失效，请刷新验证码!!");
			return setModelMap(modelMap, HttpCode.GONE);
		} else {
			if(bln){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enable", 1);
				params.put("account", user.getAccount());
				params.put("tenantId", user.getTenantId());
				Parameter parameter = new Parameter("sysUserService", "queryList").setMap(params);
				logger.info("{} execute sysUserService.queryList start...", parameter.getNo());
				List<SysUser> list = (List<SysUser>) provider.execute(parameter).getList();
				logger.info("{} execute sysUserService.queryList end.", parameter.getNo());
				if(list.size() == 0){
					modelMap.put("msg", "账号不存在!!");
					return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
				}
				if(list.get(0).getLogin() == 2){
					modelMap.put("msg", "账号被禁止登录!!");
					return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
				}
				if (LoginHelper.login(user.getAccount(), user.getPassword(),user.getTenantId())) {
					request.setAttribute("msg", "[" + user.getAccount() + "]登录成功.");
					return setSuccessModelMap(modelMap);
				}
				request.setAttribute("msg", "[" + user.getAccount() + "]登录失败.");
				throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
			}else{
				request.setAttribute("msg", "[" + user.getAccount() + "]登录失败.");
				modelMap.put("msg", "验证码错误!!");
				return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
			}
		}
	}

	@ApiOperation(value = "用户登出")
	@PostMapping("/logout")
	public Object logout(HttpServletRequest request, ModelMap modelMap) {
		String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
		SecurityUtils.getSubject().logout();//会调用sessionDao的delete清楚缓存
		SysSession ss = new SysSession();
		ss.setSessionId(sessionId);
		Parameter parameter = new Parameter("sysSessionService", "deleteBySessionId").setModel(ss);	
		logger.info("{} execute deleteBySessionId start...", parameter.getNo());
		provider.execute(parameter);
		logger.info("{} execute deleteBySessionId end.", parameter.getNo());//清除sys_session数据
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "用户注册验证手机短信")
	@PostMapping("/regin/check_code")
	public Object reginCheckCode(ModelMap modelMap, @RequestBody SysUser sysUser,HttpServletRequest request) {
		Assert.mobile(sysUser.getPhone());
		Assert.isNotBlank(sysUser.getRemark(), "REMARK");
		String key = Constants.REGIN_CODE + ":" + sysUser.getPhone();
		Boolean flag = CheckCodeUtil.validateMessageCheckCode(sysUser.getRemark(),key);
		if(flag == null){
			modelMap.put("msg", "短信验证码已经过期，请重新获取！");
			return setModelMap(modelMap, HttpCode.GONE);
		}else{
			if(flag){
				//将此人手机号保存到session，再注册的时候去验证，只能注册的账号是这个手机号
				WebUtil.setSession(Constants.REGIN_CODE,sysUser.getPhone());
				//查询sys_unit返回成功注册过的租户
				List<UserBelongedToUnit> returnList = new ArrayList<>();
				
				Map<String, Object> paramUnit = new HashMap<String, Object>();
				paramUnit.put("phone", sysUser.getPhone());
				Parameter parameterUnit = new Parameter("sysUnitService", "queryList").setMap(paramUnit);
				List<SysUnit> listUnit = (List<SysUnit>) provider.execute(parameterUnit).getList();
				if(listUnit.size() == 0){
					return setSuccessModelMap(modelMap,returnList);
				}
				paramUnit.clear();
				paramUnit.put("enable", 1);
				paramUnit.put("account", sysUser.getPhone());
				Parameter parameter = new Parameter("sysUserService", "queryList").setMap(paramUnit);
				logger.info("{} execute sysUserService.queryList  start...", parameter.getNo());
				List<SysUser> list = (List<SysUser>) provider.execute(parameter).getList();
				logger.info("{} execute sysUserService.queryList end.", parameter.getNo());
				for(SysUnit unit:listUnit){
					UserBelongedToUnit ubtu = new UserBelongedToUnit(unit.getUnitName(),unit.getUnitCode());
					for (SysUser object : list) {
						if(unit.getId().longValue() == object.getTenantId().longValue()){
							ubtu.setUserId(object.getId());	
							break;
						}
					}
					returnList.add(ubtu);
				}
				modelMap.put("msg", "验证通过！");
				return setSuccessModelMap(modelMap,returnList);
			}else{
				modelMap.put("msg", "和短信验证码不一致，请重新输入！");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
		}
	}
	
	/**
	 * 
	 * @param modelMap
	 * @param type		== web_dynamic_password web端动态密码的效验； == app_dynamic_password app的动态密码效验
	 * @param sysUser
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "动态密码登录--校验")
	@PostMapping("/login/{type}/check_code")
	public Object dynamicCheckCode(ModelMap modelMap,@PathVariable("type") String type, @RequestBody SysUser sysUser,HttpServletRequest request) {
		Assert.notNull(type, "type");
		Assert.mobile(sysUser.getPhone());
		Assert.isNotBlank(sysUser.getRemark(), "REMARK");
		String keyPrefix = null;
		if(LoginConstant.web_dynamic_password.toString().equals(type.toString())){
			//web动态密码
			keyPrefix = Constants.DYNAMIC_WEB_CODE;
		} else if(LoginConstant.app_dynamic_password.toString().equals(type.toString())){
			//app动态密码
			keyPrefix = Constants.DYNAMIC_APP_CODE;
		} else {
			return setModelMap(modelMap, HttpCode.BAD_REQUEST);
		}
		String key = keyPrefix + ":" + sysUser.getPhone();
		Boolean flag = CheckCodeUtil.validateMessageCheckCode(sysUser.getRemark(),key);
		if(flag == null){
			modelMap.put("msg", "短信动态密码已经过期，请重新获取！");
			return setModelMap(modelMap, HttpCode.GONE);
		}else{
			if(flag){
				//将此人手机号保存到session，选择租户登录的时候，账号必须是这个手机号。
				WebUtil.setSession(keyPrefix,sysUser.getPhone());
				//返回这个user对应的所有unit
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enable", 1);
				params.put("account", sysUser.getPhone());
				Parameter parameter = new Parameter("sysUserService", "queryList").setMap(params);
				logger.info("{} execute sysUserService.queryList  start...", parameter.getNo());
				List<SysUser> list = (List<SysUser>) provider.execute(parameter).getList();
				logger.info("{} execute sysUserService.queryList end.", parameter.getNo());
				if(list != null && list.size() > 0){
					List<Long> tenantIds = new ArrayList<>();
					for (SysUser object : list) {
						if(object != null){
							tenantIds.add(object.getTenantId());
						}
					}
					Parameter sysUnitParameter = new Parameter("sysUnitService", "getList").setList(tenantIds);
					List<SysUnit> sysUnitList = (List<SysUnit>) provider.execute(sysUnitParameter).getList();
					List<UserBelongedToUnit> returnMap = new ArrayList<>();
					for(SysUnit unit:sysUnitList){
						if(null != unit){
							UserBelongedToUnit ubtu = new UserBelongedToUnit(unit.getUnitName(),unit.getUnitCode());
							for (SysUser object : list) {
								if(unit.getId().longValue() == object.getTenantId().longValue()){
									ubtu.setUserId(object.getId());	
									break;
								}
							}
							returnMap.add(ubtu);
						}
					}
					modelMap.put("msg", "验证通过！");
					return setSuccessModelMap(modelMap, returnMap);
				}else{
					modelMap.put("msg", "没有对应账号!!");
					return setModelMap(modelMap, HttpCode.GONE);
				}
			}else{
				modelMap.put("msg", "和短信验证码不一致，请重新输入！");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
		}
	}
	
	/**
	 * 目前此接口，三个地方使用		1 直接web动态密码登录	2 本来短信验证注册的，然后选择租户登录		3	直接app动态密码登陆
	 * 								type = 	nopassword_login_web			type = regin_login			 type = nopassword_login_app
	 * 								区别是：为了避免   注册的短信密码和动态登录短信的密码冲突
	 * 								动态密码登录：	session-key = Constants.DYNAMIC_CODE
	 * 								使用注册的短信密码登录： 	seesion-key = Constants.REGIN_CODE; 注册或者登录二者只能成功一次，就删除这个key。
	 * 										
	 * @param modelMap
	 * @param sysUser
	 * @param type
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "动态密码登录--效验成功或，选择租户登录")
	@PostMapping("/login/dynamic/{type}")
	public Object dynamicPassword(ModelMap modelMap, @RequestBody SysUser sysUser,@PathVariable("type") String type,HttpServletRequest request) {
		Assert.notNull(sysUser.getId(), "id");
		
		String key = null;
		if(LoginConstant.nopassword_login_web.toString().equals(type.toString())){
			//无密码登录web
			key = Constants.DYNAMIC_WEB_CODE;
		} else if(LoginConstant.regin_login.toString().equals(type.toString())){
			//本来短信验证注册的，然后选择租户登录
			key = Constants.REGIN_CODE;
		} else if(LoginConstant.nopassword_login_app.toString().equals(type.toString())){
			//无密码登录web
			key = Constants.DYNAMIC_APP_CODE;
		} else {
			return setModelMap(modelMap, HttpCode.BAD_REQUEST);
		}
	
		//获取当前浏览器————登录人的账号
		HttpSession session = request.getSession();
		String phone = null;
		if(null != session){
			Object account = session.getAttribute(key);
			if(null != account){
				phone = account.toString();
			}
		}
	
		if(phone == null){
			modelMap.put("msg", "动态密码已失效，请返回上一步重新获取密码。");
			return setModelMap(modelMap, HttpCode.GONE);
		} else {
			
			//判断是否已经登陆
			if(SecurityUtils.getSubject().isAuthenticated()){
				modelMap.put("msg", "已经登陆，请不要重复登陆!!");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
			
			//验证
			Parameter parameter = new Parameter("sysUserService", "queryById").setId(sysUser.getId());
			parameter = provider.execute(parameter);
			SysUser user = parameter == null ? null : (SysUser)parameter.getModel();
			if(null == user){
				modelMap.put("msg", "没有对应账号!!");
				return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
			}
			if(user.getLogin() == 2){
				modelMap.put("msg", "账号被禁止登录!!");
				return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
			}
			if(!user.getAccount().equals(phone)){
				modelMap.put("msg", "您选择的账号和刚刚验证通过的账号不匹配。");
				return setModelMap(modelMap, HttpCode.LOGIN_FAIL);
			}
			
			//无密码登录登录
			if (LoginHelper.login(user.getAccount(),user.getTenantId())) {
				//登录成功，清除 session.getAttribute(key)。如果再次动态密码登录，必须再次手机验证。
				session.removeAttribute(key);
				request.setAttribute("msg", "[" + user.getAccount() + "]登录成功.");
				return setSuccessModelMap(modelMap);
			}
			request.setAttribute("msg", "[" + user.getAccount() + "]登录失败.");
			throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
		}
	}
	
	// 注册
	@ApiOperation(value = "用户注册")
	@PostMapping("/regin")
	public Object regin(ModelMap modelMap, @RequestBody SysUser sysUser) {
		Assert.notNull(sysUser.getAccount(), "ACCOUNT");
		Assert.notNull(sysUser.getPassword(), "PASSWORD");
		Assert.notNull(sysUser.getUnitName(), "UNITNAME");
		sysUser.setPassword(SecurityUtil.entryptPassword(sysUser.getPassword()));
		//校验公司
		Map<String, Object> paramUnit = new HashMap<String, Object>();
		paramUnit.put("unitName", sysUser.getUnitName());
		Parameter parameterUnit = new Parameter("sysUnitService", "queryList").setMap(paramUnit);
		List<SysUnit> listUnit = (List<SysUnit>) provider.execute(parameterUnit).getList();
		if(listUnit!=null&&listUnit.size()>0){
			modelMap.put("msg", "注册公司名称已存在！");
			return setModelMap(modelMap, HttpCode.GONE);
		}
		provider.execute(new Parameter("sysUserService", "regin").setModel(sysUser));
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "获取公司行业")
	@PostMapping(value = "/login/get_busines")
	public Object getBusines(ModelMap modelMap,@RequestBody Map<String, Object> param){
		//不传参数 会出现 登录 问题，，，
		Parameter parameter = new Parameter("sysBusinessService", "queryList").setMap(param);
        List<?> list = provider.execute(parameter).getList();
        return setSuccessModelMap(modelMap, list);
	}
	
	// 没有登录
	@RequestMapping(value = "/unauthorized", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public Object unauthorized(ModelMap modelMap) throws Exception {
		return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
	}
	

	// 没有权限
	@RequestMapping(value = "/forbidden", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public Object forbidden(ModelMap modelMap) {
		return setModelMap(modelMap, HttpCode.FORBIDDEN);
	}
	
	
	
	
}
