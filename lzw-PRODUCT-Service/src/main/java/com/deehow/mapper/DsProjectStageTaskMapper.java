package com.deehow.mapper;

import com.deehow.model.DsProject;
import com.deehow.model.DsProjectStageTask;
import com.deehow.core.base.BaseMapper;
import com.deehow.model.DsStage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
public interface DsProjectStageTaskMapper extends BaseMapper<DsProjectStageTask> {

    List<DsStage> selectStageProgressBarByTaskId(@Param("taskId") Long taskId);

    List<DsProject> selectProjectProgressBarByTaskId(@Param("taskId") Long taskId);

    List<DsStage> selectStageProgressBarByProjectIdAndStageId(@Param("projectId") Long projectId,@Param("stageId") Long stageId);

    List<DsProject> selectProjectProgressBarByProjectId(@Param("projectId") Long projectId);

    List<DsProjectStageTask> selectTree(@Param("cm") Map<String, Object> params);
}