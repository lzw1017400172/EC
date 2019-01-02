package com.deehow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deehow.core.base.BaseService;
import com.deehow.mapper.SysModelMenuMapper;
import com.deehow.model.SysModelMenu;

/**
 * <p>
 * 系统模块菜单表 服务实现类
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
@Service
public class SysModelMenuService extends BaseService<SysModelMenu>{
	@Autowired
	protected SysModelMenuMapper sysModelMenumapper;
	
	public List<SysModelMenu> queryListByModelIds(List<Long> list) {
		List<Long> ids = sysModelMenumapper.selectIdByModelIds(list);
		List<SysModelMenu> sysModelMenus = getList(ids);
		return sysModelMenus;
	}
	
//	public List<SysModelMenu> queryListByMap(Map<Object,Object> map) {
//		List<Long> ids = sysModelMenumapper.selectIdByMap(map);
//		List<SysModelMenu> sysModelMenus = getList(ids);
//		return sysModelMenus;
//	}
//	
//	/**
//	 * 查询出用户可以分配及未分配的菜单
//	 * @param map
//	 * @return
//	 */
//	public List<SysModelMenu> selectMenuByModelIdsAndUserId(Map<Object,Object> map) {
//		List<SysModelMenu> sysModelMenus = sysModelMenumapper.selectMenuByModelIdsAndUserId(map);
//		return sysModelMenus;
//	}
//	/**
//	 * 查询角色可以分配及未分配的菜单
//	 */
//	public List<SysModelMenu> selectMenuByRole(Map<Object,Object> map) {
//		List<SysModelMenu> sysModelMenus = sysModelMenumapper.selectMenuByRole(map);
//		return sysModelMenus;
//	}
}
