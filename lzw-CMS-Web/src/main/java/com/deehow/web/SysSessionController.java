package com.deehow.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.listener.SessionListener;
import com.deehow.model.SysSession;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户会话管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:06
 */
@RestController
@Api(value = "会话管理", description = "会话管理")
@RequestMapping(value = "/session")
public class SysSessionController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "sysSessionService";
	}

	// 查询会话
	@ApiOperation(value = "查询会话")
	@PutMapping(value = "/read/page")
	//@RequiresPermissions("sys.base.session.read")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
//		Integer number = SessionListener.getAllUserNumber();
//		modelMap.put("userNumber", number); // 用户数大于会话数,有用户没有登录
//		return super.query(modelMap, param);
		Parameter parameter = new Parameter(getService(), "selectCount").setMap(param);
        Object number = provider.execute(parameter).getResult();
		modelMap.put("userNumber", number);
		return super.query(modelMap, param);
	}

	@DeleteMapping
	@ApiOperation(value = "删除会话")
	@RequiresPermissions("sys.base.session.delete")
	public Object delete(ModelMap modelMap, @RequestBody SysSession param) {
		return super.delete(modelMap, param);
	}
}
