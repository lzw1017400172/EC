package com.deehow.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deehow.core.base.AbstractController;
import com.deehow.core.base.BaseModel;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.model.SysRole;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:15:43
 */
@RestController
@Api(value = "角色管理", description = "角色管理")
@RequestMapping(value = "role")
public class SysRoleController extends AbstractController<ICmsProvider> {
	
	public String getService() {
		return "sysRoleService";
	}

	
	@ApiOperation(value = "查询当前租户所有角色list")
	//@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/list")
	public Object queryList(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("tenantId", getCurrTenant());
		return super.queryList(modelMap, param);
	}

	@ApiOperation(value = "查询当前租户所有角色page")
	//@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("tenantId", getCurrTenant());
		return super.query(modelMap, param);
	}

	@ApiOperation(value = "角色详情")
	//@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody SysRole param) {
		return super.get(modelMap, param);
	}

	@PostMapping(value="/update")
	@ApiOperation(value = "修改角色")
	//@RequiresPermissions("sys.base.role.update")
	public Object update(ModelMap modelMap, @RequestBody SysRole param) {
		if(param.getId() == null){
			Assert.length(param.getRoleName(), 2, 20, "roleName");
		} else {
			if(StringUtils.isNotBlank(param.getRoleName())){
				Assert.length(param.getRoleName(), 2, 20, "roleName");	
			}
		}
		Long userId = getCurrUser();
		Long tenantId = getCurrTenant();
		Date date = new Date();
		if(param.getId() == null){
			param.setCreateBy(userId);
			param.setCreateTime(date);
			param.setEnable(1);
			param.setTenantId(tenantId);
		}
		param.setUpdateTime(date);
		param.setUpdateBy(userId);
		Parameter parameter = new Parameter(getService(), "update").setModel(param);
        logger.info("{} execute update start...", parameter.getNo());
        BaseModel model = provider.execute(parameter).getModel();
        logger.info("{} execute update end.", parameter.getNo());
        return setSuccessModelMap(modelMap,model);
	}

	@DeleteMapping
	@ApiOperation(value = "删除角色")
	//@RequiresPermissions("sys.base.role.delete")
	public Object delete(ModelMap modelMap, @RequestBody SysRole param) {
		Assert.notNull(param.getId(), "ID");

		//判断是否是系统内置角色
		Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
		parameter = provider.execute(parameter);
		SysRole role = parameter == null ? null :(SysRole) parameter.getModel();
		if(parameter == null){
			return setModelMap(modelMap,HttpCode.CONFLICT);
		}
		if(role.getRoleType() == 3){
			modelMap.put("msg","系统内置角色不能删除。");
			return setModelMap(modelMap,HttpCode.CONFLICT);
		}

        //判断此角色，是否 分配了人员/是否分配了菜单
		Map<String,Object> mmp = new HashMap<>();
		mmp.put("roleId", param.getId());
		Parameter roleUser = new Parameter("sysUserRoleService", "queryIdList").setMap(mmp);
        List<?> list1 = provider.execute(roleUser).getList();
        if(list1.size() > 0){
        	modelMap.put("msg", "此角色下面已经分配人员，不能删除！！！");
        	return setModelMap(modelMap, HttpCode.CONFLICT);
        }
        
        mmp.clear();
        mmp.put("roleId", param.getId());
        Parameter menuParam = new Parameter("sysRoleMenuService", "queryIdList").setMap(mmp);
        List<?> list2 = provider.execute(menuParam).getList();
        if(list2.size() > 0){
        	modelMap.put("msg", "此角色下面已经分配菜单，不能删除！！！");
        	return setModelMap(modelMap, HttpCode.CONFLICT);
        }

        //删除角色
		Parameter deptParam = new Parameter(getService(), "delete").setId(param.getId());
        logger.info("{} execute delete start...", deptParam.getNo());
        provider.execute(deptParam);
        logger.info("{} execute delete end.", deptParam.getNo());
		return setSuccessModelMap(modelMap);
	}
	
}
