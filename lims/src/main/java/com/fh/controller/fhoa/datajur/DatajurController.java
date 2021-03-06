package com.fh.controller.fhoa.datajur;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.fhoa.datajur.DatajurManager;
import com.fh.service.fhoa.department.DepartmentManager;

/** 
 * 说明：组织数据权限表
 * @author ：FH Q313596790
 *@date ：2016-04-26
 */
@Controller
@RequestMapping(value="/datajur")
public class DatajurController extends BaseController {
	/**
	 * menuUrl //菜单地址（权限用）
	 */
	String menuUrl = "datajur/list.do";
	@Resource(name="datajurService")
	private DatajurManager datajurService;
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		String type = "edit";
		logBefore(logger, Jurisdiction.getUsername()+"修改Datajur");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, type)){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 部门ID集
		pd.put("DEPARTMENT_IDS", departmentService.getDEPARTMENT_IDS(pd.getString("DEPARTMENT_ID")));
		datajurService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(
				departmentService.listAllDepartmentToSelect(Jurisdiction.getDEPARTMENT_ID(),zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		// 根据ID读取
		pd = datajurService.findById(pd);
		mv.addObject("DATAJUR_ID", pd.getString("DATAJUR_ID"));
		// 读取部门数据(用部门名称)
		pd = departmentService.findById(pd);
		mv.setViewName("fhoa/datajur/datajur_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
