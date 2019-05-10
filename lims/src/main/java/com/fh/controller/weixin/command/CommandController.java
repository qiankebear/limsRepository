package com.fh.controller.weixin.command;

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
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.Jurisdiction;
import com.fh.service.weixin.command.CommandService;

/** 
 * 类名称：CommandController
 * 创建人：FH  313596790
 * 创建时间：2015-05-09
 */
@Controller
@RequestMapping(value="/command")
public class CommandController extends BaseController {
	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "command/list.do";
	@Resource(name="commandService")
	private CommandService commandService;
	
	/**新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Command");
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 主键
		pd.put("COMMAND_ID", this.get32UUID());
		// 创建时间
		pd.put("CREATETIME", Tools.date2Str(new Date()));
		commandService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Command");
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		}
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			commandService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Command");
<<<<<<< HEAD
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
=======
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
>>>>>>> origin/master
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		commandService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Command");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				pd.put("KEYWORD", KEYWORD.trim());
			}
			page.setPd(pd);
			// 列出Command列表
			List<PageData>	varList = commandService.list(page);
			mv.setViewName("weixin/command/command_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
<<<<<<< HEAD
			mv.addObject("QX", Jurisdiction.getHC());	//按钮权限
=======
			// 按钮权限
			mv.addObject("QX", Jurisdiction.getHC());
>>>>>>> origin/master
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @return
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Command页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("weixin/command/command_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**去修改页面
	 * @return
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Command页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			// 根据ID读取
			pd = commandService.findById(pd);
			mv.setViewName("weixin/command/command_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**批量删除
	 * @return
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Command");
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
			return null;
		}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				commandService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Command到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			//1
			titles.add("关键词");
			//2
			titles.add("应用路径");
			//3
			titles.add("创建时间");
			//4
			titles.add("状态");
			//5
			titles.add("备注");
			dataMap.put("titles", titles);
			List<PageData> varOList = commandService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				// 1
				vpd.put("var1", varOList.get(i).getString("KEYWORD"));
				// 2
				vpd.put("var2", varOList.get(i).getString("COMMANDCODE"));
				// 3
				vpd.put("var3", varOList.get(i).getString("CREATETIME"));
				// 4
				vpd.put("var4", varOList.get(i).get("STATUS").toString());
				// 5
				vpd.put("var5", varOList.get(i).getString("BZ"));
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv, dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
