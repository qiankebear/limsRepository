package com.fh.controller.information.attachedmx;

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
import com.fh.util.Jurisdiction;
import com.fh.service.information.attachedmx.AttachedMxManager;

/** 
 * @decritption：明细表
 * @author ：FH Q313596790
 * @date：2016-04-17
 */
@Controller
@RequestMapping(value="/attachedmx")
public class AttachedMxController extends BaseController {
	/**
	 *菜单地址(权限用)
	 */

	String menuUrl = "attachedmx/list.do";
	@Resource(name="attachedmxService")
	private AttachedMxManager attachedmxService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增AttachedMx");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 主键
		pd.put("ATTACHEDMX_ID", this.get32UUID());
		attachedmxService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除AttachedMx");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		attachedmxService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改AttachedMx");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		attachedmxService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表AttachedMx");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		// 列出AttachedMx列表
		List<PageData>	varList = attachedmxService.list(page);
		mv.setViewName("information/attachedmx/attachedmx_list");
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
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("information/attachedmx/attachedmx_edit");
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
		// 根据ID读取
		pd = attachedmxService.findById(pd);
		mv.setViewName("information/attachedmx/attachedmx_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除AttachedMx");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return null;
		}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String data_ids = pd.getString("DATA_IDS");
		if(null != data_ids && !"".equals(data_ids)){
			String[] arrayData_ids = data_ids.split(",");
			attachedmxService.deleteAll(arrayData_ids);
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
		 * @param var2  "标题"
		 * @param var3  "创建日期"
		 * @param var4  "单价"
		 */
		logBefore(logger, Jurisdiction.getUsername()+"导出AttachedMx到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>(16);
		List<String> titles = new ArrayList<String>();
		titles.add("名称");
		titles.add("标题");
		titles.add("创建日期");
		titles.add("单价");
		dataMap.put("titles", titles);
		List<PageData> varOList = attachedmxService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i = 0; i<varOList.size(); i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("NAME"));
			vpd.put("var2", varOList.get(i).getString("TITLE"));
			vpd.put("var3", varOList.get(i).getString("CTIME"));
			vpd.put("var4", varOList.get(i).get("PRICE").toString());
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
