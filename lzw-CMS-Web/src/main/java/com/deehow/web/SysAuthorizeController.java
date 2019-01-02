package com.deehow.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.exception.IllegalParameterException;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.support.cache.shiro.RedisSessionDao;
import com.deehow.core.util.CacheUtil;
import com.deehow.model.OrganizeVO;
import com.deehow.model.SysRoleMenu;
import com.deehow.model.SysUserMenu;
import com.deehow.model.SysUserRole;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 权限管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:05
 */
@RestController
@Api(value = "权限管理", description = "权限管理")
public class SysAuthorizeController extends AbstractController<ICmsProvider> {
    @Autowired
    private RedisSessionDao sessionDao;

    public String getService() {
        return "sysAuthorizeService";
    }

//    @ApiOperation(value = "获取用户菜单编号")
//    @PutMapping(value = "user/read/menu")
//    @RequiresPermissions("sys.permisson.userMenu.read")
//    public Object getUserMenu(ModelMap modelMap, @RequestBody SysUserMenu param) {
//        Parameter parameter = new Parameter(getService(), "queryMenuIdsByUserId").setId(param.getUserId());
//        logger.info("{} execute queryMenuIdsByUserId start...", parameter.getNo());
//        List<?> menus = provider.execute(parameter).getList();
//        logger.info("{} execute queryMenuIdsByUserId end.", parameter.getNo());
//        return setSuccessModelMap(modelMap, menus);
//    }
//
//    @ApiOperation(value = "修改用户菜单")
//    @PostMapping(value = "/user/update/menu")
//    @RequiresPermissions("sys.permisson.userMenu.update")
//    public Object userMenu(ModelMap modelMap, @RequestBody List<SysUserMenu> list) {
//        Long userId = null;
//        Long currentUserId = getCurrUser();
//        for (SysUserMenu sysUserMenu : list) {
//            if (sysUserMenu.getUserId() != null) {
//                if (userId != null && sysUserMenu.getUserId() != null
//                    && userId.longValue() != sysUserMenu.getUserId()) {
//                    throw new IllegalParameterException("参数错误.");
//                }
//                userId = sysUserMenu.getUserId();
//            }
//            sysUserMenu.setCreateBy(currentUserId);
//            sysUserMenu.setUpdateBy(currentUserId);
//            sysUserMenu.setCreateTime(new Date());
//            sysUserMenu.setUpdateTime(new Date());
//        }
//        Parameter parameter = new Parameter(getService(), "updateUserMenu").setList(list);
//        logger.info("{} execute updateUserMenu start...", parameter.getNo());
//        provider.execute(parameter);
//        logger.info("{} execute updateUserMenu end.", parameter.getNo());
//        return setSuccessModelMap(modelMap);
//    }
//
//    @ApiOperation(value = "获取用户角色")
//    @PutMapping(value = "user/read/role")
//    @RequiresPermissions("sys.permisson.userRole.read")
//    public Object getUserRole(ModelMap modelMap, @RequestBody SysUserRole param) {
//        Parameter parameter = new Parameter(getService(), "getRolesByUserId").setId(param.getUserId());
//        logger.info("{} execute getRolesByUserId start...", parameter.getNo());
//        List<?> menus = provider.execute(parameter).getList();
//        logger.info("{} execute getRolesByUserId end.", parameter.getNo());
//        return setSuccessModelMap(modelMap, menus);
//    }
//
//    @ApiOperation(value = "修改用户角色")
//    @PostMapping(value = "/user/update/role")
//    @RequiresPermissions("sys.permisson.userRole.update")
//    public Object userRole(ModelMap modelMap, @RequestBody List<SysUserRole> list) {
//        Long userId = null;
//        Long currentUserId = getCurrUser();
//        for (SysUserRole sysUserRole : list) {
//            if (sysUserRole.getUserId() != null) {
//                if (userId != null && sysUserRole.getUserId() != null
//                    && userId.longValue() != sysUserRole.getUserId()) {
//                    throw new IllegalParameterException("参数错误.");
//                }
//                userId = sysUserRole.getUserId();
//            }
//            sysUserRole.setCreateBy(currentUserId);
//            sysUserRole.setUpdateBy(currentUserId);
//            sysUserRole.setCreateTime(new Date());
//            sysUserRole.setUpdateTime(new Date());
//        }
//        Parameter parameter = new Parameter(getService(), "updateUserRole").setList(list);
//        logger.info("{} execute updateUserRole start...", parameter.getNo());
//        provider.execute(parameter);
//        logger.info("{} execute updateUserRole end.", parameter.getNo());
//        return setSuccessModelMap(modelMap);
//    }
//
//    @ApiOperation(value = "获取角色菜单编号")
//    @PutMapping(value = "role/read/menu")
//    @RequiresPermissions("sys.permisson.roleMenu.read")
//    public Object getRoleMenu(ModelMap modelMap, @RequestBody SysRoleMenu param) {
//        Parameter parameter = new Parameter(getService(), "queryMenuIdsByRoleId").setId(param.getRoleId());
//        logger.info("{} execute queryMenuIdsByRoleId start...", parameter.getNo());
//        List<?> menus = provider.execute(parameter).getList();
//        logger.info("{} execute queryMenuIdsByRoleId end.", parameter.getNo());
//        return setSuccessModelMap(modelMap, menus);
//    }
//
//    @ApiOperation(value = "修改角色菜单")
//    @PostMapping(value = "/role/update/menu")
//    @RequiresPermissions("sys.permisson.roleMenu.update")
//    public Object roleMenu(ModelMap modelMap, @RequestBody List<SysRoleMenu> list) {
//        Long roleId = null;
//        Long userId = getCurrUser();
//        for (SysRoleMenu sysRoleMenu : list) {
//            if (sysRoleMenu.getRoleId() != null) {
//                if (roleId != null && sysRoleMenu.getRoleId() != null
//                    && roleId.longValue() != sysRoleMenu.getRoleId()) {
//                    throw new IllegalParameterException("参数错误.");
//                }
//                roleId = sysRoleMenu.getRoleId();
//            }
//            sysRoleMenu.setCreateBy(userId);
//            sysRoleMenu.setUpdateBy(userId);
//            sysRoleMenu.setCreateTime(new Date());
//            sysRoleMenu.setUpdateTime(new Date());
//        }
//        Parameter parameter = new Parameter(getService(), "updateRoleMenu");
//        parameter.setList(list);
//        logger.info("{} execute updateRoleMenu start...", parameter.getNo());
//        provider.execute(parameter);
//        logger.info("{} execute updateRoleMenu end.", parameter.getNo());
//        return setSuccessModelMap(modelMap);
//    }
//
//    @ApiOperation(value = "获取人员操作权限")
//    @PutMapping(value = "user/read/permission")
//    @RequiresPermissions("sys.permisson.user.read")
//    public Object queryUserPermissions(ModelMap modelMap, @RequestBody SysUserMenu record) {
//        Parameter parameter = new Parameter(getService(), "queryUserPermissions").setModel(record);
//        logger.info("{} execute queryUserPermissions start...", parameter.getNo());
//        List<?> menuIds = provider.execute(parameter).getList();
//        logger.info("{} execute queryUserPermissions end.", parameter.getNo());
//        return setSuccessModelMap(modelMap, menuIds);
//    }
//
//    @ApiOperation(value = "修改用户操作权限")
//    @PostMapping(value = "/user/update/permission")
//    @RequiresPermissions("sys.permisson.user.update")
//    public Object updateUserPermission(ModelMap modelMap, @RequestBody List<SysUserMenu> list) {
//        Long userId = null;
//        Long currentUserId = getCurrUser();
//        for (SysUserMenu sysUserMenu : list) {
//            if (sysUserMenu.getUserId() != null) {
//                if (userId != null && sysUserMenu.getUserId() != null
//                    && userId.longValue() != sysUserMenu.getUserId()) {
//                    throw new IllegalParameterException("参数错误.");
//                }
//                userId = sysUserMenu.getUserId();
//            }
//            sysUserMenu.setCreateBy(currentUserId);
//            sysUserMenu.setUpdateBy(currentUserId);
//            sysUserMenu.setCreateTime(new Date());
//            sysUserMenu.setUpdateTime(new Date());
//        }
//        Parameter parameter = new Parameter(getService(), "updateUserPermission").setList(list);
//        logger.info("{} execute updateUserPermission start...", parameter.getNo());
//        provider.execute(parameter);
//        logger.info("{} execute updateUserPermission end.", parameter.getNo());
//        return setSuccessModelMap(modelMap);
//    }
//
//    @ApiOperation(value = "获取角色操作权限")
//    @PutMapping(value = "role/read/permission")
//    @RequiresPermissions("sys.permisson.role.read")
//    public Object queryRolePermissions(ModelMap modelMap, @RequestBody SysRoleMenu record) {
//        Parameter parameter = new Parameter(getService(), "queryRolePermissions").setModel(record);
//        logger.info("{} execute queryRolePermissions start...", parameter.getNo());
//        List<?> menuIds = provider.execute(parameter).getList();
//        logger.info("{} execute queryRolePermissions end.", parameter.getNo());
//        return setSuccessModelMap(modelMap, menuIds);
//    }
//
//    @ApiOperation(value = "修改角色操作权限")
//    @PostMapping(value = "/role/update/permission")
//    @RequiresPermissions("sys.permisson.role.update")
//    public Object updateRolePermission(ModelMap modelMap, @RequestBody List<SysRoleMenu> list) {
//        Long roleId = null;
//        Long userId = getCurrUser();
//        for (SysRoleMenu sysRoleMenu : list) {
//            if (sysRoleMenu.getRoleId() != null) {
//                if (roleId != null && sysRoleMenu.getRoleId() != null
//                    && roleId.longValue() != sysRoleMenu.getRoleId()) {
//                    throw new IllegalParameterException("参数错误.");
//                }
//                roleId = sysRoleMenu.getRoleId();
//            }
//            sysRoleMenu.setCreateBy(userId);
//            sysRoleMenu.setUpdateBy(userId);
//            sysRoleMenu.setCreateTime(new Date());
//            sysRoleMenu.setUpdateTime(new Date());
//        }
//        Parameter parameter = new Parameter(getService(), "updateRolePermission").setList(list);
//        logger.info("{} execute updateRolePermission start...", parameter.getNo());
//        provider.execute(parameter);
//        logger.info("{} execute updateRolePermission end.", parameter.getNo());
//        return setSuccessModelMap(modelMap);
//    }

    @ApiOperation(value = "清理缓存")
    @RequiresPermissions("sys.cache.update")
    @RequestMapping(value = "/cache/update", method = RequestMethod.POST)
    public Object flush(ModelMap modelMap, @RequestBody Map<String, Object> param) {
        Parameter parameter = new Parameter("sysCacheService", "flush").setMap(param);
        logger.info("{} execute sysCacheService.flush start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute sysCacheService.flush end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "查询缓存中在线用户")
//    @RequiresPermissions("sys.cache.update")
    @RequestMapping(value = "/read/online_user", method = RequestMethod.POST)
    public Object onlineUser(ModelMap modelMap, @RequestBody Map<String, Object> param) {
        Set<Object> objs = CacheUtil.getCache().getAll("*-*-*-*-*");
        JSONArray jsonArray = new JSONArray();
        for (Object obj : objs) {
            if(obj != null) {
                Session session = (Session) CacheUtil.getRedisHelper().deserialize(obj.toString());
                JSONObject jsonObject = new JSONObject();
                String userId = session.getAttribute("CURRENT_USER") != null ?
                        session.getAttribute("CURRENT_USER").toString() : "";
                jsonObject.put("userId", userId);
                String tenantId = session.getAttribute("CURRENT_TENANT") != null ?
                        session.getAttribute("CURRENT_TENANT").toString() : "";
                jsonObject.put("tenantId", tenantId);
                jsonObject.put("host", session.getHost());
                jsonObject.put("loginStartTime", session.getStartTimestamp());
                Object attribute = session.getAttribute("org.apache.shiro.subject.support" +
                        ".DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
                SimplePrincipalCollection object = attribute != null ?
                        (SimplePrincipalCollection) attribute : new SimplePrincipalCollection();
                jsonObject.put("account", object.getPrimaryPrincipal());
                jsonObject.put("userName", object.getRealmNames());
                jsonObject.put("sessionId", session.getId());
                jsonArray.add(jsonObject);
            }
        }
        return  setSuccessModelMap(modelMap,jsonArray);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**	分割线#################分割线#################分割线#################分割线#################分割线#################	*/
    /**	分割线#################分割线#################分割线#################分割线#################分割线#################	*/
    /**	分割线#################分割线#################分割线#################分割线#################分割线#################	*/
    /**	分割线#################分割线#################分割线#################分割线#################分割线#################	*/
    /**	分割线#################分割线#################分割线#################分割线#################分割线#################	*/
    /**	分割线#################分割线#################分割线#################分割线#################分割线#################	*/
    
    
    @ApiOperation(value = "获取用户菜单编号")
    @PostMapping(value = "/user/read/menu")
    public Object getUserMenu(ModelMap modelMap, @RequestBody SysUserMenu param) {
        Parameter parameter = new Parameter(getService(), "queryUserMenus").setId(param.getUserId());
        logger.info("{} execute queryUserMenus start...", parameter.getNo());
        List<?> menus = provider.execute(parameter).getList();
        logger.info("{} execute queryUserMenus end.", parameter.getNo());
        return setSuccessModelMap(modelMap, menus);
    }

    @ApiOperation(value = "获取用户操作权限")
    @PostMapping(value = "/user/read/permission")
    public Object getUserPermission(ModelMap modelMap, @RequestBody SysUserMenu param) {
        Parameter parameter = new Parameter(getService(), "newQueryUserPermissions").setId(param.getUserId());
        logger.info("{} execute queryUserMenus start...", parameter.getNo());
        List<?> menus = provider.execute(parameter).getList();
        logger.info("{} execute queryUserMenus end.", parameter.getNo());
        return setSuccessModelMap(modelMap, menus);
    }
    
    @ApiOperation(value = "获取用户角色")
    @PostMapping(value = "/user/read/role")
//    @RequiresPermissions("sys.permisson.userRole.read")
    public Object getUserRole(ModelMap modelMap, @RequestBody SysUserRole param) {
        Parameter parameter = new Parameter(getService(), "getRolesByUserId").setId(param.getUserId());
        logger.info("{} execute getRolesByUserId start...", parameter.getNo());
        List<?> menus = provider.execute(parameter).getList();
        logger.info("{} execute getRolesByUserId end.", parameter.getNo());
        return setSuccessModelMap(modelMap, menus);
    }

    @ApiOperation(value = "修改用户角色")
    @PostMapping(value = "/user/update/role")
    public Object userRole(ModelMap modelMap, @RequestBody OrganizeVO organizeVO) {
    	Assert.notNull(organizeVO.getSysUserRoleList(), "sysUserRoleList");
    	Assert.notNull(organizeVO.getUserId(), "userId");
    	
    	List<SysUserRole> sysUserRoleList = organizeVO.getSysUserRoleList();
        Long userId = organizeVO.getUserId();
        Long tenantId = getCurrTenant();
        organizeVO.setTenantId(tenantId);
        Long currentUserId = getCurrUser();
        Date date = new Date();
        for (SysUserRole sysUserRole : sysUserRoleList) {
            if (sysUserRole.getRoleId() == null) {
            	return setModelMap(modelMap, HttpCode.BAD_REQUEST);
            }
            sysUserRole.setUserId(userId);
            sysUserRole.setTenantId(tenantId);
            sysUserRole.setCreateBy(currentUserId);
            sysUserRole.setUpdateBy(currentUserId);
            sysUserRole.setCreateTime(date);
            sysUserRole.setUpdateTime(date);
        }
        organizeVO.setSysUserRoleList(sysUserRoleList);
        Parameter parameter = new Parameter(getService(), "newUpdateUserRole").setModel(organizeVO);
        logger.info("{} execute newUpdateUserRole start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute newUpdateUserRole end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "获取角色菜单编号")
    @PostMapping(value = "/role/read/menu")
//    @RequiresPermissions("sys.permisson.roleMenu.read")
    public Object getRoleMenu(ModelMap modelMap, @RequestBody SysRoleMenu param) {
        Parameter parameter = new Parameter(getService(), "queryMenuIdsByRoleId").setId(param.getRoleId());
        logger.info("{} execute queryMenuIdsByRoleId start...", parameter.getNo());
        List<?> menus = provider.execute(parameter).getList();
        logger.info("{} execute queryMenuIdsByRoleId end.", parameter.getNo());
        return setSuccessModelMap(modelMap, menus);
    }

    @ApiOperation(value = "修改角色菜单")
    @PostMapping(value = "/role/update/menu")
//    @RequiresPermissions("sys.permisson.roleMenu.update")
    public Object roleMenu(ModelMap modelMap, @RequestBody OrganizeVO organizeVO) {
    	Assert.notNull(organizeVO.getSysRoleMenuList(), "sysRoleMenuList");
    	Assert.notNull(organizeVO.getRoleId(), "roleId");
    	
        Long roleId = organizeVO.getRoleId();
        Long userId = getCurrUser();
        Long tenantId = getCurrTenant();
        organizeVO.setTenantId(tenantId);
        Date date = new Date();
        for (SysRoleMenu sysRoleMenu : organizeVO.getSysRoleMenuList()) {
            if (sysRoleMenu.getMenuId() == null) {
            	return setModelMap(modelMap, HttpCode.BAD_REQUEST);
            }
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setCreateBy(userId);
            sysRoleMenu.setUpdateBy(userId);
            sysRoleMenu.setCreateTime(date);
            sysRoleMenu.setUpdateTime(date);
            sysRoleMenu.setTenantId(tenantId);
        }
        Parameter parameter = new Parameter(getService(), "newUpdateRoleMenu");
        parameter.setModel(organizeVO);
        logger.info("{} execute updateRoleMenu start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute updateRoleMenu end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "获取角色操作权限")
    @PostMapping(value = "/role/read/permission")
//    @RequiresPermissions("sys.permisson.role.read")
    public Object queryRolePermissions(ModelMap modelMap, @RequestBody SysRoleMenu record) {
        Parameter parameter = new Parameter(getService(), "queryRolePermissions").setModel(record);
        logger.info("{} execute queryRolePermissions start...", parameter.getNo());
        List<?> menuIds = provider.execute(parameter).getList();
        logger.info("{} execute queryRolePermissions end.", parameter.getNo());
        return setSuccessModelMap(modelMap, menuIds);
    }

    @ApiOperation(value = "修改角色操作权限")
    @PostMapping(value = "/role/update/permission")
//    @RequiresPermissions("sys.permisson.role.update")
    public Object updateRolePermission(ModelMap modelMap, @RequestBody OrganizeVO organizeVO) {
    	Assert.notNull(organizeVO.getSysRoleMenuList(), "sysRoleMenuList");
    	Assert.notNull(organizeVO.getRoleId(), "roleId");
    	
    	Long roleId = organizeVO.getRoleId();
    	Long userId = getCurrUser();
    	Long tenantId = getCurrTenant();
    	organizeVO.setTenantId(tenantId);
    	Date date = new Date();
        for (SysRoleMenu sysRoleMenu : organizeVO.getSysRoleMenuList()) {
        	if (sysRoleMenu.getMenuId() == null) {
        		return setModelMap(modelMap, HttpCode.BAD_REQUEST);
        	}
        	sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setCreateBy(userId);
            sysRoleMenu.setUpdateBy(userId);
            sysRoleMenu.setCreateTime(new Date());
            sysRoleMenu.setUpdateTime(new Date());
            sysRoleMenu.setTenantId(tenantId);
        }
        Parameter parameter = new Parameter(getService(), "newUpdateRolePermission").setModel(organizeVO);
        logger.info("{} execute updateRolePermission start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute updateRolePermission end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
    }

    
    @ApiOperation(value = "获取角色--的用户")
    @PostMapping(value = "/role/read/user")
//    @RequiresPermissions("sys.permisson.userRole.read")
    public Object getRoleUser(ModelMap modelMap, @RequestBody SysUserRole param) {
    	Assert.notNull(param.getRoleId(), "roleId");
        Parameter parameter = new Parameter(getService(), "getUsersByRoleId").setId(param.getRoleId());
        logger.info("{} execute getUsersByRoleId start...", parameter.getNo());
        List<Long> menus = (List<Long>) provider.execute(parameter).getList();
        logger.info("{} execute getUsersByRoleId end.", parameter.getNo());
        List<String> returnList = new ArrayList<>();
        for(Long id:menus){
        	returnList.add(id.toString());
        }
        return setSuccessModelMap(modelMap, returnList);
    }
    
    @ApiOperation(value = "修改角色用户")
    @PostMapping(value = "/role/update/user")
    public Object roleUser(ModelMap modelMap, @RequestBody OrganizeVO organizeVO) {
    	Assert.notNull(organizeVO.getSysUserIdList(), "sysUserIdList");
    	Assert.notNull(organizeVO.getRoleId(), "roleId");
    	
    	List<Long> userIds = organizeVO.getSysUserIdList();
        Long roleId = organizeVO.getRoleId();
        Long tenantId = getCurrTenant();
        organizeVO.setTenantId(tenantId);
        Long currentUserId = getCurrUser();
        Date date = new Date();
        
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        for (Long userId : userIds) {
        	SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            sysUserRole.setTenantId(tenantId);
            sysUserRole.setCreateBy(currentUserId);
            sysUserRole.setUpdateBy(currentUserId);
            sysUserRole.setCreateTime(date);
            sysUserRole.setUpdateTime(date);
            sysUserRoleList.add(sysUserRole);
        }
        organizeVO.setSysUserRoleList(sysUserRoleList);
        Parameter parameter = new Parameter(getService(), "newUpdateRoleUser").setModel(organizeVO);
        logger.info("{} execute newUpdateRoleUser start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{} execute newUpdateRoleUser end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
    }
}
