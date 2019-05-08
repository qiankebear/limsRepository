package com.fh.controller.system.instrument;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.entity.system.instrument.Instrument;
import com.fh.entity.system.instrument.InstrumentRecord;
import com.fh.service.system.instrument.InstrumentMapper;
import com.fh.service.system.instrument.InstrumentRecordMapper;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value="/instrument")
public class InstrumentController extends BaseController {
    String menuUrl = "main/instrument/toinstrument"; //菜单地址(权限用)
    // 仪器使用记录
    @Resource(name = "instrumentRecordService")
    private InstrumentRecordMapper instrumentRecordService;
    // 仪器管理
    @Resource(name = "instrumentService")
    private InstrumentMapper instrumentService;
    // 用户
    @Resource(name = "userService")
    private UserManager userService;

    /**
    *@Desc 页面跳转，获取页面数据
    *@Author Wangjian
    *@Date 2018/10/31 14:35
    *@Params  * @param null
    */
    @RequestMapping(value = "/list")
    public ModelAndView List(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        logBefore(logger, "跳转页面");
        List<PageData> allMessage = instrumentService.findAll(page);
        mv.addObject("QX",Jurisdiction.getHC()); // 权限
        mv.addObject("list",allMessage);
        mv.addObject("pd",pd);
        mv.setViewName("system/instrument/instrument");
        return mv;
    }
    /**
    *@Desc 新增页面
    *@Author Wangjian
    *@Date 2018/11/1 13:32
    *@Params  * @param null
    */
    @RequestMapping(value="/goAddU")
    public ModelAndView goAddU()throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("ROLE_ID", "1");
        mv.setViewName("system/instrument/instrumentAdd");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }
    /**
    *@Desc 保存仪器管理信息
    *@Author Wangjian
    *@Date 2018/11/1 16:10
    *@Params  * @param null
    */
    @RequestMapping(value="/save")
    public Object saveU() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView mv = this.getModelAndView();
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"新增文件");
        PageData pd = this.getPageData();
        // 执行保存
        instrumentService.saveMessage(pd);
        String result = "00";         //成功状态
        map.put("result",result);
        mv.setViewName("save_result");
        return mv;
    }

    /**
    *@Desc 回显
    *@Author Wangjian
    *@Date 2018/11/1 21:35
    *@Params  * @param null
    */
    @RequestMapping(value="/goEditU")
    public ModelAndView findeMessage() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("ID");
        pd = instrumentService.findById(pd);						//根据ID读取
        mv.setViewName("system/instrument/instrumentAdd");
        mv.addObject("msg", "editInstrument");
        mv.addObject("pd", pd);
        return mv;
    }
    /**
    *@Desc 修改数据
    *@Author Wangjian
    *@Date 2018/11/2 16:10
    *@Params  * @param null
    */
    @RequestMapping(value="/editInstrument")
    public ModelAndView editInstrument() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改数据");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        instrumentService.updateMessage(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
    *@Desc 删除一条记录
    *@Author Wangjian
    *@Date 2018/11/1 21:55
    *@Params  * @param null
    */
    @RequestMapping(value="/del")
    public ModelAndView del() throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"删除数据");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        instrumentService.deleteById(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     *@Desc 查询仪器使用记录
     *@Author Wangjian
     *@Date 2018/11/2 15:30
     *@Params  * @param null
     */
    @RequestMapping(value = "/recordList")
    public ModelAndView recordList(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        logBefore(logger, "跳转页面");
        List<PageData> getAll = instrumentRecordService.findAll(page);
        mv.addObject("QX",Jurisdiction.getHC()); // 权限
        mv.addObject("list",getAll);
        mv.addObject("pd",pd);
        mv.setViewName("system/instrument/instrumentRecord");
        return mv;
    }
    /**
     *@Desc 跳转仪器使用记录添加页面
     *@Author Wangjian
     *@Date 2018/11/2 16:25
     *@Params  * @param null
     */
    @RequestMapping(value="/goAddRecord")
    public ModelAndView goAddRecord()throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        // 查询仪器类别
        List<Instrument> type = instrumentService.findType(pd);
        // 查询用户名称
        List<PageData> name = userService.findName(pd);
        mv.setViewName("system/instrument/instrumentRecordAdd");
        mv.addObject("msg", "saveRecord");
        mv.addObject("list", type);
        mv.addObject("nameList", name);
        return mv;
    }
    /**
     *@Desc 添加仪器使用记录信息
     *@Author Wangjian
     *@Date 2018/11/2 17:11
     *@Params  * @param null
     */
    @RequestMapping(value="/saveRecord")
    public Object saveRecord() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView mv = this.getModelAndView();
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"新增文件");
        PageData pd = this.getPageData();
        // 执行保存
        instrumentRecordService.saveRecordMessage(pd);
        String result = "00";         //成功状态
        map.put("result",result);
        mv.setViewName("save_result");
        return mv;
    }
    /**
     *@Desc 删除一条记录
     *@Author Wangjian
     *@Date 2018/11/1 21:55
     *@Params  * @param null
     */
    @RequestMapping(value="/delRecord")
    public ModelAndView delRecord() throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"删除数据");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        instrumentRecordService.deleteRecordById(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
   /**
   *@Desc 仪器使用记录数据回显
   *@Author Wangjian
   *@Date 2018/11/2 23:43
   *@Params  * @param null
   */
    @RequestMapping(value="/goEditRecord")
    public ModelAndView findeRecordMessage() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        // 查询仪器类别
        List<Instrument> type = instrumentService.findType(pd);
        // 查询用户名称
        List<PageData> name = userService.findName(pd);
        //根据ID读取
        pd = instrumentRecordService.findById(pd);
        mv.setViewName("system/instrument/instrumentRecordAdd");
        mv.addObject("msg", "editRecord");
        mv.addObject("list", type);
        mv.addObject("nameList", name);
        mv.addObject("pd", pd);
        return mv;
    }
    /**
    *@Desc 仪器使用记录数据修改保存
    *@Author Wangjian
    *@Date 2018/11/3 0:35
    *@Params  * @param null
    */
    @RequestMapping(value="/editRecord")
    public ModelAndView editRecord() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改使用记录数据");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        instrumentRecordService.updateMessage(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }
}
