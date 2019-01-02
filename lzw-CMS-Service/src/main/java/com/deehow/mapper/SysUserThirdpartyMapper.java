package com.deehow.mapper;

import org.apache.ibatis.annotations.Param;
import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysUserThirdparty;

public interface SysUserThirdpartyMapper extends BaseMapper<SysUserThirdparty> {
	Long queryUserIdByThirdParty(@Param("provider") String provider, @Param("openId") String openId);
}