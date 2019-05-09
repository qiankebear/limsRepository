package com.fh.controller.system.onlinemanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.Jurisdiction;

/** 
 * 类名称：在线管理列表
 * 创建人：FH 
 * 创建时间：2015-05-25
 */
@Controller
@RequestMapping(value="/onlinemanager")
public class OnlineManagerController extends BaseController {
	/**
	 * //菜单地址(权限用)
	 */
	String menuUrl = "onlinemanager/list.do";
	
	/**列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		logBefore(logger, "列表OnlineManager");
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/onlinemanager/onlinemanager_list");
		// 按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
