package com.fh.controller.information.attached;

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
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.information.attached.AttachedManager;
import com.fh.service.information.attachedmx.AttachedMxManager;

/** 
 * @decription：主附结构
 * @author ：FH Q313596790
 * @date ：2016-04-17
 */
@Controller
@RequestMapping(value="/attached")
public class AttachedController extends BaseController {
	/**
	 *@param menuUrl 菜单地址(权限用)
	 */
	String menuUrl = "attached/list.do";
	@Resource(name="attachedService")
	private AttachedManager attachedService;
	
	@Resource(name="attachedmxService")
	private AttachedMxManager attachedmxService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Attached");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//主键
		pd.put("ATTACHED_ID", this.get32UUID());
		//创建时间
		pd.put("CTIME", Tools.date2Str(new Date()));
		attachedService.save(pd);
		//根据ID读取
		pd = attachedService.findById(pd);
		mv.setViewName("information/attached/attached_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws NumberFormatException 
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete() throws NumberFormatException, Exception{
		/**
		 * @param del  “del”
		 */
		String del = "del";
		logBefore(logger, Jurisdiction.getUsername()+"删除Attached");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, del)){return null;}
		Map<String, String> map = new HashMap<String, String>(16);
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		//获取zs的值
		int zs =Integer.parseInt(attachedmxService.findCount(pd).get("zs").toString());
		if(zs > 0){
			errInfo = "false";
		}else{
			attachedService.delete(pd);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		/**
		 * @param edit "edit"
		 * @param success "success"
		 */
		String edit = "edit";
		String success = "success";
		logBefore(logger, Jurisdiction.getUsername()+"修改Attached");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, edit))
		{return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		attachedService.edit(pd);
		mv.addObject("msg", success);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Attached");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		// 列出Attached列表
		List<PageData>	varList = attachedService.list(page);
		mv.setViewName("information/attached/attached_list");
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
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		/**
		 * @param save "save"
		 */
		String save = "save";
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("information/attached/attached_edit");
		mv.addObject("msg", save);
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
		// 根据ID读取
		pd = attachedService.findById(pd);
		mv.setViewName("information/attached/attached_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Attached");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return null;
		}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			attachedService.deleteAll(ArrayDATA_IDS);
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
		/**
		 * @param var1  "名称"
		 * @param var2  "描述"
		 * @param var3  "价格"
		 * @param var4  "创建时间"
		 */
		logBefore(logger, Jurisdiction.getUsername()+"导出Attached到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>(16);
		List<String> titles = new ArrayList<String>();
		titles.add("名称");
		titles.add("描述");
		titles.add("价格");
		titles.add("创建时间");
		dataMap.put("titles", titles);
		List<PageData> varOList = attachedService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0; i<varOList.size(); i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("NAME"));
			vpd.put("var2", varOList.get(i).getString("FDESCRIBE"));
			vpd.put("var3", varOList.get(i).get("PRICE").toString());
			vpd.put("var4", varOList.get(i).getString("CTIME"));
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
