package com.deehow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysMidMvDTO;
import com.deehow.model.SysTenantModel;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
public interface SysTenantModelMapper extends BaseMapper<SysTenantModel> {

//	List<Long> readIdPage(@Param("cm") Map<String, Object> param);
//
//	List<Long> queryListByModelType(@Param("cm") Map<String, Object> param);
//	
	

	
	
	List<SysMidMvDTO> queryMidMvByTenantId(@Param("cm") Map<String, Object> param);
	
}