package com.fh.controller.activiti.procdef;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.activiti.AcBaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.FileZip;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.service.activiti.procdef.ProcdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;

/** 
 * 说明：流程管理
 * @author ：FH Admin Q313596790
 * @date：2018-01-06
 * @version 1.0
 */
@Controller
@RequestMapping(value="/procdef")
public class ProcdefController extends AcBaseController {
	/**
	 * @paeam menuUrl 菜单地址（权限用）
	 */
	private String menuUrl = "procdef/list.do";
	@Resource(name="procdefService")
	private ProcdefManager procdefService;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		/*if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)*/
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keyWords = pd.getString("keywords");
		if(null != keyWords && !"".equals(keyWords)){
			pd.put("keywords", keyWords.trim());
		}
		/**
		 * @param lastStart 开始时间
		 * @param lastEnd 结束时间
		 * @param time  0：00:00"
		 */
		String lastStart = pd.getString("lastStart");
		String lastEnd = pd.getString("lastEnd");
		String time = "00:00:00";
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+time);
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+time);
		}
		page.setPd(pd);
		// 列出Procdef列表
		List<PageData>	varList = procdefService.list(page);
		mv.setViewName("activiti/procdef/procdef_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**去预览XML页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goViewXml")
	public ModelAndView goViewXml()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 部署ID
		String deployment_id = pd.getString("DEPLOYMENT_ID_");
		// 生成XML和PNG
		createXmlAndPng(deployment_id);
		String code = Tools.readTxtFileAll(Const.FILEACTIVITI+URLDecoder.decode(
				pd.getString("FILENAME"), "UTF-8"), "utf-8");
		pd.put("code", code);
		mv.setViewName("activiti/fhmodel/xml_view");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去预览PNG页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goViewPng")
	public ModelAndView goViewPng()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 部署ID
		String deployment_id = pd.getString("DEPLOYMENT_ID_");
		// 生成XML和PNG
		createXmlAndPng(deployment_id);
		String fileName = URLDecoder.decode(pd.getString("FILENAME"), "UTF-8");
		pd.put("FILENAME", fileName);
		String imgSrcPath = PathUtil.getClasspath()+Const.FILEACTIVITI+fileName;
		// 解决图片src中文乱码，把图片转成base64格式显示(这样就不用修改tomcat的配置了)
		pd.put("imgSrc", "data:image/jpeg;base64,"+ImageAnd64Binary.getImageStr(imgSrcPath));
		mv.setViewName("activiti/procdef/png_view");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**打包下载xml和png
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/download")
	public void download(HttpServletResponse response)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		// 部署ID
		String deployment_id = pd.getString("DEPLOYMENT_ID_");
		// 生成XML和PNG
		createXmlAndPng(deployment_id);
		/*生成的全部代码压缩成zip文件*/
		if(FileZip.zip(PathUtil.getClasspath()+ "uploadFiles/activitiFile",
				PathUtil.getClasspath()+"uploadFiles/activitiFile.zip")){
			/**
			 * @param downloadUrl 下载代码
			 */
			String downloadUrl = "uploadFiles/activitiFile.zip";
			FileDownload.fileDownload(response, PathUtil.getClasspath()+downloadUrl, "activitiFile.zip");
		}
	}
	
	/**打开上传流程页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadPro")
	public ModelAndView goUploadPro()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("activiti/procdef/procdef_upload");
		return mv;
	}
	
	/**导入流程
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadPro")
	public ModelAndView readExcel(
			@RequestParam(value="zip", required=false) MultipartFile file
			){
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		if (null != file && !file.isEmpty()) {
			//文件上传路径
			String filePath = PathUtil.getClasspath() + Const.FILEACTIVITI;
			//执行上传
			String fileName =  FileUpload.fileUp(file, filePath, "proFile");
			try {
				deploymentProcessDefinitionFromZip("FHPRO", filePath+fileName);
			} catch (Exception e) {
				mv.addObject("errer", "errer");
				mv.addObject("msgContent", "文件资源不符合流程标准( 或缺少 xml or png )");
			}
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**激活or挂起流程实例
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/onoffPro")
	@ResponseBody
	public Object onoffProcessDefinition()throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		PageData pd = new PageData();		
		Map<String, Object> map = new HashMap<String, Object>(16);
		pd = this.getPageData();
		int status = Integer.parseInt(pd.get("STATUS").toString());
		String id = pd.getString("ID_");
		if(status == 2){
			// 挂起前先把此流程的所有任务状态设置成激活状态
			pd.put("STATUS", 1);
			ruprocdefService.onoffAllTask(pd);
			// 挂起流程实例
			suspendProcessDefinitionById(id);
		}else{
			// 激活前先把此流程的所有任务状态设置成挂起状态
			pd.put("STATUS", 2);
			ruprocdefService.onoffAllTask(pd);
			// 激活流程实例
			activateProcessDefinitionById(id);
		}
		// 返回结果
		map.put("msg", "ok");
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		// 部署ID
		String deployment_id = pd.getString("DEPLOYMENT_ID_");
		deleteDeployment(deployment_id);
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
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();		
		Map<String, Object> map = new HashMap<String, Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String data_ids = pd.getString("DATA_IDS");
		if(null != data_ids && !"".equals(data_ids)){
			String[] arrayData_ids = data_ids.split(",");
			for(int i=0;i<arrayData_ids.length;i++){
				deleteDeployment(arrayData_ids[i]);
			}
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
}
