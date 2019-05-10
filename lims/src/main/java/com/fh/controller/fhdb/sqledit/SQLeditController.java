package com.fh.controller.fhdb.sqledit;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.AppUtil;
import com.fh.util.DbFH;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * @decription：SQL编辑器
 * @author ：FH Q313596790
 * @date：2016-03-30
 * @version  1.0
 */
@Controller
@RequestMapping(value="/sqledit")
public class SQLeditController extends BaseController {
	/**
	 * @param menuUrl // 菜单地址(权限用)
	 * @param typeCha "cha
	 * @param typeEdit "edit"
	 */

	String menuUrl = "sqledit/view.do";
	String typeCha = "cha";
	String typeEdit = "edit";

	/**进入页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view()throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, typeCha)){return null;}
		logBefore(logger, Jurisdiction.getUsername()+"进入SQL编辑页面");
		// 校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		if(!Jurisdiction.buttonJurisdiction(menuUrl, typeCha)){return null;}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fhdb/sqledit/sql_edit");
		// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}

	/**执行查询语句
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/executeQuery")
	@ResponseBody
	public Object executeQuery(){
		logBefore(logger, Jurisdiction.getUsername()+"执行查询语句");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, typeCha)){return null;}
		Map<String, Object> map = new HashMap<String, Object>(16);
		List<PageData> pdList = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 前台传过来的sql语句
		String sql = pd.getString("sql");
		// 存放字段名
		List<String> columnList = new ArrayList<String>();
		// 存放数据(从数据库读出来的一条条的数据)
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		// 请求起始时间_毫秒
		long startTime = System.currentTimeMillis();
		Object[] arrOb = null;
		try {
			arrOb = DbFH.executeQueryFH(sql);
			// 请求结束时间_毫秒
			long endTime = System.currentTimeMillis();
			// 存入数据库查询时间
			pd.put("rTime", String.valueOf((endTime - startTime)/1000.000));
			if(null != arrOb){
				columnList = (List<String>)arrOb[0];
				dataList = (List<List<Object>>)arrOb[1];
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
		} catch (Exception e) {
			pd.put("msg", "no");
			logger.error("执行SQL报错", e);
		}
		pdList.add(pd);
		// 存放字段名
		map.put("columnList", columnList);
		// 存放数据(从数据库读出来的一条条的数据)
		map.put("dataList", dataList);
		// 消息类型
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**执行 INSERT、UPDATE 或 DELETE
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/executeUpdate")
	@ResponseBody
	public Object executeUpdate(){
		logBefore(logger, Jurisdiction.getUsername()+"执行更新语句");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, typeEdit)){return null;}
		Map<String, Object> map = new HashMap<String, Object>(16);
		List<PageData> pdList = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 前台传过来的sql语句
		String sql = pd.getString("sql");
		// 请求起始时间_毫秒
		long startTime = System.currentTimeMillis();
		try {
			DbFH.executeUpdateFH(sql);
			pd.put("msg", "ok");
		} catch (ClassNotFoundException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		} catch (SQLException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		}
		// 请求结束时间_毫秒
		long endTime = System.currentTimeMillis();
		// 存入数据库查询时间
		pd.put("rTime", String.valueOf((endTime - startTime)/1000.000));
		pdList.add(pd);
		// 消息类型
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**导出数据到EXCEL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, typeCha)){
				// 前台传过来的sql语句
				String sql = pd.getString("sql");
				// 存放字段名
				List<String> columnList = new ArrayList<String>();
				// 存放数据(从数据库读出来的一条条的数据)
				List<List<Object>> dataList = new ArrayList<List<Object>>();
				Object[] arrOb = null;
				try {
					arrOb = DbFH.executeQueryFH(sql);
					if(null != arrOb){
						columnList = (List<String>)arrOb[0];
						dataList = (List<List<Object>>)arrOb[1];
					}else{
						return null;
					}
				} catch (Exception e) {
					logger.error("导出excelSQL报错", e);
					return null;
				}
				Map<String, Object> dataMap = new HashMap<String, Object>(16);
				List<String> titles = new ArrayList<String>();
				for(int i=0;i<columnList.size();i++){
					// 字段名当标题
					titles.add(columnList.get(i).toString());
				}
				dataMap.put("titles", titles);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<dataList.size();i++){
					PageData vpd = new PageData();
					for(int j=0;j<dataList.get(i).size();j++){
						// 赋值
						vpd.put("var"+(j+1), dataList.get(i).get(j).toString());
					}
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				// 执行excel操作
				ObjectExcelView erv = new ObjectExcelView();
				mv = new ModelAndView(erv, dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
