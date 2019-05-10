package com.fh.controller.activiti.fhmodel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.activiti.AcBaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.activiti.fhmodel.FHModelManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：工作流模型管理
 * @author ：FH Admin Q- 3 1 359 6790
 * @date：2017-12-26
 * @version 1.0
 */
@Controller
@RequestMapping(value="/fhmodel")
public class FHModelController extends AcBaseController{
	//菜单地址(权限用)
	String menuUrl = "fhmodel/list.do";
	@Resource(name="fhmodelService")
	private FHModelManager fhmodelService;
	@Resource(name="userService")
	private UserManager userService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		// 定义变量listFHModel
		String listFHModel = "列表FHMOodel";
		logBefore(logger, Jurisdiction.getUsername()+listFHModel);
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		// 列出FHModel列表
		List<PageData>	varList = fhmodelService.list(page);
		mv.setViewName("activiti/fhmodel/fhmodel_list");
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
		pd.put("USERNAME", Jurisdiction.getUsername());
		pd = userService.findByUsername(pd);
		// 通过当前用户名获取用户姓名充当流程作者
		pd.put("process_author", pd.getString("NAME"));
		mv.setViewName("activiti/fhmodel/fhmodel_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		// 定义新增列表变量
		String newFHModel = "新增FHModel";
		logBefore(logger, Jurisdiction.getUsername()+newFHModel);
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 流程作者
		String process_author = pd.getString("process_author");
		// 流程名称
		String name  = pd.getString("name");
		// 流程标识
		String process_id  = pd.getString("process_id");
		// 模型名称
		String modelname  = pd.getString("modelname");
		// 模型描述
		String description  = pd.getString("description");
		// 模型分类
		String category  = pd.getString("category");
		String modelId = createModel(process_id,process_author,name,modelname,description,category);
		mv.addObject("msg", "success");
		mv.addObject("sunval", modelId);
		mv.setViewName("save_result");
		return mv;
	}
	
	 /**从流程定义映射模型
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveModelFromPro")
	@ResponseBody
	public Object saveModelFromPro(){
		logBefore(logger, Jurisdiction.getUsername()+"新增FHModel从流程定义");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		String msg = "ok";
		pd = this.getPageData();
		// 流程定义ID
		String processDefinitionId = pd.getString("processDefinitionId");
		// 定义错误变量
		String errer= "errer";
		try {
			saveModelFromPro(processDefinitionId);
		} catch (Exception e) {
			msg = errer;
		}
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**打开流程编辑器页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editor")
	public ModelAndView editor()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("activiti/fhmodel/editor");
		mv.addObject("pd", pd);
		return mv;
	}
	
	 /**去修改类型页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 根据ID读取
		pd = fhmodelService.findById(pd);
		mv.setViewName("activiti/fhmodel/fhmodel_type");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**修改类型
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		//  定义修改列表变量
		String editFHModel = "修改FHModel类型";
		logBefore(logger, Jurisdiction.getUsername()+editFHModel);
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		fhmodelService.edit(pd);
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
		// 定义删除FHMOodel变量
		String deleteFHModel = "删除FHModel";
		logBefore(logger, Jurisdiction.getUsername()+deleteFHModel);
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		String modelId = pd.getString("ID_");
		deleteModel(modelId);
		out.write("success");
		out.close();
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		// 定义批量删除FHModel变量
		String deleteFHModels = "批量删除FHModel";
		logBefore(logger, Jurisdiction.getUsername()+deleteFHModels);
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for(int i=0;i<ArrayDATA_IDS.length;i++){
				deleteModel(ArrayDATA_IDS[i]);
			}
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**部署流程定义
	 * @return 
	 */
	@RequestMapping(value="/deployment")
	@ResponseBody
	public Object deployment(){
		// 定义部署流程
		String deploymentProcedures = "部署流程定义";
		logBefore(logger, Jurisdiction.getUsername()+deploymentProcedures);
		Map<String,Object> map = new HashMap<String,Object>(16);
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "success";
		try{
			// 部署流程定义
			deploymentProcessDefinitionFromModelId(pd.getString("modelId"));
		}catch (Exception e){
			result = "error";
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断能否正常根据模型ID导出xml文件
	 * @return 
	 */
	@RequestMapping(value="/isCanexportXml")
	@ResponseBody
	public Object isCanexportXml(HttpServletResponse response){
		logBefore(logger, Jurisdiction.getUsername()+"判断能否正常导出模型xml");
		Map<String,Object> map = new HashMap<String,Object>(16);
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "success";
		try{
			isCanexportXmlFromModelId(response,pd.getString("modelId"));
		}catch (Exception e){
			result = "error";
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**正式根据模型ID导出xml文件
	 * @return 
	 */
	@RequestMapping(value="/exportXml")
	public void exportXml(HttpServletResponse response){
		logBefore(logger, Jurisdiction.getUsername()+"导出模型xml");
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			// 导出xml文件
			exportXmlFromModelId(response,pd.getString("modelId"));
		}catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			logAfter(logger);
		}
	}
	
	/**去预览XML页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String code = viewXmlFromModelId(pd.getString("modelId"));
		pd.put("code", code);
		mv.setViewName("activiti/fhmodel/xml_view");
		mv.addObject("pd", pd);
		return mv;
	}
	
}
