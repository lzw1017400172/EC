package com.deehow.service;

import com.deehow.model.SysModel;
import com.deehow.model.Preferential;
import com.alibaba.druid.util.StringUtils;
import com.deehow.core.base.BaseService;
import com.deehow.mapper.SysModelMapper;
import com.deehow.mapper.PreferentialMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  优惠方案--服务实现类
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@Service
public class PreferentialService extends BaseService<Preferential>{
	@Autowired
	private PreferentialMapper preferentialMapper;
	@Autowired
	private SysModelMapper sysModelMapper;
	//查询所有的优惠方案以及优惠方案下的模块信息
	public List<Preferential> queryAllPreferentialAndModel(Map<String,Object> paramMap){
		paramMap=new HashMap<String,Object>();
		List<Preferential> sList= super.queryList(paramMap);
		//查询优惠方案下的模块集合
		for(int i=0;i<sList.size();i++){
			Preferential sysPreferential=sList.get(i);
			String modelId=sysPreferential.getModelId();
			if(!StringUtils.isEmpty(modelId)){
				Map<String,Object> pMap=new HashMap<String,Object>();
				pMap.put("ids", modelId);
				List<SysModel> mList=sysModelMapper.selectModelByIds(pMap);
				sysPreferential.setModelList(mList);
			}
		}
		return sList;
	}
	//根据优惠方案id查询优惠方案下的模块信息
	public List<SysModel> queryModelByPreferentialId(Map<String,Object> paramMap){
		Preferential sysPreferential=super.queryById(((Integer) paramMap.get("id")).longValue());
		String modelId=sysPreferential.getModelId();
		List<SysModel> mList=new ArrayList<SysModel>();
		if(!StringUtils.isEmpty(modelId)){
			Map<String,Object> pMap=new HashMap<String,Object>();
			pMap.put("ids", modelId);
			mList=sysModelMapper.selectModelByIds(pMap);
		}
		return mList;
	}
	//优惠方案添加模块信息，本质是修改优惠方案表的modelId
	public void insertPreferentialModel(Map<String,Object> paramMap){
		Preferential sysPreferential=super.queryById(((Integer)paramMap.get("id")).longValue());
		sysPreferential.setModelId((String) paramMap.get("modelId"));//模块id
		sysPreferential.setUpdateBy((Long) paramMap.get("userId"));//当前登录人id
		sysPreferential.setUpdateTime(new Date());
		super.update(sysPreferential);
	}
}
