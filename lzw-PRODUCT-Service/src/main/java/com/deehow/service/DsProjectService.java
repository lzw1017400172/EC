package com.deehow.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.DataUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.mapper.DsProjectMapper;
import com.deehow.mapper.DsProjectStageTaskMapper;
import com.deehow.mapper.DsProjectUserMapper;
import com.deehow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * cms-協同设计项目 服务实现类
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Service
public class DsProjectService extends BaseService<DsProject> {

    @Autowired
    private DsProjectMapper dsProjectMapper;

    @Autowired
    private DsProjectUserMapper dsProjectUserMapper;

    @Autowired
    private DsProjectTypeService dsProjectTypeService;

    @Autowired
    private DsProjectStageTaskMapper dsProjectStageTaskMapper;

    @Autowired
    private DsStageService dsStageService;

    @Autowired
    private DsTaskService dsTaskService;

    public int getProjectSize(DsProject record){
        Wrapper<DsProject> wrapper = new EntityWrapper<>(record);
        return dsProjectMapper.selectCount(wrapper);
    }

    @Transactional
    public void updateProjectAndUser(DsProject record){

        if(record.getId() != null && record.getParticipants() != null){
            dsProjectUserMapper.deleteByMap(InstanceUtil.newHashMap("project_id",record.getId()));
        }

        List<Long> participants = record.getParticipants();
        record.setParticipants(null);
        if(record.getId() == null){
            record.setProgressBar(0);
            record.setState(1);
        } else {
            record.setProgressBar(null);
            record.setProjectTypeId(null);
        }
        update(record);
        if(participants != null && participants.size() > 0){
            List<DsProjectUser> users = new ArrayList<>();
            for(Long uid:participants){
                DsProjectUser dsProjectUser = new DsProjectUser();
                dsProjectUser.setEnable(1);
                dsProjectUser.setProjectId(record.getId());
                dsProjectUser.setUserId(uid);
                dsProjectUser.setCreateTime(record.getUpdateTime());
                dsProjectUser.setCreateBy(record.getUpdateBy());
                dsProjectUser.setUpdateBy(record.getUpdateBy());
                users.add(dsProjectUser);
            }
            dsProjectUserMapper.insertAll(users);
        }
    }


    /** 分页查询 */
    public static Page<DsProject> getModelPage(Map<String, Object> params) {
        Integer current = 1;
        Integer size = 10;
        String orderBy = "dp.id_";
        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
            current = Integer.valueOf(params.get("pageNum").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageIndex"))) {
            current = Integer.valueOf(params.get("pageIndex").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
            size = Integer.valueOf(params.get("pageSize").toString());
        }
        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }
        if (size == -1) {
            Page<DsProject> page = new Page<DsProject>();
            page.setOrderByField(orderBy);
            page.setAsc(false);
            return page;
        }
        Page<DsProject> page = new Page<DsProject>(current, size, orderBy);
        page.setAsc(false);
        return page;
    }

    /** 根据Id查询(默认类型T) */
    public Page<DsProject> getModelPage(Page<DsProject> ids) {
        if (ids != null) {
            Page<DsProject> page = new Page<DsProject>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<DsProject> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    public void run() {
                        records.set(index, queryByModel(ids.getRecords().get(index)));
                    }
                });
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                logger.error("awaitTermination", "", e);
            }
            page.setRecords(records);
            return page;
        }
        return new Page<DsProject>();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public DsProject queryByModel(DsProject model) {
        String key = getCacheKey(model.getId());
        DsProject record = null;
        try {
            record = (DsProject) CacheUtil.getCache().get(key);
        } catch (Exception e) {
            logger.error(Constants.Exception_Head, e);
        }
        if (record == null) {
            String lockKey = getLockKey(model.getId());
            if (CacheUtil.getLock(lockKey)) {
                try {
                    record = mapper.selectById(model.getId());
                    try {
                        CacheUtil.getCache().set(key, record);
                    } catch (Exception e) {
                        logger.error(Constants.Exception_Head, e);
                    }
                } finally {
                    CacheUtil.unlock(lockKey);
                }
            } else {
                logger.debug(getClass().getSimpleName() + ":" + model.getId() + " retry queryByModel.");
                sleep(20);
                return queryByModel(model);
            }
        }
        if(record != null){
            record.setParticipants(model.getParticipants());
            DsProjectType projectType = dsProjectTypeService.queryById(record.getProjectTypeId());
            record.setProjectTypeName(projectType == null ? "" : projectType.getName());
        }
        return record;
    }

    /** 分页 */
    public Page<DsProject> queryModelAndUser(Map<String, Object> params) {

        Integer count = dsProjectMapper.selectCountByPageCondition(params);
        Page<DsProject> page = getModelPage(params);
        page.setTotal(count);
        page.getPages();
        params.put("offset",page.getOffset());
        params.put("limit",page.getLimit());
        page.setRecords(dsProjectMapper.selectModelPage(params));
        return  getModelPage(page);
    }


    public List<DsStage> getDetail(Long projectId){
        List<DsProjectStageTask> dtos = dsProjectStageTaskMapper.selectTree(InstanceUtil.newHashMap("projectId",projectId));
        if(dtos.size() > 0){
            List<Long> stageIdSet = new ArrayList<>();
            Set<Long> taskIdSet = new HashSet<>();
            for(DsProjectStageTask dto:dtos){
                if(dto.getTaskId() == 0){
                    stageIdSet.add(dto.getStageId());
                } else {
                    taskIdSet.add(dto.getTaskId());
                }
            }
            List<DsStage> stages = dsStageService.getList(stageIdSet);
            List<DsTask> tasks = dsTaskService.getList(InstanceUtil.newArrayList(taskIdSet));

            for(DsStage ds:stages){
                if(ds != null){
                    List<DsTask> taskList = new ArrayList<>();
                    for(DsProjectStageTask dto:dtos){
                        if(ds.getId().longValue() == dto.getStageId().longValue()){
                            if(dto.getTaskId() == 0){
                                ds.setPstId(dto.getId());
                                ds.setSort(dto.getSort());
                            } else {
                                DsTask dt = getTask(dto.getTaskId(),tasks);
                                if(dt != null){
                                    dt.setPstId(dto.getId());
                                    dt.setSort(dto.getSort());
                                    taskList.add(dt);
                                }
                            }
                        }
                    }
                    ds.setTaskList(taskList);
                }
            }
            return stages;
        } else {
            return new ArrayList<>();
        }
    }

    private DsTask getTask(Long taskId,List<DsTask> tasks){
        DsTask targetTask = null;
        for (DsTask ta : tasks) {
            if (ta != null && ta.getId().longValue() == taskId.longValue()) {
                targetTask = ta;
                break;
            }
        }
        return targetTask;
    }

}

