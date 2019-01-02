package com.deehow.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deehow.core.Constants;
import com.deehow.core.base.Parameter;
import com.deehow.core.util.CacheUtil;
import com.deehow.model.SysMidMvDTO;
import com.deehow.provider.ICmsProvider;

public class ModelMenuUtils {
	
	public final static String TENANT_MODEL_KEY = Constants.CACHE_NAMESPACE + "TENANT_MODEL:";
	public final static String USER_MENU_KEY = Constants.CACHE_NAMESPACE + "USER_MENU:";

	
	//TODO 只要改变 sysTenantModel表，就要更新这个
	//TODO	1	购买了模块/续费了模块
	//TODO	2	添加了新的模块
	
    /**
     * 租户拥有的model放入缓存
     * 包含过期和未过期的
     */
	public static List<SysMidMvDTO> setTenantModelCache(Long tenantId,ICmsProvider provider){
		if(null == tenantId){
			return null;
		}
		Map<String,Object> map = new HashMap<>();
		map.put("tenantId",tenantId);
		Parameter parameter = new Parameter("sysTenantModelService","queryMidMvByTenantId").setMap(map);
		List<SysMidMvDTO> midMvDTOList = (List<SysMidMvDTO>) provider.execute(parameter).getList();
		if(midMvDTOList.size() > 0){
			String cacheKey = TENANT_MODEL_KEY + tenantId;
			try{
				CacheUtil.getCache().set(cacheKey,CacheUtil.getRedisHelper().serialize(midMvDTOList),60*60*24*5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return midMvDTOList;
	}
	
	/**
	 * 获取缓存中对应租户拥有的模块
	 * @param tenantId
	 */
	public static List<SysMidMvDTO> getTenantModelCache(Long tenantId,ICmsProvider provider){
		if(null == tenantId){
			return new ArrayList<>();
		}
		String cacheKey = TENANT_MODEL_KEY + tenantId;
		List<SysMidMvDTO> midMvDTOList = null;
		try{
			Object cache = CacheUtil.getCache().getFinal(cacheKey);			
			if(null != cache){
				// 重置有效期 ,60*60*24*5·
				CacheUtil.getCache().expire(cacheKey,60*60*24*5);
				midMvDTOList = (List<SysMidMvDTO>)CacheUtil.getRedisHelper().deserialize(cache.toString());
			} else {
				midMvDTOList = setTenantModelCache(tenantId,provider);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return midMvDTOList != null ? midMvDTOList : new ArrayList<>();
	}
	
	/**
	 * 	//模块model的命中率很高，得到当前租户的modelIds直接取缓存中获取model。
	 * 获取未到期的模块
	 * @param tenantId
	 * @param provider
	 */
	public static List<Long> getTenantModelUndueCache(Long tenantId,ICmsProvider provider){
		List<SysMidMvDTO> midMvDTOList = getTenantModelCache(tenantId,provider);
		List<Long> modelIds = new ArrayList<>();
		Date date = new Date();
		for(SysMidMvDTO dto:midMvDTOList){
			if(dto.getModelValidate() != null && date.before(dto.getModelValidate()) && dto.getEnable() == 1){
				modelIds.add(dto.getModelId());
			}
		}
		return modelIds;
	}
	
}
	
	
