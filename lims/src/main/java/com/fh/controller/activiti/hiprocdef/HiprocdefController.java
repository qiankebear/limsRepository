package com.fh.controller.activiti.hiprocdef;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.activiti.AcBusinessController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;

/** 
 * 说明：历史流程任务
 * @author ：FH Q313596790
 * @date：2018-01-28
 * @version 1.0
 * @parammenuUrl 菜单地址（权限用）
 */
@Controller
@RequestMapping(value="/hiprocdef")
public class HiprocdefController extends AcBusinessController {
	private String menuUrl = "hiprocdef/list.do";
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	

	@RequestMapping(value="/list")
	/**列表
	 * @param page
	 * @param keyWords 关键词检索条件
	 * @param lastStart 开始时间
	 * @param lastEnd  结束时间
	 * @param time "00:00:00"
	 * @throws Exception
	 */
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Hiprocdef");
		/*if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)*/
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keyWords = pd.getString("keywords");
		if(null != keyWords && !"".equals(keyWords)){
			pd.put("keywords", keyWords.trim());
		}
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
		// 列出Hiprocdef列表
		List<PageData> varList = hiprocdefService.list(page);
		for(int i=0;i<varList.size();i++){
			Long ztime = Long.parseLong(varList.get(i).get("DURATION_").toString());
			Long tian = ztime / (1000*60*60*24);
			Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
			Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
			varList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分");
			// 流程申请人
			varList.get(i).put("INITATOR", getInitiator(varList.get(i).getString("PROC_INST_ID_")));
		}
		mv.setViewName("activiti/hiprocdef/hiprocdef_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		//按钮权限
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**查看流程信息页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 列出历史流程变量列表
		List<PageData>	varList = hiprocdefService.hivarList(pd);
		// 历史任务节点列表
		List<PageData>	hitaskList = ruprocdefService.hiTaskList(pd);
		// 根据耗时的毫秒数计算天时分秒
		for(int i=0;i<hitaskList.size();i++){
			if(null != hitaskList.get(i).get("DURATION_")){
				Long ztime = Long.parseLong(hitaskList.get(i).get("DURATION_").toString());
				Long tian = ztime / (1000*60*60*24);
				Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
				Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
				Long miao = (ztime % (1000*60*60*24))%(1000*60*60)%(1000*60)/1000;
				hitaskList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分"+miao+"秒");
			}
		}
		String FILENAME = URLDecoder.decode(pd.getString("FILENAME"), "UTF-8");
		//生成当前任务节点的流程图片
		createXmlAndPngAtNowTask(pd.getString("PROC_INST_ID_"),FILENAME);
		pd.put("FILENAME", FILENAME);
		String imgSrcPath = PathUtil.getClasspath()+Const.FILEACTIVITI+FILENAME;
		// 解决图片src中文乱码，把图片转成base64格式显示(这样就不用修改tomcat的配置了)
		pd.put("imgSrc", "data:image/jpeg;base64,"+ImageAnd64Binary.getImageStr(imgSrcPath));
		mv.setViewName("activiti/hiprocdef/hiprocdef_view");
		mv.addObject("varList", varList);
		mv.addObject("hitaskList", hitaskList);
		mv.addObject("pd", pd);
		return mv;
	}
	

	@RequestMapping(value="/delete")
	/**删除
	 * @param delete  "del"
	 * @param out
	 * @throws Exception
	 */
	public void delete(PrintWriter out) throws Exception{
		String delete = "del";
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, delete)){return;}
		PageData pd = new PageData();
		pd = this.getPageData();
		deleteHiProcessInstance(pd.getString("PROC_INST_ID_"));
		out.write("success");
		out.close();
	}


	@RequestMapping(value="/deleteAll")
	@ResponseBody
	/**批量删除
	 * @param delete "del"
	 * @throws Exception
	 */
	public Object deleteAll() throws Exception{
		String delete = "del";
		// 校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		PageData pd = new PageData();		
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String data_ids = pd.getString("DATA_IDS");
		if(null != data_ids && !"".equals(data_ids)){
			String[] arrayData_ids = data_ids.split(",");
			for(int i=0;i<arrayData_ids.length;i++){
				deleteHiProcessInstance(arrayData_ids[i]);
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
