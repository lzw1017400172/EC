package com.deehow.web;

import com.deehow.constant.LoginConstant;
import com.deehow.core.Constants;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.exception.BusinessException;
import com.deehow.core.support.Assert;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.*;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
@Api(value = "公共接口", description = "公共接口")
@RequestMapping("/public")
public class PublicController extends AbstractController<ICmsProvider> {

	public String getService() {
		return null;
	}

	@ApiOperation(value = "获取验证码")
	@RequestMapping(value = "/anon/get/check_code", method = { RequestMethod.GET,RequestMethod.POST })
	public void getCheckCode(HttpServletRequest request ,HttpServletResponse response){
		//生成验证码，并且放入缓存
		BufferedImage image = CheckCodeUtil.getCheckCode();
		
		response.setContentType("image/jpeg");
		OutputStream strm = null;
		try {
			strm = response.getOutputStream();
			ImageIO.write(image, "jpeg", strm);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				strm.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * type  		= regin:注册短信;	=web_dynamic_password:动态密码;	=app_dynamic_password:动态密码;	=retrieve_password:找回密码的手机验证
	 * @param modelMap
	 * @param type
	 * @return
	 */
	@ApiOperation(value = "获取短信验证码")
	@PostMapping("/anon/get/{type}/code")
	public Object getCode(ModelMap modelMap,@PathVariable("type") String type,@RequestBody Map<String,Object> param) {
		Assert.notNull(type, "type");
		Object phone = param.get("phone");
		Assert.notNull(phone, "phone");
		
		String key = null;
		String msg = null;
		int second;
		if(LoginConstant.regin.toString().equals(type.toString())){
			//注册短信
			key = Constants.REGIN_CODE + ":" +  phone;
			msg = Constants.MSG_REGIN;
			second = 60 * 5;
		} else {
			//若系统中不存在此账号，不发送短信
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("account", phone.toString());
			Parameter parameter = new Parameter("sysUserService", "queryList").setMap(params);
			logger.info("{} execute sysUserService.queryList  start...", parameter.getNo());
			List<SysUser> list = (List<SysUser>) provider.execute(parameter).getList();
			logger.info("{} execute sysUserService.queryList end.", parameter.getNo());
			if(list.size() == 0){
				modelMap.put("msg", "您输入的手机号，系统中不存在，请确认后再试。");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			}
			if(LoginConstant.web_dynamic_password.toString().equals(type.toString())){
				//web动态密码
				key = Constants.DYNAMIC_WEB_CODE + ":" + phone;
				msg = Constants.MSG_DYNAMIC;
				second = 60 * 5;
			} else if(LoginConstant.app_dynamic_password.toString().equals(type.toString())){
				//app动态密码
				key = Constants.DYNAMIC_APP_CODE + ":" + phone;
				msg = Constants.MSG_DYNAMIC;
				second = 60 * 5;
			} else if(LoginConstant.retrieve_password.toString().equals(type.toString())){
				//找回密码的手机验证
				key = Constants.RETRIEVE_CODE + ":" + phone;
				msg = Constants.MSG_RETRIEVE;
				second = 60 * 5;
			} else {
				return setModelMap(modelMap, HttpCode.BAD_REQUEST);
			}
		}
		CheckCodeUtil.getMessageCheckCode(phone.toString(),key,msg,second);
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "获取二维码")
	@RequestMapping(value = "/anon/get/qrcode", method = { RequestMethod.GET })
	public void getQrcode(HttpServletResponse response,String content){
		Assert.isNotBlank(content, "content");
		QrcodeUtil.responesQrcode(response,300,300,LoginConstant.deehow_login.toString()+":"+content);
	}
	
	@ApiOperation(value = "获取sessionId二维码")
	@RequestMapping(value = "/anon/get/session_id/qrcode", method = { RequestMethod.GET })
	public void getSessionIdQrcode(HttpServletResponse response){
		Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
		if(sessionId != null){
			QrcodeUtil.responesQrcode(response,300,300,LoginConstant.deehow_login.toString()+":"+sessionId.toString());
		} else {
			throw new BusinessException("获取session失败！！！");
		}
	}
	
	@ApiOperation(value = "文件下载/图片预览")
	@GetMapping(value = "/file/{type:download|view}")
	public Object fileDownloadOrView(ModelMap modelMap,String filePath,String fileName,@PathVariable ("type") String type,
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
	
	@ApiOperation(value = "解析excel")
	@PostMapping(value = "/excel/analysis")
	public Object excelAnalysis(ModelMap modelMap,@RequestParam("file") MultipartFile part,int[] sheetNumber) {
		List<String[]> strList = new ArrayList<>();
		try {
			strList = ExcelReaderUtil.excelToArrayList(part.getOriginalFilename(),part.getInputStream(),sheetNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setSuccessModelMap(modelMap,strList);
	}


	@ApiOperation(value = "导出excel模板title")
	@GetMapping(value = "/excel2007/import")
	public void excel2007Import(HttpServletResponse responsen,String[] titleNames) {
		Assert.notNull(titleNames,"titleNames");
		List<String> columnNames = InstanceUtil.newArrayList(titleNames);
		try {
			Export2007ExcelUtil.writeExcel(responsen, DateUtil.format(new Date(),DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS), "Sheet1", columnNames, "Sheet1", new ArrayList<>(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @ApiOperation(value = "解析excel只解析标题")
    @PostMapping(value = "/excel2007/analysis/first_row")
    public Object excel2007AnalysisFirstRow(ModelMap modelMap,@RequestParam("file") MultipartFile part) {
	    /** 默认解析第一个sheet的第一行 */
        List<String> strList = new ArrayList<>();
		try {
            List<String[]> list = ExcelReaderUtil.excelHead(part.getOriginalFilename(),part.getInputStream());
            if(list.size() == 1){
                Collections.addAll(strList,list.get(0));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return setSuccessModelMap(modelMap,strList);
    }

}
