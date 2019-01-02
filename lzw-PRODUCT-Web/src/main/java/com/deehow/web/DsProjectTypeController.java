package com.deehow.web;

import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.model.DsProject;
import com.deehow.model.DsProjectType;
import com.deehow.provider.IEdmProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * cms-協同设计项目类型  前端控制器
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Controller
@RequestMapping("/project_type")
@Api(value = "cms-協同设计项目类型", description = "cms-協同设计项目类型")
public class DsProjectTypeController extends AbstractController<IEdmProvider> {
	public String getService() {
		return "dsProjectTypeService";
	}

	@ApiOperation(value = "查询cms-協同设计项目类型")
	//@RequiresPermissions(".read")
	@PostMapping(value = "/get/all")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.queryList(modelMap, param);
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改cms-協同设计项目类型")
//	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody DsProjectType param) {
		Long userId = getCurrUser();
		if(param.getId() == null){
			Assert.isNotBlank(param.getName(),"name");
			param.setEnable(1);
			param.setCreateTime(new Date());
			param.setCreateBy(userId);
		} else {
		    if(StringUtils.isBlank(param.getName())){
                return setSuccessModelMap(modelMap);
            }
        }
		param.setUpdateBy(userId);
		Parameter parameter = new Parameter(getService(), "verifiedUpdate").setModel(param);
		logger.info("{} execute verifiedUpdate start...", parameter.getNo());
		Map<String,Object> map = provider.execute(parameter).getMap();
		logger.info("{} execute verifiedUpdate end.", parameter.getNo());
		if((int)map.get("state") == 1){
            return setSuccessModelMap(modelMap);
        } else {
		    modelMap.put("msg",map.get("msg"));
		    return setModelMap(modelMap, HttpCode.CONFLICT);
        }
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除cms-協同设计项目类型")
//	@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody DsProjectType param) {
	    Assert.notNull(param.getId(),"ID");
        DsProject project = new DsProject();
        project.setProjectTypeId(param.getId());
        Parameter parameter = new Parameter("dsProjectService", "getProjectSize").setModel(project);
        int result = (int)provider.execute(parameter).getResult();
        if(result > 0){
            modelMap.put("msg","数据已经被使用，不能删除。");
            return setModelMap(modelMap, HttpCode.CONFLICT);
        } else {
            return super.delete(modelMap, param);
        }
	}
}