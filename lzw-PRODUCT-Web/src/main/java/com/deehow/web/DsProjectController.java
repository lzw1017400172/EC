package com.deehow.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.InstanceUtil;
import com.deehow.model.DsProject;
import com.deehow.model.DsProjectStageTask;
import com.deehow.model.DsStage;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;
import com.deehow.provider.IEdmProvider;
import io.netty.util.collection.LongObjectHashMap;
import io.netty.util.collection.LongObjectMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * <p>
 * cms-協同设计项目  前端控制器
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Controller
@RequestMapping("/project")
@Api(value = "cms-協同设计项目", description = "cms-協同设计项目")
public class DsProjectController extends AbstractController<IEdmProvider> {
	public String getService() {
		return "dsProjectService";
	}

    @Autowired
    @Qualifier("cmsProvider")
    private ICmsProvider cmsProvider;

//	@ApiOperation(value = "查询cms-協同设计项目")
//	@RequiresPermissions(".read")
//	@PostMapping(value = "/read/list")
//	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
//		return super.queryList(modelMap, param);
//	}

    @ApiOperation(value = "查询cms-協同设计项目")
	@PostMapping(value = "/get/detail")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
        Assert.notNull(param.get("projectId"),"projectId");
        Parameter parameter = new Parameter(getService(), "getDetail").setId(Long.valueOf(param.get("projectId").toString()));
        List<DsStage> list = (List<DsStage>) provider.execute(parameter).getList();
        if(list.size() > 0){
            Set<Long> uidSet = new HashSet<>();
            for(DsStage stage:list){
                uidSet.add(stage.getLeader());
            }

            parameter = new Parameter("sysUserService", "getList").setList(InstanceUtil.newArrayList(uidSet));
            List<SysUser> users = (List<SysUser>) cmsProvider.execute(parameter).getList();
            LongObjectMap<String> uMap = new LongObjectHashMap<>();
            for(SysUser user:users){
                if(user != null){
                    uMap.put(user.getId(),user.getUserName());
                }
            }
            for(DsStage stage:list){
                stage.setLeaderName(uMap.get(stage.getLeader()));
//                    StringBuffer  participantNames = new StringBuffer();
//                    for(Long uid:project.getParticipants()){
//                        participantNames.append(uMap.get(uid)).append(",");
//                    }
//                    project.setParticipantNames(participantNames.length() > 0 ? participantNames.substring(0,
//                            participantNames.length()-1):participantNames.toString());
            }
        }

        return setSuccessModelMap(modelMap, list);
	}


	@ApiOperation(value = "分页cms-協同设计项目")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/get_basic_data/pagination")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
        Parameter parameter = new Parameter(getService(), "queryModelAndUser").setMap(param);
        logger.info("{} execute queryModelAndUser start...", parameter.getNo());
        Page<DsProject> list = (Page<DsProject>) provider.execute(parameter).getPage();
        logger.info("{} execute queryModelAndUser end.", parameter.getNo());
        if(list.getRecords().size() > 0){
            Set<Long> usetIds = new HashSet<>();
            for(DsProject project:list.getRecords()){
                if(project != null){
                    usetIds.add(project.getLeader());
                    usetIds.addAll(project.getParticipants());
                }
            }
            parameter = new Parameter("sysUserService", "getList").setList(InstanceUtil.newArrayList(usetIds));
            List<SysUser> users = (List<SysUser>) cmsProvider.execute(parameter).getList();
            LongObjectMap<String> uMap = new LongObjectHashMap<>();
            for(SysUser user:users){
                if(user != null){
                    uMap.put(user.getId(),user.getUserName());
                }
            }

            for(DsProject project:list.getRecords()){
                if(project != null){
                    project.setLeaderName(uMap.get(project.getLeader()));
                    StringBuffer  participantNames = new StringBuffer();
                    List<String> pStrs = new ArrayList<>();
                    for(Long uid:project.getParticipants()){
                        participantNames.append(uMap.get(uid)).append(",");
                        pStrs.add(uid.toString());
                    }
                    project.setParticipantsStr(pStrs);
                    project.setParticipantNames(participantNames.length() > 0 ? participantNames.substring(0,
                            participantNames.length()-1):participantNames.toString());

                }
            }
        }

        return setSuccessModelMap(modelMap, list);
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改cms-協同设计项目")
//	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody DsProject param) {
		Long userId = getCurrUser();
		if(param.getId() == null){
			Assert.isNotBlank(param.getName(),"name");
			Assert.notNull(param.getLeader(),"leader");
			Assert.notNull(param.getProjectTypeId(),"projectTypeId");
			Assert.notNull(param.getStartTime(),"startTime");
			Assert.notNull(param.getEndTime(),"endTime");

			param.setEnable(1);
			param.setCreateTime(new Date());
			param.setCreateBy(userId);
		}
		param.setUpdateBy(userId);
		Parameter parameter = new Parameter(getService(), "updateProjectAndUser").setModel(param);
		logger.info("{} execute updateProjectAndUser start...", parameter.getNo());
		provider.execute(parameter);
		logger.info("{} execute updateProjectAndUser end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除cms-協同设计项目")
//	@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody DsProject param) {
        Assert.notNull(param.getId(),"ID");
        DsProjectStageTask dpst = new DsProjectStageTask();
        dpst.setProjectId(param.getId());
        Parameter parameter = new Parameter("dsProjectStageTaskService", "getProjectStageTaskSize").setModel(dpst);
        int result = (int)provider.execute(parameter).getResult();
        if(result > 0){
            modelMap.put("msg","数据已经被使用，不能删除。");
            return setModelMap(modelMap, HttpCode.CONFLICT);
        } else {
            return super.delete(modelMap, param);
        }
	}
}