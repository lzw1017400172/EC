package com.deehow.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deehow.core.base.AbstractController;
import com.deehow.core.base.BaseModel;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.WebUtil;
import com.deehow.model.MyModelDTO;
import com.deehow.model.SysMidMvDTO;
import com.deehow.model.SysModel;
import com.deehow.model.SysTenantModel;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;
import com.deehow.util.ModelMenuUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 租户模块表  前端控制器
 * </p>
 *	更新此表数据，请去更新 ModelMenuUtils
 *
 * @author hzy
 * @since 2017-07-27
 */
@Controller
@RequestMapping("/sys_tenant_model")
@Api(value = "租户模块", description = "租户模块")
public class SysTenantModelController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "sysTenantModelService";
	}

	@ApiOperation(value = "查询租户模块表")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/list")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
        Parameter parameter = new Parameter(getService(), "queryList").setMap(param);
        logger.info("{} execute queryList start...", parameter.getNo());
        List<?> list = provider.execute(parameter).getList();
        logger.info("{} execute queryList end.", parameter.getNo());
        return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "租户模块表详情")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody SysTenantModel param) {
        Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
        logger.info("{} execute queryById start...", parameter.getNo());
        BaseModel result = provider.execute(parameter).getModel();
        logger.info("{} execute queryById end.", parameter.getNo());
        return setSuccessModelMap(modelMap, result);
	}

	@PostMapping
	@ApiOperation(value = "运维--修改租户模块表")
	@RequiresPermissions("systemModule.update")
	public Object update(ModelMap modelMap, @RequestBody SysTenantModel param) {
		Assert.notNull(param.getTenantId(), "TENANTID");
		Assert.notNull(param.getModelId(), "MODELID");
		
		Long userId = WebUtil.getCurrentUser();
		
		Map<String,Object> mmp = new HashMap<>();
		mmp.put("tenantId", param.getTenantId());
		mmp.put("modelId", param.getModelId());
		Parameter parameter = new Parameter(getService(), "queryList").setMap(mmp);
        List<SysTenantModel> list = (List<SysTenantModel>) provider.execute(parameter).getList();
        if(list.size() == 0){
        	//新增
        	Parameter parameterU = new Parameter("sysUserService", "queryById").setId(userId);
        	parameterU = provider.execute(parameterU);
        	String userName = parameterU == null ? "" : ((SysUser)parameterU.getModel()).getUserName();
        	param.setCreateBy(userId);
        	param.setCreateTime(new Date());
        	param.setEnable(1);
        	param.setBuy(1);
        	param.setCreateName(userName);
		} else {
			//编辑
	        param.setId(list.get(0).getId());
		}
        param.setUpdateBy(userId);
        param.setUpdateTime(new Date());
        Parameter parameterUp = new Parameter(getService(), "update").setModel(param);
        provider.execute(parameterUp);
        //更新缓存
        ModelMenuUtils.setTenantModelCache(param.getTenantId(), provider);
        return setSuccessModelMap(modelMap);
	}

	@DeleteMapping
	@ApiOperation(value = "删除租户模块表")
	@RequiresPermissions("systemModule.delete")
	public Object delete(ModelMap modelMap, @RequestBody SysTenantModel param) {
		Parameter parameterU = new Parameter("sysTenantModelService", "queryById").setId(param.getId());
		param = (SysTenantModel) provider.execute(parameterU).getModel();
		if(param == null){
			return setModelMap(modelMap, HttpCode.GONE);
		}
        Parameter parameter = new Parameter(getService(), "delete").setId(param.getId());
        provider.execute(parameter);
        
        //更新缓存
        ModelMenuUtils.setTenantModelCache(param.getTenantId(), provider);
        return setSuccessModelMap(modelMap);
	}
	
	/**
	 * type 	==	disable :禁用		/	==	start_using:启用
	 * @param modelMap
	 * @param type
	 * @param param
	 * @return
	 */
	@PostMapping("/{type}")
	@ApiOperation(value = "禁用租户的模块")
	@RequiresPermissions("systemModule.update")
	public Object disable(ModelMap modelMap, @PathVariable("type") String type,@RequestBody SysTenantModel param) {
		Assert.notNull(param.getTenantId(), "TENANTID");
		Assert.notNull(param.getModelId(), "MODELID");
		
		Map<String,Object> mmp = new HashMap<>();
		mmp.put("tenantId", param.getTenantId());
		mmp.put("modelId", param.getModelId());
		Parameter parameter = new Parameter(getService(), "queryList").setMap(mmp);
        List<SysTenantModel> list = (List<SysTenantModel>) provider.execute(parameter).getList();
        
		SysTenantModel model = new SysTenantModel();
		model.setId(list.get(0).getId());
		if("disable".equals(type)){
			if(list.size() == 1){
				if(list.get(0).getEnable() == 0){
					modelMap.put("msg", "已经被禁用不能重复禁用。");
					return setModelMap(modelMap, HttpCode.CONFLICT);
				} else {
					model.setEnable(0);
				}
			} else {
				modelMap.put("msg", "还未购买不能禁用。");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
		} else if("start_using".equals(type)){
			if(list.size() == 1){
				if(list.get(0).getEnable() == 1){
					modelMap.put("msg", "已经启用不能重复启用。");
					return setModelMap(modelMap, HttpCode.CONFLICT);
				} else {
					model.setEnable(1);
				}
			} else {
				modelMap.put("msg", "还未购买不能启用。");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
		} else {
			return setModelMap(modelMap, HttpCode.BAD_REQUEST);
		}
		Parameter parameterUp = new Parameter(getService(), "update").setModel(model);
        provider.execute(parameterUp);
        
        //更新缓存
        ModelMenuUtils.setTenantModelCache(list.get(0).getTenantId(), provider);
        return setSuccessModelMap(modelMap);
	}
	
	
	@ApiOperation(value = "获取租户拥有的模块(我所有购买过的模块，包含 使用中和已过期)")
	@PostMapping(value = "/read/my_model/by_tenant")
	public Object readMyModelByTenant(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Long tenantId = null;
		if( param.get("tenantId") != null){
			tenantId = Long.valueOf(param.get("tenantId").toString());
		} else {
			tenantId = getCurrTenant();
		}
		//当前租户 拥有的模块
		Map<String,Object> mmp = new HashMap<>();
		mmp.put("tenantId", tenantId);
		Parameter parameterTm = new Parameter("sysTenantModelService", "queryList").setMap(mmp);
        List<SysTenantModel> tmList = (List<SysTenantModel>) provider.execute(parameterTm).getList();
        List<Long> modelIds = new ArrayList<>();
        for(SysTenantModel tm:tmList){
        	modelIds.add(tm.getModelId());
		}
        Parameter parameterM = new Parameter("sysModelService", "getList").setList(modelIds);
        List<SysModel> list = (List<SysModel>) provider.execute(parameterM).getList();
		
        List<MyModelDTO> returnList = new ArrayList<>();
        Date date = new Date();
        for(SysModel model : list){
        	if(null != model){
        		MyModelDTO mmDto = new MyModelDTO(model.getId(),model.getModelName(),model.getType());
        		for(SysTenantModel tm:tmList){
        			if(model.getId().longValue() == tm.getModelId().longValue()){
        				mmDto.setCreateName(tm.getCreateName());
        				mmDto.setStartTime(tm.getCreateTime());
        				mmDto.setEndTime(tm.getModelValidate());
        				mmDto.setTenantModelId(tm.getId());
        				mmDto.setPayment(tm.getPayment());
        				if(tm.getModelValidate() != null && date.before(tm.getModelValidate()) && tm.getEnable() == 1){
        					mmDto.setEnabled("Y");
            			} else {
            				mmDto.setEnabled("N");
            			}
        				break;
        			}
        		}
        		returnList.add(mmDto);
        	}
        }
        return setSuccessModelMap(modelMap, returnList);
	}
	
	@ApiOperation(value = "获取所有模块，根据租户区别状态")
	@PostMapping(value = "/read/all_model/by_tenant")
	public Object readAllModelByTenant(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Long tenantId = null;
		if( param.get("tenantId") != null){
			tenantId = Long.valueOf(param.get("tenantId").toString());
		} else {
			tenantId = getCurrTenant();
		}
		//查询所有模块
		Parameter parameterM = new Parameter("sysModelService", "queryList").setMap(new HashMap<>());
        List<SysModel> listM = (List<SysModel>) provider.execute(parameterM).getList();
		//使用中的modelIds
        List<Long> myModelIds =  ModelMenuUtils.getTenantModelUndueCache(tenantId,provider);
        
        List<MyModelDTO> returnList = new ArrayList<>();
        for(SysModel model:listM){
        	MyModelDTO mmDto = new MyModelDTO();
        	mmDto.setModelId(model.getId());
        	mmDto.setModelName(model.getModelName());
        	mmDto.setIcon(model.getModelLogo());
        	mmDto.setModelDesc(model.getModelDesc());
        	mmDto.setModelLable(model.getModelLable());
        	mmDto.setType(model.getType());
        	mmDto.setRemark(model.getRemark());
        	mmDto.setModelInfo(model.getModelInfo());
        	mmDto.setModelUrl(model.getModelUrl());
        	
        	if(myModelIds.contains(model.getId())){
        		mmDto.setEnabled("Y");
        	} else {
        		mmDto.setEnabled("N");
        	}
        	returnList.add(mmDto);
        }
        return setSuccessModelMap(modelMap, returnList);
	}
}