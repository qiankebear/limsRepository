package com.fh.controller.project.projectmanager;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import com.fh.entity.project.KitRecord;
import com.fh.entity.project.ProjectManager;
import com.fh.entity.system.User;
import com.fh.service.customer.CustomerService;
import com.fh.service.project.samplemanager.impl.SampleManagerService;
import com.fh.service.system.user.impl.UserService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.project.projectmanager.ProjectManagerManager;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


/**
 * 项目管理
 * @author wuan
 * @version 1.0
 */
@Controller
@RequestMapping(value="/projectmanager")
public class ProjectManagerController extends BaseController {
	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "projectmanager/list.do";
	@Resource(name="projectmanagerService")
	private ProjectManagerManager projectmanagerService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "CustomerService")
	private CustomerService customerService;
	@Resource(name = "samplemanagerService")
	private SampleManagerService sampleManagerService;

/**保存
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/save")
	public ModelAndView save(String choice,String choices) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ProjectManager");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 项目负责人
		String admin = (String) pd.get("sl");
		// 实验员
		String tester = (String) pd.get("TESTER");
		// 访客
		String visitor = (String) pd.get("VISITOR");
		// 实验员
		String[] testerList = 	tester.split(",");
		// 访客
		String[] visitorList = visitor.split(",");
		long clientId = Long.valueOf(pd.get("client").toString());
		pd.put("project_client",clientId);
		projectmanagerService.save(pd);
		long pId = Long.valueOf(pd.get("id").toString());
		// 插入项目跟用户关联
		PageData pd3 = new PageData();
		pd3.put("project_id",pId);
		pd3.put("user_id",admin);
		pd3.put("member_kind",1);
		pd3.put("project_permission",1);
		projectmanagerService.savepu(pd3);
		// 实验员
		for (int i = 0; i < testerList.length; i++) {
			PageData pd4 = new PageData();
			pd3.put("project_id", pId);
			pd3.put("user_id",testerList[i]);
			pd3.put("member_kind",2);
			pd3.put("project_permission",2);
			projectmanagerService.savepu(pd3);
		}
		// 访客
		for (int i = 0; i < visitorList.length; i++) {
			PageData pd4 = new PageData();
			pd3.put("project_id",pId);
			pd3.put("user_id",visitorList[i]);
			pd3.put("member_kind",3);
			pd3.put("project_permission",2);
			projectmanagerService.savepu(pd3);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**保存出库记录
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/outPut")
	public ModelAndView outPut() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增出库数据");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd5 = new PageData();
		KitRecord kitRecord = new KitRecord();
		// 项目id
		long projectId = Long.valueOf(pd.get("id").toString());
		kitRecord.setRepertory_project_id(projectId);

		// 产品id
		long kitId = Long.valueOf(pd.get("sl").toString());
		pd5.put("kit_id",kitId);
		pd5.put("kit_project_id",projectId);
		PageData pd3 = projectmanagerService.findRepByKPId(pd5);
		kitRecord.setKit_repertory_id(kitId);


		// 当前用户
		String user = Jurisdiction.getUsername();
		PageData pd2 = new PageData();
		pd2.put("USERNAME",user);
		PageData user2 = userService.findByUsername(pd2);
		String userId = user2.get("USER_ID").toString();
		kitRecord.setOperation_name(userId);
		// 改变数量
		float changeCount = Float.valueOf(pd.get("change_count").toString());
		kitRecord.setChange_count(changeCount);
		// 当前库存
		float kitNum = Float.valueOf(pd.get("kit_num").toString());
		kitRecord.setCurrent_count(kitNum);
		// 调整后库存
		float completeCount = kitNum - changeCount;
		kitRecord.setComplete_count(completeCount);

		// 出库原因
		String outCurse = pd.get("outcurse").toString();
		kitRecord.setDecrease_reason(outCurse);
		PageData pd4 = new PageData();
		pd4.put("kit_num",completeCount);
		pd4.put("kit_id",kitId);
		pd4.put("kit_project_id",projectId);
		projectmanagerService.editRep(pd4);
		projectmanagerService.saveoutput(kitRecord);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**赋权
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/emp")
	public ModelAndView emp() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"赋权");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData tester = projectmanagerService.findPUByid1(pd);
		tester.remove("project_permission");
		String isUp = pd.get("isup").toString();
		if("1".equals(isUp)) {
			tester.put("project_permission", 1);
			projectmanagerService.editPUser(tester);
		}else if ("2".equals(isUp)){
			tester.put("project_permission", 2);
			projectmanagerService.editPUser(tester);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**保存入库记录
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/inPut")
	public ModelAndView inPut() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增入库数据");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		KitRecord kitRecord = new KitRecord();
		PageData pd1 = new PageData();

		// 项目id
		long projectId = Long.valueOf(pd.get("id").toString());
		kitRecord.setRepertory_project_id(projectId);

		// 产品id
		long kitId = Long.valueOf(pd.get("sl2").toString());
		pd1.put("kit_id",kitId);
		pd1.put("kit_project_id",projectId);
		PageData pd3 = projectmanagerService.findRepByKPId(pd1);

		// 当前用户
		String user = Jurisdiction.getUsername();
		PageData pd2 = new PageData();
		pd2.put("USERNAME",user);
		PageData user2 = userService.findByUsername(pd2);
		String userId = user2.get("USER_ID").toString();
		kitRecord.setOperation_name(userId);
		// 改变数量
		float changeCount = Float.valueOf(pd.get("change_count").toString());
		kitRecord.setChange_count(changeCount);

		if(pd3 != null) {
			long kitRepId = Long.valueOf(pd3.get("kit_id").toString());
			kitRecord.setKit_repertory_id(kitRepId);
			// 当前库存
			float kitNum = Float.valueOf(pd3.get("kit_num").toString()) ;
			kitRecord.setCurrent_count(kitNum);
			// 调整后库存
			float completeCount = kitNum + changeCount;
			kitRecord.setComplete_count(completeCount);
			PageData pd4 = new PageData();
			pd4.put("kit_num", completeCount);
			pd4.put("id", kitRepId);
			projectmanagerService.editInputRep(pd4);
		}else {
			kitRecord.setKit_repertory_id(kitId);
			kitRecord.setCurrent_count(0);
			kitRecord.setComplete_count(changeCount);
			PageData pd4 = new PageData();
			pd4.put("kit_id",kitId);
			pd4.put("kit_num",changeCount);
			pd4.put("kit_project_id",projectId);
			projectmanagerService.saveRep(pd4);
		}
		projectmanagerService.saveinput(kitRecord);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ProjectManager");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("project_id",Long.valueOf(pd.get("id").toString()));
		List<PageData> pd3 = projectmanagerService.findPPByProjectId(pd);
		if (pd3.isEmpty()) {
				projectmanagerService.delete(pd);
				projectmanagerService.deletepu(pd);
				out.write("success");
				out.close();
		}
		out.write("faild");
		out.close();
	}

	/**样本总数确认
	 * @throws Exception
	 */
	@RequestMapping(value="/sampleCheck")
	public ModelAndView sampleCheck(String id) throws Exception{
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername()+"样本总数确认");
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
			pd.put("project_id", id);
			PageData pd3 = sampleManagerService.listSampleCount(pd);
			// 汇总数量
			int allSample = Integer.valueOf(pd3.get("allsample").toString());
			// 导出数量
			int exp = Integer.valueOf(pd3.get("normalsample").toString());
			// 重做数量
			int reform = Integer.valueOf(pd3.get("reformsample").toString());
			int noCheckAndIssueSample = Integer.valueOf(pd3.get("nocheckandissuesample").toString());
			boolean tf;
			String tof = null ;
			if (allSample == (exp + reform + noCheckAndIssueSample)) {
				tf = true;
				tof = "是";
			} else {
				tf = false;
				tof = "否";
			}
		String data = "样本总数量:  " + allSample + "\n" + "导出样本数量:  " + exp + "\n" + "重做样本数量:  " + reform + "\n" + "问题样本数量:  " + noCheckAndIssueSample + "\n" + "比较是否一致:  " + tof;
		// 相当于Jason
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		map.put("result", errInfo);
		map.put("data", data);
		view.setAttributesMap(map);
		mv.setView(view);
		return mv;
	}


	/**结束项目
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/endProject")
	public void endProject(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"结束ProjectManager");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("id",Long.valueOf(pd.get("id").toString()));
		pd.put("PROJECT_STATUS",3);
		projectmanagerService.endProject(pd);
		out.write("success");
		out.close();
	}


/**修改
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ProjectManager");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		long clientId = Long.valueOf(pd.get("client").toString());
		long pId = Long.valueOf(pd.get("id").toString());
		pd.put("project_id",pId);
		// 项目负责人
		String admin = (String) pd.get("sl");
		// 实验员
		String tester = (String) pd.get("TESTER");
		// 访客
		String visitor = (String) pd.get("VISITOR");
		// 实验员
		String[] testerList = 	tester.split(",");
		// 访客
		String[] visitorList = visitor.split(",");
		// 删除关联表
		projectmanagerService.deletepu(pd);
		pd.put("project_client",clientId);

		// 插入项目跟用户关联
		PageData pd3 = new PageData();
		pd3.put("project_id",pId);
		pd3.put("user_id",admin);
		pd3.put("member_kind",1);
		pd3.put("project_permission",1);
		projectmanagerService.savepu(pd3);
		// 实验员
		for (int i = 0; i < testerList.length; i++) {
			PageData pd4 = new PageData();
			pd3.put("project_id", pId);
			pd3.put("user_id",testerList[i]);
			pd3.put("member_kind",2);
			pd3.put("project_permission",2);
			projectmanagerService.savepu(pd3);
		}
		// 访客
		for (int i = 0; i < visitorList.length; i++) {
			PageData pd4 = new PageData();
			pd3.put("project_id",pId);
			pd3.put("user_id",visitorList[i]);
			pd3.put("member_kind",3);
			pd3.put("project_permission",2);
			projectmanagerService.savepu(pd3);
		}
		projectmanagerService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

    /**出入库记录
     * @param
     * @throws Exception
     */

    @RequestMapping(value="/oiPut")
    public ModelAndView oiPut() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改出入库记录");
		// 校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
        	return null;
        }
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        projectmanagerService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ProjectManager");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
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
		List projectAll = projectmanagerService.findprojectall(pd);
		pd.put("user_id",user1.get("USER_ID").toString());
		User userInfo = userService.getUserAndRoleById(user1.get("USER_ID").toString());
		// 获取当前用户的角色
		String roleName = userInfo.getRole().getRNUMBER();
		if("R20171231726481".equals(roleName) || "R20180131375361".equals(roleName)|| "R20170000000001".equals(roleName)){
			List<PageData>	varList = projectmanagerService.list(page);
			List<PageData> projectList1 = new ArrayList<PageData>();
			// projectList.addAll(PUList);
			for (int i = 0; i < varList.size(); i++) {

				// 获取该用户参与的所有项目的id
				long project_id =Long.valueOf(varList.get(i).get("id").toString());
				pd.put("id",project_id);
				PageData project = projectmanagerService.findProjectByUserId(pd);
				List<PageData> projectUser = projectmanagerService.findPUByProjectUser(pd);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String newDate = simpleDateFormat.format((Date)varList.get(i).get("project_starttime"));
                project.put("newDate",newDate);
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
				project.put("roleName",roleName);
				projectList1.add(project);
			}
			mv.addObject("varList", projectList1);
		}else {
			// 通过当前登录用户获取用户和项目的中间表
			List<PageData> PUList = projectmanagerService.findPUByUserIdlistPage(page);
			List<PageData> projectList = new ArrayList<PageData>();
			// projectList.addAll(PUList);
			for (int i = 0; i < PUList.size(); i++) {
				// 获取该用户参与的所有项目的id
				long project_id =Long.valueOf(PUList.get(i).get("project_id").toString());
				pd.put("id",project_id);
				PageData project = projectmanagerService.findProjectByUserId(pd);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String newDate = simpleDateFormat.format((Date)project.get("project_starttime"));
                project.put("newDate",newDate);
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
			mv.addObject("varList", projectList);
		}
		mv.addObject("pd", pd);
		mv.addObject("projectAll",projectAll);
		// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		mv.addObject("user",user);
		mv.setViewName("project/projectmanager/projectmanager_list");
		return mv;
	}
	/**列表所有项目
	/**列表
	/**列表
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/listAllProject")
	public  ModelAndView listAllProject() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ProjectManager");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		// 列出ProjectManager列表
		List<ProjectManager> varList = projectmanagerService.listAllProject(pd);
		mv.setViewName("project/projectmanager/projectmanager_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	/**列表库存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/listRep")
	public  ModelAndView listRep(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表库存");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();

		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		Long project_id = Long.valueOf(pd.get("id").toString());
		List<PageData>	varList = projectmanagerService.findRepById(page);	//列出ProjectManager列表
		List<PageData>	varList1 = projectmanagerService.findInputOutputById(project_id);

		for (int i1 = 0; i1 < varList.size(); i1++) {
			float add = 0;
			float lessen = 0 ;
		for (int i = 0; i < varList1.size(); i++) {
				if (varList.get(i1).get("kit_id").toString().equals(varList1.get(i).get("kit_repertory_id").toString())){
					// 如果为增加类型
					if(Integer.valueOf(varList1.get(i).get("change_type").toString())==0){
						add += Float.valueOf(varList1.get(i).get("change_count").toString());
					}
					// 如果为减少类型
					if(Integer.valueOf(varList1.get(i).get("change_type").toString())==1){
						lessen += Float.valueOf(varList1.get(i).get("change_count").toString());
					}
				}
			}
			varList.get(i1).put("add",add);
			varList.get(i1).put("lessen",lessen);
		}
		mv.setViewName("project/projectmanager/projectmanager_listRep");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**相关人员
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/listPerson")
	public  ModelAndView listPerson(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"获取相关人员");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();

		PageData pd = new PageData();
		pd = this.getPageData();
		PageData admin = projectmanagerService.findPUById(pd);	//列出ProjectManager列表
		String admin1  = admin.get("NAME").toString();
		List<PageData> testerList = projectmanagerService.findPU1ById(pd);
		List<String> tester1List = new ArrayList<>();
		for (int i = 0; i < testerList.size(); i++) {
			String tester = testerList.get(i).get("NAME").toString();
			tester1List.add(tester);
		}
		String mListStr = listToString(tester1List);
		List<PageData> visitor = projectmanagerService.findPU2ById(pd);
			List<String> visitorList = new ArrayList<>();
			for (int i = 0; i < visitor.size(); i++) {
				String visitor1 = visitor.get(i).get("NAME").toString();
				visitorList.add(visitor1);
			}
			String visitor1 = listToString(visitorList);
		mv.setViewName("project/projectmanager/projectmanager_listPerson");
		mv.addObject("admin", admin1);
		mv.addObject("testerList", mListStr);
		mv.addObject("visitor", visitor1);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}


	/**
	 * 去新增页面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = userService.findName(pd);
		List<PageData> customerList = customerService.listCustomer(page);
		mv.setViewName("project/projectmanager/projectmanager_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("varList",varList);
		mv.addObject("customerList",customerList);
		return mv;
	}	
	

/**去修改页面
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 根据ID读取
		pd = projectmanagerService.findById(pd);
        List<PageData> varList = userService.findAllName(page);
		List<PageData> customerList = customerService.listCustomer(page);
		long projectId = Long.valueOf(pd.get("id").toString());
		List<PageData> tester = new ArrayList<>();
		// 项目负责人
		PageData admin = projectmanagerService.findPUById(pd);
		// 实验员
		List<PageData> pd1 = projectmanagerService.findPU1ById(pd);
		for (int i = 0; i < varList.size(); i++) {
			for (int i1 = 0; i1 < pd1.size(); i1++) {
				if(pd1.get(i1).get("user_id").toString().equals(varList.get(i).get("USER_ID").toString())){
					varList.get(i).put("verify",1);
				}
			}
		}
		// 访客
		List<PageData> pd2 = projectmanagerService.findPU2ById(pd);
		for (int i = 0; i < varList.size(); i++) {
			for (int i1 = 0; i1 < pd2.size(); i1++) {
				if(pd2.get(i1).get("user_id").toString().equals(varList.get(i).get("USER_ID").toString())){
					varList.get(i).put("verify1",2);
				}
			}
		}
		mv.setViewName("project/projectmanager/projectmanager_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("pd1",pd1);
		mv.addObject("pd2",pd2);
		mv.addObject("admin",admin);
        mv.addObject("varList",varList);
        mv.addObject("customerList",customerList);
		return mv;
	}

	/**去出库页面
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/goOutPut")
	public ModelAndView goOutPut()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd2 = new PageData();
		pd = this.getPageData();

		Long id = Long.valueOf (pd.get("id").toString());
		pd2.put("id",id);
		List<PageData> pd1 = projectmanagerService.findRepByProjectId(pd);
		mv.setViewName("project/projectmanager/projectmanager_output");
		mv.addObject("msg", "outPut");
		mv.addObject("pd1", pd1);
		mv.addObject("pd2",pd2);
		return mv;
	}
	/**去入库页面
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/goInPut")
	public ModelAndView goInPut(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pd2 = new PageData();
		page.setPd(pd);
		Long id = Long.valueOf (pd.get("id").toString());
		pd2.put("pid",id);
//		List<PageData> pd1 = projectmanagerService.listKits1(page);
//		List<PageData> pd3 = projectmanagerService.listKits2(page);
		mv.setViewName("project/projectmanager/projectmanager_input");
		mv.addObject("msg", "outPut");
//		mv.addObject("pd1", pd1);
		mv.addObject("pd2",pd2);
//		mv.addObject("pd3",pd3);
		return mv;
	}

	/**去入库页面
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/listKits")
	public ModelAndView listKits(String type)throws Exception{
		ModelAndView mv = this.getModelAndView();
		Page page = new Page();
		List<PageData> pd1 = new ArrayList<>();
		if("1".equals(type)){
			pd1 = projectmanagerService.listKits1(page);
		}else if("2".equals(type)){
 			pd1 = projectmanagerService.listKits2(page);
		}
		// 相当于Jason
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("msg", "outPut");
		attributes.put("pd1", pd1);
		view.setAttributesMap(attributes);
		mv.setView(view);
		return mv;
	}

	/**去赋权页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEmp")
	public ModelAndView goEmp()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 根据ID获取项目
		pd = projectmanagerService.findById(pd);
		long projectId = (Long) pd.get("id");
		// 通过项目id找到中间表权限为1的项目负责人
		PageData pd2 = projectmanagerService.findPUById(pd);
		// 通过id找到所有该项目的实验员
		List<PageData> varList = projectmanagerService.findAllPUById(projectId);
		String user = Jurisdiction.getUsername();
		String name ;
		if(pd2 != null){
			// 获取项目负责人的username与当前登录者进行比较， 如果相同则可以修改
			PageData pd3 = userService.findNameById((String) pd2.get("user_id"));
			name = (String) pd3.get("USERNAME");
		}else{
			name = "null";
		}
		// 当前登录用户
		mv.setViewName("project/projectmanager/projectmanager_emp");
		mv.addObject("msg", "emp");
		mv.addObject("pd", pd);
		mv.addObject("name", name);
		mv.addObject("user", user);
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	

/**批量删除
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ProjectManager");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return null;
		}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			projectmanagerService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	

/**导出到excel
	 * @param
	 * @throws Exception
	 */

	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出ProjectManager到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		//1
		titles.add("项目编号");
		//2
		titles.add("项目名称");
		//3
		titles.add("项目缩写");
		//4
		titles.add("项目状态");
		//5
		titles.add("复核孔个数");
		//6
		titles.add("项目开始时间");
		//7
		titles.add("项目结束时间");
		dataMap.put("titles", titles);
		List<PageData> varOList = projectmanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0; i<varOList.size(); i++){
			PageData vpd = new PageData();
			//1
			vpd.put("var1", varOList.get(i).getString("PROJECT_NUMBER"));
			//2
			vpd.put("var2", varOList.get(i).getString("PROJECT_NAME"));
			//3
			vpd.put("var3", varOList.get(i).getString("PROJECT_NUMBER_ABBREVIATION"));
			//4
			vpd.put("var4", varOList.get(i).get("PROJECT_STATUS").toString());
			//5
			vpd.put("var5", varOList.get(i).get("RECHECK_HOLE_AMOUNT").toString());
			//6
			vpd.put("var6", varOList.get(i).getString("PROJECT_STARTTIME"));
			//7
			vpd.put("var7", varOList.get(i).getString("PROJECT_ENDTIME"));
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	/**
	 * for all jdk version
	 * @param mList
	 * @return
	 */
	public static String listToString(List<String> mList) {
		String convertedListStr = "";
		if (null != mList && mList.size() > 0) {
			String[] mListArray = mList.toArray(new String[mList.size()]);
			for (int i = 0; i < mListArray.length; i++) {
				if (i < mListArray.length - 1) {
					convertedListStr += mListArray[i] + ",";
				} else {
					convertedListStr += mListArray[i];
				}
			}
			return convertedListStr;
		} else{
			return "无";
		}
	}

	/**
	 * 判断项目编号是否存在
	 * @return
	 */
	@RequestMapping(value="/hasNumber")
	@ResponseBody
	public Object hasNumber(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if (!pd.get("NUMBER").toString().equals(pd.get("NUMBER1").toString())) {
                PageData pd1 = projectmanagerService.findProjectByNumber(pd);
                if (pd1 != null) {
                    errInfo = "error";
                }
            }
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	/**
	 * 判断项目名称是否存在
	 * @return
	 */
	@RequestMapping(value="/hasName")
	@ResponseBody
	public Object hasName(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if (!pd.get("NAME").toString().equals(pd.get("NAME1").toString())) {
                PageData pd1 = projectmanagerService.findProjectByName(pd);
                if (pd1 != null) {
                    errInfo = "error";
                }
            }
		} catch(Exception e){
			logger.error(e.toString(), e);
	}
		// 返回结果
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
}

