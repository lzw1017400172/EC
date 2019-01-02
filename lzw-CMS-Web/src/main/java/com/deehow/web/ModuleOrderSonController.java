package com.deehow.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deehow.core.base.AbstractController;
import com.deehow.model.ModuleOrderSon;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 模块订单子表  前端控制器
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@Controller
@RequestMapping("/module_order_son")
@Api(value = "模块订单子表", description = "模块订单子表")
public class ModuleOrderSonController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "moduleOrderSonService";

	}

	@ApiOperation(value = "查询模块订单子表")
	@RequiresPermissions(".read")
	@PostMapping(value = "/read/list")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.queryList(modelMap, param);
	}
	
	@ApiOperation(value = "分页模块订单子表")
	@RequiresPermissions(".read")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.query(modelMap, param);
	}
	
	@ApiOperation(value = "模块订单子表详情")
	@RequiresPermissions(".read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody ModuleOrderSon param) {
		return super.get(modelMap, param);
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改模块订单子表")
	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody ModuleOrderSon param) {
//		param.setTenantId(getCurrTenant());
		param.setEnable(1);
		return super.update(modelMap, param);
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除模块订单子表")
	@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody ModuleOrderSon param) {
		param.setEnable(0);
		return super.update(modelMap, param);
	}
}