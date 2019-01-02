package com.deehow.web;

import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.model.DsProjectStageTask;
import com.deehow.model.DsStage;
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
import java.util.List;

/**
 * <p>
 * cms-協同设计阶段  前端控制器
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Controller
@RequestMapping("/stage")
@Api(value = "cms-協同设计阶段", description = "cms-協同设计阶段")
public class DsStageController extends AbstractController<IEdmProvider> {
	public String getService() {
		return "dsStageService";
	}

//	@ApiOperation(value = "查询cms-協同设计阶段")
//	@RequiresPermissions(".read")
//	@PostMapping(value = "/read/list")
//	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
//		return super.queryList(modelMap, param);
//	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改cms-協同设计阶段")
//	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody DsStage param) {
		Long userId = getCurrUser();
		if(param.getId() == null){
			Assert.isNotBlank(param.getName(),"name");
            Assert.notNull(param.getProjectId(),"projectId");
            Assert.notNull(param.getLeader(),"leader");
			param.setCreateBy(userId);
			param.setEnable(1);
			param.setCreateTime(new Date());
		} else {
			if(StringUtils.isNotBlank(param.getName()) && param.getLeader() == null ){
				return setSuccessModelMap(modelMap);
			}
		}
		param.setUpdateTime(new Date());
		Parameter parameter = new Parameter(getService(), "updateStage").setModel(param);
		logger.info("{} execute updateStage start...", parameter.getNo());
		provider.execute(parameter);
		logger.info("{} execute updateStage end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除cms-協同设计阶段")
//	@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody DsStage param) {
        Assert.notNull(param.getId(),"ID");
        DsProjectStageTask dpst = new DsProjectStageTask();
        dpst.setStageId(param.getId());
        dpst.setTaskId(0l);
        Parameter parameter = new Parameter("dsProjectStageTaskService", "getProjectStageTaskModel").setModel(dpst);
        List<DsProjectStageTask> list = (List<DsProjectStageTask>) provider.execute(parameter).getList();
        if(list.size() > 0){
            DsProjectStageTask dpstSize = new DsProjectStageTask();
            dpstSize.setProjectId(list.get(0).getProjectId());
            dpstSize.setStageId(param.getId());
            parameter = new Parameter("dsProjectStageTaskService", "getProjectStageTaskSize").setModel(dpstSize);
            int result = (int)provider.execute(parameter).getResult();
            if(result > 1){
                modelMap.put("msg","此阶段下面还有任务，不能删除。");
                return setModelMap(modelMap, HttpCode.CONFLICT);
            } else {
                param.setProjectId(list.get(0).getProjectId());
                parameter = new Parameter(getService(), "deleteStage").setModel(param);
                provider.execute(parameter);
                return setSuccessModelMap(modelMap);
            }
        } else {
            return setSuccessModelMap(modelMap);
        }
	}


    @PostMapping(value = "/update_sort_all")
    @ApiOperation(value = "修改cms-協同设计阶段--排序")
//	@RequiresPermissions(".update")
    public Object updatSortAll(ModelMap modelMap, @RequestBody List<DsProjectStageTask> params) {
        Long userId = getCurrUser();
        if(params!=null && params.size() > 0){
            Parameter parameter = new Parameter("dsProjectStageTaskService", "updatSortAll").setList(params);
            logger.info("{} dsProjectStageTaskService execute updatSortAll start...", parameter.getNo());
            provider.execute(parameter);
            logger.info("{} dsProjectStageTaskService execute updatSortAll end.", parameter.getNo());
        }
        return setSuccessModelMap(modelMap);
    }
}