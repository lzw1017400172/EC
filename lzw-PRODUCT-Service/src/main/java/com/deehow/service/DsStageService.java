package com.deehow.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.InstanceUtil;
import com.deehow.mapper.DsProjectStageTaskMapper;
import com.deehow.model.DsProjectStageTask;
import com.deehow.model.DsStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * cms-協同设计阶段 服务实现类
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Service
public class DsStageService extends BaseService<DsStage> {

    @Autowired
    private DsProjectStageTaskMapper dsProjectStageTaskMapper;

    @Autowired
    private DsProjectStageTaskService dsProjectStageTaskService;

    @Transactional
    public void insertStage(DsStage record){
        Long projectId = record.getProjectId();
        if(projectId == null){
            return;
        }
        record.setProgressBar(0);
        record.setId(null);
        record.setProjectId(null);
        update(record);
        DsProjectStageTask pst = new DsProjectStageTask();
        pst.setProjectId(projectId);
        pst.setStageId(record.getId());
        pst.setTaskId(0l);
        pst.setSort(record.getSort() == null ? 0 : record.getSort());
        pst.setEnable(1);
        pst.setCreateBy(record.getCreateBy());
        pst.setUpdateBy(record.getCreateBy());
        pst.setCreateTime(record.getCreateTime());
        pst.setUpdateTime(record.getCreateTime());
        dsProjectStageTaskMapper.insert(pst);
    }

    @Transactional
    public void updateStage(DsStage record){
        if(record.getId() == null){
            insertStage(record);
        } else {
            record.setSort(null);
            record.setProgressBar(null);
            update(record);
        }
    }

    @Transactional
    public void deleteStage(DsStage record){
        if(record.getId() == null || record.getProjectId() == null){
            return;
        }
        DsProjectStageTask dsProjectStageTask = new DsProjectStageTask();
        dsProjectStageTask.setProjectId(record.getProjectId());
        dsProjectStageTask.setStageId(record.getId());
        dsProjectStageTask.setTaskId(0l);
        dsProjectStageTaskService.deleteProjectStageTaskModel(dsProjectStageTask);

        delete(record.getId());
    }
}
