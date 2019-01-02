package com.deehow.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deehow.core.base.AbstractController;
import com.deehow.provider.ICmsProvider;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author wz
 * @since 2017-07-08
 */
@Controller
@RequestMapping("/sys_position")
public class SysPositionController extends AbstractController<ICmsProvider> {

	@Override
	public String getService() {
		return "sysPositionService";
	}

//	@ApiOperation(value = "查询职位")
//	// @RequiresPermissions("sys.base.dept.read")
//	@PostMapping(value = "/read/list")
//	public Object list(ModelMap modelMap, @RequestBody Map<String, Object> param,HttpServletRequest request) {
//		
//		 HttpSession session = request.getSession();
//		
//		Long userId = getCurrUser();
//		if (userId == -1) {
//			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
//		}
//		Parameter sysUserparameter = new Parameter("sysUserService",
//				"queryById").setId(userId);
//		logger.info("{} execute queryById start...", sysUserparameter.getNo());
//		SysUser sysUser = (SysUser) provider.execute(sysUserparameter)
//				.getModel();
//		logger.info("{} execute queryById end...", sysUserparameter.getNo());
//		System.out
//				.println("租户iD========" + sysUser.getTenantId() + "=========");
//		param.put("tenantId", sysUser.getTenantId());
//		Parameter parameter = new Parameter(getService(), "queryList")
//				.setMap(param);
//		List<?> list = provider.execute(parameter).getList();
//		System.out.println("结果大小========" + list.size() + "=========");
//		JSONArray menuList = JSONArray.parseArray(JSONArray.toJSON(list)
//				.toString());
//		System.out.println("======" + menuList.toString() + "===========");
//		JSONArray treeMenuList = ConstantSystem.treeMenuList(menuList, "0");
//		return setModelMap(modelMap, HttpCode.OK, treeMenuList);
//	}
//	
//	// 无分页查询职位
//	@ApiOperation(value = "查询用户")
//	//@RequiresPermissions("sys.base.user.read")
//	@PostMapping(value = "/read/queryList")
//	public Object queryList(ModelMap modelMap, @RequestBody Map<String, Object> param) {
//		Long userId = getCurrUser();
//		if(userId == -1){
//			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
//		}
//		Parameter sysUserparameter = new Parameter("sysUserService", "queryById").setId(userId);
//		logger.info("{} execute queryById start...", sysUserparameter.getNo());
//		SysUser sysUser = (SysUser) provider.execute(sysUserparameter).getModel();
//		logger.info("{} execute queryById end...", sysUserparameter.getNo());
//		param.put("tenantId", sysUser.getTenantId());
//		return super.queryList(modelMap, param);
//	}
//
//	@ApiOperation(value = "查询职位(分页)")
//	// @RequiresPermissions("sys.base.dept.read")
//	@PostMapping(value = "/read/page")
//	public Object query(ModelMap modelMap,
//			@RequestBody Map<String, Object> param) {
//		return super.query(modelMap, param);
//	}
//
//	@ApiOperation(value = "职位详情")
//	// @RequiresPermissions("sys.base.dept.read")
//	@PostMapping(value = "/read/detail")
//	public Object get(ModelMap modelMap, @RequestBody SysPosition param) {
//		return super.get(modelMap, param);
//	}
//
//	@PostMapping(value = "/update")
//	@ApiOperation(value = "修改职位")
//	// @RequiresPermissions("sys.base.dept.update")
//	public Object update(ModelMap modelMap, @RequestBody SysPosition param) {
//		Map<String, Object> positionMap = new HashMap<String, Object>();
//		Long userId = getCurrUser();
//		if (userId == -1) {
//			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
//		}
//		Parameter sysUserparameter = new Parameter("sysUserService",
//				"queryById").setId(userId);
//		logger.info("{} execute queryById start...",
//				sysUserparameter.getNo());
//		SysUser sysUser = (SysUser) provider.execute(sysUserparameter)
//				.getModel();
//		logger.info("{} execute queryById end...", sysUserparameter.getNo());
//		positionMap.put("tenantId", sysUser.getTenantId());
//		positionMap.put("deptId", param.getDeptId());
//		positionMap.put("positionTitle", param.getPositionTitle());
//		positionMap.put("positionNo", param.getPositionNo());
//		positionMap.put("parentId", param.getParentId());
//		if (param.getId() != null) {
//			Long positionId = param.getId();
//			Parameter positionparameter = new Parameter(getService(),
//					"queryById").setId(positionId);
//			logger.info("{} execute queryById start...",
//					positionparameter.getNo());
//			SysPosition position = (SysPosition) provider.execute(
//					positionparameter).getModel();
//			logger.info("{} execute queryById end...",
//					positionparameter.getNo());
//			Long parentId = position.getParentId();
//			if(parentId == null){
//				parentId = (long) 0;
//			}
//			if (position.getDeptId().equals(param.getDeptId())
//					&& position.getPositionNo().equals(param.getPositionNo())
//					&& position.getPositionTitle().equals(
//							param.getPositionTitle())
//					&& (long)parentId==(long)param.getParentId()) {
//				return super.update(modelMap, param);
//			} else {
//				if(!position.getPositionNo().equals(param.getPositionNo())){
//					positionMap.put("noUnique", param.getPositionNo());
//				}
//				Parameter positionparam = new Parameter(getService(),
//						"queryList").setMap(positionMap);
//				logger.info("{} execute queryById start...",
//						positionparam.getNo());
//				List<?> list = provider.execute(positionparam).getList();
//				if(list!=null&&list.size()>0){
//					modelMap.put("msg", "更新时,该部门下的职位名称或职位编号已存在!!!");
//					return setModelMap(modelMap, HttpCode.CONFLICT);
//				}else{
//					return super.update(modelMap, param);
//				}
//			}
//		} else {
//			positionMap.put("noUnique", param.getPositionNo());
//			Parameter positionparam = new Parameter(getService(),
//					"queryList").setMap(positionMap);
//			logger.info("{} execute queryList start...",
//					positionparam.getNo());
//			List<?> list = provider.execute(positionparam).getList();
//			logger.info("{} execute queryList end...",
//					positionparam.getNo());
//			if(list!=null&&list.size()>0){
//				modelMap.put("msg", "新增时,该部门下的职位名称或职位编号已存在!!!");
//				return setModelMap(modelMap, HttpCode.CONFLICT);
//			}else{
//				param.setTenantId(sysUser.getTenantId());
//				return super.update(modelMap, param);
//			}
//		}
//	}
//
//	@PostMapping(value = "/delete")
//	@ApiOperation(value = "删除职位")
//	// @RequiresPermissions("sys.base.dept.delete")
//	public Object delete(ModelMap modelMap, @RequestBody SysPosition param) {
//		Long positionId = param.getId();
//		Map<String, Object> positionMap = new HashMap<String, Object>();
//		positionMap.put("parentId", positionId);
//		Parameter deptParam = new Parameter(getService(), "queryList")
//				.setMap(positionMap);
//		List<?> deptList = provider.execute(deptParam).getList();
//		if (deptList != null && deptList.size() > 0) {
//			modelMap.put("msg", "该职位含有下级职位...无法删除!!");
//			return setModelMap(modelMap, HttpCode.CONFLICT);
//		}
//		Long userId = getCurrUser();
//		if (userId == -1) {
//			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
//		}
//		Parameter sysUserparam = new Parameter("sysUserService", "queryById")
//				.setId(userId);
//		logger.info("{} execute queryById start...", sysUserparam.getNo());
//		SysUser sysUser = (SysUser) provider.execute(sysUserparam).getModel();
//		logger.info("{} execute queryById end...", sysUserparam.getNo());
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("positionId", positionId);
//		map.put("tenantId", sysUser.getTenantId());
//		Parameter sysUserparameter = new Parameter("sysUserService",
//				"queryList").setMap(map);
//		List<?> list = provider.execute(sysUserparameter).getList();
//		if (list != null && list.size() > 0) {
//			modelMap.put("msg", "该职位已分配人员...无法删除!!");
//			return setModelMap(modelMap, HttpCode.CONFLICT);
//		} else {
//			return super.delete(modelMap, param);
//		}
//	}
//	
//	//关联用户
//	@PostMapping(value = "/relateUser")
//	@ApiOperation(value = "职位关联用户")
//	public Object relateUser(ModelMap modelMap,@RequestBody Map<String, Object> param){
//		String userId = (String) param.get("userId");
//		String positionId = (String) param.get("positionId");
//		String positionTitle = (String) param.get("positionTitle");
//		List<Long> userIds = new ArrayList<Long>();
//		if(StringUtils.isNotBlank(userId)){
//			String[] userIdStrs = userId.split(",");
//			for (String string : userIdStrs) {
//				userIds.add(Long.valueOf(string));
//			}
//		}
//		if(userIds!=null&&userIds.size()>0){
//			ExecutorService executorService = Executors.newFixedThreadPool(20);
//			for (Long long1 : userIds) {
//				executorService.execute(new Runnable() {
//					public void run() {
//						SysUser sysUser = new SysUser();
//						sysUser.setId(long1);
//						sysUser.setPositionId(Long.valueOf(positionId));
//						sysUser.setPosition(positionTitle);
//						Parameter sysUserparam = new Parameter("sysUserService", "update").setModel(sysUser);
//						provider.execute(sysUserparam);
//					}
//				});
//			}
//			executorService.shutdown();
//			try {
//				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
//			} catch (InterruptedException e) {
//				logger.error("awaitTermination", "", e);
//			}
//		}
//		return setModelMap(modelMap, HttpCode.OK);
//	}
//
//	@ApiOperation(value = "查询当前租户的职位-人员tree")
//	@PostMapping(value = "/readPositionAndUser/tree")
//	public Object readPositionAndUserTree(ModelMap modelMap) {
//		Long tenantId = getCurrTenant();
//		if(tenantId == -1){
//			modelMap.put("msg", "获取租户失败，不能查询职位！！！");
//			return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
//		}
//		Map<String, Object> param = new HashMap<>();
//		param.put("tenantId", tenantId);
//		//查询职位
//		Parameter parameter = new Parameter(getService(), "queryList").setMap(param);
//		List<SysPosition> listPosition = (List<SysPosition>) provider.execute(parameter).getList();
//		//查询人员
//		Parameter parameterUser = new Parameter("sysUserService", "queryList").setMap(param);
//		List<SysUser> listUser = (List<SysUser>) provider.execute(parameterUser).getList();
//		
//		Map<Long,List<SysUser>> map = new HashMap<>();
//		for(SysUser user:listUser){
//			if(user.getPositionId() != null){
//				List<SysUser> uList = map.get(user.getPositionId());
//				if(uList != null){
//					uList.add(user);
//					map.put(user.getPositionId(), uList);
//				} else {
//					List<SysUser> uuList = new ArrayList<>();
//					uuList.add(user);
//					map.put(user.getPositionId(), uuList);
//				}
//			}
//		}
//		for(SysPosition position:listPosition){
//			position.setUserList(map.get(position.getId()));
//		}
//		return setSuccessModelMap(modelMap,listPosition);
//	}
}
