package com.deehow.service;

import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.mapper.DsProjectStageTaskMapper;
import com.deehow.mapper.DsTaskModelMapper;
import com.deehow.mapper.DsTaskUserMapper;
import com.deehow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * cms-協同设计任务 服务实现类
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Service
public class DsTaskService extends BaseService<DsTask> {


    @Autowired
    private DsTaskUserMapper dsTaskUserMapper;

    @Autowired
    private DsProjectStageTaskMapper dsProjectStageTaskMapper;

    @Autowired
    private DsStageService dsStageService;

    @Autowired
    private DsProjectService dsProjectService;

    @Autowired
    private DsTaskModelMapper dsTaskModelMapper;

    @Transactional
    public void updateTask(DsTask record){
        if(record.getId() == null){
            insertTask(record);
        } else {
            List<Long> participants = record.getParticipants();
            List<Long> models = record.getModels();
            record.setParticipants(null);
            record.setProgressBar(null);
            update(record);
            if(participants != null){
                dsTaskUserMapper.deleteByMap(InstanceUtil.newHashMap("task_id",record.getId()));
                if(participants.size() > 0){
                    handlerTaskUser(participants,record.getId(),record.getUpdateBy());
                }
            }

            if(models != null){
                dsTaskModelMapper.deleteByMap(InstanceUtil.newHashMap("task_id",record.getId()));
                if(models.size() > 0){
                    handlerTaskModel(models,record.getId());
                }
            }
        }
    }

    /**
     * 新增任务 and 参与人
     * @param record
     */
    @Transactional
    public void insertTask(DsTask record){
        List<Long> participants = record.getParticipants();
        List<Long> models = record.getModels();
        record.setId(null);
        record.setProgressBar(0);
        record.setEnable(1);
        record.setParticipants(null);
        record.setModels(null);
        super.update(record);
        handlerTaskUser(participants,record.getId(),record.getCreateBy());
        if(record.getType() == 2){
            handlerTaskModel(models,record.getId());
        }
    }

    /**
     * 新建task_user
     * @param participants      任务参与人ids
     * @param taskId
     * @param userId
     */
    private void handlerTaskUser(List<Long> participants, Long taskId, Long userId){
        if(participants != null && participants.size() > 0){
            List<DsTaskUser> users = new ArrayList<>();
            for(Long uid:participants){
                DsTaskUser dsTaskUser = new DsTaskUser();
                dsTaskUser.setEnable(1);
                dsTaskUser.setTaskId(taskId);
                dsTaskUser.setUserId(uid);
                dsTaskUser.setCreateBy(userId);
                dsTaskUser.setUpdateBy(userId);
                users.add(dsTaskUser);
            }
            dsTaskUserMapper.insertAll(users);
        }
    }

    /**
     * 新建task_model
     * @param models      模型ids
     * @param taskId
     */
    private void handlerTaskModel(List<Long> models, Long taskId){
        if(models != null && models.size() > 0){
            List<DsTaskModel> taskModels = new ArrayList<>();
            for(Long mid:models){
                DsTaskModel dsTaskModel = new DsTaskModel();
                dsTaskModel.setTaskId(taskId);
                dsTaskModel.setModelId(mid);
                taskModels.add(dsTaskModel);
            }
            dsTaskModelMapper.insertAll(taskModels);
        }
    }

    /**
     * 任务进度改变
     * @param record
     */
    @Transactional
    public void progress(DsTask record){
        if(record.getId() == null || record.getProgressBar() == null){
            return;
        }
        int progressBar = record.getProgressBar();
        if(progressBar < 0){
            record.setProgressBar(0);
        }
        if(progressBar > 100){
            record.setProgressBar(100);
        }

        /** 更新task进度 */
        DsTask upModel = new DsTask();
        upModel.setId(record.getId());
        upModel.setProgressBar(record.getProgressBar());
        update(upModel);
        /** 更新stage进度 */
        int stageUpSize = updateStageProgressBarByTask(record.getId());
        /** 更新project进度 */
        if(stageUpSize > 0) {
            updateProjectProgressBarByTask(record.getId());
        }
    }

    public int updateStageProgressBarByTask(Long taskId){
        String lockKey = getLockKey("U_STAGE_PROGRESS_BAR");
        if (CacheUtil.getLock(lockKey)) {
            try {
                List<DsStage> stages = dsProjectStageTaskMapper.selectStageProgressBarByTaskId(taskId);
                if(stages.size() > 0){
                    dsStageService.updateAll(stages);
                }
                return stages.size();
            } finally {
                CacheUtil.unlock(lockKey);
            }
        } else {
            sleep(20);
            return updateStageProgressBarByTask(taskId);
        }
    }

    public void updateProjectProgressBarByTask(Long taskId){
        String lockKey = getLockKey("U_PROJECT_PROGRESS_BAR");
        if (CacheUtil.getLock(lockKey)) {
            try {
                List<DsProject> projects = dsProjectStageTaskMapper.selectProjectProgressBarByTaskId(taskId);
                if(projects.size() > 0){
                    dsProjectService.updateAll(projects);
                }
            } finally {
                CacheUtil.unlock(lockKey);
            }
        } else {
            sleep(20);
            updateProjectProgressBarByTask(taskId);
        }
    }


    public int updateStageProgressBarByProjectIdAndStageId(Long projectId,Long stageId){
        String lockKey = getLockKey("U_STAGE_PROGRESS_BAR");
        if (CacheUtil.getLock(lockKey)) {
            try {
                List<DsStage> stages = dsProjectStageTaskMapper.selectStageProgressBarByProjectIdAndStageId(projectId,stageId);
                if(stages.size() > 0){
                    dsStageService.updateAll(stages);
                }
                return stages.size();
            } finally {
                CacheUtil.unlock(lockKey);
            }
        } else {
            sleep(20);
            return updateStageProgressBarByProjectIdAndStageId(projectId,stageId);
        }
    }

    public void updateProjectProgressBarByProjectId(Long projectId){
        String lockKey = getLockKey("U_PROJECT_PROGRESS_BAR");
        if (CacheUtil.getLock(lockKey)) {
            try {
                List<DsProject> projects = dsProjectStageTaskMapper.selectProjectProgressBarByProjectId(projectId);
                if(projects.size() > 0){
                    dsProjectService.updateAll(projects);
                }
            } finally {
                CacheUtil.unlock(lockKey);
            }
        } else {
            sleep(20);
            updateProjectProgressBarByProjectId(projectId);
        }
    }
}
