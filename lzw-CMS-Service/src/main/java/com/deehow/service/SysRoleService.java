package com.deehow.service;

import com.deehow.core.base.BaseService;
import com.deehow.mapper.SysRoleMapper;
import com.deehow.mapper.SysUserRoleMapper;
import com.deehow.model.SysRole;
import com.deehow.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月31日 上午11:01:33
 */
@Service
@CacheConfig(cacheNames = "sysRole")
public class SysRoleService extends BaseService<SysRole> {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserService sysUserService;

    public List<SysUser> getUserByRoleNames(Map<String,Object> param){
        if(param.get("roleNameList") == null || param.get("tenantId") == null){
            return null;
        }
        List<Long> ids = sysRoleMapper.selectIdPage(param);
        List<Long> userIds = sysUserRoleMapper.queryUserIdListByRoleIdList(ids);
        if(userIds.size() > 0) {
            List<SysUser> userList = sysUserService.getList(userIds);
            return userList;
        } else {
            return new ArrayList<>();
        }
    }

}
