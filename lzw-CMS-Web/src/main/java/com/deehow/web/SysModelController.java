package com.deehow.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deehow.constant.AppConstant;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.UploadUtil;
import com.deehow.core.util.WebUtil;
import com.deehow.model.SysModel;
import com.deehow.provider.ICmsProvider;
import com.deehow.util.ModelMenuUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统模块表  前端控制器
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
@Controller
@RequestMapping("/app")
@Api(value = "系统模块", description = "系统模块")
public class SysModelController extends AbstractController<ICmsProvider> {
	
	public String getService() {
		return "sysModelService";
	}
	
	@ApiOperation(value = "获取APP集合")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/list")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
        return queryList(modelMap,param);
	}
	
	@PostMapping(value = "/update")
	@ApiOperation(value = "修改APP")
//	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody SysModel param) {
		if(null==param.getId()) {
			modelMap.put("msg", "缺少需要修改的App");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
        Long userId = WebUtil.getCurrentUser();
        param.setUpdateBy(userId);
        param.setUpdateTime(new Date());
        Parameter parameter = new Parameter(getService(), "update").setModel(param);
        SysModel sysModel = (SysModel) provider.execute(parameter).getModel();
        return setSuccessModelMap(modelMap, sysModel);
	}
	
	@ApiOperation(value = "详情")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody SysModel param) {
        return super.get(modelMap, param);
	}

	@RequestMapping("/upload")
	@ApiOperation(value = "logo图片上传")
	public Object uploadImage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String pathDir = AppConstant.TITLE + date + "/";
		File filePathDir = new File(pathDir);
		if(!filePathDir.exists()){
			filePathDir.mkdirs();//不存在创建
		}
		String filePath = "";
		try {
			List<String> uploadFile = UploadUtil.uploadFiles(request,pathDir);
			if (uploadFile.size()>0) {
				StringBuffer sb = new StringBuffer();
				for (String string : uploadFile) {
					sb.append(pathDir + string + ",");
				}
				filePath = sb.toString();
				filePath = filePath.substring(0, filePath.lastIndexOf(","));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.put("filePath", filePath);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "查看我的模块，模块是根据租户区别的")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/my_model/home_page")
	public Object myModelHomePage(ModelMap modelMap,@RequestBody Map<String, Object> param) {
		String type = "WEB";
		if(param.get("type") != null){
			type = param.get("type").toString();
		}
		Long tenantId = getCurrTenant();
		if(tenantId == -1){
			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
		}
		List<Long> modelIds = ModelMenuUtils.getTenantModelUndueCache(tenantId,provider);
		List<SysModel> modelList = new ArrayList<>();
		if(modelIds.size() > 0){
			Parameter parameter = new Parameter(getService(), "getList").setList(modelIds);
			List<SysModel> modelListAll = (List<SysModel>) provider.execute(parameter).getList();
			for(SysModel model:modelListAll){
				if(null != model && type.equals(model.getType())){
					modelList.add(model);
				}
			}
		}
        return setSuccessModelMap(modelMap,modelList);
	}
}