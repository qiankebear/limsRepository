package com.fh.controller.activiti.rutask;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fh.service.project.projectmanager.ProjectManagerManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.activiti.AcBusinessController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.system.fhsms.FhsmsManager;

/** 
 * 说明：待办任务
 * @author ：FH Q313596790
 * @date：2018-02-10
 * @version 1.0
 */
@Controller
@RequestMapping(value="/rutask")
public class RuTaskController extends AcBusinessController {
	//菜单地址(权限用)
	String menuUrl = "rutask/list.do";
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	@Resource(name="fhsmsService")
	private FhsmsManager fhsmsService;
	@Resource(name="projectmanagerService")
	private ProjectManagerManager projectmanagerService;
	
	/**待办任务列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表待办任务");
		/*if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)*/
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//关键词检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		//开始时间
		String lastStart = pd.getString("lastStart");
		//结束时间
		String lastEnd = pd.getString("lastEnd");
		//定义开始时间变量
		String startTime = " 00:00:00";
		String endTime = " 23:59:59";
		if(null != lastStart  && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+startTime);
		}
		if(null != lastEnd  && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+endTime);
		}
		//查询当前用户的任务(用户名查询)
		pd.put("USERNAME", Jurisdiction.getUsername());
		//查询当前用户的任务(角色编码查询)
		pd.put("RNUMBERS", Jurisdiction.getRnumber());
		page.setPd(pd);
		//列出Rutask列表
		List<PageData>	varList = ruprocdefService.list(page);
		for(int i=0;i<varList.size();i++){
			//流程申请人
			varList.get(i).put("INITATOR", getInitiator(varList.get(i).getString("PROC_INST_ID_")));
		}
		mv.setViewName("activiti/rutask/rutask_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**待办任务列表(只显示5条,用于后台顶部小铃铛左边显示)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getList")
	@ResponseBody
	public Object getList(Page page) throws Exception{
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>(16);
		//查询当前用户的任务(用户名查询)
		pd.put("USERNAME", Jurisdiction.getUsername());
		//查询当前用户的任务(角色编码查询)
		pd.put("RNUMBERS", Jurisdiction.getRnumber());
		page.setPd(pd);
		page.setShowCount(5);
		//列出Rutask列表
		List<PageData>	varList = ruprocdefService.list(page);
		List<PageData> pdList = new ArrayList<PageData>();
		for(int i=0;i<varList.size();i++){
			PageData tpd = new PageData();
			//任务名称
			tpd.put("NAME_", varList.get(i).getString("NAME_"));
			//流程名称
			tpd.put("PNAME_", varList.get(i).getString("PNAME_"));
			pdList.add(tpd);
		}
		map.put("list", pdList);
		map.put("taskCount", page.getTotalResult());
		return AppUtil.returnObject(pd, map);
	}
	
	/**去办理任务页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goHandle")
	public ModelAndView goHandle()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//列出流程变量列表
		List<PageData>	varList = ruprocdefService.varList(pd);
		//历史任务节点列表
		List<PageData>	hitaskList = ruprocdefService.hiTaskList(pd);
		//根据耗时的毫秒数计算天时分秒
		for(int i=0;i<hitaskList.size();i++){
			if(null != hitaskList.get(i).get("DURATION_")){
				Long ztime = Long.parseLong(hitaskList.get(i).get("DURATION_").toString());
				Long tian = ztime / (1000*60*60*24);
				Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
				Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
				Long miao = (ztime % (1000*60*60*24))%(1000*60*60)%(1000*60)/1000;
				hitaskList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分"+miao+"秒");
			}
		}
		String FILENAME = URLDecoder.decode(pd.getString("FILENAME"), "UTF-8");
		//生成当前任务节点的流程图片
		createXmlAndPngAtNowTask(pd.getString("PROC_INST_ID_"),FILENAME);
		pd.put("FILENAME", FILENAME);
		String imgSrcPath = PathUtil.getClasspath()+Const.FILEACTIVITI+FILENAME;
		//解决图片src中文乱码，把图片转成base64格式显示(这样就不用修改tomcat的配置了)
		pd.put("imgSrc", "data:image/jpeg;base64,"+ImageAnd64Binary.getImageStr(imgSrcPath));
		mv.setViewName("activiti/rutask/rutask_handle");
		mv.addObject("varList", varList);
		mv.addObject("hitaskList", hitaskList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**办理任务
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/handle")
	public ModelAndView handle() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"办理任务");
		Session session = Jurisdiction.getSession();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd1 = new PageData();
		pd = this.getPageData();
		//任务ID
		String taskId = pd.getString("ID_");
		String sfrom = "";
		Object ofrom = getVariablesByTaskIdAsMap(taskId,"审批结果");
		if(null != ofrom){
			sfrom = ofrom.toString();
		}
		Map<String,Object> map = new LinkedHashMap<String, Object>(16);
		//审批人的姓名+审批意见
		String OPINION = sfrom + Jurisdiction.getU_name() + ",fh,"+pd.getString("OPINION");
		String msg = pd.getString("msg");
		String projectId = pd.getString("projectId");
		if("yes".equals(msg)){
			/*审批结果
			设置流程变量*/
			map.put("审批结果", "【批准】" + OPINION);
			setVariablesByTaskIdAsMap(taskId,map);
			setVariablesByTaskId(taskId,"RESULT","批准");
			completeMyPersonalTask(taskId);
		}else{
			/*驳回
			审批结果
			设置流程变量*/
			map.put("审批结果", "【驳回】" + OPINION);
			setVariablesByTaskIdAsMap(taskId,map);
			setVariablesByTaskId(taskId,"RESULT","驳回");
			completeMyPersonalTask(taskId);
		}
		try{
			//移除流程变量(从正在运行中)
			removeVariablesByPROC_INST_ID_(pd.getString("PROC_INST_ID_"),"RESULT");
		}catch(Exception e){
			/*此流程变量在历史中**/
		}
		try{
			//下一待办对象
			String ASSIGNEE_ = pd.getString("ASSIGNEE_");
			if(Tools.notEmpty(ASSIGNEE_)){
				//指定下一任务待办对象
				setAssignee(session.getAttribute("TASKID").toString(),ASSIGNEE_);
			}else{
				Object os = session.getAttribute("YAssignee");
				if(null != os && !"".equals(os.toString())){
					//没有指定就是默认流程的待办人
					ASSIGNEE_ = os.toString();
				}else{
					pd1.put("id",projectId);
					pd1.put("PROJECT_STATUS",3);
					projectmanagerService.endProject(pd1);
					//没有任务监听时，默认流程结束，发送站内信给任务发起人
					trySendSms(mv,pd);
				}
			}
			//用于给待办人发送新任务消息
			mv.addObject("ASSIGNEE_",ASSIGNEE_);
		}catch(Exception e){
			/*手动指定下一待办人，才会触发此异常。
			 * 任务结束不需要指定下一步办理人了,发送站内信通知任务发起人**/
			trySendSms(mv,pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**尝试站内信
	 * @param mv
	 * @param pd
	 * @throws Exception
	 */
	public void trySendSms(ModelAndView mv,PageData pd)throws Exception{
		//列出历史流程变量列表
		List<PageData>	hivarList = hiprocdefService.hivarList(pd);
		for(int i=0;i<hivarList.size();i++){
			if("USERNAME".equals(hivarList.get(i).getString("NAME_"))){
				sendSms(hivarList.get(i).getString("TEXT_"));
				mv.addObject("FHSMS",hivarList.get(i).getString("TEXT_"));
				break;
			}
		}
	}
	
	/**发站内信通知审批结束
	 * @param USERNAME
	 * @throws Exception
	 */
	public void sendSms(String USERNAME) throws Exception{
		PageData pd = new PageData();
		//ID
		pd.put("SANME_ID", this.get32UUID());
		//发送时间
		pd.put("SEND_TIME", DateUtil.getTime());
		//主键
		pd.put("FHSMS_ID", this.get32UUID());
		//类型1：收信
		pd.put("TYPE", "1");
		//收信人
		pd.put("FROM_USERNAME", USERNAME);
		pd.put("TO_USERNAME", "系统消息");
		pd.put("CONTENT", "您申请的任务已经审批完毕,请到已办任务列表查看");
		pd.put("STATUS", "2");
		fhsmsService.save(pd);
	}
	
	/**去审批详情页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/details")
	public ModelAndView goDelegate(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("activiti/rutask/handle_details");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
}
