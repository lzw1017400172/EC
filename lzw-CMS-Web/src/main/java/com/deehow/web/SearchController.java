package com.deehow.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "搜索", description = "搜索")
public class SearchController extends AbstractController<ICmsProvider> {

	public String getService() {
		return "searchService";
	}

	@PutMapping("query")
	@ApiOperation(value = "全库搜索")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Parameter parameter = new Parameter(getService(), "query").setMap(param);
		List<?> list = provider.execute(parameter).getList();
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "查询菜单")
	@PostMapping(value = "/flush")
	@RequiresPermissions("cache.flush.update")
	public Object cache(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Parameter parameter = new Parameter("sysCacheService", "flush").setMap(param);
		provider.execute(parameter);
		return setSuccessModelMap(modelMap);
	}
}
