package com.fh.controller.system.Resources;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.Resources.ResourcesService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
@Controller
@RequestMapping(value="/resources")
public class ResourcesController extends BaseController {
    //资源路径接口
    @Resource(name = "resourcesService")
    private ResourcesService resourcesService;

    /**
    *@Desc 查询全部数据显示列表
    *@Author Wangjian
    *@Date 2018/11/12 15:06
    *@Params  * @param null
    */
    @RequestMapping(value = "/list")
    public ModelAndView List(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        logBefore(logger, "跳转页面");
        List<PageData> all = resourcesService.findAll(page);
        mv.addObject("QX",Jurisdiction.getHC()); // 权限
        mv.addObject("list",all);
        mv.setViewName("system/Resources/resources_List");
        return mv;
    }

    /**
    *@Desc 新增修改页面
    *@Author Wangjian
    *@Date 2018/11/12 15:37
    *@Params  * @param null
    */
    @RequestMapping(value="/goAddU")
    public ModelAndView goAddU()throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("ROLE_ID", "1");
        mv.setViewName("system/Resources/resources_Edit");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }
    /**
    *@Desc 保存新增资源路径
    *@Author Wangjian
    *@Date 2018/11/12 15:50
    *@Params  * @param null
    */
    @RequestMapping(value="/save")
    public Object saveU() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView mv = this.getModelAndView();
        logBefore(logger, Jurisdiction.getUsername()+"新增文件");
        PageData pd = this.getPageData();
        pd.remove("id");
        // 执行保存
        resourcesService.saveMessage(pd);
        mv.setViewName("save_result");
        return mv;
    }
    /**
    *@Desc 查询单条记录并做回显
    *@Author Wangjian
    *@Date 2018/11/12 15:59
    *@Params  * @param null
    */
    @RequestMapping(value="/goEditU")
    public ModelAndView findeMessage() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = resourcesService.findById(pd);
        mv.setViewName("system/Resources/resources_Edit");
        mv.addObject("msg", "editResources");
        mv.addObject("pd", pd);
        return mv;
    }
    /**
    *@Desc 修改数据
    *@Author Wangjian
    *@Date 2018/11/12 16:13
    *@Params  * @param null
    */
    @RequestMapping(value="/editResources")
    public ModelAndView editInstrument() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改数据");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        resourcesService.updateMessage(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
    *@Desc 删除记录
    *@Author Wangjian
    *@Date 2018/11/12 16:18
    *@Params  * @param null
    */
    @RequestMapping(value="/del")
    public ModelAndView del() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"删除数据");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        resourcesService.deleteById(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
}
