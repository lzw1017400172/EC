package com.deehow.web;

import com.deehow.core.support.Assert;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.deehow.core.base.AbstractController;
import com.deehow.core.support.HttpCode;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 用户登录
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:11:21
 */
@RestController
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends AbstractController<ICmsProvider> {

	public String getService() {
		return "sysUserService";
	}
	
	// 没有登录
	@RequestMapping(value = "/unauthorized", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public Object unauthorized(ModelMap modelMap) throws Exception {
		return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
	}
	

	// 没有权限
	@RequestMapping(value = "/forbidden", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public Object forbidden(ModelMap modelMap) {
		return setModelMap(modelMap, HttpCode.FORBIDDEN);
	}


	@ApiOperation(value = "文件下载/图片预览")
	@GetMapping(value = "/file/{type:download|view}")
	public Object fileDownloadOrView(ModelMap modelMap,String filePath,String fileName,@PathVariable("type") String type,
									 HttpServletResponse responsen ) throws Exception {
		Assert.isNotBlank(filePath, "filePath");

		File file = new File(filePath);
		if(file.exists()){
			if("download".equals(type)){
				String fileSuffix  = file.getName().substring(file.getName().lastIndexOf("."),file.getName().length());
				if(StringUtils.isEmpty(fileName)){
					fileName = UUID.randomUUID().toString();
				}
				fileName = fileName+fileSuffix;
				try {
					responsen.setContentType("multipart/form-data");
					responsen.addHeader("Content-Length", "" + file.length());
					responsen.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}

			FileInputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = new FileInputStream(file);
				outputStream = responsen.getOutputStream();
				IOUtils.copy(inputStream, outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		} else {
			modelMap.put("msg", "文件不存在！！！");
			return setModelMap(modelMap, HttpCode.CONFLICT);
		}
	}
	
}
