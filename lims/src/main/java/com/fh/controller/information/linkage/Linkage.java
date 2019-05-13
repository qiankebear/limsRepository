package com.fh.controller.information.linkage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.Tools;

/**
 * @author Administrator
 * @decription：4级联动
 * @date：2016-05-19
 */
@Controller
@RequestMapping(value="/linkage")
public class Linkage extends BaseController{
	/**
	 *@param menuUrl param 菜单地址(权限用)
	 */
	String menuUrl = "linkage/view.do";
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("information/linkage/view");
		return mv;
	}
	
	/**获取连级数据
	 * @return
	 */
	@RequestMapping(value="/getLevels")
	@ResponseBody
	public Object getLevels(){
		Map<String,Object> map = new HashMap<String,Object>(16);
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String dictionaries_id = pd.getString("DICTIONARIES_ID");
			dictionaries_id = Tools.isEmpty(dictionaries_id)?"0":dictionaries_id;
			// 用传过来的ID获取此ID下的子列表数据
			List<Dictionaries>	varList = dictionariesService.listSubDictByParentId(dictionaries_id);
			List<PageData> pdList = new ArrayList<PageData>();
			for(Dictionaries d :varList){
				PageData pdf = new PageData();
				pdf.put("DICTIONARIES_ID", d.getDICTIONARIES_ID());
				pdf.put("BIANMA", d.getBIANMA());
				pdf.put("NAME", d.getNAME());
				pdList.add(pdf);
			}
			map.put("list", pdList);	
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		// 返回结果
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

}
