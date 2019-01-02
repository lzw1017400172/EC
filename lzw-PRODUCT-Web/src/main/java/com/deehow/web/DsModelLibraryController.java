package com.deehow.web;

import com.deehow.core.base.AbstractController;
import com.deehow.provider.IEdmProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * <p>
 * cms-協同设计模型庫  前端控制器
 * </p>
 *
 * @author liuzw
 * @since 2018-12-26
 */
@Controller
@RequestMapping("/ds_model_library")
@Api(value = "cms-協同设计模型庫", description = "cms-協同设计模型庫")
public class DsModelLibraryController extends AbstractController<IEdmProvider> {
	public String getService() {
		return "dsModelLibraryService";
	}

	@ApiOperation(value = "查询cms-協同设计模型庫")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/list")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.queryList(modelMap, param);
	}
	
	@ApiOperation(value = "分页cms-協同设计模型庫")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.query(modelMap, param);
	}


}