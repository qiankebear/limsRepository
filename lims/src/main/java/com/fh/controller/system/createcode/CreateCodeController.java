package com.fh.controller.system.createcode;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.createcode.CreateCodeManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileZip;
import com.fh.util.Freemarker;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

/** 
 * 类名称： 代码生成器
 * 创建人：FH Q313596790
 * 修改时间：2015年11月23日
 * @version
 */
@Controller
@RequestMapping(value="/createCode")
public class CreateCodeController extends BaseController {
	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "createcode/list.do";
	@Resource(name="createcodeService")
	private CreateCodeManager createcodeService;
	
	/**列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			keywords = keywords.trim();
			pd.put("keywords", keywords);
		}
		page.setPd(pd);
		// 列出CreateCode列表
		List<PageData>	varList = createcodeService.list(page);
		mv.setViewName("system/createcode/createcode_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**去代码生成器页面(进入弹窗)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goProductCode")
	public ModelAndView goProductCode() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String CREATECODE_ID = pd.getString("CREATECODE_ID");
		if(!"add".equals(CREATECODE_ID)){
			pd = createcodeService.findById(pd);
			mv.addObject("pd", pd);
			mv.addObject("msg", "edit");
			
		}else{
			mv.addObject("msg", "add");
		}
		List<PageData> varList = createcodeService.listFa(); //列出所有主表结构的
		mv.addObject("varList", varList);
		mv.setViewName("system/createcode/productCode");
		return mv;
	}
	
	/**生成代码
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/proCode")
	public void proCode(HttpServletResponse response) throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){}
		logBefore(logger, Jurisdiction.getUsername()+"执行代码生成器");
		PageData pd = new PageData();
		pd = this.getPageData();
		// 保存到数据库
		save(pd);
		/* ============================================================================================= */
		// 主表名				========参数0-1 主附结构用
		String faobject = pd.getString("faobject");
		// 模块类型			========参数0-2 类型，单表、树形结构、主表明细表
		String FHTYPE = pd.getString("FHTYPE");
		// 说明				========参数0
		String TITLE = pd.getString("TITLE");
		// 包名				========参数1
		String packageName = pd.getString("packageName");
		// 类名				========参数2
		String objectName = pd.getString("objectName");
		// 表前缀				========参数3
		String tabletop = pd.getString("tabletop");
		// 表前缀转大写
		tabletop = null == tabletop?"":tabletop.toUpperCase();
		//属性总数
		String zindext = pd.getString("zindex");
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		// 属性集合			========参数4
		List<String[]> fieldList = new ArrayList<String[]>();
		for(int i=0; i< zindex; i++){
			// 属性放到集合里面
			fieldList.add(pd.getString("field"+i).split(",fh,"));
		}
		// 创建数据模型
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("fieldList", fieldList);
		// 主附结构用，主表名
		root.put("faobject", faobject.toUpperCase());
		// 说明
		root.put("TITLE", TITLE);
		// 包名
		root.put("packageName", packageName);
		// 类名
		root.put("objectName", objectName);
		// 类名(全小写)
		root.put("objectNameLower", objectName.toLowerCase());
		// 类名(全大写)
		root.put("objectNameUpper", objectName.toUpperCase());
		// 表前缀
		root.put("tabletop", tabletop);
		// 当前日期
		root.put("nowDate", new Date());

		// 生成代码前,先清空之前生成的代码
		DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl");
		/* ============================================================================================= */
		// 存放路径
		String filePath = "admin/ftl/code/";
		// ftl路径
		String ftlPath = "createCode";
		if("tree".equals(FHTYPE)){
			ftlPath = "createTreeCode";
			/*生成实体类*/
			Freemarker.printFile("entityTemplate.ftl", root,
					"entity/"+packageName+"/"+objectName+".java", filePath, ftlPath);
			/*生成jsp_tree页面*/
			Freemarker.printFile("jsp_tree_Template.ftl", root, "jsp/"+packageName+"/" +
					objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_tree.jsp", filePath, ftlPath);
		}else if("fathertable".equals(FHTYPE)){
			// 主表
			ftlPath = "createFaCode";
		}else if("sontable".equals(FHTYPE)){
			// 明细表
			ftlPath = "createSoCode";
		}
		/*生成controller*/
		Freemarker.printFile("controllerTemplate.ftl", root, "controller/"+packageName+"/"+
				objectName.toLowerCase()+"/"+objectName+"Controller.java", filePath, ftlPath);
		/*生成service*/
		Freemarker.printFile("serviceTemplate.ftl", root, "service/"+packageName+"/"+
				objectName.toLowerCase()+"/impl/"+objectName+"Service.java", filePath, ftlPath);
		/*生成manager*/
		Freemarker.printFile("managerTemplate.ftl", root, "service/"+packageName+"/"+
				objectName.toLowerCase()+"/"+objectName+"Manager.java", filePath, ftlPath);
		/*生成mybatis xml*/
		Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/"+packageName+"/"+
				objectName+"Mapper.xml", filePath, ftlPath);
		Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis_oracle/"+packageName+"/"+
				objectName+"Mapper.xml", filePath, ftlPath);
		Freemarker.printFile("mapperSqlserverTemplate.ftl", root, "mybatis_sqlserver/"+packageName+"/"+
				objectName+"Mapper.xml", filePath, ftlPath);
		/*生成SQL脚本*/
		Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql数据库脚本/"+tabletop+
				objectName.toUpperCase()+".sql", filePath, ftlPath);
		Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle数据库脚本/"+tabletop+
				objectName.toUpperCase()+".sql", filePath, ftlPath);
		Freemarker.printFile("sqlserver_SQL_Template.ftl", root, "sqlserver数据库脚本/"+
				tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
		/*生成jsp页面*/
		Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/"+packageName+"/"+
				objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_list.jsp", filePath, ftlPath);
		Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/"+packageName+"/"+
				objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_edit.jsp", filePath, ftlPath);
		/*生成说明文档*/
		Freemarker.printFile("docTemplate.ftl", root, "部署说明.doc", filePath, ftlPath);
		//this.print("oracle_SQL_Template.ftl", root);  控制台打印
		/*生成的全部代码压缩成zip文件*/
		if(FileZip.zip(PathUtil.getClasspath()+"admin/ftl/code", PathUtil.getClasspath()+"admin/ftl/code.zip")){
			/*下载代码*/
			FileDownload.fileDownload(response, PathUtil.getClasspath()+"admin/ftl/code.zip", "code.zip");
		}
	}
	
	/**保存到数据库
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception{
		// 包名
		pd.put("PACKAGENAME", pd.getString("packageName"));
		// 类名
		pd.put("OBJECTNAME", pd.getString("objectName"));
		// 表名
		pd.put("TABLENAME", pd.getString("tabletop")+",fh,"+pd.getString("objectName").toUpperCase());
		// 属性集合
		pd.put("FIELDLIST", pd.getString("FIELDLIST"));
		// 创建时间
		pd.put("CREATETIME", DateUtil.getTime());
		// 说明
		pd.put("TITLE", pd.getString("TITLE"));
		// 主键
		pd.put("CREATECODE_ID", this.get32UUID());
		createcodeService.save(pd);
	}
	
	/**
	 * 通过ID获取数据
	 */
	@RequestMapping(value="/findById")
	@ResponseBody
	public Object findById() throws Exception {
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
			return null;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = createcodeService.findById(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除
	 * @param out
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除CreateCode");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		createcodeService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"批量删除CreateCode");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){
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
				createcodeService.deleteAll(ArrayDATA_IDS);
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
	
}
//FHQ 3 1 3 5 9 6 7 9 0