package com.fh.controller.system.user;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.GetPinyin;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead;
import com.fh.util.PageData;
import com.fh.util.ObjectExcelView;
import com.fh.util.PathUtil;
import com.fh.util.Tools;

/** 
 * 类名称：UserController
 * 创建人：FH fh313596790qq(青苔)
 * 更新时间：2015年11月3日
 * @version
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {

	/**
	 *菜单地址(权限用)
	 */

	String menuUrl = "user/listUsers.do";
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/**显示用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		// 开始时间
		String lastLoginStart = pd.getString("lastLoginStart");
		// 结束时间
		String lastLoginEnd = pd.getString("lastLoginEnd");
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 23:59:59");
		}
		if(!StringUtils.isEmpty(pd.getString("ROLE_ID"))){
			pd.put("PARENT_ID", pd.getString("ROLE_ID").toString());
		}else {
			pd.put("PARENT_ID", "");
		}
		page.setPd(pd);
		// 列出用户列表
		List<PageData>	userList = userService.listUsers(page);
		pd.put("ROLE_ID", "1");
		// 列出所有系统用户角色
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}

	
	/**删除用户
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return;
		}
		logBefore(logger, Jurisdiction.getUsername()+"删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		userService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除系统用户："+pd);
		out.write("success");
		out.close();
	}
	
	/**去新增用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		pd.put("POSITION_STATUS", "1");
		// 列出所有系统用户角色
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**保存用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		logBefore(logger, Jurisdiction.getUsername()+"新增user");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 验证权限是否是admin本人操作
		PageData pd1 = new PageData();
		pd1.put("USERNAME","admin");
		pd1 = userService.findByUsername(pd1);
		if(pd.get("adminpassword")==null || pd1.get("PASSWORD")==null || !pd1.get("PASSWORD").toString().equals(new SimpleHash("SHA-1", pd1.getString("USERNAME"), pd.getString("adminpassword")).toString())){
			mv.addObject("verifyError","verifyError");
			mv.addObject("verifyErrorMsg","请输入正确的管理员密码");
			mv.setViewName("save_result");
			return mv;
		}
		// ID 主键
		pd.put("USER_ID", this.get32UUID());
		// 最后登录时间
		pd.put("LAST_LOGIN", "");
		// IP
		pd.put("IP", "");
		// 状态
		pd.put("STATUS", "0");
		// 用户默认皮肤
		pd.put("SKIN", "no-skin");
		pd.put("RIGHTS", "");
		// 密码加密
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		// 判断用户名是否存在
		if (null == userService.findByUsername(pd)) {
			// 执行保存
			userService.saveU(pd);
			FHLOG.save(Jurisdiction.getUsername(), "新增系统用户："+pd.getString("USERNAME"));
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**判断用户名是否存在
	 * @return
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUsername(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		// 返回结果
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断邮箱是否存在
	 * @return
	 */
	@RequestMapping(value="/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if (userService.findByUE(pd) != null) {
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		// 返回结果
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUN(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		// 返回结果
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**去修改用户页面(系统用户列表修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 不能修改admin用户
		if ("1".equals(pd.getString("USER_ID"))) {
			return null;
		}
		pd.put("ROLE_ID", "1");
		// 列出所有系统用户角色
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		mv.addObject("fx", "user");
		// 根据ID读取
		pd = userService.findById(pd);
		// 存放副职角色
		List<Role> froleList = new  ArrayList<Role>();
		// 副职角色ID
		String ROLE_IDS = pd.getString("ROLE_IDS");
		if (Tools.notEmpty(ROLE_IDS)) {
			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
			for (int i = 0; i < roleList.size(); i++) {
				Role role = roleList.get(i);
				String roleId = role.getROLE_ID();
				for (int n = 0; n < arryROLE_ID.length; n++) {
					if (arryROLE_ID[n].equals(roleId)) {
						// 此时的目的是为了修改用户信息上，能看到副职角色都有哪些
						role.setRIGHTS("1");
						break;
					}
				}
				froleList.add(role);
			}
		}else{
			froleList = roleList;
		}
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("froleList", froleList);
		return mv;
	}
	
	/**去修改用户页面(个人修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditMyU")
	public ModelAndView goEditMyU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		pd.put("ROLE_ID", "1");
		// 列出所有系统用户角色
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		pd.put("USERNAME", Jurisdiction.getUsername());
		// 根据用户名读取
		pd = userService.findByUsername(pd);
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**查看用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String USERNAME = pd.getString("USERNAME");
		// 不能查看admin用户
		if ("admin".equals(USERNAME)) {
			return null;
		}
		pd.put("ROLE_ID", "1");
		// 列出所有系统用户角色
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		// 根据ID读取
		pd = userService.findByUsername(pd);
		if (null == pd) {
			PageData rpd = new PageData();
			// 用户名查不到数据时就把数据当作角色的编码去查询角色表(工作流的待办人物，查看代办人资料时用到)
			rpd.put("RNUMBER", USERNAME);
			rpd = roleService.getRoleByRnumber(rpd);
			mv.addObject("rpd", rpd);
		}
		mv.setViewName("system/user/user_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**去修改用户页面(在线管理页面打开)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditUfromOnline")
	public ModelAndView goEditUfromOnline() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 不能查看admin用户
		if ("admin".equals(pd.getString("USERNAME"))) {
			return null;
		}
		pd.put("ROLE_ID", "1");
		// 列出所有系统用户角色
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		// 根据ID读取
		pd = userService.findByUsername(pd);
		// 存放副职角色
		List<Role> froleList = new  ArrayList<Role>();
		// 副职角色ID
		String ROLE_IDS = pd.getString("ROLE_IDS");
		if (Tools.notEmpty(ROLE_IDS)) {
			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
			for (int i = 0; i < roleList.size(); i++) {
				Role role = roleList.get(i);
				String roleId = role.getROLE_ID();
				for (int n = 0; n < arryROLE_ID.length; n++) {
					if (arryROLE_ID[n].equals(roleId)) {
						// 此时的目的是为了修改用户信息上，能看到副职角色都有哪些
						role.setRIGHTS("1");
						break;
					}
				}
				froleList.add(role);
			}
		}else{
			froleList = roleList;
		}
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("froleList", froleList);
		return mv;
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ser");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 验证权限是否是admin本人操作
		PageData pd1 = new PageData();
		pd1.put("USERNAME","admin");
		pd1 = userService.findByUsername(pd1);
		if (pd.get("adminpassword")!=null &&(pd1.get("PASSWORD")==null || !pd1.get("PASSWORD").toString().equals
				(new SimpleHash("SHA-1", pd1.getString("USERNAME"), pd.getString("adminpassword")).toString())) ) {
			mv.addObject("verifyError","verifyError");
			mv.addObject("verifyErrorMsg","请输入正确的管理员密码");
			mv.setViewName("save_result");
			return mv;
		}
		// 如果当前登录用户修改用户资料提交的用户名非本人
		if (!Jurisdiction.getUsername().equals(pd.getString("USERNAME"))) {
			//校验权限 判断当前操作者有无用户管理查看权限
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
				return null;
			}
			//校验权限判断当前操作者有无用户管理修改权限
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
				return null;
			}
			//非admin用户不能修改admin
			if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())) {
				return null;
			}
		}else{	//如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			// 对角色ID还原本人角色ID
			pd.put("ROLE_ID", userService.findByUsername(pd).getString("ROLE_ID"));
			// 对角色ID还原本人副职角色ID
			pd.put("ROLE_IDS", userService.findByUsername(pd).getString("ROLE_IDS"));
		}
		if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		// 执行修改
		userService.editU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "修改系统用户："+pd.getString("USERNAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 批量删除
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		}
		logBefore(logger, Jurisdiction.getUsername()+"批量删除user");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除user");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if (null != USER_IDS && !"".equals(USER_IDS)) {
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			userService.deleteAllU(ArrayUSER_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**导出用户信息到EXCEL
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "导出用户信息到EXCEL");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if (Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
				//关键词检索条件
				String keywords = pd.getString("keywords");
				if (null != keywords && !"".equals(keywords)) {
					pd.put("keywords", keywords.trim());
				}
				// 开始时间
				String lastLoginStart = pd.getString("lastLoginStart");
				// 结束时间
				String lastLoginEnd = pd.getString("lastLoginEnd");
				if (lastLoginStart != null && !"".equals(lastLoginStart)) {
					pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
				}
				if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
					pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
				} 
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				// 1
				titles.add("用户名");
				// 2
				titles.add("编号");
				// 3
				titles.add("姓名");
				// 4
				titles.add("职位");
				// 5
				titles.add("手机");
				// 6
				titles.add("邮箱");
				// 7
				titles.add("最近登录");
				// 8
				titles.add("上次登录IP");
				dataMap.put("titles", titles);
				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<userList.size();i++){
					PageData vpd = new PageData();
					// 1
					vpd.put("var1", userList.get(i).getString("USERNAME"));
					// 2
					vpd.put("var2", userList.get(i).getString("NUMBER"));
					// 3
					vpd.put("var3", userList.get(i).getString("NAME"));
					// 4
					vpd.put("var4", userList.get(i).getString("ROLE_NAME"));
					// 5
					vpd.put("var5", userList.get(i).getString("PHONE"));
					// 6
					vpd.put("var6", userList.get(i).getString("EMAIL"));
					// 7
					vpd.put("var7", userList.get(i).getString("LAST_LOGIN"));
					// 8
					vpd.put("var8", userList.get(i).getString("IP"));
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				// 执行excel操作
				ObjectExcelView erv = new ObjectExcelView();
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			// 文件上传路径
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;
			// 执行上传
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");
			// 执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);
			/*存入数据库操作======================================*/
			// 权限
			pd.put("RIGHTS", "");
			// 最后登录时间
			pd.put("LAST_LOGIN", "");
			// IP
			pd.put("IP", "");
			// 状态
			pd.put("STATUS", "0");
			// 默认皮肤
			pd.put("SKIN", "no-skin");
			pd.put("ROLE_ID", "1");
			// 列出所有系统用户角色
			List<Role> roleList = roleService.listAllRolesByPId(pd);
			// 设置角色ID为随便第一个
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			for (int i=0; i < listPd.size(); i++) {
				// ID
				pd.put("USER_ID", this.get32UUID());
				// 姓名
				pd.put("NAME", listPd.get(i).getString("var1"));
				// 根据姓名汉字生成全拼
				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"));
				pd.put("USERNAME", USERNAME);
				//根据姓名汉字生成全拼
				if (userService.findByUsername(pd) != null) {
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"))+Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				// 备注
				pd.put("BZ", listPd.get(i).getString("var4"));
				//备注
				if (Tools.checkEmail(listPd.get(i).getString("var3"))) {
					pd.put("EMAIL", listPd.get(i).getString("var3"));
					// 备注
					if (userService.findByUE(pd) != null) {
						continue;
					}
				}else{
					continue;
				}
				// 编号已存在就跳过
				pd.put("NUMBER", listPd.get(i).getString("var0"));
				// 手机号
				pd.put("PHONE", listPd.get(i).getString("var2"));
				// 默认密码123
				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString());
				if (userService.findByUN(pd) != null) {
					continue;
				}
				userService.saveU(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**显示用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listUsersForWindow")
	public ModelAndView listUsersForWindow(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		// 开始时间
		String lastLoginStart = pd.getString("lastLoginStart");
		// 结束时间
		String lastLoginEnd = pd.getString("lastLoginEnd");
		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		// 列出用户列表(弹窗选择用)
		List<PageData>	userList = userService.listUsersBystaff(page);
		pd.put("ROLE_ID", "1");
		// 列出所有系统用户角色
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		mv.setViewName("system/user/window_user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

}
