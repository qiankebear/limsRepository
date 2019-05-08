package com.fh.controller.project.samplemanager;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.fh.service.project.projectmanager.ProjectManagerManager;
import com.fh.service.system.user.UserManager;
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
import com.fh.service.project.samplemanager.SampleManagerManager;

/** 
 * 说明：样本表
 * 创建人：FH Q313596790
 * 创建时间：2018-11-08
 */
@Controller
@RequestMapping(value="/samplemanager")
public class SampleManagerrController extends BaseController {
	
	String menuUrl = "samplemanager/list.do"; //菜单地址(权限用)
	@Resource(name="samplemanagerService")
	private SampleManagerManager samplemanagerService;
	@Resource(name="projectmanagerService")
	private ProjectManagerManager projectmanagerService;
	@Resource
	private UserManager userManager;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增SampleManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		samplemanagerService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除SampleManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		samplemanagerService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改SampleManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		samplemanagerService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表SampleManager");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
        List projectAll = projectmanagerService.findprojectall(pd);
		List userAll = userManager.findName(pd);
		if (pd.size() != 0) {
			// 列出SampleManagerr列表
			List<PageData> varList = samplemanagerService.list(page);
//		// 列出所有轮数的样本
//		List<PageData>	varList1 = samplemanagerService.list1(pd);
			for (int i = 0; i < varList.size(); i++) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String newDate = simpleDateFormat.format((Date) varList.get(i).get("sample_generate_time"));
				varList.get(i).put("newDate", newDate);
//			for (int i1 = 0; i1 < varList1.size(); i1++) {
//				// 比较名称是否一致
//				if (varList.get(i).get("sample_number").toString().equals(varList1.get(i1).get("sample_number").toString())){
//					// 名称一致则将第一轮的孔板编号放入
////					if(varList1.get(i1).get("lims_pore_serial").toString().equals("1")){
//						varList.get(i).put("firstPPName",varList1.get(i1).get("pore_plate_name"));
////					}
//				}
			}
//		}

			mv.setViewName("project/samplemanager/samplemanager_list");
			mv.addObject("varList", varList);
		}
		mv.addObject("userAll",userAll);
        mv.setViewName("project/samplemanager/samplemanager_list");
        mv.addObject("projectAll", projectAll);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		mv.setViewName("project/samplemanager/samplemanager_edit");
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
		pd = samplemanagerService.findById(pd);	//根据ID读取
		mv.setViewName("project/samplemanager/samplemanager_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除SampleManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			samplemanagerService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出SampleManager到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("样本编号");	//1
		titles.add("收样时间");	//2
		titles.add("样本进程");	//3
		titles.add("轮数");	//4
		titles.add("项目");	//5
		dataMap.put("titles", titles);
		List<PageData> varOList = samplemanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("SAMPLE_NUMBER"));	    //1
			vpd.put("var2", varOList.get(i).getString("SAMPLE_GENERATE_TIME"));	    //2
			vpd.put("var3", varOList.get(i).get("SAMPLE_COURSE").toString());	//3
			vpd.put("var4", varOList.get(i).get("SAMPLE_SERIAL").toString());	//4
			vpd.put("var5", varOList.get(i).get("SAMPLE_PROJECT_ID").toString());	//5
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
