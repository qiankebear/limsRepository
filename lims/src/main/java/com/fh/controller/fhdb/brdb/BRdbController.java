package com.fh.controller.fhdb.brdb;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
import com.fh.util.FileUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.fhdb.brdb.BRdbManager;

/** 
 * 说明：数据库管理(备份和还原)
 * 创建人：FH Q313596790
 * 创建时间：2016-03-30
 * @ param FHDB_ID      主键
 * @ param USERNAME     操作用户
 * @ param BACKUP_TIME  备份时间
 * @ param TABLENAME    表名
 * @ param SQLPATH      存储位置
 * @ param DBSIZE       文件大小
 * @ param TYPE         1: 备份整库，2：备份某表
 * @ param BZ           备注
 * @ param dbtype       数据库类型
 */
@Controller
@RequestMapping(value="/brdb")
public class BRdbController extends BaseController {
	// 菜单地址(权限用)数据还原菜单
	String menuUrl = "brdb/list.do";
	// 菜单地址(权限用)数据备份菜单
	String menuUrlb = "brdb/listAllTable.do";
	@Resource(name="brdbService")
	private BRdbManager brdbService;
	
	/**列出所有表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listAllTable")
	public ModelAndView listAllTable() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列出所有表");
		// 校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		if(!Jurisdiction.buttonJurisdiction(menuUrlb, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		Object[] arrOb = DbFH.getTables();
		List<String> tblist = (List<String>)arrOb[1];
		mv.setViewName("fhdb/brdb/table_list");
		// 所有表
		mv.addObject("varList", tblist);
		// 数据库类型
		mv.addObject("dbtype", arrOb[2]);
		// 数据库名
		mv.addObject("databaseName", arrOb[0]);
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	 /**备份全库
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/backupAll")
	@ResponseBody
	public Object backupAll(){
		String username = Jurisdiction.getUsername();
		logBefore(logger, username+"备份全库");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrlb, "add")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String kackupPath;
		try {
			// 调用数据库备份
			kackupPath = DbFH.getDbFH().backup("").toString();
			if(Tools.notEmpty(kackupPath) && !"errer".equals(kackupPath)){
				pd.put("FHDB_ID", this.get32UUID());
				pd.put("USERNAME", username);
				pd.put("BACKUP_TIME", Tools.date2Str(new Date()));
				pd.put("TABLENAME", "整库");
				pd.put("SQLPATH", kackupPath);
				pd.put("DBSIZE", FileUtil.getFilesize(kackupPath));
				pd.put("TYPE", 1);
				pd.put("BZ", username+"备份全库操作");
				pd.put("msg", "ok");
				try {
					brdbService.save(pd);
				} catch (Exception e) {
					pd.put("msg", "no");
				}
			}else{
				pd.put("msg", "no");
			}
		} catch (InterruptedException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		} catch (ExecutionException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**备份单表
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/backupTable")
	@ResponseBody
	public Object backupTable(){
		String username = Jurisdiction.getUsername();
		logBefore(logger, username+"备份单表");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrlb, "add")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String, Object>(16);
		pd = this.getPageData();
		// 页面ajax传过来的表名
		String TABLENAME = pd.getString("fhtable");
		List<PageData> pdList = new ArrayList<PageData>();
		String kackupPath;
		try {
			// 调用数据库备份
			kackupPath = DbFH.getDbFH().backup(TABLENAME).toString();
			if(Tools.notEmpty(kackupPath) && !"errer".equals(kackupPath)){
				pd.put("FHDB_ID", this.get32UUID());
				pd.put("USERNAME", username);
				pd.put("BACKUP_TIME", Tools.date2Str(new Date()));
				pd.put("TABLENAME", TABLENAME);
				pd.put("SQLPATH", kackupPath);
				pd.put("DBSIZE", FileUtil.getFilesize(kackupPath));
				pd.put("TYPE", 2);
				pd.put("BZ", username+"备份单表");
				pd.put("msg", "ok");
				try {
					brdbService.save(pd);
				} catch (Exception e) {
					pd.put("msg", "no");
				}
			}else{
				pd.put("msg", "no");
			}
		} catch (InterruptedException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		} catch (ExecutionException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**数据还原操作
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/dbRecover")
	@ResponseBody
	public Object dbRecover(){
		String username = Jurisdiction.getUsername();
		logBefore(logger, username+"数据还原操作");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		PageData pd = new PageData();		
		Map<String, Object> map = new HashMap<String, Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		// 页面ajax传过来的表名或数据库名
		String TABLENAME = pd.getString("TABLENAME");
		// 页面ajax传过来的备份文件完整路径
		String SQLPATH = pd.getString("SQLPATH");
		try {
			String returnStr = DbFH.getDbFH().recover(TABLENAME, SQLPATH).toString();
			if("ok".equals(returnStr)){
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
		} catch (InterruptedException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		} catch (ExecutionException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Fhdb");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		brdbService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Fhdb");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		brdbService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Fhdb");
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
		// 结束时间
		String lastEnd = pd.getString("lastEnd");
		String startTime = " 00:00:00";
		String endTime = " 00:00:00";
		if(Tools.notEmpty(lastStart)){
			pd.put("lastLoginStart", lastStart+startTime);
		}
		if(Tools.notEmpty(lastEnd)){
			pd.put("lastLoginEnd", lastEnd+endTime);
		} 
		page.setPd(pd);
		// 列出Fhdb列表
		List<PageData>	varList = brdbService.list(page);
		Map<String,String> DBmap = DbFH.getDBParameter();
		mv.setViewName("fhdb/brdb/brdb_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("dbtype", DBmap.get("dbtype").toString());
		// 是否远程备份数据库 yes or no
		mv.addObject("remoteDB", DBmap.get("remoteDB").toString());
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
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
		// 根据ID读取
		pd = brdbService.findById(pd);
		mv.setViewName("fhdb/brdb/brdb_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除备份记录");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			brdbService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
