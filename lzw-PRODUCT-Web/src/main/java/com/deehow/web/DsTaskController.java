package com.deehow.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.provider.ICmsProvider;
import com.deehow.core.support.Assert;
import com.deehow.core.util.InstanceUtil;
import com.deehow.model.DsProject;
import com.deehow.model.DsTask;
import com.deehow.model.SysUser;
import com.deehow.provider.IEdmProvider;

import io.netty.util.collection.LongObjectHashMap;
import io.netty.util.collection.LongObjectMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * cms-協同设计任务  前端控制器
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Controller
@Api(value = "cms-協同设计任务", description = "cms-協同设计任务")
public class DsTaskController extends AbstractController<IEdmProvider> {
	public String getService() {
		return "dsTaskService";
	}
	@Autowired
    @Qualifier("cmsProvider")
    private ICmsProvider cmsProvider;

	@ApiOperation(value = "查询cms-協同设计任务")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/task/read/list")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.queryList(modelMap, param);
	}
	
	@ApiOperation(value = "查询cms-協同设计任务")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/task/read/page")
	public Object readPage(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Parameter parameter = new Parameter(getService(), "query").setMap(param);
        Page<DsTask> list = (Page<DsTask>) provider.execute(parameter).getPage();
//        if(list.getRecords().size() > 0){
//        	Set<Long> usetIds = new HashSet<>();
//        	for(DsTask project:list.getRecords()){
//                if(project != null){
//                    usetIds.add(project.getLeader());
//                }
//            }
//        	parameter = new Parameter("sysUserService", "getList").setList(InstanceUtil.newArrayList(usetIds));
//            List<SysUser> users = (List<SysUser>) cmsProvider.execute(parameter).getList();
//            LongObjectMap<String> uMap = new LongObjectHashMap<>();
//            for(SysUser user:users){
//                if(user != null){
//                    uMap.put(user.getId(),user.getUserName());
//                }
//            }
//            for(DsTask project:list.getRecords()){
//                if(project != null){
//                    project.setLeaderName(uMap.get(project.getLeader()));
//                    StringBuffer  participantNames = new StringBuffer();
//                    List<String> pStrs = new ArrayList<>();
//                    for(Long uid:project.getParticipants()){
//                        participantNames.append(uMap.get(uid)).append(",");
//                        pStrs.add(uid.toString());
//                    }
//                    project.setParticipantsStr(pStrs);
//                    project.setParticipantNames(participantNames.length() > 0 ? participantNames.substring(0,
//                            participantNames.length()-1):participantNames.toString());
//
//                }
//            }
//        }
        return setSuccessModelMap(modelMap, list);
	}
	

	@PostMapping(value = "/task/update")
	@ApiOperation(value = "修改cms-協同设计任务")
//	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody DsTask param) {
		Long userId = getCurrUser();
		if(param.getId() == null){
			Assert.isNotBlank(param.getName(),"name");
			Assert.notNull(param.getLeader(),"leader");
			Assert.notNull(param.getStartTime(),"startTime");
			Assert.notNull(param.getEndTime(),"endTime");
			Assert.notNull(param.getType(),"type");
			param.setCreateBy(userId);
			param.setEnable(1);
			param.setCreateTime(new Date());
		}
		param.setUpdateTime(new Date());
        param.setUpdateBy(userId);
		Parameter parameter = new Parameter(getService(), "updateTask").setModel(param);
		logger.info("{} execute updateTask start...", parameter.getNo());
		provider.execute(parameter);
		logger.info("{} execute updateTask end.", parameter.getNo());
		return setSuccessModelMap(modelMap);
	}

	@PostMapping(value = "/task/delete")
	@ApiOperation(value = "删除cms-協同设计任务")
//	@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody DsTask param) {
        //TODO  关联阶段之后是否可以删除
	    //TODO  关联模型之后是否可以删除
        //TODO  删除之后，此任务对应模型产生的数据，是否还存在
		param.setEnable(0);
		return super.update(modelMap, param);
	}

    @PostMapping(value = "/task/progress")
    @ApiOperation(value = "任务进度改变")
//	@RequiresPermissions(".update")
    public Object progress(ModelMap modelMap, @RequestBody DsTask param) {
        Assert.notNull(param.getId(),"ID");
        Assert.notNull(param.getProgressBar(),"progressBar");
        param.setCreateBy(getCurrUser());
        Parameter parameter = new Parameter(getService(), "progress").setModel(param);
        logger.info("{} execute progress...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute progress end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
    }

    @PostMapping(value = "/project_stage_task/relate")
    @ApiOperation(value = "阶段关联任务")
//	@RequiresPermissions(".update")
    public Object relate(ModelMap modelMap, @RequestBody Map<String,Object> param) {
        Assert.notNull(param.get("stageId"),"stageId");
        Assert.notNull(param.get("taskId"),"taskId");
        param.put("userId",getCurrUser());
        Parameter parameter = new Parameter("dsProjectStageTaskService", "relate").setMap(param);
        logger.info("{} dsProjectStageTaskService execute relate start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} dsProjectStageTaskService execute relate end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
    }

    @PostMapping(value = "/select_models/by_task")
    @ApiOperation(value = "查看任务关联的模型")
//	@RequiresPermissions(".update")
    public Object selectModelsByTask(ModelMap modelMap, @RequestBody Map<String,Object> param) {
        Assert.notNull(param.get("taskId"),"taskId");
        Parameter parameter = new Parameter("dsTaskModelService", "selectModelsByTask").setMap(param);
        logger.info("{} dsTaskModelService execute selectModelsByTask start...", parameter.getNo());
        List<?> list = provider.execute(parameter).getList();
        logger.info("{} dsTaskModelService execute selectModelsByTask end.", parameter.getNo());
        return setSuccessModelMap(modelMap,list);
    }
}