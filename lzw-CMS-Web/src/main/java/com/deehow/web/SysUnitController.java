package com.deehow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.deehow.core.Constants;
import com.baomidou.mybatisplus.plugins.Page;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.WebUtil;
import com.deehow.model.SysUnit;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 单位管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "单位管理", description = "单位管理")
@RequestMapping(value = "unit")
public class SysUnitController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "sysUnitService";
	}

	@ApiOperation(value = "查询单位列表")
	@PostMapping(value = "/read/list")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param,HttpServletRequest request) {
		Parameter parameter = new Parameter(getService(), "queryAllList").setMap(param);
		logger.info("{} execute sysUnitService.queryList  start...", parameter.getNo());
		List<SysUnit> list = (List<SysUnit>) provider.execute(parameter).getList();
		logger.info("{} execute sysUnitService.queryList end...", parameter.getNo());
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "企业专员对应的租户查询")
	@PostMapping(value = "/read/operating/list")
	public Object operatingList(ModelMap modelMap, @RequestBody Map<String, Object> param,HttpServletRequest request) {
		param.put("operatingPersonnel", WebUtil.getCurrentUser(request));
		Parameter parameter = new Parameter(getService(), "queryOperatingList").setMap(param);
		logger.info("{} execute sysUnitService.queryList  start...", parameter.getNo());
		List<SysUnit> list = (List<SysUnit>) provider.execute(parameter).getList();
		logger.info("{} execute sysUnitService.queryList end...", parameter.getNo());
		return setSuccessModelMap(modelMap, list);
	}
	@ApiOperation(value = "分页查询单位列表")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("TENANTID", getCurrTenant());
		Parameter parameter = new Parameter(getService(), "queryUnitByPage").setMap(param);
		logger.info("{} execute sysUnitService.query  start...", parameter.getNo());
		Page<SysUnit> list = (Page<SysUnit>) provider.execute(parameter).getPage();
		logger.info("{} execute sysUnitService.query end...", parameter.getNo());
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "单位详情")
//	@RequiresPermissions("sys.base.unit.read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody SysUnit param) {
		Long id = getCurrTenant();
		param.setId(id);
		return super.get(modelMap, param);
	}

	@ApiOperation(value = "修改单位")
//	@RequiresPermissions("sys.base.unit.update")
	@PostMapping("/update")
	public Object update(ModelMap modelMap, @RequestBody SysUnit param) {
		Long id = getCurrTenant();
		param.setId(id);
		return super.update(modelMap, param);
	}

	@ApiOperation(value = "修改单位名")
//	@RequiresPermissions("sys.base.unit.update")
	@PostMapping("/update_unit_name")
	public Object updateUnitName(ModelMap modelMap, @RequestBody SysUnit param,HttpServletRequest request) {
		//获取当前登录人
		Long tenantId = getCurrTenant();
		int userType = WebUtil.getCurrentUserType(request);

		if (Constants.ADMINISTRATOR == userType) {
			modelMap.put("msg","你无权限修改!");
			return  setSuccessModelMap(modelMap, HttpCode.FORBIDDEN);
		}
		param.setId(tenantId);
		return super.update(modelMap, param);
	}
	
	@ApiOperation(value = "获取用户所属租户")
	@RequestMapping(value = "/anon/get/{account}/tenant", method = { RequestMethod.GET})
	public Object getTenant(@PathVariable("account") String account,ModelMap modelMap){
		if(StringUtils.isNotBlank(account)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enable", 1);
			params.put("account", account);
			Parameter parameter = new Parameter("sysUserService", "queryList").setMap(params);
			logger.info("{} execute sysUserService.queryList  start...", parameter.getNo());
			List<?> list = provider.execute(parameter).getList();
			logger.info("{} execute sysUserService.queryList end.", parameter.getNo());
			if(list!=null && list.size()>0){
				List<Long> tenantIds = new ArrayList<>();
				for (Object object : list) {
					SysUser sysUser = (SysUser) object;
					tenantIds.add(sysUser.getTenantId());
				}
				Parameter sysUnitParameter = new Parameter("sysUnitService", "getList").setList(tenantIds);
				List<?> sysUnitList = provider.execute(sysUnitParameter).getList();
				return setSuccessModelMap(modelMap, sysUnitList);
			}else{
				modelMap.put("msg", "没有对应账号!!");
				return setModelMap(modelMap, HttpCode.GONE);
			}
		}else{
			modelMap.put("msg", "输入账号为空!!");
			return setModelMap(modelMap, HttpCode.GONE);
		}
	}
}
