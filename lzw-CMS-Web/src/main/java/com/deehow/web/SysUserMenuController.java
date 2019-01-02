package com.deehow.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deehow.core.base.AbstractController;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;

/**
 * 菜单管理控制器
 * 
 * @author ldh
 */
@RestController
@Api(value = "菜单管理")
@RequestMapping(value = "/menu")
public class SysUserMenuController extends AbstractController<ICmsProvider> {

	public String getService() {
		return "sysUserService";
	}
}
