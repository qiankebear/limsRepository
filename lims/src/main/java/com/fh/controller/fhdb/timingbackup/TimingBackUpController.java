package com.fh.controller.fhdb.timingbackup;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DbFH;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.QuartzManager;
import com.fh.util.Tools;
import com.fh.service.fhdb.timingbackup.TimingBackUpManager;

/**
 * 说明：定时备份
 * 创建人：FH Q313596790
 * 创建时间：2016-04-09
 * @version 1.0
 * @update :修改不规则注释，局部变量命名不规范问题
 */
@Controller
@RequestMapping(value="/timingbackup")
public class TimingBackUpController extends BaseController {
	//任务组
	private static String JOB_GROUP_NAME = "DB_JOBGROUP_NAME";
	//触发器组
	private static String TRIGGER_GROUP_NAME = "DB_TRIGGERGROUP_NAME";
	//菜单地址(权限用)
	String menuUrl = "timingbackup/list.do";
	@Resource(name="timingbackupService")
	private TimingBackUpManager timingbackupService;

	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增TimingBackUp");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//任务名称
		String jobName = pd.getString("TABLENAME")+"_"+Tools.getRandomNum();
		//时间规则
		String FHTime = pd.getString("FHTIME");
		//表名or整库(all)
		String tableName = pd.getString("TABLENAME");
		String tiMingBackUp_id = this.get32UUID();
		//主键
		pd.put("TIMINGBACKUP_ID", tiMingBackUp_id);
		//任务名称
		pd.put("JOBNAME", jobName);
		//创建时间
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		//状态
		pd.put("STATUS", "1");
		timingbackupService.save(pd);
		//添加任务
		this.addJob(jobName, FHTime, tableName, tiMingBackUp_id);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除TimingBackUp");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		//删除任务
		this.removeJob(timingbackupService.findById(pd).getString("JOBNAME"));
		//删除数据库记录
		timingbackupService.delete(pd);
		out.write("success");
		out.close();
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改TimingBackUp");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//删除任务(修改时可能会修改要备份的表，所以任务名称会改变，所以执行删除任务再新增任务来完成修改任务的效果)
		this.removeJob(timingbackupService.findById(pd).getString("JOBNAME"));
		//任务名称
		String jobName = pd.getString("TABLENAME")+"_"+Tools.getRandomNum();
		//时间规则
		String FHTime = pd.getString("FHTIME");
		//表名or整库(all)//
		String tableName = pd.getString("TABLENAME");
		//任务数据库记录的ID
		String tiMingBackUp_id = pd.getString("TIMINGBACKUP_ID");
		//添加任务
		this.addJob(jobName, FHTime, tableName,tiMingBackUp_id);
		//任务名称
		pd.put("JOBNAME", jobName);
		//创建时间
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));
		//状态
		pd.put("STATUS", "1");
		timingbackupService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TimingBackUp");
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
		String startTime = " 00:00:00";
		String endTime = " 00:00:00";
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+startTime);
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+endTime);
		}
		page.setPd(pd);
		//列出TimingBackUp列表
		List<PageData>	varList = timingbackupService.list(page);
		mv.setViewName("fhdb/timingbackup/timingbackup_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject("QX", Jurisdiction.getHC());
		return mv;
	}

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Object[] arrOb = DbFH.getTables();
		List<String> tblist = (List<String>)arrOb[1];
		//所有表
		mv.addObject("varList", tblist);
		//数据库类型
		mv.addObject("dbtype", arrOb[2]);
		mv.setViewName("fhdb/timingbackup/timingbackup_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**去修改页面
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Object[] arrOb = DbFH.getTables();
		List<String> tblist = (List<String>)arrOb[1];
		//所有表
		mv.addObject("varList", tblist);
		//数据库类型
		mv.addObject("dbtype", arrOb[2]);
		//根据ID读取
		pd = timingbackupService.findById(pd);
		mv.setViewName("fhdb/timingbackup/timingbackup_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除TimingBackUp");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(Tools.notEmpty(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for(int i=0;i<ArrayDATA_IDS.length;i++){
				pd.put("TIMINGBACKUP_ID", ArrayDATA_IDS[i]);
				//删除任务
				this.removeJob(timingbackupService.findById(pd).getString("JOBNAME"));
			}
			//删除数据库记录
			timingbackupService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**切换状态
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/changeStatus")
	@ResponseBody
	public Object changeStatus() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"切换状态");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>(16);
		pd = this.getPageData();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		int STATUS = Integer.parseInt(pd.get("STATUS").toString());
		//根据ID读取
		pd = timingbackupService.findById(pd);
		if(STATUS == 2){
			pd.put("STATUS", 2);
			//删除任务
			this.removeJob(pd.getString("JOBNAME"));
		}else{
			pd.put("STATUS", 1);
			//任务名称
			String JOBNAME = pd.getString("JOBNAME");
			//时间规则
			String FHTIME = pd.getString("FHTIME");
			//表名or整库(all)
			String TABLENAME = pd.getString("TABLENAME");
			//任务数据库记录的ID
			String TIMINGBACKUP_ID = pd.getString("TIMINGBACKUP_ID");
			//添加任务
			this.addJob(JOBNAME, FHTIME, TABLENAME, TIMINGBACKUP_ID);
		}
		timingbackupService.changeStatus(pd);
		pd.put("msg", "ok");
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
		logBefore(logger, Jurisdiction.getUsername()+"导出TimingBackUp到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>(16);
		List<String> titles = new ArrayList<String>();
		//1
		titles.add("任务名称");
		//2
		titles.add("创建时间");
		//3
		titles.add("表名");
		//4
		titles.add("状态");
		//5
		titles.add("时间规则");
		//6
		titles.add("规则说明");
		//7
		titles.add("备注");
		dataMap.put("titles", titles);
		List<PageData> varOList = timingbackupService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			//1
			vpd.put("var1", varOList.get(i).getString("JOBNAME"));
			//2
			vpd.put("var2", varOList.get(i).getString("CREATE_TIME"));
			//3
			vpd.put("var3", varOList.get(i).getString("TABLENAME"));
			//4
			vpd.put("var4", varOList.get(i).get("STATUS").toString());
			//5
			vpd.put("var5", varOList.get(i).getString("FHTIME"));
			//6
			vpd.put("var6", varOList.get(i).getString("TIMEEXPLAIN"));
			//7
			vpd.put("var7", varOList.get(i).getString("BZ"));
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**新增任务
	 * @param jobName	任务名称
	 * @param FHTime	时间规则
	 * @paramparameter 传的参数
	 * @param tiMingBackUp_id 定时备份任务的ID
	 *
	 */
	public void addJob(String jobName, String FHTime, String tableName, String tiMingBackUp_id){
		Map<String, Object> parameter = new HashMap<String, Object>(16);
		parameter.put("TABLENAME", tableName);
		parameter.put("TIMINGBACKUP_ID", tiMingBackUp_id);
		QuartzManager.addJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, DbBackupQuartzJob.class, FHTime, parameter);
	}

	/**删除任务
	 * @param jobName
	 */
	public void removeJob(String jobName){
		QuartzManager.removeJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
