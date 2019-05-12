package com.fh.controller.statistical.statisticaltable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
import com.fh.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.statistical.statisticaltable.StatisticalTableManager;

/** 
 * 说明：统计表
 * 创建人：FH Q313596790
 * 创建时间：2018-11-13
 */
@Controller
@RequestMapping(value="/statisticaltable")
public class StatisticalTableController extends BaseController {
	/**
	 * 菜单地址(权限用)
	 */
	String menuUrl = "statisticaltable/list.do";
	@Resource(name="statisticaltableService")
	private StatisticalTableManager statisticaltableService;
	@Resource(name="projectmanagerService")
	private ProjectManagerManager projectmanagerService;

	private String templateFile = PathUtil.getClassResources()+"template/template.xls";

	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表StatisticalTable");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("sl1");
		String keywords1 = pd.getString("keywords1");
        List projectAll = projectmanagerService.findprojectall(pd);
		if((null != keywords && !"".equals(keywords)) || (keywords1 != null && !"".equals(keywords1)) ){
			pd.put("keywords", keywords.trim());
			pd.put("keywords1", keywords1.trim());
		page.setPd(pd);
		// 汇总数据
		List<PageData>	varList = statisticaltableService.list(page);	//列出StatisticalTable列表

		for (int i = 0; i < varList.size(); i++) {
			PageData pd1 = new PageData();
			pd1.put("id", varList.get(i).get("pid"));
			// 普通样本
			List<PageData>	normal = statisticaltableService.findNormalByPId(pd1);
			// 微变异
			List<PageData>	mincro = statisticaltableService.findMincroByPId(pd1);
			// 稀有等位
			List<PageData>	rate = statisticaltableService.findRateByPId(pd1);
			// 三等位
			List<PageData>	three = statisticaltableService.findThreeByPId(pd1);
			// 重做
			List<PageData>	reform = statisticaltableService.findReformByPId(pd1);
			// 空卡
			List<PageData>	empty = statisticaltableService.findEmptycardByPId(pd1);
			// 导出
			List<PageData>	exp = statisticaltableService.findExpByPId(pd1);
			// 总数
			List<PageData>	sum = statisticaltableService.findSumByPId(pd1);

			// 插入正常样本
			for (int i1 = 0; i1 < normal.size(); i1++) {
				if (normal.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("succeedsample", normal.get(i1).get("succeedsample"));
				}
			}
			// 插入微变异样本
			for (int i1 = 0; i1 < mincro.size(); i1++) {
				if (mincro.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("mincrochange", mincro.get(i1).get("mincrochange"));
				}
			}

			// 插入稀有等位
			for (int i1 = 0; i1 < rate.size(); i1++) {
				if (rate.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("rate", rate.get(i1).get("rate"));
				}
			}

			// 插入三等位
			for (int i1 = 0; i1 < three.size(); i1++) {
				if (three.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("three", three.get(i1).get("three"));
				}
			}

			// 插入重做
			for (int i1 = 0; i1 < reform.size(); i1++) {
				if (reform.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("reform", reform.get(i1).get("reform"));
					varList.get(i).put("reform1", reform.get(i1).get("reform"));
				}
			}

			// 插入空卡
			for (int i1 = 0; i1 < empty.size(); i1++) {
				if (empty.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("emptycard", empty.get(i1).get("emptycard"));
				}
			}

			// 插入导出
			for (int i1 = 0; i1 < exp.size(); i1++) {
				if (exp.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("exp", exp.get(i1).get("exp"));
				}
			}

			// 插入总数
			for (int i1 = 0; i1 < sum.size(); i1++) {
				if (sum.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("sum", sum.get(i1).get("sum"));
				}
			}
			if(varList.get(i).get("succeedsample")==null) {
				varList.get(i).put("succeedsample", 0);
			}
			if(varList.get(i).get("mincrochange")==null) {
				varList.get(i).put("mincrochange", 0);
			}
			if(varList.get(i).get("rate")==null) {
				varList.get(i).put("rate", 0);
			}
			if(varList.get(i).get("three")==null) {
				varList.get(i).put("three", 0);
			}
			if(varList.get(i).get("reform")==null) {
				varList.get(i).put("reform", 0);
			}
			if(varList.get(i).get("emptycard")==null) {
				varList.get(i).put("emptycard", 0);
			}
			if(varList.get(i).get("exp")==null) {
				varList.get(i).put("exp", 0);
			}
			if(varList.get(i).get("sum")==null) {
				varList.get(i).put("sum", 0);
			}
			if(varList.get(i).get("reform1")==null) {
				varList.get(i).put("reform1", 0);
			}
			if(varList.get(i).get("slotting")==null) {
				varList.get(i).put("slotting", "无");
			}
			if(varList.get(i).get("pcr")==null) {
				varList.get(i).put("pcr", "无");
			}
			if(varList.get(i).get("checked")==null) {
				varList.get(i).put("checked", "无");
			}
			if(varList.get(i).get("analyse")==null) {
				varList.get(i).put("analyse", "无");
			}
		}

		mv.addObject("varList", varList);
		mv.addObject("pd", pd);

			// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
        }
        mv.addObject("projectAll", projectAll);
        mv.setViewName("statistical/statisticaltable/statisticaltable_list");
		return mv;
	}

	 /**去导出excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goExcel")
	public ModelAndView goExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 根据ID读取
		List<PageData> endProjectList = projectmanagerService.findprojectall(pd);
		mv.setViewName("statistical/statisticaltable/statisticaltable_edit");
		mv.addObject("msg", "edit");
		mv.addObject("endProject", endProjectList);
		return mv;
	}	

	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出StatisticalTable到excel");
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<>();
		//  汇总数据
		// 列出StatisticalTable列表
		List<PageData>	varList = statisticaltableService.findTableListByProjectId(pd);
		// 普通样本
		List<PageData>	normal = statisticaltableService.findNormalByPId(pd);
		// 微变异
		List<PageData>	mincro = statisticaltableService.findMincroByPId(pd);
		// 稀有等位
		List<PageData>	rate = statisticaltableService.findRateByPId(pd);
		// 三等位
		List<PageData>	three = statisticaltableService.findThreeByPId(pd);
		// 重做
		List<PageData>	reform = statisticaltableService.findReformByPId(pd);
		// 空卡
		List<PageData>	empty = statisticaltableService.findEmptycardByPId(pd);
		// 导出
		List<PageData>	exp = statisticaltableService.findExpByPId(pd);
		// 总数
		List<PageData>	sum = statisticaltableService.findSumByPId(pd);

		for (int i = 0; i < varList.size(); i++) {

			// 插入正常样本
			for (int i1 = 0; i1 < normal.size(); i1++) {
				if (normal.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("succeedsample", normal.get(i1).get("succeedsample"));
				}
			}
			// 插入微变异样本
			for (int i1 = 0; i1 < mincro.size(); i1++) {
				if (mincro.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("mincrochange", mincro.get(i1).get("mincrochange"));
				}
			}

			// 插入稀有等位
			for (int i1 = 0; i1 < rate.size(); i1++) {
				if (rate.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("rate", rate.get(i1).get("rate"));
				}
			}

			// 插入三等位
			for (int i1 = 0; i1 < three.size(); i1++) {
				if (three.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("three", three.get(i1).get("three"));
				}
			}

			// 插入重做
			for (int i1 = 0; i1 < reform.size(); i1++) {
				if (reform.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("reform", reform.get(i1).get("reform"));
					varList.get(i).put("reform1", reform.get(i1).get("reform"));
				}
			}

			// 插入空卡
			for (int i1 = 0; i1 < empty.size(); i1++) {
				if (empty.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("emptycard", empty.get(i1).get("emptycard"));
				}
			}

			// 插入导出
			for (int i1 = 0; i1 < exp.size(); i1++) {
				if (exp.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("exp", exp.get(i1).get("exp"));
				}
			}

			// 插入总数
			for (int i1 = 0; i1 < sum.size(); i1++) {
				if (sum.get(i1).get("id").equals( varList.get(i).get("id"))){
					varList.get(i).put("sum", sum.get(i1).get("sum"));
				}
			}
			if(varList.get(i).get("succeedsample")==null) {
				varList.get(i).put("succeedsample", 0);
			}
			if(varList.get(i).get("mincrochange")==null) {
				varList.get(i).put("mincrochange", 0);
			}
			if(varList.get(i).get("rate")==null) {
				varList.get(i).put("rate", 0);
			}
			if(varList.get(i).get("three")==null) {
				varList.get(i).put("three", 0);
			}
			if(varList.get(i).get("reform")==null) {
				varList.get(i).put("reform", 0);
			}
			if(varList.get(i).get("emptycard")==null) {
				varList.get(i).put("emptycard", 0);
			}
			if(varList.get(i).get("exp")==null) {
				varList.get(i).put("exp", 0);
			}
			if(varList.get(i).get("sum")==null) {
				varList.get(i).put("sum", 0);
			}
			if(varList.get(i).get("reform1")==null) {
				varList.get(i).put("reform1", 0);
			}
			if(varList.get(i).get("slotting")==null) {
				varList.get(i).put("slotting", "无");
			}
			if(varList.get(i).get("pcr")==null) {
				varList.get(i).put("pcr", "无");
			}
			if(varList.get(i).get("checked")==null) {
				varList.get(i).put("checked", "无");
			}
			if(varList.get(i).get("analyse")==null) {
				varList.get(i).put("analyse", "无");
			}

			varList.get(i).put("missing", Integer.valueOf(varList.get(i).get("mincrochange").toString())+
					Integer.valueOf(varList.get(i).get("rate").toString())+
					Integer.valueOf(varList.get(i).get("three").toString()));
		}
		// 第二轮
		List<PageData> secondRound = statisticaltableService.findSecondByProjectId(pd);

		// 问题
		List<PageData> issueSamples = statisticaltableService.findIssueByProjectId(pd);
		// 第三轮
		List<PageData> threeRound = statisticaltableService.findThreeByProjectId(pd);

		// 第四轮
		List<PageData> fourRound = statisticaltableService.findFourByProjectId(pd);

		// 第五轮
		List<PageData> fiveRound = statisticaltableService.findFiveByProjectId(pd);

		List<PageData> special = statisticaltableService.findSpecialByPid(pd);

//		if (secondRound.size() ==0) {
//			deleteSheet(templateFile,"第二轮");
//		}else{
//			dataMap.put("secondRroud", secondRound);
//		}
//		if (threeRound.size() ==0) {
//			deleteSheet(templateFile,"第三轮");
//		}else{
//			dataMap.put("threeRound",threeRound);
//		}
//		if (fourRound.size() ==0){
//			deleteSheet(templateFile,"第四轮");
//		}else{
//			dataMap.put("fourRound",fourRound);
//		}
//		if (fiveRound.size() ==0) {
//			deleteSheet(templateFile,"第五轮");
//		} else{
//			dataMap.put("fiveRound",fiveRound);
//		}
		dataMap.put("user1", varList);
		dataMap.put("secondRroud", secondRound);
		dataMap.put("threeRound", threeRound);
		dataMap.put("fourRound", fourRound);
		dataMap.put("fiveRound", fiveRound);
		dataMap.put("issueSamples", issueSamples);
		dataMap.put("special", special);
//		dataMap.put("serial1",serial1);
		String projectName = "unkownName";
		String projectNumber = "unkownNumber";
		PageData pd3 = projectmanagerService.findById(pd);
		if (pd3!=null) {
			projectName = pd3.get("project_name").toString();
			projectNumber = pd3.get("project_number").toString();
		}
		return new ModelAndView(new JxlsExcelView(projectNumber+"-"+projectName+"-统计表"), dataMap);

	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
	/**
	 * 删除指定的Sheet
	 * @param targetFile  目标文件
	 * @param sheetName   Sheet名称
	 */
	public void deleteSheet(String targetFile, String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(targetFile);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			// 删除Sheet
			wb.removeSheetAt(wb.getSheetIndex(sheetName));
			this.fileWrite(targetFile, wb);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写隐藏/删除后的Excel文件
	 * @param targetFile  目标文件
	 * @param wb          Excel对象
	 * @throws Exception
	 */
	public void fileWrite(String targetFile,HSSFWorkbook wb) throws Exception{
		FileOutputStream fileOut = new FileOutputStream("e:11.xls");
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}
