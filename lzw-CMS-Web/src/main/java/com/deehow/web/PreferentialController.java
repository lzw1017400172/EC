package com.deehow.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.WebUtil;
import com.deehow.model.SysModel;
import com.deehow.model.SysUnit;
import com.deehow.model.Preferential;
import com.deehow.provider.ICmsProvider;


/**
 * <p>
 *   优惠方案  前端控制器
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@Controller
@RequestMapping("/preferential")
@Api(value = "", description = "")
public class PreferentialController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "preferentialService";
	}

	@ApiOperation(value = "查询")
	@PostMapping(value = "/anon/read/list")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Parameter parameter = new Parameter(getService(), "queryAllPreferentialAndModel").setMap(param);
		logger.info("{} execute queryList start...", parameter.getNo());
		List<Preferential> list = (List<Preferential>) provider.execute(parameter).getList();
		logger.info("{} execute queryList end.", parameter.getNo());
		modelMap.put("login", "no");
		// 租户信息
		if (SecurityUtils.getSubject().isAuthenticated()) {//是否登录
			Parameter unitParam = new Parameter("sysUnitService", "queryById").setId(WebUtil.getCurrentTenant());
			SysUnit sysUnit = (SysUnit) provider.execute(unitParam).getModel();
			modelMap.put("unitName", sysUnit.getUnitName());
			modelMap.put("unitCode", sysUnit.getUnitCode());
			modelMap.put("login", "yes");
		}
		modelMap.put("msg", "查询成功");
		return setModelMap(modelMap, HttpCode.OK, list);
	}
	@ApiOperation(value = "查询某个优惠方案下的模块信息")
	@PostMapping(value = "/read/module_list")
	public Object listModule(ModelMap modelMap, @RequestBody Map<String, Object> param){
		if(param.get("id")==null){
			modelMap.put("msg", "优惠方案id为空");
			return setModelMap(modelMap,HttpCode.BAD_REQUEST);
		}
		Parameter parameter = new Parameter(getService(), "queryModelByPreferentialId").setMap(param);
	    logger.info("{} execute queryList start...", parameter.getNo());
	    List<SysModel> list = (List<SysModel>) provider.execute(parameter).getList();
	    logger.info("{} execute queryList end.", parameter.getNo());
	    modelMap.put("msg", "查询成功");
		return setModelMap(modelMap,HttpCode.OK,list);
	}
	@ApiOperation(value = "添加优惠方案模块")
	@PostMapping(value = "/insert/module")
	public Object insertPreModule(ModelMap modelMap, @RequestBody Map<String, Object> param){
		if(param.get("id")==null){
			modelMap.put("msg", "优惠方案id为空");
			return setModelMap(modelMap,HttpCode.BAD_REQUEST);
		}
		if(param.get("modelId")==null){
			modelMap.put("msg", "modelId为空");
			return setModelMap(modelMap,HttpCode.BAD_REQUEST);
		}
		param.put("userId", WebUtil.getCurrentUser());
		Parameter parameter = new Parameter(getService(), "insertPreferentialModel").setMap(param);
	    provider.execute(parameter);
	    modelMap.put("msg", "添加成功");
		return setModelMap(modelMap,HttpCode.OK);
	}
	@ApiOperation(value = "分页")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.query(modelMap, param);
	}
	
	@ApiOperation(value = "详情")
	@RequiresPermissions(".read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody Preferential param) {
		return super.get(modelMap, param);
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改")
	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody Preferential param) {
//		param.setTenantId(getCurrTenant());
		param.setEnable(1);
		return super.update(modelMap, param);
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除")
	@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody Preferential param) {
		param.setEnable(0);
		return super.update(modelMap, param);
	}

	@ApiOperation(value = "判断是否登录")
	@PostMapping(value = "/anon/check_login")
	public Object checkLogin(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		// 判断是否已经登陆
		if (SecurityUtils.getSubject().isAuthenticated()) {
			modelMap.put("msg", "已登录！");
			modelMap.put("result", "true");
		} else {
			modelMap.put("msg", "没有登录！");
			modelMap.put("result", "false");
		}
		return setModelMap(modelMap, HttpCode.OK);
	}

}