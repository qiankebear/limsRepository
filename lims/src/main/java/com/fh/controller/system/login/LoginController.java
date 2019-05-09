package com.fh.controller.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.fh.entity.Page;
import com.fh.service.project.projectmanager.ProjectManagerManager;
import com.fh.service.project.samplemanager.impl.SampleManagerService;
import com.fh.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.fhoa.datajur.DatajurManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.buttonrights.ButtonrightsManager;
import com.fh.service.system.fhbutton.FhbuttonManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.loginimg.LogInImgManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;

/**
 * 总入口
 * @author fh QQ 3 1 3 5 9 6 7 9 0[青苔]
 * 修改日期：2015/11/2
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name="fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="datajurService")
	private DatajurManager datajurService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="loginimgService")
	private LogInImgManager loginimgService;
	@Resource(name="projectmanagerService")
	private ProjectManagerManager projectmanagerService;
	@Resource(name = "samplemanagerService")
	private SampleManagerService sampleManagerService;
	/**访问登录页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 设置登录页面的配置参数
		pd = this.setLoginPd(pd);
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
			return mv;
	}
	
	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		int position_status = 0;
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq313596790fh", "").
				replaceAll("QQ978336446fh", "").split(",fh,");
		if(null != KEYDATA && KEYDATA.length == 3){
			Session session = Jurisdiction.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
			String code = KEYDATA[2];
			// 判断效验码
			if(null == code || "".equals(code)){
				// 效验码为空
				errInfo = "nullcode";
			}else{
				// 登录过来的用户名
				String USERNAME = KEYDATA[0];
				// 登录过来的密码
				String PASSWORD  = KEYDATA[1];
				pd.put("USERNAME", USERNAME);
				// 判断登录验证码
				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){
					// 密码加密
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();
					pd.put("PASSWORD", passwd);
					// 根据用户名和密码去读取用户信息
					pd = userService.getUserByNameAndPwd(pd);
					if(pd != null){
						// 请缓存
						this.removeSession(USERNAME);
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						position_status = pd.getInt("POSITION_STATUS");
						// 把用户信息放session中
						session.setAttribute(Const.SESSION_USER, user);
						// 清除登录验证码的session
						session.removeAttribute(Const.SESSION_SECURITY_CODE);
						//shiro加入身份验证
						Subject subject = SecurityUtils.getSubject(); 
					    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
					    try { 
					        subject.login(token); 
					    } catch (AuthenticationException e) { 
					    	errInfo = "身份验证失败！";
					    }
					}else{
						// 用户名或密码有误
						errInfo = "usererror";
						logBefore(logger, USERNAME+"登录系统密码或用户名错误");
						FHLOG.save(USERNAME, "登录系统密码或用户名错误");
					}
				}else{
					// 验证码输入有误
					errInfo = "codeerror";
				}
				if(Tools.isEmpty(errInfo)){
					if( position_status != 1 ){
						// 离职用户
						errInfo = "errornostatus";
					}else{
						// 验证成功
						errInfo = "success";
						logBefore(logger, USERNAME+"登录系统");
						FHLOG.save(USERNAME, "登录系统");
					}

				}
			}
		}else{
			// 缺少参数
			errInfo = "error";
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**访问系统首页
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			// 读取session中的用户信息(单独用户信息)
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				// 读取session中的用户信息(含角色信息)
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);

				if(null == userr){
					// 通过用户ID读取用户信息和角色信息
					user = userService.getUserAndRoleById(user.getUSER_ID());
					// 存入session
					session.setAttribute(Const.SESSION_USERROL, user);
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				// 获取用户角色
				Role role = user.getRole();
				// 角色权限(菜单权限)
				String roleRights = role!=null ? role.getRIGHTS() : "";
				String ROLE_IDS = user.getROLE_IDS();
				// 将角色权限存入session
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights);
				// 放入用户名到session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);
				// 放入用户姓名到session
				session.setAttribute(Const.SESSION_U_NAME, user.getNAME());
				// 把用户的组织机构权限放到session里面
				this.setAttributeToAllDEPARTMENT_ID(session, USERNAME);
				List<Menu> allmenuList = new ArrayList<Menu>();
				// 菜单缓存
				allmenuList = this.getAttributeMenu(session, USERNAME, roleRights, getArrayRoleRights(ROLE_IDS));
				List<Menu> menuList = new ArrayList<Menu>();
				// 切换菜单
				menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					// 主职角色按钮权限放到session中
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));
					// 副职角色按钮权限放到session中
					session.setAttribute(USERNAME + Const.SESSION_QX2, this.getUQX2(USERNAME));
				}
				this.getRemortIP(USERNAME);	//更新登录IP
				mv.setViewName("system/index/main");
				mv.addObject("user", user);
				mv.addObject("SKIN", null == session.getAttribute(Const.SKIN)?user.getSKIN():session.getAttribute(Const.SKIN)); 	//用户皮肤
				mv.addObject("menuList", menuList);
			}else {
				mv.setViewName("system/index/login");	//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		// pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		// 配置系统名称
		pd.put("SYSNAME", "实验室LIMS系统");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**获取副职角色权限List
	 * @param ROLE_IDS
	 * @return
	 * @throws Exception
	 */
	public List<String> getArrayRoleRights(String ROLE_IDS) throws Exception{
		if(Tools.notEmpty(ROLE_IDS)){
			List<String> list = new ArrayList<String>();
			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
			for(int i=0;i<arryROLE_ID.length;i++){
				PageData pd = new PageData();
				pd.put("ROLE_ID", arryROLE_ID[i]);
				pd = roleService.findObjectById(pd);
				if(null != pd){
					String RIGHTS = pd.getString("RIGHTS");
					if(Tools.notEmpty(RIGHTS)){
						list.add(RIGHTS);
					}
				}
			}
			return list.size() == 0 ? null : list;
		}else{
			return null;
		}
	}
	
	/**菜单缓存
	 * @param session
	 * @param USERNAME
	 * @param roleRights
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAttributeMenu(Session session, String USERNAME, String roleRights, List<String> arrayRoleRights) throws Exception{
		List<Menu> allmenuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){
			// 获取所有菜单
			allmenuList = menuService.listAllMenuQx("0");
			if(Tools.notEmpty(roleRights)){
				// 根据角色权限获取本权限的菜单列表
				allmenuList = this.readMenu(allmenuList, roleRights, arrayRoleRights);
			}
			// 菜单权限放入session中
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);
		}else{
			allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}
		return allmenuList;
	}
	
	/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights, List<String> arrayRoleRights){
		for(int i=0;i<menuList.size();i++){
			Boolean b1 = RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID());
			// 赋予主职角色菜单权限
			menuList.get(i).setHasMenu(b1);
			if(!b1 && null != arrayRoleRights){
				for(int n=0;n<arrayRoleRights.size();n++){
					if(RightsHelper.testRights(arrayRoleRights.get(n), menuList.get(i).getMENU_ID())){
						menuList.get(i).setHasMenu(true);
						break;
					}
				}
			}
			// 判断是否有此菜单权限
			if(menuList.get(i).isHasMenu()){
				// 是：继续排查其子菜单
				this.readMenu(menuList.get(i).getSubMenu(), roleRights, arrayRoleRights);
			}
		}
		return menuList;
	}
	
	/**切换菜单处理
	 * @param allmenuList
	 * @param session
	 * @param USERNAME
	 * @param changeMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> changeMenuF(List<Menu> allmenuList, Session session, String USERNAME, String changeMenu){
		List<Menu> menuList = new ArrayList<Menu>();
		/** 菜单缓存为空 或者 传入的菜单类型和当前不一样的时候，条件成立，重新拆分菜单，把选择的菜单类型放入缓存 */
		if(null == session.getAttribute(USERNAME + Const.SESSION_menuList) || (!changeMenu.equals(session.getAttribute("changeMenu")))){
			List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			List<Menu> menuList3 = new ArrayList<Menu>();
			List<Menu> menuList4 = new ArrayList<Menu>();
			// 拆分菜单
			for(int i=0;i<allmenuList.size();i++){
				Menu menu = allmenuList.get(i);
				if("1".equals(menu.getMENU_TYPE())){
					// 系统菜单
					menuList1.add(menu);
				}else if("2".equals(menu.getMENU_TYPE())){
					// 业务菜单
					menuList2.add(menu);
				}else if("3".equals(menu.getMENU_TYPE())){
					// 菜单类型三
					menuList3.add(menu);
				}else if("4".equals(menu.getMENU_TYPE())){
					// 菜单类型四
					menuList4.add(menu);
				}
			}
			session.removeAttribute(USERNAME + Const.SESSION_menuList);
			if("index".equals(changeMenu)){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "index");
				menuList = menuList2;
			}else if("2".equals(changeMenu)){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "2");
				menuList = menuList1;
			}else if("3".equals(changeMenu)){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList3);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "3");
				menuList = menuList3;
			}else if("4".equals(changeMenu)){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList4);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "4");
				menuList = menuList4;
			}
		}else{
			menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_menuList);
		}
		return menuList;
	}
	
	/**把用户的组织机构权限放到session里面
	 * @param session
	 * @param USERNAME
	 * @return
	 * @throws Exception 
	 */
	public void setAttributeToAllDEPARTMENT_ID(Session session, String USERNAME) throws Exception{
		String DEPARTMENT_IDS = "0",DEPARTMENT_ID = "0";
		if(!"admin".equals(USERNAME)){
			PageData pd = datajurService.getDEPARTMENT_IDS(USERNAME);
			DEPARTMENT_IDS = null == pd?"无权":pd.getString("DEPARTMENT_IDS");
			DEPARTMENT_ID = null == pd?"无权":pd.getString("DEPARTMENT_ID");
		}
		// 把用户的组织机构权限集合放到session里面
		session.setAttribute(Const.DEPARTMENT_IDS, DEPARTMENT_IDS);
		// 把用户的最高组织机构权限放到session里面
		session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID);
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/index/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		// 关键词检索条件
		String keywords1 = pd.getString("keywords1");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != keywords1 && !"".equals(keywords1)){
			pd.put("keywords1", keywords1.trim());
		}
		page.setPd(pd);
		String user = Jurisdiction.getUsername();
		pd.put("USERNAME",user);
		PageData user1 = userService.findByUsername(pd);
		pd.put("user_id",user1.get("USER_ID").toString());
		User userInfo = userService.getUserAndRoleById(user1.get("USER_ID").toString());
		// 获取当前用户的角色
		String roleName = userInfo.getRole().getRNUMBER();
		if("R20171231726481".equals(roleName) || "R20180131375361".equals(roleName)|| "R20170000000001".equals(roleName)){
			List<PageData>	varList = projectmanagerService.list1(page);
			List<PageData> projectList1 = new ArrayList<PageData>();
			// projectList.addAll(PUList);
			for (int i = 0; i < varList.size(); i++) {
				// 获取该用户参与的所有项目的id
				long project_id =Long.valueOf(varList.get(i).get("id").toString());
				pd.put("id",project_id);
				PageData project = projectmanagerService.findProjectByUserId(pd);
				List<PageData> projectUser = projectmanagerService.findPUByProjectUser(pd);

				List<String> memnerList = new ArrayList<>();
				for (int i1 = 0; i1 < projectUser.size(); i1++) {
					long memner_kind = Long.valueOf(projectUser.get(i1).get("member_kind").toString());
					memnerList.add(String.valueOf(memner_kind));
				}
				if(memnerList.contains("1")){
					project.put("member_kind", 1);
				}else if (memnerList.contains("2")){
					project.put("member_kind", 2);
				}else if (memnerList.contains("3")){
					project.put("member_kind", 3);
				}
				projectList1.add(project);
			}
//			for (int i = 0; i < projectList1.size(); i++) {
//				// 通过项目id查找该项目的各种状态的样本个数
//				PageData sampleCountList = sampleManagerService.listSampleCountIndex(projectList1.get(i));
//				// 接收样本总数
//				String allSample = sampleCountList.get("allsample").toString();
//				projectList1.get(i).put("allsample",allSample);
//				// 检出样本总数
//				String normalSample = sampleCountList.get("normalsample").toString();
//				projectList1.get(i).put("normalsample",normalSample);
//			}
			mv.addObject("varList", projectList1);
		}else {
			// 通过当前登录用户获取用户和项目的中间表
			List<PageData> PUList = projectmanagerService.findPUByUserIdlistPage1(page);
			List<PageData> projectList = new ArrayList<PageData>();
			// projectList.addAll(PUList);
			for (int i = 0; i < PUList.size(); i++) {
				// 获取该用户参与的所有项目的id
				long project_id =Long.valueOf(PUList.get(i).get("project_id").toString());
				pd.put("id",project_id);
				PageData project = projectmanagerService.findProjectByUserId(pd);
				List<PageData> projectUser = projectmanagerService.findPUByProjectUser(pd);

				List<String> memnerList = new ArrayList<>();
				for (int i1 = 0; i1 < projectUser.size(); i1++) {
					long memner_kind = Long.valueOf(projectUser.get(i1).get("member_kind").toString());
					memnerList.add(String.valueOf(memner_kind));
				}
				if(memnerList.contains("1")){
					project.put("member_kind", 1);
				}else if (memnerList.contains("2")){
					project.put("member_kind", 2);
				}else if (memnerList.contains("3")){
					project.put("member_kind", 3);
				}
				projectList.add(project);
			}
//			for (int i = 0; i < projectList.size(); i++) {
//				// 通过项目id查找该项目的各种状态的样本个数
//				PageData sampleCountList = sampleManagerService.listSampleCountIndex(projectList.get(i));
//				// 接收样本总数
//				String allSample = sampleCountList.get("allsample").toString();
//				projectList.get(i).put("allsample",allSample);
//				// 检出样本总数
//				String normalSample = sampleCountList.get("normalsample").toString();
//				projectList.get(i).put("normalsample",normalSample);
//			}
			mv.addObject("varList", projectList);
		}
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("user",user);



		mv.setViewName("system/index/default");
		return mv;
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout() throws Exception{
		// 当前登录的用户名
		String USERNAME = Jurisdiction.getUsername();
		logBefore(logger, USERNAME+"退出系统");
		FHLOG.save(USERNAME, "退出");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		// 清缓存
		this.removeSession(USERNAME);
		// shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		// 设置登录页面的配置参数
		pd = this.setLoginPd(pd);
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME){
		// 以下清除session缓存
		Session session = Jurisdiction.getSession();
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(USERNAME + Const.SESSION_QX2);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_U_NAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute(Const.SESSION_RNUMBERS);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}
	
	/**设置登录页面的配置参数
	 * @param pd
	 * @return
	 */
	public PageData setLoginPd(PageData pd){
		// 读取系统名称
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME));

		// 读取登录页面配置
		String strLOGINEDIT = Tools.readTxtFile(Const.LOGINEDIT);
		if(null != strLOGINEDIT && !"".equals(strLOGINEDIT)){
			String strLo[] = strLOGINEDIT.split(",fh,");
			if(strLo.length == 2){
				pd.put("isZhuce", strLo[0]);
				pd.put("isMusic", strLo[1]);
			}
		}
		try {
			// 登录背景图片
			List<PageData> listImg = loginimgService.listAll(pd);
			pd.put("listImg", listImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**获取用户权限
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME){
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			
			PageData userpd = new PageData();
			// 通过用户名获取用户信息
			userpd = userService.findByUsername(pd);
			String ROLE_ID = userpd.get("ROLE_ID").toString();
			String ROLE_IDS = userpd.getString("ROLE_IDS");
			// 获取角色ID
			pd.put("ROLE_ID", ROLE_ID);
			// 获取角色信息
			pd = roleService.findObjectById(pd);
			// 增
			map.put("adds", pd.getString("ADD_QX"));
			// 删
			map.put("dels", pd.getString("DEL_QX"));
			// 改
			map.put("edits", pd.getString("EDIT_QX"));
			// 查
			map.put("chas", pd.getString("CHA_QX"));
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if("admin".equals(USERNAME)){
				// admin用户拥有所有按钮权限
				buttonQXnamelist = fhbuttonService.listAll(pd);
			}else{
				// (主副职角色综合按钮权限)
				if(Tools.notEmpty(ROLE_IDS)){
					ROLE_IDS = ROLE_IDS + ROLE_ID;
					String arryROLE_ID[] = ROLE_IDS.split(",fh,");
					// (主职角色按钮权限)
					buttonQXnamelist = buttonrightsService.listAllBrAndQxnameByZF(arryROLE_ID);
				}else{
					// 此角色拥有的按钮权限标识列表
					buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd);
				}
			}
			for(int i=0;i<buttonQXnamelist.size();i++){
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"),"1");			//按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return map;
	}
	
	/**获取用户权限(处理副职角色)
	 * @param session
	 * @return
	 */
	public Map<String, List<String>> getUQX2(String USERNAME){
		PageData pd = new PageData();
		Map<String, List<String>> maps = new HashMap<String, List<String>>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			PageData userpd = new PageData();
			// 通过用户名获取用户信息
			userpd = userService.findByUsername(pd);
			String ROLE_IDS = userpd.getString("ROLE_IDS");
			if(Tools.notEmpty(ROLE_IDS)){
				String arryROLE_ID[] = ROLE_IDS.split(",fh,");
				PageData rolePd = new PageData();
				List<String> addsList = new ArrayList<String>();
				List<String> delsList = new ArrayList<String>();
				List<String> editsList = new ArrayList<String>();
				List<String> chasList = new ArrayList<String>();
				for(int i=0;i<arryROLE_ID.length;i++){
					rolePd.put("ROLE_ID", arryROLE_ID[i]);
					rolePd = roleService.findObjectById(rolePd);
					addsList.add(rolePd.getString("ADD_QX"));
					delsList.add(rolePd.getString("DEL_QX"));
					editsList.add(rolePd.getString("EDIT_QX"));
					chasList.add(rolePd.getString("CHA_QX"));
				}
				// 增
				maps.put("addsList", addsList);
				// 删
				maps.put("delsList", delsList);
				// 改
				maps.put("editsList", editsList);
				// 查
				maps.put("chasList", chasList);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return maps;
	}
	
	/** 更新登录用户的IP
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}  
	
}
