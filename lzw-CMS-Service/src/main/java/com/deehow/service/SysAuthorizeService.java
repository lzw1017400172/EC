package com.deehow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deehow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.mapper.SysAuthorizeMapper;
import com.deehow.mapper.SysRoleMenuMapper;
import com.deehow.mapper.SysUserMenuMapper;
import com.deehow.mapper.SysUserRoleMapper;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@Service
@CacheConfig(cacheNames = "sysAuthorize")
public class SysAuthorizeService extends BaseService<SysMenu> {
    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAuthorizeMapper sysAuthorizeMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysDicService sysDicService;
    @Autowired
    private SysModelMenuService sysModelMenuService;
	@Autowired
	private SysCacheService sysCacheService;
	@Autowired
	private SysUserService sysUserService;
    
    
//    @Transactional
//    @CacheEvict(value = {Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
//        Constants.CACHE_NAMESPACE + "userPermission"}, allEntries = true)
//    public void updateUserMenu(List<SysUserMenu> sysUserMenus) {
//        Long userId = null;
//        for (SysUserMenu sysUserMenu : sysUserMenus) {
//            if (sysUserMenu.getUserId() != null && "read".equals(sysUserMenu.getPermission())) {
//                userId = sysUserMenu.getUserId();
//            }
//        }
//        if (userId != null) {
//            sysAuthorizeMapper.deleteUserMenu(userId, "read");
//        }
//        for (SysUserMenu sysUserMenu : sysUserMenus) {
//            if (sysUserMenu.getUserId() != null && sysUserMenu.getMenuId() != null
//                && "read".equals(sysUserMenu.getPermission())) {
//                sysUserMenuMapper.insert(sysUserMenu);
//            }
//        }
//    }

//    @Transactional
//    @CacheEvict(value = {Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
//        Constants.CACHE_NAMESPACE + "userPermission"}, allEntries = true)
//    public void updateUserPermission(List<SysUserMenu> sysUserMenus) {
//        Long userId = null;
//        for (SysUserMenu sysUserMenu : sysUserMenus) {
//            if (sysUserMenu.getUserId() != null) {
//                userId = sysUserMenu.getUserId();
//                break;
//            }
//        }
//        if (userId != null) {
//            Map<String, Object> dicParam = InstanceUtil.newHashMap();
//            dicParam.put("type", "CRUD");
//            List<SysDic> sysDics = sysDicService.queryList(dicParam);
//            for (SysDic sysDic : sysDics) {
//                if (!"read".equals(sysDic.getCode())) {
//                    sysAuthorizeMapper.deleteUserMenu(userId, sysDic.getCode());
//                }
//            }
//        }
//        for (SysUserMenu sysUserMenu : sysUserMenus) {
//            if (sysUserMenu.getUserId() != null && sysUserMenu.getMenuId() != null
//                && !"read".equals(sysUserMenu.getPermission())) {
//                sysUserMenuMapper.insert(sysUserMenu);
//            }
//        }
//    }

    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysUserRole> userRoles = getRolesByUserId(userId);
        List<Long> roleIds = new ArrayList<>();
        for(SysUserRole userRole:userRoles){
            if(userRole.getRoleId() != null) {
                roleIds.add(userRole.getRoleId());
            }
        }
        return sysRoleService.getList(roleIds);
    }

    public List<SysUserRole> getRolesByUserId(Long userId) {
        SysUserRole sysUserRole = new SysUserRole(userId, null);
        Wrapper<SysUserRole> wrapper = new EntityWrapper<SysUserRole>(sysUserRole);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(wrapper);
        return userRoles;
    }
    
    public List<Long> getUsersByRoleId(Long roleId) {
    	if(roleId == null){
    		return new ArrayList<>();
    	}
    	return sysUserRoleMapper.queryUserIdListByRoleId(roleId);
    }

//    @Transactional
//    @CacheEvict(value = {Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
//        Constants.CACHE_NAMESPACE + "userPermission", Constants.CACHE_NAMESPACE + "rolePermission"}, allEntries = true)
//    public void updateUserRole(List<SysUserRole> sysUserRoles) {
//        Long userId = null;
//        for (SysUserRole sysUserRole : sysUserRoles) {
//            if (sysUserRole.getUserId() != null) {
//                userId = sysUserRole.getUserId();
//                break;
//            }
//        }
//        if (userId != null) {
//            sysAuthorizeMapper.deleteUserRole(userId);
//        }
//        for (SysUserRole sysUserRole : sysUserRoles) {
//            if (sysUserRole.getUserId() != null && sysUserRole.getRoleId() != null) {
//                sysUserRoleMapper.insert(sysUserRole);
//            }
//        }
//    }

    public List<String> queryMenuIdsByRoleId(Long roleId) {
        List<String> resultList = InstanceUtil.newArrayList();
        List<Long> list = sysRoleMenuMapper.queryMenuIdsByRoleId(roleId);
        for (Long id : list) {
            resultList.add(id.toString());
        }
        return resultList;
    }

//    @Transactional
//    @CacheEvict(value = {Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
//        Constants.CACHE_NAMESPACE + "userPermission", Constants.CACHE_NAMESPACE + "rolePermission"}, allEntries = true)
//    public void updateRoleMenu(List<SysRoleMenu> sysRoleMenus) {
//        Long roleId = null;
//        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
//            if (sysRoleMenu.getRoleId() != null && "read".equals(sysRoleMenu.getPermission())) {
//                roleId = sysRoleMenu.getRoleId();
//                break;
//            }
//        }
//        if (roleId != null) {
//            sysAuthorizeMapper.deleteRoleMenu(roleId, "read");
//        }
//        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
//            if (sysRoleMenu.getRoleId() != null && sysRoleMenu.getMenuId() != null
//                && "read".equals(sysRoleMenu.getPermission())) {
//                sysRoleMenuMapper.insert(sysRoleMenu);
//            }
//        }
//    }

//    @Transactional
//    @CacheEvict(value = {Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
//        Constants.CACHE_NAMESPACE + "userPermission", Constants.CACHE_NAMESPACE + "rolePermission"}, allEntries = true)
//    public void updateRolePermission(List<SysRoleMenu> sysRoleMenus) {
//        Long roleId = null;
//        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
//            if (sysRoleMenu.getRoleId() != null) {
//                roleId = sysRoleMenu.getRoleId();
//            }
//        }
//        if (roleId != null) {
//            Map<String, Object> dicParam = InstanceUtil.newHashMap();
//            dicParam.put("type", "CRUD");
//            List<SysDic> sysDics = sysDicService.queryList(dicParam);
//            for (SysDic sysDic : sysDics) {
//                if (!"read".equals(sysDic.getCode())) {
//                    sysAuthorizeMapper.deleteRoleMenu(roleId, sysDic.getCode());
//                }
//            }
//        }
//        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
//            if (sysRoleMenu.getRoleId() != null && sysRoleMenu.getMenuId() != null
//                && !"read".equals(sysRoleMenu.getPermission())) {
//                sysRoleMenuMapper.insert(sysRoleMenu);
//            }
//        }
//    }

//    @Cacheable(value = Constants.CACHE_NAMESPACE + "menuPermission")
//    public List<SysMenu> queryAuthorizeByUserId(Long userId) {
//        List<Long> menuIds = sysAuthorizeMapper.getAuthorize(userId);
//        List<SysMenu> menus = sysMenuService.getList(menuIds);
//        Map<Long, List<SysMenu>> map = InstanceUtil.newHashMap();
//        for (SysMenu sysMenu : menus) {
//            if (map.get(sysMenu.getParentId()) == null) {
//                List<SysMenu> menuBeans = InstanceUtil.newArrayList();
//                map.put(sysMenu.getParentId(), menuBeans);
//            }
//            map.get(sysMenu.getParentId()).add(sysMenu);
//        }
//        List<SysMenu> result = InstanceUtil.newArrayList();
//        for (SysMenu sysMenu : menus) {
//            if (sysMenu.getParentId() == null || sysMenu.getParentId() == 0) {
//                sysMenu.setLeaf(0);
//                sysMenu.setMenuBeans(getChildMenu(map, sysMenu.getId()));
//                result.add(sysMenu);
//            }
//        }
//        return result;
//    }

    // 递归获取子菜单
    private List<SysMenu> getChildMenu(Map<Long, List<SysMenu>> map, Long id) {
        List<SysMenu> menus = map.get(id);
        if (menus != null) {
            for (SysMenu sysMenu : menus) {
                sysMenu.setMenuBeans(getChildMenu(map, sysMenu.getId()));
            }
        }
        return menus;
    }

//    @Cacheable(Constants.CACHE_NAMESPACE + "sysPermission")
//    public List<String> queryPermissionByUserId(Long userId) {
//        return sysAuthorizeMapper.queryPermissionByUserId(userId);
//    }

//    @Cacheable(Constants.CACHE_NAMESPACE + "userPermission")
//    public List<String> queryUserPermission(Long userId) {
//        return sysUserMenuMapper.queryPermission(userId);
//    }

    @Cacheable(Constants.CACHE_NAMESPACE + "rolePermission")
    public List<String> queryRolePermission(Long roleId) {
        return sysRoleMenuMapper.queryPermission(roleId);
    }

    public List<SysMenu> queryMenusPermission() {
        return sysAuthorizeMapper.queryMenusPermission();
    }

    public List<Map<String, Object>> queryUserPermissions(SysUserMenu sysUserMenu) {
        List<Map<String, Object>> list = sysUserMenuMapper.queryPermissions(sysUserMenu.getUserId());
        return list;
    }

    public List<Map<String, Object>> queryRolePermissions(SysRoleMenu sysRoleMenu) {
        List<Map<String, Object>> list = sysRoleMenuMapper.queryPermissions(sysRoleMenu.getRoleId());
        return list;
    }
    
    
    
    
    
    
    
    
    
    
    /**			分割线		分割线		分割线		分割线		分割线		分割线		分割线		分割线		分割线			*/
    
    
    /** 用户菜单缓存key	*/
    private String getMenuRedisUserKey(Long tenantId,Long userId){
    	return new StringBuilder(Constants.CACHE_NAMESPACE).append("userMenuIds:").append(tenantId).append(":").append(userId).toString();
    }
    private String getMenuRedisTenantIdKey(Long tenantId){
    	return new StringBuilder(Constants.CACHE_NAMESPACE).append("userMenuIds:").append(tenantId).append(":*").toString();
    }
    
    /** shiro缓存管理[用户Permission缓存key]	*/
    private String getPermissionRedisUserKey(Long tenantId,Long userId){
    	return new StringBuilder(Constants.CACHE_NAMESPACE).append("shiro_redis_cache:").append(tenantId).append(":").append(userId).toString();
    }
    private String getPermissionRedisTenantIdKey(Long tenantId){
    	return new StringBuilder(Constants.CACHE_NAMESPACE).append("shiro_redis_cache:").append(tenantId).append(":*").toString();
    }
    
    /**
     * 修改 user-role时，更新这个;修改role-menu时更新这个；
     * 获取user菜单
     * @param userId
     * @return
     */
    public List<Long> newQueryUserMenuIds(Long userId) {
    	SysUser user = sysUserService.queryById(userId);
    	List<Long> lList = null;
    	if(user != null){
    		String key = getMenuRedisUserKey(user.getTenantId(),userId);
        	try {
        		Object ob = CacheUtil.getCache().get(key);
        		lList = ob != null ? (List<Long>)ob : null; 
    		} catch (Exception e) {
    			logger.error(Constants.Exception_Head, e);
    		}
    		if(lList == null){
    			lList = sysAuthorizeMapper.getNewMenuByUser(userId);
    			try {
    				CacheUtil.getCache().set(key, (ArrayList)lList);
    			} catch (Exception e) {
    				logger.error(Constants.Exception_Head, e);
    			}
    		}
    	} else {
    		lList = new ArrayList<>();
    	}
        return lList;
    }
    
    /**	目前再shiro里面会用这个	*/
    public List<ModelPermissionDTO> newQueryUserPermissions(Long userId) {
        return sysAuthorizeMapper.getNewPermissionsByUser(userId);
    }
    
    /**	 查询此用户的菜单	*/
    public List<SysModelMenu> queryUserMenus(Long userId){
    	List<Long> ids = newQueryUserMenuIds(userId);
    	return sysModelMenuService.getList(ids);
    }
    
    /**
     * 无论删除，新增，修改	user_role 全走这里
     * @param sysUserRoles
     */
    @Transactional
    public void newUpdateUserRole(OrganizeVO organizeVO) {
    	if(organizeVO.getSysUserRoleList() == null || organizeVO.getTenantId() == null || organizeVO.getUserId() == null){
    		return;
    	}
    	List<SysUserRole> trueList = organizeVO.getSysUserRoleList();
    	Long userId = organizeVO.getUserId();
        Long tenantId = organizeVO.getTenantId();
        
        sysAuthorizeMapper.deleteUserRole(userId);
        if(trueList.size() > 0){
        	sysUserRoleMapper.insertAll(trueList);
        }
        
        //清除 此用户菜单缓存和shi'ro的权限缓存管理
        String menuKey = getMenuRedisUserKey(tenantId,userId);
        String permissionKey = getPermissionRedisUserKey(tenantId,userId);
        try {
        	CacheUtil.getCache().del(menuKey);//清除自定义缓存
        	CacheUtil.getCache().del(permissionKey);//清除shiro自带缓存管理
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
    }
    
    /**
     * 无论删除，新增，修改	role-menu 全走这里
     * 给角色分配菜单	read权限
     * role-menu
     * @param sysRoleMenus
     */
    @Transactional
    public void newUpdateRoleMenu(OrganizeVO organizeVO) {
    	if(organizeVO.getSysRoleMenuList() == null || organizeVO.getTenantId() == null || organizeVO.getRoleId() == null){
    		return;
    	}
        Long roleId = organizeVO.getRoleId();
        Long tenantId = organizeVO.getTenantId();
        List<SysRoleMenu> trueList = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : organizeVO.getSysRoleMenuList()) {
            if (sysRoleMenu.getRoleId() != null && sysRoleMenu.getMenuId() != null
                    && "read".equals(sysRoleMenu.getPermission())) {
            	trueList.add(sysRoleMenu);
            }
        }
        
        sysAuthorizeMapper.deleteRoleMenu(roleId, "read");
        if(trueList.size() > 0){
        	sysRoleMenuMapper.insertAll(trueList);
        }
        
        //清除当前租户所有用户的      [菜单缓存]和[shi'ro的权限缓存管理]
        String menuKeyStart = getMenuRedisTenantIdKey(tenantId);
        String permissionKeyStart = getPermissionRedisTenantIdKey(tenantId);
        try {
        	CacheUtil.getCache().delAll(menuKeyStart);//清除自定义缓存
        	CacheUtil.getCache().delAll(permissionKeyStart);//清除shiro自带缓存管理
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
    }
    
    /**
     * 无论删除，新增，修改	role-menu 全走这里
     * 给角色分配 permission权限，除了read
     * @param sysRoleMenus
     */
    @Transactional
    public void newUpdateRolePermission(OrganizeVO organizeVO) {
    	if(organizeVO.getSysRoleMenuList() == null || organizeVO.getTenantId() == null || organizeVO.getRoleId() == null){
    		return;
    	}
    	
    	Long roleId = organizeVO.getRoleId();
    	Long tenantId = organizeVO.getTenantId();
        List<SysRoleMenu> trueList = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : organizeVO.getSysRoleMenuList()) {
            if (sysRoleMenu.getRoleId() != null && sysRoleMenu.getMenuId() != null
                    && !"read".equals(sysRoleMenu.getPermission())) {
            	trueList.add(sysRoleMenu);
            }
        }
        Map<String, Object> dicParam = InstanceUtil.newHashMap();
        dicParam.put("type", "CRUD");
        List<SysDic> sysDics = sysDicService.queryList(dicParam);
        for (SysDic sysDic : sysDics) {
            if (!"read".equals(sysDic.getCode())) {
                sysAuthorizeMapper.deleteRoleMenu(roleId, sysDic.getCode());
            }
        }
        if(trueList.size() > 0){
        	sysRoleMenuMapper.insertAll(trueList);
        }
        //清除当前租户所有用户的      [菜单缓存]和[shi'ro的权限缓存管理]
        String menuKeyStart = getMenuRedisTenantIdKey(tenantId);
        String permissionKeyStart = getPermissionRedisTenantIdKey(tenantId);
        try {
        	CacheUtil.getCache().delAll(menuKeyStart);//清除自定义缓存
        	CacheUtil.getCache().delAll(permissionKeyStart);//清除shiro自带缓存管理
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
    }
    
    
    /**
     * 无论删除，新增，修改	user_role 全走这里
     * @param sysUserRoles
     */
    @Transactional
    public void newUpdateRoleUser(OrganizeVO organizeVO) {
    	if(organizeVO.getSysUserRoleList() == null || organizeVO.getTenantId() == null || organizeVO.getRoleId() == null){
    		return;
    	}
    	List<SysUserRole> trueList = organizeVO.getSysUserRoleList();
    	Long roleId = organizeVO.getRoleId();
        Long tenantId = organizeVO.getTenantId();
        
        sysAuthorizeMapper.deleteUserRoleByRoleId(roleId);
        if(trueList.size() > 0){
        	sysUserRoleMapper.insertAll(trueList);
        }
        
        //清除当前租户所有用户的      [菜单缓存]和[shi'ro的权限缓存管理]
        String menuKeyStart = getMenuRedisTenantIdKey(tenantId);
        String permissionKeyStart = getPermissionRedisTenantIdKey(tenantId);
        try {
        	CacheUtil.getCache().delAll(menuKeyStart);//清除自定义缓存
        	CacheUtil.getCache().delAll(permissionKeyStart);//清除shiro自带缓存管理
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
    }
}
