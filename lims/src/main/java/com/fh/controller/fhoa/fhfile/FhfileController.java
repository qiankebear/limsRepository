package com.fh.controller.fhoa.fhfile;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.fh.util.Const;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.service.fhoa.fhfile.FhfileManager;

/** 
 * 说明：文件管理
 * @author ：FH Q313596790
 * @date：2016-05-27
 * @version 1.0
 */
@Controller
@RequestMapping(value="/fhfile")
public class FhfileController extends BaseController {
	/**
	 * @param menuUrl 菜单地址(权限用)
	 */
	String menuUrl = "fhfile/list.do";
	@Resource(name="fhfileService")
	private FhfileManager fhfileService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Fhfile");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 主键
		pd.put("FHFILE_ID", this.get32UUID());
		// 上传时间
		pd.put("CTIME", Tools.date2Str(new Date()));
		// 上传者
		pd.put("USERNAME", Jurisdiction.getUsername());
		// 部门ID
		pd.put("DEPARTMENT_ID", Jurisdiction.getDEPARTMENT_ID());
		// 文件大小
		pd.put("FILESIZE", FileUtil.getFilesize(
				PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("FILEPATH")));
		fhfileService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Fhfile");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);
		fhfileService.delete(pd);
		// 删除文件
		DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHFILEOA + pd.getString("FILEPATH"));
		out.write("success");
		out.close();
	}

	/**获取文件类型
	 * @param varList
	 * @param i
	 */
	protected String getFileType(List<PageData> varList,int i){
		String filePath = varList.get(i).getString("FILEPATH");
		// 文件拓展名
		String extension_name =  filePath.substring(20, filePath.length());
		String fileType = "file";
		int zindex1 = "java,php,jsp,html,css,txt,asp".indexOf(extension_name);
		if(zindex1 != -1){
			// 文本类型
			fileType = "wenben";
		}
		int zindex2 = "jpg,gif,bmp,png".indexOf(extension_name);
		if(zindex2 != -1){
			// 图片文件类型
			fileType = "tupian";
		}
		int zindex3 = "rar,zip,rar5".indexOf(extension_name);
		if(zindex3 != -1){
			// 压缩文件类型
			fileType = "yasuo";
		}
		int zindex4 = "doc,docx".indexOf(extension_name);
		if(zindex4 != -1){
			// doc文件类型
			fileType = "doc";
		}
		int zindex5 = "xls,xlsx".indexOf(extension_name);
		if(zindex5 != -1){
			// xls文件类型
			fileType = "xls";
		}
		int zindex6 = "ppt,pptx".indexOf(extension_name);
		if(zindex6 != -1){
			// ppt文件类型
			fileType = "ppt";
		}
		int zindex7 = "pdf".indexOf(extension_name);
		if(zindex7 != -1){
			// ppt文件类型
			fileType = "pdf";
		}
		int zindex8 = "fly,f4v,mp4,m3u8,webm,ogg,avi".indexOf(extension_name);
		if(zindex8 != -1){
			// 视频文件类型
			fileType = "video";
		}
		return fileType;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Fhfile");
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
		String item = Jurisdiction.getDEPARTMENT_IDS();
		if("0".equals(item) || "无权".equals(item)){
			//根据部门ID过滤
			pd.put("item", "");
			// 根据部门ID过滤
			pd.put("item", "");
		}else{
			//  定义双斜杠
			String startSlash = "\\(";
			// 定义中间符号
			String centerSlash = "\\('";
			// 定义结束符号
			String endSymbol = "',";
			pd.put("item", item.replaceFirst(startSlash, centerSlash+Jurisdiction.getDEPARTMENT_ID()+endSymbol));
		}
		page.setPd(pd);
		// 列出Fhfile列表
		List<PageData>	varList = fhfileService.list(page);
		List<PageData>	nvarList = new ArrayList<PageData>();
		for(int i=0;i<varList.size();i++){
			PageData npd = new PageData();
			String fileType = "file";
			String filePath = varList.get(i).getString("FILEPATH");
			// 文件拓展名
			String extension_name =  filePath.substring(20, filePath.length());
			getFileType(varList,i);
			// 用于文件图标
			npd.put("fileType", fileType);
			// 唯一ID
			npd.put("FHFILE_ID", varList.get(i).getString("FHFILE_ID"));
			// 文件名
			npd.put("NAME", varList.get(i).getString("NAME"));
			// 文件名+扩展名
			npd.put("FILEPATH", filePath);
			 // 上传时间
			npd.put("CTIME", varList.get(i).getString("CTIME"));
			  // 用户名
			npd.put("USERNAME", varList.get(i).getString("USERNAME"));
			// 机构级别
			npd.put("DEPARTMENT_ID", varList.get(i).getString("DEPARTMENT_ID"));
			// 文件大小
			npd.put("FILESIZE", varList.get(i).getString("FILESIZE"));
			// 备注
			npd.put("BZ", varList.get(i).getString("BZ"));
			nvarList.add(npd);
		}
		mv.setViewName("fhoa/fhfile/fhfile_list");
		mv.addObject("varList", nvarList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject("QX", Jurisdiction.getHC());
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
		mv.setViewName("fhoa/fhfile/fhfile_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去预览pdf文件页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goViewPdf")
	public ModelAndView goViewPdf()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);
		mv.setViewName("fhoa/fhfile/fhfile_view_pdf");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去预览txt,java,php,等文本文件页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goViewTxt")
	public ModelAndView goViewTxt()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String encoding = pd.getString("encoding");
		pd = fhfileService.findById(pd);
		String code = Tools.readTxtFileAll(Const.FILEPATHFILEOA+pd.getString("FILEPATH"), encoding);
		pd.put("code", code);
		mv.setViewName("fhoa/fhfile/fhfile_view_txt");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Fhfile");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();		
		Map<String, Object> map = new HashMap<String, Object>(16);
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String data_ids = pd.getString("DATA_IDS");
		if(null != data_ids && !"".equals(data_ids)){
			String[] arrayData_ids = data_ids.split(",");
			PageData fpd = new PageData();
			for(int i=0;i<arrayData_ids.length;i++){
				fpd.put("FHFILE_ID", arrayData_ids[i]);
				fpd = fhfileService.findById(fpd);
				// 删除物理文件
				DelAllFile.delFolder(
						PathUtil.getClasspath()+ Const.FILEPATHFILEOA + fpd.getString("FILEPATH"));
			}
			// 删除数据库记录
			fhfileService.deleteAll(arrayData_ids);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**下载
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/download")
	public void downExcel(HttpServletResponse response)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);
		String fileName = pd.getString("FILEPATH");
		FileDownload.fileDownload(response, PathUtil.getClasspath()
				+ Const.FILEPATHFILEOA + fileName, pd.getString("NAME")
				+fileName.substring(19, fileName.length()));
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
