package com.deehow.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.ExceptionUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.mapper.DsProjectStageTaskMapper;
import com.deehow.model.DsProject;
import com.deehow.model.DsProjectStageTask;
import com.deehow.model.DsStage;
import com.deehow.model.DsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * cms-協同设计阶段任务-任务关联 服务实现类
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Service
public class DsProjectStageTaskService   {

    @Autowired
    private DsProjectStageTaskMapper dsProjectStageTaskMapper;

    @Autowired
    private DsStageService dsStageService;

    @Autowired
    private DsTaskService dsTaskService;

    /**
     * 求数量
     * @param record
     * @return
     */
    public int getProjectStageTaskSize(DsProjectStageTask record){
        Wrapper<DsProjectStageTask> wrapper = new EntityWrapper<>(record);
        return dsProjectStageTaskMapper.selectCount(wrapper);
    }

    /**
     * 查询数据
     * @param record
     * @return
     */
    public List<DsProjectStageTask> getProjectStageTaskModel(DsProjectStageTask record){
        Wrapper<DsProjectStageTask> wrapper = new EntityWrapper<>(record);
        return dsProjectStageTaskMapper.selectList(wrapper);
    }

    /**
     * 删除
     * @param record
     */
    public void deleteProjectStageTaskModel(DsProjectStageTask record){
        Wrapper<DsProjectStageTask> wrapper = new EntityWrapper<>(record);
        dsProjectStageTaskMapper.delete(wrapper);
    }


    /**
     *  阶段关联任务
     * @param param
     */
    @Transactional
    public void relate(Map<String,Object> param){
        if(param.get("stageId") == null || param.get("taskId") == null){
            return;
        }
        Long stageId = Long.valueOf(param.get("stageId").toString());
        Long taskId = Long.valueOf(param.get("taskId").toString());
        Long userId = Long.valueOf(param.get("userId").toString());
        Integer sort = param.get("userId") == null ? 0 : Integer.valueOf(param.get("userId").toString());
        DsProjectStageTask pst = new DsProjectStageTask();
        pst.setStageId(stageId);
        pst.setTaskId(0l);
        List<DsProjectStageTask> psts = getProjectStageTaskModel(pst);
        if(psts.size() > 0){
            DsProjectStageTask insertPst = new DsProjectStageTask();
            insertPst.setProjectId(psts.get(0).getProjectId());
            insertPst.setStageId(stageId);
            insertPst.setTaskId(taskId);
            insertPst.setSort(sort);
            insertPst.setEnable(1);
            insertPst.setCreateTime(new Date());
            insertPst.setUpdateTime(new Date());
            insertPst.setCreateBy(userId);
            insertPst.setUpdateBy(userId);
            try {
                dsProjectStageTaskMapper.insert(insertPst);
            } catch (DuplicateKeyException e) {
                throw new RuntimeException("已经存在相同的配置.");
            } catch (Exception e) {
                throw new RuntimeException(ExceptionUtil.getStackTraceAsString(e));
            }

            int stageUpSize = dsTaskService.updateStageProgressBarByProjectIdAndStageId(psts.get(0).getProjectId(),stageId);
            if(stageUpSize > 0) {
                dsTaskService.updateProjectProgressBarByProjectId(psts.get(0).getProjectId());
            }
        }
    }

    @Transactional
    public void updatSortAll(List<DsProjectStageTask> params){
        List<DsProjectStageTask> upList = new ArrayList<>();
        for(DsProjectStageTask pst:params){
            if(pst.getId() != null && pst.getSort() != null){
                DsProjectStageTask upModel = new DsProjectStageTask();
                upModel.setId(pst.getId());
                upModel.setSort(pst.getSort());
                dsProjectStageTaskMapper.updateById(upModel);
            }
        }
    }

}
