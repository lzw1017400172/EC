package com.deehow.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deehow.core.Constants;
import com.deehow.core.base.AbstractController;
import com.deehow.core.base.Parameter;
import com.deehow.core.support.HttpCode;
import com.deehow.core.util.SecurityUtil;
import com.deehow.core.util.WebUtil;
import com.deehow.model.UnitRegisterApply;
import com.deehow.model.SysUser;
import com.deehow.provider.ICmsProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 单位--注册申请表 前端控制器
 * </p>
 *
 * @author WangShengChao
 * @since 2018-07-18
 */
@Controller
@Api(value = "单位--注册申请表", description = "单位--注册申请表")
@RequestMapping(value = "register")
public class UnitRegisterApplyController extends AbstractController<ICmsProvider> {
	public String getService() {
		return "unitRegisterApplyService";
	}

	@ApiOperation(value = "查询单位--注册申请表")
	// @RequiresPermissions(".read")
	@PostMapping(value = "/read/list")
	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("status", "0");
		return super.queryList(modelMap, param);
	}

	@ApiOperation(value = "分页单位--注册申请表")
	@PostMapping(value = "/read/page")
	public Object query(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		param.put("status", "0");
		return super.query(modelMap, param);
	}

	@ApiOperation(value = "单位--注册申请表详情")
	@PostMapping(value = "/read/detail")
	public Object get(ModelMap modelMap, @RequestBody UnitRegisterApply param) {
		return super.get(modelMap, param);
	}

	@ApiOperation(value = "修改单位--注册申请表")
	@PostMapping(value = "/update")
	public Object update(ModelMap modelMap, @RequestBody UnitRegisterApply param, HttpServletRequest request) {
		// 密码格式验证
		String phoneRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
		// 邮箱格式验证
		String emailRegex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if (!param.getPassword().matches(phoneRegex)) {
			modelMap.put("msg", "密码需为8-20位的字母和数字组合!");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		if (!param.getEmail().matches(emailRegex)) {
			modelMap.put("msg", "邮箱格式不正确!");
			return setModelMap(modelMap, HttpCode.INTERNAL_SATICEFY_ERROR);
		}
		// 获取当前浏览器————登录人的账号
		HttpSession session = request.getSession();
		String phone = null;
		if (null != session) {
			Object account = session.getAttribute(Constants.REGIN_CODE);
			if (null != account) {
				phone = account.toString();
			}
		}

		if (phone == null) {
			modelMap.put("msg", "动态密码已失效，请返回上一步重新获取密码。");
			return setModelMap(modelMap, HttpCode.GONE);
		} else {
			if (!param.getPhone().equals(phone)) {
				modelMap.put("msg", "您选择的账号和刚刚验证通过的账号不匹配。");
				return setModelMap(modelMap, HttpCode.CONFLICT);
			} else {
				Long userId = null;
				try {
					userId = WebUtil.getCurrentUser();
				} catch (Exception ex) {
					userId = 1L;
				}
				if (param.getId() == null) {
					param.setCreateBy(userId);
					param.setCreateTime(new Date());
					param.setEnable(1);
					param.setPassword(SecurityUtil.entryptPassword(param.getPassword()));
				}
				param.setUpdateBy(userId);
				param.setUpdateTime(new Date());
				param.setEnable(1);
				Parameter parameter = new Parameter(getService(), "update").setModel(param);
				parameter = provider.execute(parameter);
				modelMap.put("msg", "操作成功！");
				return setModelMap(modelMap, HttpCode.OK);
			}
		}
	}

	@ApiOperation(value = "审核-单位信息")
	@PostMapping(value = "/audit_unit")
	public Object auditUnit(ModelMap modelMap, @RequestBody UnitRegisterApply param) {
		Long userId = null;
		try {
			userId = WebUtil.getCurrentUser();
		} catch (Exception ex) {
			userId = 1L;
		}
		Parameter params = new Parameter("sysUserService", "queryById").setId(userId);
		logger.info("{} execute unitRegisterApplyService.queryById  start...", params.getNo());
		params = provider.execute(params);
		SysUser user = params == null ? null : (SysUser) params.getModel();
		if (null == user) {
			modelMap.put("msg", "没有对应账号，请先登录！");
			return setModelMap(modelMap, HttpCode.GONE);
		}
		logger.info("{} execute unitRegisterApplyService.queryById end...", params.getNo());
		param.setUpdateBy(userId);
		param.setUpdateTime(new Date());
		param.setEnable(1);
		param.setAuditTime(new Date());
		param.setPrincipal(user.getUserName());
		param.setPrincipalId(userId);
		Parameter parameter = new Parameter(getService(), "updateUnitRegisterApply").setModel(param);
		parameter = provider.execute(parameter);
		return setModelMap(modelMap, HttpCode.OK);
	}

}