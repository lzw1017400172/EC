package com.deehow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.deehow.core.base.BaseService;
import com.deehow.model.SysDept;
import com.deehow.model.SysUser;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@Service
@CacheConfig(cacheNames = "sysDept")
public class SysDeptService extends BaseService<SysDept> {

	@Autowired
	private SysUserService sysUserService;
	
	public List<Long> getDeptIds(Map<String,Object> param){
		return  mapper.selectIdPage(param);
	}
	
	/**
	 * 根据deptIds获取负责人集合信息
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getUsers(Map<String, Object> param) {
		String[] deptIds = param.get("deptIds").toString().split(",");
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int index = 0; index < deptIds.length; index++) {
			long id = Long.valueOf(deptIds[index]);
			executorService.execute(new Runnable() {
				public void run() {
					SysDept dept = queryById(id);
					SysUser user = sysUserService.queryById(dept.getLeadingOfficial());
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("userName", user.getUserName());
					paramMap.put("userId", user.getId().toString());
					userList.add(paramMap);				}
			});
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			logger.error("awaitTermination", "", e);
		}
		return userList;
	}
}
