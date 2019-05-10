package com.fh.controller.fhoa.myleave;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.activiti.AcStartController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.fhoa.myleave.MyleaveManager;

/** 
 * 说明：请假申请
 * 创建人：FH Q313596790
 * 修改时间：2018-02-09
 */
@Controller
@RequestMapping(value="/myleave")
public class MyleaveController extends AcStartController {
	//菜单地址(权限用)
	String menuUrl = "myleave/list.do";
	@Resource(name="myleaveService")
	private MyleaveManager myleaveService;
	
	/**保存请假单
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(){
		logBefore(logger, Jurisdiction.getUsername()+"新增Myleave");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//主键
		pd.put("MYLEAVE_ID", this.get32UUID());
		//用户名
		pd.put("USERNAME", Jurisdiction.getUsername());
		try {
			/** 工作流的操作 **/
			Map<String,Object> map = new LinkedHashMap<String, Object>(16);
			//当前用户的姓名
			map.put("请假人员", Jurisdiction.getU_name());
			map.put("开始时间", pd.getString("STARTTIME"));
			map.put("结束时间", pd.getString("ENDTIME"));
			map.put("请假时长", pd.getString("WHENLONG"));
			map.put("请假类型", pd.getString("TYPE"));
			map.put("请假事由", pd.getString("REASON"));
			//指派代理人为当前用户
			map.put("USERNAME", Jurisdiction.getUsername());
			//TODO KEY_leave   KEY_approvl
			//启动流程实例(请假单流程)通过KEY
			startProcessInstanceByKeyHasVariables("KEY_leave",map);
			//记录存入数据库
			myleaveService.save(pd);
			//用于给待办人发送新任务消息
			mv.addObject("ASSIGNEE_",Jurisdiction.getUsername());
			mv.addObject("msg","success");
		} catch (Exception e) {
			mv.addObject("errer","errer");
			mv.addObject("msgContent","请联系管理员部署相应业务流程!");
		}								
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Myleave");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		myleaveService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Myleave");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		myleaveService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Myleave");
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
		//除admin用户外，只能查看自己的数据
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername());
		page.setPd(pd);
		//列出Myleave列表
		List<PageData>	varList = myleaveService.list(page);
		mv.setViewName("fhoa/myleave/myleave_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("fhoa/myleave/myleave_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据ID读取
		pd = myleaveService.findById(pd);
		mv.setViewName("fhoa/myleave/myleave_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Myleave");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			myleaveService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Myleave到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>(16);
		List<String> titles = new ArrayList<String>();
		//1
		titles.add("用户名");
		//2
		titles.add("类型");
		//3
		titles.add("开始时间");
		//4
		titles.add("结束时间");
		//5
		titles.add("时长");
		//6
		titles.add("事由");
		//7
		titles.add("审批意见");
		dataMap.put("titles", titles);
		List<PageData> varOList = myleaveService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			//1
			vpd.put("var1", varOList.get(i).getString("USERNAME"));
			//2
			vpd.put("var2", varOList.get(i).getString("TYPE"));
			//3
			vpd.put("var3", varOList.get(i).getString("STARTTIME"));
			//4
			vpd.put("var4", varOList.get(i).getString("ENDTIME"));
			//5
			vpd.put("var5", varOList.get(i).getString("WHENLONG"));
			//6
			vpd.put("var6", varOList.get(i).getString("REASON"));
			//7
			vpd.put("var7", varOList.get(i).getString("OPINION"));
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
}
