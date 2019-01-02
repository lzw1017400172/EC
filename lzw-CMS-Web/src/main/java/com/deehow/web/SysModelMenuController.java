package com.deehow.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deehow.core.base.AbstractController;
import com.deehow.model.SysModelMenu;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统模块菜单表  前端控制器
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
@Controller
@RequestMapping("/sys_model_menu")
public class SysModelMenuController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "sysModelMenuService";
	}

	@ApiOperation(value = "查询系统模块菜单表")
	@RequiresPermissions(".read")
	@PutMapping(value = "/read/list")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.query(modelMap, param);
	}

	@ApiOperation(value = "系统模块菜单表详情")
	@RequiresPermissions(".read")
	@PutMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody SysModelMenu param) {
		return super.get(modelMap, param);
	}

	@PostMapping
	@ApiOperation(value = "修改系统模块菜单表")
	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody SysModelMenu param) {
		return super.update(modelMap, param);
	}

	@DeleteMapping
	@ApiOperation(value = "删除系统模块菜单表")
	@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody SysModelMenu param) {
		return super.delete(modelMap, param);
	}
	
//	@PostMapping("/getAllTenantMenu")
//	@ApiOperation(value = "获取租户所有菜单")
//	public Object getAllTenantMenu(ModelMap modelMap,HttpServletRequest request){
//		Long userId = getCurrUser();
//		Long tenantId = getCurrTenant();
//		int userType = WebUtil.getCurrentUserType(request);
//		
//		JSONArray treeMenuList = null;
//		Date currentDate = new Date();
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		if(Constants.ADMINISTRATOR == userType){
//			Map<String,Object> tenamtMap = new HashMap<>();
//			tenamtMap.put("tenantId", tenantId);
//			tenamtMap.put("modelValidate", sf.format(currentDate));
//			Parameter tenantParam = new Parameter("sysTenantModelService", "queryList").setMap(tenamtMap);
//			List<?> sysTenantModelList = provider.execute(tenantParam).getList();
//			if(sysTenantModelList!=null&&sysTenantModelList.size()>0){
//				List<Long> modelIds = new ArrayList<Long>();
//				for (Object object : sysTenantModelList) {
//					SysTenantModel sysTenantModel = (SysTenantModel) object;
//					modelIds.add(sysTenantModel.getModelId());
//				}
//				Parameter menuParam = new Parameter("sysModelMenuService", "queryListByModelIds").setList(modelIds);
//				List<?> list = provider.execute(menuParam).getList();
//				JSONArray menuJsonList = JSONArray.parseArray(JSONArray.toJSON(list).toString());
//				treeMenuList = treeMenuList(menuJsonList,"0");
//			}
//		}else{
//			return setModelMap(modelMap, HttpCode.FORBIDDEN);
//		}
//		
//		return setModelMap(modelMap, HttpCode.OK, treeMenuList);
//	}
//	
//	@PostMapping("/allocationMenu")
//	@ApiOperation(value = "分配菜单")
//	public Object allocationMenu(ModelMap modelMap,HttpServletRequest request){
//		Long userId = getCurrUser();
//		Long tenantId = getCurrTenant();
//		int userType = WebUtil.getCurrentUserType(request);
//		
//		JSONArray treeMenuList = null;
//		Date currentDate = new Date();
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		if(Constants.ADMINISTRATOR == userType){
//			Map<String,Object> tenamtMap = new HashMap<>();
//			tenamtMap.put("tenantId", tenantId);
//			tenamtMap.put("modelValidate", sf.format(currentDate));
//			Parameter tenantParam = new Parameter("sysTenantModelService", "queryList").setMap(tenamtMap);
//			List<?> sysTenantModelList = provider.execute(tenantParam).getList();
//			if(sysTenantModelList!=null&&sysTenantModelList.size()>0){
//				List<Long> modelIds = new ArrayList<Long>();
//				for (Object object : sysTenantModelList) {
//					SysTenantModel sysTenantModel = (SysTenantModel) object;
//					modelIds.add(sysTenantModel.getModelId());
//				}
//				Parameter menuParam = new Parameter("sysModelMenuService", "queryListByModelIds").setList(modelIds);
//				List<?> list = provider.execute(menuParam).getList();
//				JSONArray menuJsonList = JSONArray.parseArray(JSONArray.toJSON(list).toString());
//				treeMenuList = treeMenuList(menuJsonList,"0");
//			}
//		}else{
//			return setModelMap(modelMap, HttpCode.FORBIDDEN);
//		}
//		
//		return setModelMap(modelMap, HttpCode.OK, treeMenuList);
//	}
//	
//	public JSONArray treeMenuList(JSONArray menuList, String parentId) {
//        JSONArray childMenu = new JSONArray(); 
//        for (Object object : menuList) { 
//            JSONObject jsonMenu = JSONObject.parseObject(JSONObject.toJSON(object).toString());
//            String menuId = jsonMenu.getString("id"); 
//            String pid = jsonMenu.getString("parentId"); 
//            if(!StringUtils.isNotBlank(pid)){
//            	pid = "0";
//            }
//            if (pid.equals(parentId)) { 
//                JSONArray c_node = treeMenuList(menuList, menuId); 
//                jsonMenu.put("children", c_node); 
//                childMenu.add(jsonMenu); 
//            } 
//        } 
//        return childMenu; 
//    }
}