package com.fh.controller.project.kitrecord;

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
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.project.kitrecord.KitRecordManager;

/** 
 * 说明：出入库记录
 * 创建人：FH Q313596790
 * 创建时间：2018-11-08
 */
@Controller
@RequestMapping(value="/kitrecord")
public class KitRecordController extends BaseController {
	/**
	 *菜单地址(权限用)
	 */

	String menuUrl = "kitrecord/list.do";
	@Resource(name="kitrecordService")
	private KitRecordManager kitrecordService;
    @Resource(name="projectmanagerService")
    private ProjectManagerManager projectmanagerService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表KitRecord");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 关键词检索条件
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		List projectAll = projectmanagerService.findprojectall(pd);
		page.setPd(pd);
		// 列出KitRecord列表
		List<PageData>	varList = kitrecordService.list(page);
		for (int i = 0; i < varList.size(); i++) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String newDate = simpleDateFormat.format((Date)varList.get(i).get("PERATION_TIME"));
			varList.get(i).put("newDate", newDate);
		}
		mv.setViewName("project/kitrecord/kitrecord_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("projectAll", projectAll);
		// 按钮权限
		mv.addObject("QX", Jurisdiction.getHC());
		return mv;
	}

    /**列表
     * @param
     * @throws Exception
     */
    @RequestMapping(value="/listProjectRep")
    public ModelAndView listProjectRep() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"项目出入库记录");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        PageData pd2 = new PageData();
		// 列出KitRecord列表
        List<PageData>	varList = kitrecordService.showkitRep(pd);
        List<PageData> varList1 = kitrecordService.findkitall(pd);

        List<PageData> pd1 = new ArrayList<>();


        for (int i = 0; i < varList1.size(); i++) {
            float add = 0;
            float lessen = 0;
            pd2 = new PageData();
            for (int i1 = 0; i1 < varList.size(); i1++) {
            	if (varList.get(i1).get("kit_name")!=null) {
					if (varList1.get(i).get("kit_name").toString().equals(varList.get(i1).get("kit_name").toString())) {
						// 如果为增加类型
						if (Integer.valueOf(varList.get(i1).get("CHANGE_TYPE").toString()) == 0) {
							add += Float.valueOf(varList.get(i1).get("CHANGE_COUNT").toString());
						}
						// 如果为减少类型
						if (Integer.valueOf(varList.get(i1).get("CHANGE_TYPE").toString()) == 1) {
							lessen += Float.valueOf(varList.get(i1).get("CHANGE_COUNT").toString());
						}
					}
				}
            }
            if (add>0 || lessen>0) {
				pd2.clear();
				pd2.put("kit_name", varList1.get(i).get("kit_name").toString());
				pd2.put("add", add);
				pd2.put("lessen", lessen);
				pd1.add(pd2);
			}
        }
        mv.setViewName("project/kitrecord/kitrecord_listprojectrec");
        mv.addObject("varList", pd1);
        mv.addObject("pd", pd);

        mv.addObject("QX", Jurisdiction.getHC());	//按钮权限
        return mv;
    }

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
