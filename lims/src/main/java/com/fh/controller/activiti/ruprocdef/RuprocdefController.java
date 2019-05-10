package com.fh.controller.activiti.ruprocdef;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.activiti.AcBusinessController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.system.fhsms.FhsmsManager;

/** 
 * 说明：正在运行的流程
 * @author ：FH Q313596790
 * @date：2018-02-10
 * @version 1.0
 */
@Controller
@RequestMapping(value="/ruprocdef")
public class RuprocdefController extends AcBusinessController {
	/**
	 * @param menuUrl 菜单地址(权限用)
	 */
	private String menuUrl = "ruprocdef/list.do";
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="fhsmsService")
	private FhsmsManager fhsmsService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Ruprocdef");
		/*if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)*/
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		// 开始时间
		String lastStart = pd.getString("lastStart");
		//结束时间
		String lastEnd = pd.getString("lastEnd");
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		}
		page.setPd(pd);
		// 列出Ruprocdef列表
		List<PageData>	varList = ruprocdefService.list(page);
		for(int i=0;i<varList.size();i++){
			// 流程申请人
			varList.get(i).put("INITATOR", getInitiator(varList.get(i).getString("PROC_INST_ID_")));
		}
		mv.setViewName("activiti/ruprocdef/ruprocdef_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**去委派页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/goDelegate")
	public ModelAndView goDelegate(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("activiti/ruprocdef/ruprocdef_delegate");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**委派
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delegate")
	public ModelAndView delegate() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new LinkedHashMap<String, Object>(16);
		// 审批结果中记录委派
		map.put("审批结果", " (任务由["+Jurisdiction.getUsername()+"]委派) ");
		// 设置流程变量
		setVariablesByTaskIdAsMap(pd.getString("ID_"),map);
		setAssignee(pd.getString("ID_"),pd.getString("ASSIGNEE_"));
		//用于给待办人发送新任务消息
		mv.addObject("ASSIGNEE_",pd.getString("ASSIGNEE_"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**激活or挂起任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/onoffTask")
	@ResponseBody
	public Object onoffTask()throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		ruprocdefService.onoffTask(pd);
		// 返回结果
		map.put("msg", "ok");
		return AppUtil.returnObject(pd, map);
	}
	
	/**作废流程
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		// 作废原因
		String reason = "【作废】"+Jurisdiction.getU_name()+"："+URLDecoder.decode(pd.getString("reason"), "UTF-8");
		/*
		 *任务结束时发站内信通知审批结束
		 * 列出历史流程变量列表
		 */
		List<PageData>	hivarList = hiprocdefService.hivarList(pd);
		for(int i=0;i<hivarList.size();i++){
			if("USERNAME".equals(hivarList.get(i).getString("NAME_"))){
				sendSms(hivarList.get(i).getString("TEXT_"));
				break;
			}
		}
		// 作废流程
		deleteProcessInstance(pd.getString("PROC_INST_ID_"),reason);
		out.write("success");
		out.close();
	}
	
	/**发站内信通知审批结束
	 * @param USERNAME
	 * @throws Exception
	 */
	public void sendSms(String USERNAME) throws Exception{
		PageData pd = new PageData();
		//I D
		pd.put("SANME_ID", this.get32UUID());
		// 发送时间
		pd.put("SEND_TIME", DateUtil.getTime());
		// 主键
		pd.put("FHSMS_ID", this.get32UUID());
		// 类型1：收信
		pd.put("TYPE", "1");
		// 收信人
		pd.put("FROM_USERNAME", USERNAME);
		pd.put("TO_USERNAME", "系统消息");
		pd.put("CONTENT", "您申请的任务已经被作废,请到已办任务列表查看");
		pd.put("STATUS", "2");
		fhsmsService.save(pd);
	}
	
}
