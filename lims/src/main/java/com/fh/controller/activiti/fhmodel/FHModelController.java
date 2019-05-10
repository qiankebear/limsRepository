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
 * @param menuUrl  菜单地址(权限用)
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value="/fhmodel")
public class FHModelController extends AcBaseController{

	@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
	private String menuUrl = "fhmodel/list.do";
	@Resource(name="fhmodelService")
	private FHModelManager fhmodelService;
	@Resource(name="userService")
	private UserManager userService;
	
	/**列表
	 * @param page
	 * @param listFHModel
	 * @param keyWords 关键词检索条件
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		String listFHModel = "列表FHMOodel";
		logBefore(logger, Jurisdiction.getUsername()+listFHModel);
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keyWords = pd.getString("keywords");
		if(null != keyWords && !"".equals(keyWords)){
			pd.put("keywords", keyWords.trim());
		}
		page.setPd(pd);
		// 列出FHModel列表
		List<PageData>	varList = fhmodelService.list(page);
		mv.setViewName("activiti/fhmodel/fhmodel_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// 按钮权限
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
	 * @param newFHModel
	 * @param process_author 流程作者
	 * @param name 流程名称
	 * @param process_id 流程标识
	 * @param modelName 模型名称
	 * @param description 模型描述
	 * @param category 模型分类
	 * @param modelId
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		String newFHModel = "新增FHModel";
		logBefore(logger, Jurisdiction.getUsername()+newFHModel);
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String process_author = pd.getString("process_author");
		String name  = pd.getString("name");
		String process_id  = pd.getString("process_id");
		String modelName  = pd.getString("modelname");
		String description  = pd.getString("description");
		String category  = pd.getString("category");
		String modelId = createModel(process_id,process_author,name,modelName,description,category);
		mv.addObject("msg","success");
		mv.addObject("sunval",modelId);
		mv.setViewName("save_result");
		return mv;
	}
	
	 /**从流程定义映射模型
	 * @param add
	 * @param msg  ok
	 * @param errer
	 * @throws Exception
	 */
	@RequestMapping(value="/saveModelFromPro")
	@ResponseBody
	public Object saveModelFromPro(){
		String add = "add";
		String errer= "errer";
		String msg = "ok";
		logBefore(logger, Jurisdiction.getUsername()+"新增FHModel从流程定义");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, add)){return null;}
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>(16);
		pd = this.getPageData();
		// 流程定义ID
		String processDefinitionId = pd.getString("processDefinitionId");
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
	 * @param edit
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		String edit = "edit";
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 根据ID读取
		pd = fhmodelService.findById(pd);
		mv.setViewName("activiti/fhmodel/fhmodel_type");
		mv.addObject("msg", edit);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**修改类型
	 * @param edit
	 * @param editFHModel  定义修改列表变量
	 * @param success
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		String edit = "edit";
		String success = "success";
		String editFHModel = "修改FHModel类型";
		logBefore(logger, Jurisdiction.getUsername()+editFHModel);
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, edit)){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		fhmodelService.edit(pd);
		mv.addObject("msg",success);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @param deleteFHModel
	 * @param modelId
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
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
	 * @param deleteFHModel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
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
	 * @param  deploymentProcedures
	 * @param  result
	 * @return
	 */
	@RequestMapping(value="/deployment")
	@ResponseBody
	public Object deployment(){
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
	 * @param result
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
