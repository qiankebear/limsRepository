package com.fh.controller.system.createcode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.AppUtil;
import com.fh.util.DbFH;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 类名称： 反向生成
 * 创建人：FH Q313596790
 * 修改时间：2018年3月20日
 * @version
 */
@Controller
@RequestMapping(value="/recreateCode")
public class ReverseCreateCodeController extends BaseController {
	
	String menuUrl = "recreateCode/list.do"; //菜单地址(权限用)
	
	/**列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/createcode/recreatecode_list");
		// 按钮权限
		mv.addObject("QX", Jurisdiction.getHC());
		return mv;
	}
	
	 /**列出所有表
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listAllTable")
	@ResponseBody
	public Object listAllTable(){
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		PageData pd = new PageData();		
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> pdList = new ArrayList<PageData>();
		List<String> tblist = new ArrayList<String>();
		try {
			Object[] arrOb = DbFH.getTables(pd);
			tblist = (List<String>)arrOb[1];
			pd.put("msg", "ok");
		} catch (ClassNotFoundException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		} catch (SQLException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		}
		pdList.add(pd);
		map.put("tblist", tblist);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
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
		String fieldType = "";
		StringBuffer sb = new StringBuffer("");
		// 读取字段信息
		List<Map<String, String>> columnList = DbFH.getFieldParameterLsit(DbFH.getFHCon(pd), pd.getString("table"));
		for(int i=0; i<columnList.size(); i++){
			Map<String,String> fmap = columnList.get(i);
			// 字段名称
			sb.append(fmap.get("fieldNanme").toString().toUpperCase());
			sb.append(",fh,");
			// 字段类型
			fieldType = fmap.get("fieldType").toString().toLowerCase();
			if(fieldType.contains("int")){
				sb.append("Integer");
			}else if(fieldType.contains("NUMBER")){
				if(Integer.parseInt(fmap.get("fieldSccle")) > 0){
					sb.append("Double");
				}else{
					sb.append("Integer");
				}
			}else if(fieldType.contains("double") || fieldType.contains("numeric")){
				sb.append("Double");
			}else if(fieldType.contains("date")){
				sb.append("Date");
			}else{
				sb.append("String");
			}
			sb.append(",fh,");
			// 备注
			sb.append("备注"+(i+1));
			sb.append(",fh,");
			// 是否前台录入
			sb.append("是");
			sb.append(",fh,");
			//默认值
			sb.append("无");
			sb.append(",fh,");
			// 长度
			sb.append(fmap.get("fieldLength").toString());
			sb.append(",fh,");
			// 小数点右边的位数
			sb.append(fmap.get("fieldSccle").toString());
			sb.append(",fh,");
			sb.append("null");	
			sb.append("Q313596790");
		}
		pd.put("FIELDLIST", sb.toString());
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.setViewName("system/createcode/productCode");
		return mv;
	}
	
}