package com.deehow.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deehow.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.deehow.core.Constants;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.BaseModel;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.CheckCodeUtil;
import com.deehow.core.util.DateUtil;
import com.deehow.core.util.DownloadExcelUtil;
import com.deehow.core.util.SecurityUtil;
import com.deehow.core.util.UploadUtil;
import com.deehow.core.util.WebUtil;
import com.deehow.provider.ICmsProvider;
import com.deehow.util.ConstantSystem;
import com.deehow.util.ModelMenuUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jxl.write.WriteException;

/**
 * 用户管理控制器
 * 
 * @author hzy
 */
@RestController
@Api(value = "用户管理")
@RequestMapping(value = "/user")
public class SysUserController extends AbstractController<ICmsProvider> {
	
	public String getService() {
		return "sysUserService";
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改用户信息")
	// @RequiresPermissions("sys.base.user.update")
	public Object update(ModelMap modelMap, @RequestBody SysUser param) {

		Long tenantId = getCurrTenant();
		if(tenantId == -1){
			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
		}
		Long userId = getCurrUser();
		if(userId == -1){
			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
		}
		
		if (param.getId() != null) {
			//不能编辑账号
			param.setAccount(null);
			param.setPassword(null);
		} else {
			Assert.mobile(param.getAccount());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", param.getAccount());
			map.put("tenantId", tenantId);
			Parameter sysparam = new Parameter(getService(), "getUserIds").setMap(map);
			List<?> list = provider.execute(sysparam).getList();
			if (list != null && list.size() > 0) {
				modelMap.put("msg", "账号已存在!!!");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
			if (StringUtils.isNotBlank(param.getPassword())) {
				param.setPassword(SecurityUtil.entryptPassword(param.getPassword()));
			} else {
				param.setPassword(SecurityUtil.entryptPassword("123456"));
			}
			param.setTenantId(tenantId);
			param.setUserType(1);
			param.setEnable(1);
			param.setLogin(1);
		}
		param.setUpdateBy(userId);
		param.setUpdateTime(new Date());
		Parameter parameter = new Parameter(getService(), "update").setModel(param);
        logger.info("{} execute update start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute update end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
	}

	// 分页查询用户
	@ApiOperation(value = "查询用户")
	// @RequiresPermissions("sys.base.user.read")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap,@RequestBody Map<String, Object> param) {
		if(param.get("deptId") == null || "0".equals(param.get("deptId"))){
			param.put("tenantId", getCurrTenant());	
		}
		Parameter parameter = new Parameter(getService(), "queryLinked").setMap(param);
        logger.info("{} execute query start...", parameter.getNo());
        Page<?> list = provider.execute(parameter).getPage();
        logger.info("{} execute query end.", parameter.getNo());
        return setSuccessModelMap(modelMap, list);
	}

	// 无分页查询用户
	@ApiOperation(value = "查询用户")
	// @RequiresPermissions("sys.base.user.read")
	@PostMapping(value = "/read/list")
	public Object queryList(ModelMap modelMap,
			@RequestBody Map<String, Object> param) {
		if(param.get("deptId") == null || "0".equals(param.get("deptId"))){
			param.put("tenantId", getCurrTenant());	
		}
		return super.queryList(modelMap, param);
	}

	// 用户详细信息
	@ApiOperation(value = "用户详细信息")
	// @RequiresPermissions("sys.base.user.read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody SysUser param) {
		return super.get(modelMap, param);
	}

	/**	enable = 1正常；= 2 禁用 ；		login = 1可以登录； =2禁止登陆	*/
	@ApiOperation(value = "操作用户")
	// @RequiresPermissions("sys.base.user.delete")
	@PostMapping(value = "/operation/{type:enable|disable|landing|notlanding}")
	public Object delete(ModelMap modelMap, @PathVariable("type") String type,@RequestBody SysUser param) {
		Assert.notNull(param.getId(), "ID");
		Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
		parameter = provider.execute(parameter);
        param = parameter == null ? null : (SysUser)parameter.getModel();
		if(null == param){
			return setModelMap(modelMap, HttpCode.GONE);
		}
		
		SysUser user = new SysUser();
		user.setId(param.getId());
		switch (type) {
			case "enable":
				if(param.getEnable() == 1){
					return setSuccessModelMap(modelMap);
				}
				user.setEnable(1);
				break;
			case "disable":
				if(param.getEnable() == 2){
					return setSuccessModelMap(modelMap);
				}
				user.setEnable(2);
				break;
			case "landing":
				if(param.getLogin() == 1){
					return setSuccessModelMap(modelMap);
				}
				user.setLogin(1);
				break;
			case "notlanding":
				if(param.getLogin() == 2){
					return setSuccessModelMap(modelMap);
				}
				user.setLogin(2);
				break;
			default:
				break;
		}

		parameter = new Parameter(getService(), "update").setModel(user);
        provider.execute(parameter);
        return setSuccessModelMap(modelMap);
	}

	// 当前用户
	@ApiOperation(value = "当前用户信息")
	@GetMapping(value = "/read/promission")
	public Object promission(ModelMap modelMap) {
		Long id = getCurrUser();
		Parameter parameter = new Parameter(getService(), "queryById")
				.setId(id);
		SysUser sysUser = (SysUser) provider.execute(parameter).getModel();
		modelMap.put("user", sysUser);
		parameter = new Parameter("sysAuthorizeService",
				"queryAuthorizeByUserId").setId(id);
		logger.info("{} execute queryAuthorizeByUserId start...",
				parameter.getNo());
		List<?> menus = provider.execute(parameter).getList();
		logger.info("{} execute queryAuthorizeByUserId end.", parameter.getNo());
		modelMap.put("menus", menus);
		return setSuccessModelMap(modelMap);
	}

	// 当前用户
	@ApiOperation(value = "当前用户信息")
	@GetMapping(value = "/read/current")
	public Object current(ModelMap modelMap) {
		Parameter parameter = new Parameter(getService(), "queryById").setId(getCurrUser());
		logger.info("{} execute queryById start...", parameter.getNo());
		SysUser user = (SysUser) provider.execute(parameter).getModel();
		logger.info("{} execute queryById end.", parameter.getNo());
		if(user.getDeptId() != null && user.getDeptId() != 0){
			parameter = new Parameter("sysDeptService", "queryById").setId(user.getDeptId());
			parameter = provider.execute(parameter);
			if(parameter == null){
				user.setDeptName("");
			} else {
				SysDept dept = (SysDept)parameter.getModel();
				user.setDeptName(dept.getDeptName());
			}
		}
		return setSuccessModelMap(modelMap, user);
	}

	@ApiOperation(value = "修改个人信息")
	@PostMapping(value = "/update/person")
	public Object updatePerson(ModelMap modelMap, @RequestBody SysUser param) {
		param.setId(getCurrUser());
		param.setPassword(null);
		Assert.isNotBlank(param.getAccount(), "ACCOUNT");
		Assert.length(param.getAccount(), 3, 15, "ACCOUNT");
		return super.update(modelMap, param);
	}

	@ApiOperation(value = "修改用户头像")
	@PostMapping(value = "/update/avatar")
	public Object updateAvatar(HttpServletRequest request, ModelMap modelMap) {
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile file = req.getFile("file");
		System.out.println("文件名:-----=" + file.getOriginalFilename()
				+ "=------------");
		List<String> fileNames = UploadUtil.uploadImageData(request);
		if (fileNames.size() > 0) {
			SysUser param = new SysUser();
			param.setId(getCurrUser());
			for (int i = 0; i < fileNames.size(); i++) {
				String filePath = UploadUtil.getUploadDir(request)
						+ fileNames.get(i);
				String avatar = UploadUtil.remove2DFS("sysUser",
						"U" + param.getId(), filePath).getRemotePath();
				param.setAvatar(avatar);
			}
			modelMap.put("data", param);
			return super.update(modelMap, param);
		} else {
			modelMap.put("msg", "请选择要上传的文件！");
			return setModelMap(modelMap, HttpCode.BAD_REQUEST);
		}
	}

	/**
	 * 修改密码
	 * 
	 */
	@RequestMapping("/update/password")
	public Object updateUserPwd(ModelMap modelMap,
			@RequestBody Map<String, Object> param) {
		// int userId,String oldPwd,String newPwd
		Assert.isNotBlank((String) param.get("oldPassword"), "OLDPASSWORD");
		Assert.isNotBlank((String) param.get("password"), "password");
		Long userId = getCurrUser();
		Parameter updatepParameter = new Parameter(getService(), "queryById")
				.setId(userId);
		SysUser model = (SysUser) provider.execute(updatepParameter).getModel();
		if (SecurityUtil.validatePassword((String) param.get("oldPassword"),
				model.getPassword())) {
			model.setPassword(SecurityUtil.entryptPassword((String) param
					.get("password")));
			Parameter passParameter = new Parameter(getService(), "update")
					.setModel(model);
			provider.execute(passParameter);
			return setModelMap(modelMap, HttpCode.OK);
		} else {
			modelMap.put("msg", "原始密码输入错误");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}

	}

	/**
	 * 重置密码
	 * 
	 * @param modelMap
	 * @param param
	 * @return
	 */
	@PostMapping("/update/reset_userpwd")
	// @RequiresPermissions("sys.base.user.delete")
	public Object resetUserPwd(ModelMap modelMap, @RequestBody SysUser param) {
		Assert.notNull(param.getId(), "ID");
		param.setPassword(StringUtils.isNotBlank(param.getPassword()) ? SecurityUtil.entryptPassword(param.getPassword()) :SecurityUtil.entryptPassword("123456"));
		return super.update(modelMap, param);
	}

	
	// 获取菜单
	@ApiOperation(value = "获取当前登录人菜单")
	@PostMapping(value = "/get_menu")
	public Object getMenu(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> param) {
		String type = "WEB";
		if(param.get("type") != null){
			type = param.get("type").toString();
		}
		Long userId = getCurrUser();
		Long tenantId = getCurrTenant();
		int userType = WebUtil.getCurrentUserType(request);
		
		//获取当前租户在有效期内的modelIds
		List<Long> modelIds = ModelMenuUtils.getTenantModelUndueCache(tenantId,provider);
		Parameter modelParam = new Parameter("sysModelService","getList").setList(modelIds);
		List<SysModel> modelList = (List<SysModel>) provider.execute(modelParam).getList();
		
		List<Long> modelIdsByType = new ArrayList<>();
		for(SysModel model:modelList){
			if(model != null && type.equals(model.getType())){
				modelIdsByType.add(model.getId());
			}
		}
		
		List<SysModelMenu> treeMenuList = new ArrayList<>();
		if(modelIdsByType.size() > 0){
			// 判断是否是管理员
			if (Constants.ADMINISTRATOR == userType) {
				//查询所有 menu
				Parameter menuParam = new Parameter("sysModelMenuService","queryListByModelIds").setList(modelIdsByType);
				treeMenuList = (List<SysModelMenu>) provider.execute(menuParam).getList();
				
				if(treeMenuList.size() > 0){
					treeMenuList = ConstantSystem.treeMenuList(treeMenuList, 0l);
				}
			} else {
				//获取普通用户的菜单
				Parameter menuParam = new Parameter("sysAuthorizeService","queryUserMenus").setId(userId);
				List<SysModelMenu> menuList = (List<SysModelMenu>) provider.execute(menuParam).getList();
				
				for(SysModelMenu menu : menuList){
					if(menu != null && modelIdsByType.contains(menu.getModelId())){
						treeMenuList.add(menu);
					}
				}
				if(treeMenuList.size() > 0){
					treeMenuList = ConstantSystem.treeMenuList(treeMenuList,0l);
				}
			}
		}
		return setSuccessModelMap(modelMap,treeMenuList);
	}
	
	
//	// 获取菜单,一级一级获取
//	@ApiOperation(value = "获取当前登录人菜单--逐级获取")
//	@PostMapping(value = "/getMenuGradually")
//	public Object getMenuGradually(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> param) {
//		Assert.notNull(param.get("modelId"), "modelId");
//		Assert.notNull(param.get("parentId"), "parentId");
//		
//		Long userId = getCurrUser();
//		Parameter parameter = new Parameter(getService(), "queryById").setId(userId);
//		logger.info("{} execute queryById start...", parameter.getNo());
//		SysUser sysUser = (SysUser) provider.execute(parameter).getModel();
//		System.out.println("当前用户====" + sysUser.getUserName() + "=======用户类型====" + sysUser.getUserType() + "=======");
//		logger.info("{} execute queryById end.", parameter.getNo());
//		Long tenantId = sysUser.getTenantId();
//		
//		Parameter modelMenuParam = new Parameter("sysModelMenuService","queryList").setMap(param);
//		logger.info("{} execute sysModelMenuService start...", modelMenuParam.getNo());
//		List<SysModelMenu> modelMenuList = (List<SysModelMenu>) provider.execute(modelMenuParam).getList();
//		logger.info("{} execute sysModelMenuService end.", modelMenuParam.getNo());
//		
//		// 判断是否是管理员
//		String administrator = Constants.ADMINISTRATOR;
//		if (administrator.equals(sysUser.getUserType())) {//管理员获取当前级别所有菜单
//			return setSuccessModelMap(modelMap,modelMenuList);
//		} else {//普通用户只获取当前级别，自己拥有的菜单
//			if(modelMenuList.size() > 0){
//				param.clear();
//				param.put("userId", userId);
//				//XXX	若要扩展用户对菜单的--可读/可写/可删除 权限。也是在sys_user_menu管理，现在获取菜单只是用了 permission_ = red
//				//XXX	新增条件 	tenamtMap.put("permission", red);
//				Parameter tenantParam = new Parameter("sysUserMenuService","queryMenuIds").setMap(param);
//				logger.info("{} execute queryMenuIds start...", parameter.getNo());
//				List<Long> menuIds = (List<Long>) provider.execute(tenantParam).getList();
//				logger.info("{} execute queryMenuIds end.", parameter.getNo());
//				List<SysModelMenu> returnModelMenuList = new ArrayList<>();
//				if (menuIds != null && menuIds.size() > 0) {
//					for(SysModelMenu modelMenu:modelMenuList){
//						if(null != modelMenu && menuIds.contains(modelMenu.getId())){
//							returnModelMenuList.add(modelMenu);
//						}
//					}
//				} 
//				return setSuccessModelMap(modelMap,returnModelMenuList);
//			} else {
//				return setSuccessModelMap(modelMap,modelMenuList);
//			}
//		}
//	}

	@ApiOperation(value = "获取当前登录人EncryptAccount")
	@PostMapping(value = "/get_encrypt_account")
	public Object getAccountMd5(ModelMap modelMap) {
		Long userId = getCurrUser();
		Parameter parameter = new Parameter(getService(), "queryById")
				.setId(userId);
		logger.info("{} execute queryById start...", parameter.getNo());
		SysUser result = (SysUser) provider.execute(parameter).getModel();
		logger.info("{} execute queryById end.", parameter.getNo());
		
		String account = result.getAccount();
		String encrypt32Md5 = SecurityUtil.encrypt32Md5(account);
		return setSuccessModelMap(modelMap, encrypt32Md5);
	}
	
	@ApiOperation(value = "手机短信--找回密码，验证短信，返回 租户列表")
	@PostMapping("/anon/retrieve_password/validate")
	public Object retrievePassword(ModelMap modelMap, @RequestBody SysUser sysUser,HttpServletRequest request) {
		if (StringUtils.isBlank(sysUser.getPhone())) {
			modelMap.put("msg", "未填写手机号！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}

		if (StringUtils.isBlank(sysUser.getRemark())) {
			modelMap.put("msg", "未填写短信验证码！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}

		// 手机号格式验证
		String regex = "((^(13|15|17|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sysUser.getPhone());
		if (!matcher.matches()) {
			modelMap.put("msg", "请填入正确的手机号！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		
		String key = Constants.RETRIEVE_CODE + ":" + sysUser.getPhone();
		Boolean flag = CheckCodeUtil.validateMessageCheckCode(sysUser.getRemark(),key);
		if(flag == null){
			modelMap.put("msg", "短信验证码已经过期，请重新获取！");
			return setModelMap(modelMap, HttpCode.GONE);
		}else{
			if(flag){
				//将此人手机号保存到session，选择租户重置密码提交的时候，账号必须是这个手机号。
				WebUtil.setSession(Constants.RETRIEVE_CODE,sysUser.getPhone());
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
	
	@ApiOperation(value = "手机短信找回密码，已经验证通过，然后重置密码")
	@PostMapping("/anon/retrieve_password/reset")
	public Object resetPassword(ModelMap modelMap, @RequestBody SysUser sysUser,HttpServletRequest request) {
		Assert.notNull(sysUser.getId(), "id");
		/**	密码6-20位，数字+字母组合			^[A-Za-z0-9]+$	*/
		Assert.pattern(sysUser.getPassword(),"^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$",true,"密码需为6-20位字母和数字组合");
		String key = Constants.RETRIEVE_CODE;
	
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
			modelMap.put("msg", "短信验证码已失效，请返回上一步重新获取验证码。");
			return setModelMap(modelMap, HttpCode.GONE);
		} else {
			//验证
			Parameter parameter = new Parameter("sysUserService", "queryById").setId(sysUser.getId());
			parameter = provider.execute(parameter);
			SysUser user = parameter == null ? null : (SysUser)parameter.getModel();
			if(null == user){
				modelMap.put("msg", "没有对应账号!!");
				return setModelMap(modelMap, HttpCode.GONE);
			}
			if(!user.getAccount().equals(phone)){
				modelMap.put("msg", "您选择的账号和刚刚验证通过的账号不匹配。");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
			//重置密码
			SysUser userReset = new SysUser();
			userReset.setId(user.getId());
			userReset.setPassword(SecurityUtil.entryptPassword(sysUser.getPassword()));
			Parameter parameterUp = new Parameter("sysUserService", "update").setModel(userReset);
			provider.execute(parameterUp);
			return setSuccessModelMap(modelMap);
		}
	}
	
	@ApiOperation(value = "导入人员，下载excel模板")
	@GetMapping(value = "/download_import_template")
	public Object downloadImportTemplate(ModelMap modelMap,HttpServletResponse response) {
		try {
			DownloadExcelUtil util = new DownloadExcelUtil(response,"用户批量导入模板","用户批量导入模板");
			String[] strs = new String[]{"账号","姓名","性别（男/女）","手机","邮箱","身份证号码","出生日期","详细地址","工号"};
			List<Object> list = new ArrayList<>();
			list.add(strs);
			util.addRows(list, null, null);
			util.reportExcel();
		} catch (WriteException  | IOException e) {
			e.printStackTrace();
		}
		return setSuccessModelMap(modelMap);
	}

	
	@ApiOperation(value = "批量导入人员")
	@PostMapping(value = "/update/all")
	public Object updateAll(ModelMap modelMap,@RequestBody List<SysUser> userArray) {
		if(userArray.size() > 0){
			Long tenantId = getCurrTenant();
			if(tenantId == -1){
				return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
			}
			Long userId = getCurrUser();
			if(userId == -1){
				return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
			}
			
			List<String> insertAccountList = new ArrayList<>();
			for(SysUser param:userArray){
				if (param.getId() != null) {
					//不能编辑账号
					param.setAccount(null);
					param.setPassword(null);
				} else {
					Assert.mobile(param.getAccount());
					
					if (StringUtils.isNotBlank(param.getPassword())) {
						param.setPassword(SecurityUtil.entryptPassword(param.getPassword()));
					} else {
						param.setPassword(SecurityUtil.entryptPassword("123456"));
					}
					param.setTenantId(tenantId);
					param.setUserType(1);
					param.setEnable(1);
					param.setLogin(1);
					
					insertAccountList.add(param.getAccount());
				}
				param.setUpdateBy(userId);
				param.setUpdateTime(new Date());
			}
			Set<String> insertAccountSet = new HashSet<>(insertAccountList);
			if(insertAccountList.size() > insertAccountSet.size()){
				modelMap.put("msg", "当前数据中有重复账号!!!");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
			
			//是否账号已经存在
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accountList", insertAccountList);
			map.put("tenantId", tenantId);
			Parameter sysparam = new Parameter(getService(), "queryList").setMap(map);
			List<SysUser> list = (List<SysUser>) provider.execute(sysparam).getList();
			if (list.size() > 0) {
				insertAccountList.clear();
				for(SysUser user:list){
					insertAccountList.add(user.getAccount());
				}
				modelMap.put("msg", "账号在系统中已存在!!!");
				return setModelMap(modelMap, HttpCode.CONFLICT,insertAccountList);
			} else {
				Parameter parameter = new Parameter(getService(), "updateAll").setList(userArray);
		        logger.info("{} execute updateAll start...", parameter.getNo());
		        provider.execute(parameter);
		        logger.info("{} execute updateAll end.", parameter.getNo());
			}
		}
		return setSuccessModelMap(modelMap);
	}
	@ApiOperation(value = "批量修改人员")
	@PostMapping(value = "/update/by/list")
	public Object updateByList(ModelMap modelMap,@RequestBody List<SysUser> userArray) {
		if(userArray.size() > 0){
			Long tenantId = getCurrTenant();
			if(tenantId == -1){
				return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
			}
			Long userId = getCurrUser();
			if(userId == -1){
				return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
			}
			List<SysUser> trueUser = new ArrayList<>();
			for(int i = 0;i < userArray.size();i++) {
				if(userArray.get(i).getId() != null){
					userArray.get(i).setUpdateBy(userId);
					userArray.get(i).setUpdateTime(new Date());
					userArray.get(i).setAccount(null);
					userArray.get(i).setPassword(null);
					userArray.get(i).setLogin(null);
					userArray.get(i).setEnable(null);
					trueUser.add(userArray.get(i));
				}
			}
			Parameter parameter = new Parameter(getService(), "updateAll").setList(userArray);
	        logger.info("{} execute updateAll start...", parameter.getNo());
	        provider.execute(parameter);
	        logger.info("{} execute updateAll end.", parameter.getNo());
			
		}
		return setSuccessModelMap(modelMap);
	}
	@ApiOperation(value = "批量导出人员信息")
	@GetMapping(value = "/export/excel")
	public Object exportExcel(ModelMap modelMap,@RequestParam("deptId") Long deptId,HttpServletResponse response) {
		Assert.notNull(deptId, "deptId");
		Map<String,Object> param = new HashMap<>();
		param.put("enable", 1);
		param.put("deptId", deptId);
		if(deptId.longValue() == 0){
			param.put("tenantId", getCurrTenant());
		}
		Parameter parameter = new Parameter(getService(), "queryList").setMap(param);
        logger.info("{} execute queryList start...", parameter.getNo());
        List<SysUser> list = (List<SysUser>) provider.execute(parameter).getList();
        logger.info("{} execute queryList end.", parameter.getNo());
        
        if(list.size() > 0){
        	List<String[]> strArrayList = new ArrayList<>();
        	strArrayList.add(null);
        	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
	        for(SysUser user:list){
	        	String sex = user.getSex() == 1 ? "男" : ( user.getSex() == 2 ? "女" : "未知");
	        	String birthDayStr = user.getBirthDay() == null ? "" : sdf.format(user.getBirthDay());
	        	String[] content = new String[]{user.getAccount(),user.getUserName(),sex ,user.getPhone(),
	        			user.getEmail(),user.getIdCard(),birthDayStr,user.getAddress(),user.getStaffNo()};
	        	strArrayList.add(content);
	        }
	        
	        //导出excel
	        String name = DateUtil.format(new Date(), DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS);
	        try {
				DownloadExcelUtil util = new DownloadExcelUtil(response,name,name);
				String[] title = new String[]{"账号","姓名","性别","手机","邮箱","身份证号码","出生日期","详细地址","工号"};
				strArrayList.set(0, title);
				util.addRows(strArrayList, null, null);
				util.reportExcel();
			} catch (WriteException  | IOException e) {
				e.printStackTrace();
			}
		}
		
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "根据userIds获取部门集合信息")
	@PostMapping(value = "/get_departments")
	public Object getDepartments(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Parameter params = new Parameter(getService(), "getDepartments").setMap(param);
		logger.info("{} execute sysUserService.getDepartments  start...", params.getNo());
		Map<String, Object> resutlMap = provider.execute(params).getMap();
		logger.info("{} execute sysUserService.getDepartments end...", params.getNo());
		return this.setModelMap(modelMap, HttpCode.OK, resutlMap);
	}
}

