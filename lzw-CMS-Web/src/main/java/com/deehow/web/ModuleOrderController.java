package com.deehow.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.DataUtil;
import com.deehow.core.util.WebUtil;
import com.deehow.model.ModuleOrder;
import com.deehow.model.SysUnit;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 模块订单表--- 前端控制器
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@Controller
@RequestMapping("/module_order")
@Api(value = "模块订单表---", description = "模块订单表---")
public class ModuleOrderController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "moduleOrderService";
	}

	// @ApiOperation(value = "查询模块订单表---")
	// @PostMapping(value = "/read/list")
	// public Object list(ModelMap modelMap, @RequestBody Map<String, Object>
	// param) {
	// return super.queryList(modelMap, param);
	// }

	@ApiOperation(value = "订单公海")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		if (param.get("operatingPersonnel") == null || param.get("operatingPersonnel").equals("")||!param.get("operatingPersonnel").equals("0")) {// 我的订单
			param.put("operatingPersonnel", WebUtil.getCurrentUser().toString());
			param.put("enable",1);
		} else {// 订单公海
			param.put("operatingPersonnel", "0");
		}
		return super.query(modelMap, param);
	}

	@ApiOperation(value = "订单详情包括模块信息")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody ModuleOrder param) {
		if (param.getId() == null) {
			modelMap.put("msg", "参数有误");
			return setModelMap(modelMap, HttpCode.BAD_REQUEST);
		}
		Parameter parameter = new Parameter(getService(), "getOrderDetail").setModel(param);
		logger.info("{} execute queryList start...", parameter.getNo());
		ModuleOrder sysModuleOrder = (ModuleOrder) provider.execute(parameter).getModel();
		logger.info("{} execute queryList end.", parameter.getNo());
		modelMap.put("msg", "查询成功");
		return setSuccessModelMap(modelMap, sysModuleOrder);
	}

	@ApiOperation(value = "运维人员添加订单")
	@PostMapping(value = "/manager/insert/order")
	public Object insertFromManager(ModelMap modelMap, @RequestBody ModuleOrder param) {
		if (param.getPreferentialId() == null) {
			modelMap.put("msg", "未选择优惠方案！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getPersonNum() == null || param.getPersonNum() == 0) {
			modelMap.put("msg", "未选择使用人数！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getValidityTime() == null || param.getValidityTime().equals("0")) {
			modelMap.put("msg", "未选择有效期！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		// 验证手机号
		String phone = param.getPhone();
		if (param.getPhone() == null) {
			modelMap.put("msg", "未填写手机号！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			modelMap.put("msg", "手机号位数不对！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			if (!isMatch) {
				modelMap.put("msg", "请填入正确的手机号！");
				return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
			}
		}
		if(DataUtil.isEmpty(param.getUnitId())){
			modelMap.put("msg", "未选择企业");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		param.setOperatingPersonnel(WebUtil.getCurrentUser().toString());
		param.setCreateBy(WebUtil.getCurrentUser());
		param.setCreateTime(new Date());
		param.setUpdateBy(WebUtil.getCurrentUser());
		param.setUpdateTime(new Date());
		Parameter parameter = new Parameter(getService(), "insertOrder").setModel(param);
		provider.execute(parameter);
		modelMap.put("msg", "添加成功");
		return setModelMap(modelMap, HttpCode.OK);
	}
	@ApiOperation(value = "添加订单")
	@PostMapping(value = "/insert/order")
	public Object insert(ModelMap modelMap, @RequestBody ModuleOrder param) {
		if (param.getPreferentialId() == null) {
			modelMap.put("msg", "未选择优惠方案！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getPersonNum() == null || param.getPersonNum() == 0) {
			modelMap.put("msg", "未选择使用人数！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getValidityTime() == null || param.getValidityTime().equals("0")) {
			modelMap.put("msg", "未选择有效期！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getPhone() == null) {
			modelMap.put("msg", "未填写手机号！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		// 验证手机号
		String phone = param.getPhone();
		if (param.getPhone() == null) {
			modelMap.put("msg", "未填写手机号！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			modelMap.put("msg", "手机号位数不对！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			if (!isMatch) {
				modelMap.put("msg", "请填入正确的手机号！");
				return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
			}
		}
		param.setUnitId(WebUtil.getCurrentTenant());
		param.setOperatingPersonnel("0");
		param.setCreateBy(WebUtil.getCurrentUser());
		param.setCreateTime(new Date());
		param.setUpdateBy(WebUtil.getCurrentUser());
		param.setUpdateTime(new Date());
		Parameter parameter = new Parameter(getService(), "insertOrder").setModel(param);
		provider.execute(parameter);
		modelMap.put("msg", "添加成功");
		return setModelMap(modelMap, HttpCode.OK);
	}
	// @PostMapping(value = "/update")
	// @ApiOperation(value = "修改模块订单表---")
	// public Object update(ModelMap modelMap, @RequestBody SysModuleOrder
	// param) {
	//// param.setTenantId(getCurrTenant());
	// param.setEnable(1);
	// return super.update(modelMap, param);
	// }
	@PostMapping(value = "/update/person")
	@ApiOperation(value = "订单认领---")
	public Object updatePerson(ModelMap modelMap, @RequestBody ModuleOrder param) {
		if (param.getId() == null) {
			modelMap.put("msg", "参数有误");
			return setModelMap(modelMap, HttpCode.BAD_REQUEST);
		}
		param.setOperatingPersonnel(WebUtil.getCurrentUser().toString());
		param.setUpdateBy(WebUtil.getCurrentUser());
		
		Parameter parameter = new Parameter(getService(), "updateOperatingPersonnel").setModel(param);
		Object o = provider.execute(parameter);
		modelMap.put("msg", "认领成功");
		if (o == null) {
			modelMap.put("msg", "此企业的订单已有专员！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		return setModelMap(modelMap, HttpCode.OK);
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除模块订单以及子表信息---")
	public Object delete(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		if (param.get("ids") == null || ((List) param.get("ids")).size() == 0) {
			modelMap.put("msg", "ids为空");
			return setModelMap(modelMap, HttpCode.BAD_REQUEST);
		}
		param.put("user", WebUtil.getCurrentUser());
		Parameter parameter = new Parameter(getService(), "deleteOrderAndSon").setMap(param);
		provider.execute(parameter);
		modelMap.put("msg", "删除成功");
		return setModelMap(modelMap, HttpCode.OK);
	}
	@PostMapping(value = "/get/operating_person")
	@ApiOperation(value = "获取企业专员---")
	public Object getPerson(ModelMap modelMap, @RequestBody Map<String,Object> param) {
		Parameter parameter = new Parameter(getService(), "getOperatingPersonnel").setId(getCurrTenant());
		ModuleOrder mo = (ModuleOrder) provider.execute(parameter).getModel();
		if(mo==null){
			modelMap.put("msg", "此企业没有专员，请联系管理员！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		Parameter userParameter = new Parameter("sysUserService", "queryById").setId(Long.valueOf(mo.getOperatingPersonnel()));
		SysUser user = (SysUser) provider.execute(userParameter).getModel();
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Parameter unitParameter = new Parameter("sysUnitService", "queryById").setId(WebUtil.getCurrentTenant());
		SysUnit unit = (SysUnit) provider.execute(unitParameter).getModel();
		if(user!=null){
			resultMap.put("phone", user.getPhone());
			resultMap.put("userName", user.getUserName());
		}
		if(unit!=null){
			resultMap.put("unitName", unit.getUnitName());
			resultMap.put("unitCode", unit.getUnitCode());
		}
		modelMap.put("msg", "查询成功");
		return setModelMap(modelMap, HttpCode.OK,resultMap);
	}
	@ApiOperation(value = "根据模块信息添加订单")
	@PostMapping(value = "/insert/order/model")
	public Object insertOrderByModel(ModelMap modelMap, @RequestBody ModuleOrder param) {
		if (param.getModelIdList()==null||param.getModelIdList().size()==0) {
			modelMap.put("msg", "未选择应用！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getPersonNum() == null || param.getPersonNum() == 0) {
			modelMap.put("msg", "未选择使用人数！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getValidityTime() == null || param.getValidityTime().equals("0")) {
			modelMap.put("msg", "未选择有效期！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (param.getPhone() == null) {
			modelMap.put("msg", "未填写手机号！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		// 验证手机号
		String phone = param.getPhone();
		if (param.getPhone() == null) {
			modelMap.put("msg", "未填写手机号！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			modelMap.put("msg", "手机号位数不对！");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			if (!isMatch) {
				modelMap.put("msg", "请填入正确的手机号！");
				return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
			}
		}
		param.setUnitId(WebUtil.getCurrentTenant());
		param.setOperatingPersonnel("0");
		param.setCreateBy(WebUtil.getCurrentUser());
		param.setCreateTime(new Date());
		param.setUpdateBy(WebUtil.getCurrentUser());
		param.setUpdateTime(new Date());
		Parameter parameter = new Parameter(getService(), "insertOrderModel").setModel(param);
		provider.execute(parameter);
		modelMap.put("msg", "添加成功");
		return setModelMap(modelMap, HttpCode.OK);
	}
}