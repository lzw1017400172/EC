package com.deehow.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deehow.core.base.AbstractController;
import com.deehow.core.base.BaseModel;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.model.SysDept;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;
import com.deehow.util.ConstantSystem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 部门管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "部门管理", description = "部门管理")
@RequestMapping(value = "dept")
public class SysDeptController extends AbstractController<ICmsProvider> {
	
	public String getService() {
		return "sysDeptService";
	}

	@ApiOperation(value = "查询当前租户所有部门tree")
	//@RequiresPermissions("sys.base.dept.read")
	@PostMapping(value = "/read/tree")
	public Object queryTree(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("tenantId", getCurrTenant());
		Parameter parameter = new Parameter(getService(), "queryList").setMap(param);
		List<SysDept> list = (List<SysDept>) provider.execute(parameter).getList();
		if(list.size() > 0){
			list = ConstantSystem.treeDeptList(list, 0l);
		}
		return setModelMap(modelMap, HttpCode.OK, list);
	}
	
	@ApiOperation(value = "查询当前租户所有部门list")
	//@RequiresPermissions("sys.base.user.read")
	@PostMapping(value = "/read/list")
	public Object queryList(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("tenantId", getCurrTenant());
		return super.queryList(modelMap, param);
	}

	@ApiOperation(value = "查询部门")
	//@RequiresPermissions("sys.base.dept.read")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("tenantId", getCurrTenant());
		return super.query(modelMap, param);
	}

	@ApiOperation(value = "部门详情")
	//@RequiresPermissions("sys.base.dept.read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody SysDept param) {
		return super.get(modelMap, param);
	}

	@PostMapping(value="/update")
	@ApiOperation(value = "修改部门")
	//@RequiresPermissions("sys.base.dept.update")
	public Object update(ModelMap modelMap, @RequestBody SysDept param) {
		if(param.getId() == null){
			Assert.length(param.getDeptName(), 2, 20, "deptName");
		} else {
			if(StringUtils.isNotBlank(param.getDeptName())){
				Assert.length(param.getDeptName(), 2, 20, "deptName");	
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
			if(param.getParentId() == null){
				param.setParentId(0L);
			}
		}
		param.setUpdateTime(date);
		param.setUpdateBy(userId);
		Parameter parameter = new Parameter(getService(), "update").setModel(param);
        logger.info("{} execute update start...", parameter.getNo());
        BaseModel model = provider.execute(parameter).getModel();
        logger.info("{} execute update end.", parameter.getNo());
        return setSuccessModelMap(modelMap,model);
	}
	
	@PostMapping(value="/use/{type:stop|open}")
	@ApiOperation(value = "停用部门")
	//@RequiresPermissions("sys.base.dept.update")
	public Object stop(ModelMap modelMap,@PathVariable("type") String type, @RequestBody SysDept param) {
		Assert.notNull(param.getId(), "ID");
		
		Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
		parameter = provider.execute(parameter);
        param = parameter == null ? null :(SysDept) parameter.getModel();
        if(param == null){
        	modelMap.put("msg", "数据可能已经被删除，请刷新后再试！");
        	return setModelMap(modelMap, HttpCode.GONE);
        }
        
        SysDept dept = new SysDept();
        dept.setId(param.getId());
        if("stop".equals(type)){
            if(param.getEnable() == 0){
            	modelMap.put("msg", "部门已经停用，请不要重复操作。");
            	return setModelMap(modelMap, HttpCode.CONFLICT);
            }
            Map<String,Object> deptMap = new HashMap<String, Object>();
    		deptMap.put("parentId", dept.getId());
    		Parameter deptParam = new Parameter(getService(), "getDeptIds").setMap(deptMap);
    		List<?> deptList = provider.execute(deptParam).getList();
    		if(deptList.size() > 0){
            	modelMap.put("msg", "该部门下面有子部门，不能禁用。");
            	return setModelMap(modelMap, HttpCode.CONFLICT);
            }
            dept.setEnable(0);
        } else if("open".equals(type)){
            if(param.getEnable() == 1){
            	modelMap.put("msg", "部门已经启用，请不要重复操作。");
            	return setModelMap(modelMap, HttpCode.CONFLICT);
            }
            dept.setEnable(1);
        }
        dept.setUpdateTime(new Date());
        dept.setUpdateBy(getCurrUser());
        
		parameter = new Parameter(getService(), "update").setModel(dept);
        logger.info("{} execute update start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute update end.", parameter.getNo());
        
        if("stop".equals(type)){
        	//停用之后，对应部门人员，进入无部门状态
        	Map<String,Object> mmp = new HashMap<>();
        	mmp.put("deptId", dept.getId());
        	mmp.put("tenantId", null);
        	parameter = new Parameter("sysUserService", "getUserIds").setMap(mmp);
    		List<Long> uList = (List<Long>) provider.execute(parameter).getList();
    		if(uList.size() > 0){
    			List<SysUser> users = new ArrayList<>();
    			for(Long id:uList){
    				SysUser user = new SysUser();
    				user.setId(id);
    				user.setDeptId(0L);
    			}
    			parameter = new Parameter("sysUserService", "updateAll").setList(users);
    	        logger.info("{} execute	sysUserService updateAll start...", parameter.getNo());
    	        provider.execute(parameter);
    	        logger.info("{} execute	sysUserService updateAll end.", parameter.getNo());
    		}
        }
        return setSuccessModelMap(modelMap);
	}

	@DeleteMapping
	@ApiOperation(value = "删除部门")
	//@RequiresPermissions("sys.base.dept.delete")
	public Object delete(ModelMap modelMap, @RequestBody SysDept param) {
		Assert.notNull(param.getId(), "ID");
		Map<String,Object> deptMap = new HashMap<String, Object>();
		deptMap.put("parentId", param.getId());
		Parameter deptParam = new Parameter(getService(), "getDeptIds").setMap(deptMap);
		List<?> deptList = provider.execute(deptParam).getList();
		if(deptList.size() > 0){
        	modelMap.put("msg", "该部门下面有子部门，不能删除。");
        	return setModelMap(modelMap, HttpCode.CONFLICT);
        }
		deptMap.clear();
        deptMap.put("deptId", param.getId());
        deptParam = new Parameter("sysUserService", "getUserIds").setMap(deptMap);
		List<Long> uList = (List<Long>) provider.execute(deptParam).getList();
		if(uList.size() > 0){
			modelMap.put("msg", "该部门下面有人员，不能删除。");
        	return setModelMap(modelMap, HttpCode.CONFLICT);
		} else {
			deptParam = new Parameter(getService(), "delete").setId(param.getId());
	        logger.info("{} execute delete start...", deptParam.getNo());
	        provider.execute(deptParam);
	        logger.info("{} execute delete end.", deptParam.getNo());
		}
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "根据deptIds获取负责人集合信息")
	@PostMapping(value = "/get_users")
	public Object getUsers(ModelMap modelMap, @RequestBody Map<String, Object> param){
		Parameter params = new Parameter(getService(), "getUsers").setMap(param);
		logger.info("{} execute sysDeptService.getUsers  start...", params.getNo());
		List<Map<String, Object>> list = (List<Map<String, Object>>) provider.execute(params).getList();
		logger.info("{} execute sysDeptService.getUsers end...", params.getNo());
		return this.setSuccessModelMap(modelMap, list);
	}
}
