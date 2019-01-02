package com.deehow.web;

import com.alibaba.fastjson.JSONArray;
import com.deehow.constant.BusinessConstant;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.DateUtil;
import com.deehow.core.util.UploadUtil;
import com.deehow.model.DsDiagramItem;
import com.deehow.provider.IEdmProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * cms-協同设计模型  前端控制器
 * </p>
 *
 * @author liuzw
 * @since 2018-12-26
 */
@Controller
@RequestMapping("/ds_diagram_item")
@Api(value = "cms-協同设计模型-原理圖", description = "cms-協同设计模型-原理圖")
public class DsDiagramItemController extends AbstractController<IEdmProvider> {
	public String getService() {
		return "dsDiagramItemService";
	}

	@ApiOperation(value = "查询cms-協同设计模型-原理圖")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/list")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.queryList(modelMap, param);
	}
	
	@ApiOperation(value = "分页cms-協同设计模型-原理圖")
//	@RequiresPermissions(".read")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		return super.query(modelMap, param);
	}
	

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改cms-協同设计模型-原理圖")
//	@RequiresPermissions(".update")
	public Object update(ModelMap modelMap, @RequestBody DsDiagramItem param) {
	    Long userId = getCurrUser();
	    if(param.getId() == null){
	        Assert.notNull(param.getTaskId(),"taskId");
            //Assert.notNull(param.getFilePath(),"filePath");
	        Assert.isNotBlank(param.getName(),"name");
            param.setEnable(1);
            param.setCreateTime(new Date());
            param.setCreateBy(userId);
        } else {
            param.setTaskId(null);
        }
        param.setUpdateTime(new Date());
        param.setUpdateBy(userId);
		return super.update(modelMap, param);
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除cms-協同设计模型-原理圖")
	//@RequiresPermissions(".delete")
	public Object delete(ModelMap modelMap, @RequestBody DsDiagramItem param) {
	    Assert.notNull(param.getId(),"ID");
        Parameter parameter = new Parameter(getService(), "delete").setId(param.getId());
        logger.info("{}  execute delete start...", parameter.getNo());
        provider.execute(parameter);
        logger.info("{}  execute delete end.", parameter.getNo());
        return setSuccessModelMap(modelMap);
	}

    @ApiOperation(value = "模型-原理圖上传 接口")
    @PostMapping(value = "/upload")
    public Object upload(ModelMap modelMap, HttpServletRequest request) {
        String pathDir = BusinessConstant.MODEL_SCHEMATIC_DIAGRAM_PATH + DateUtil.format(new Date(),DateUtil.DATE_PATTERN.YYYYMMDD) + File.separator;
        JSONArray uploadFiles = null;
        try{
            uploadFiles = UploadUtil.uploadToNamePath(request,pathDir);
        } catch (Exception ex){
            return setModelMap(modelMap, HttpCode.INTERNAL_SERVER_ERROR);
        }
        return setSuccessModelMap(modelMap,uploadFiles);
    }

}