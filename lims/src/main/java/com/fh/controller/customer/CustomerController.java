package com.fh.controller.customer;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.Customer.CustomerService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:$ 用户管理
 * @Param:$
 * @return:$
 * @Author:jdf
 * @Date:$
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController extends BaseController {

    String menuUrl = "customer/list.do"; //菜单地址(权限用)
    @Resource(name="CustomerService")
    private CustomerService customerService;

    /**去用户管理页面
     * @param
     * @return
     */
    @RequestMapping(value="/list.do")
    public ModelAndView toCustomer(Page page) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("time","");
        String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
        String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
        if(lastLoginStart != null && !"".equals(lastLoginStart)){
            pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
        }
        if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
            pd.put("lastLoginEnd", lastLoginEnd+" 24:00:00");
        }
        page.setPd(pd);
        List<PageData> customerList = customerService.listCustomer(page);	//列出用户列表
        mv.setViewName("customer/customer_list");
        mv.addObject("customerList", customerList);
        mv.addObject("pd", pd);
        mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
        return mv;
    }

    /**去新增客户页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/goAddCustomer")
    public ModelAndView goAddU()throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("ROLE_ID", "1");
        mv.setViewName("customer/customer_edit");
        mv.addObject("msg", "saveCustomer");
        mv.addObject("pd", pd);
        return mv;
    }

    /**保存客户
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/saveCustomer")
    public ModelAndView saveCustomer() throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"新增Customer");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd.remove("id");
        customerService.saveCustomer(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }


    /**查看用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/view")
    public ModelAndView view() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        pd = customerService.findById(pd);						//根据ID读取
        mv.setViewName("customer/customer_view");
        mv.addObject("msg", "editCustomer");
        mv.addObject("pd", pd);
        return mv;
    }

    /**查看用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/goEditCustomer")
    public ModelAndView goEditCustomer() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = customerService.findById(pd);						//根据ID读取
        mv.setViewName("customer/customer_edit");
        mv.addObject("msg", "editCustomer");
        mv.addObject("pd", pd);
        return mv;
    }

    /**查看项目列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/projectList")
    public ModelAndView projectList(Page page) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        List<PageData> projectByIdlistPage = customerService.findProjectByIdlistPage(page);//根据ID读取
        mv.setViewName("customer/projectList");
        mv.addObject("msg", "editCustomer");
        mv.addObject("projectByIdlistPage", projectByIdlistPage);
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 修改用户
     */
    @RequestMapping(value="/editCustomer")
    public ModelAndView editCustomer() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改customer");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        customerService.update(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value="/deleteCustomer")
    public ModelAndView deleteCustomer() throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"删除Customer");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        customerService.deleteCustomer(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 批量删除
     * @throws Exception
     */
    @RequestMapping(value="/deleteAllCustomer")
    @ResponseBody
    public Object deleteAllCustomer() throws Exception {
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"批量删除customer");
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String USER_IDS = pd.getString("ids");
        if(null != USER_IDS && !"".equals(USER_IDS)){
            String ArrayUSER_IDS[] = USER_IDS.split(",");
            customerService.deleteAllCustomer(ArrayUSER_IDS);
            pd.put("msg", "ok");
        }else{
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }

}
