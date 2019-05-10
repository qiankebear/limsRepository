package com.fh.controller.fhoa.department;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.fhoa.department.DepartmentManager;

/** 
 * 说明：组织机构
 * 创建人：FH Q313596790
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/department")
public class DepartmentController extends BaseController {
	//菜单地址(权限用)
	String menuUrl = "department/list.do";
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增department");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//主键
		pd.put("DEPARTMENT_ID", this.get32UUID());
		departmentService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 * @param DEPARTMENT_ID
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String DEPARTMENT_ID) throws Exception{
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		logBefore(logger, Jurisdiction.getUsername()+"删除department");
		Map<String,String> map = new HashMap<String,String>(16);
		PageData pd = new PageData();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
		String errInfo = "success";
		//判断是否有子级，是：不允许删除
		if(departmentService.listSubDepartmentByParentId(DEPARTMENT_ID).size() > 0){
			errInfo = "false";
		}else{
			//执行删除
			departmentService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"修改department");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		departmentService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表department");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTMENT_ID = pd.get("id").toString();
		}
		//上级ID
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
		page.setPd(pd);
		//列出Dictionaries列表
		List<PageData>	varList = departmentService.list(page);
		//传入上级所有信息
		mv.addObject("pd", departmentService.findById(pd));
		//上级ID
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);
		mv.setViewName("fhoa/department/department_list");
		mv.addObject("varList", varList);
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDepartment")
	public ModelAndView listAllDepartment(Model model,String DEPARTMENT_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartment("0"));
			String json = arr.toString();
			json = json.replaceAll("DEPARTMENT_ID", "id")
					.replaceAll("PARENT_ID", "pId")
					.replaceAll("NAME", "name")
					.replaceAll("subDepartment", "nodes")
					.replaceAll("hasDepartment", "checked")
					.replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DEPARTMENT_ID",DEPARTMENT_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("fhoa/department/department_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		String department_id = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		//上级ID
		pd.put("DEPARTMENT_ID", department_id);
		//传入上级所有信息
		mv.addObject("pds",departmentService.findById(pd));
		//传入ID，作为子级ID用
		mv.addObject("DEPARTMENT_ID", department_id);
		mv.setViewName("fhoa/department/department_edit");
		mv.addObject("msg", "save");
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
		String DEPARTMENT_ID = pd.getString("DEPARTMENT_ID");
		//根据ID读取
		pd = departmentService.findById(pd);
		//放入视图容器
		mv.addObject("pd", pd);
		//用作上级信息
		pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());
		mv.addObject("pds",departmentService.findById(pd));
		//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());
		//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);
		//复原本ID
		mv.setViewName("fhoa/department/department_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	

	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>(16);
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(null != departmentService.findByBianma(pd)){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		//返回结果
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
