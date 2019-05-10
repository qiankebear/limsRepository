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
    //菜单地址(权限用)
    String menuUrl = "customer/list.do";
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
        // 开始时间
        String lastLoginStart = pd.getString("lastLoginStart");
        // 结束时间
        String lastLoginEnd = pd.getString("lastLoginEnd");
        String startTime = " 00:00:00";
        String endTime = " 24:00:00";
        if(null != lastLoginStart && !"".equals(lastLoginStart)){
            pd.put("lastLoginStart", lastLoginStart+startTime);
        }
        if(null != lastLoginEnd && !"".equals(lastLoginEnd)){
            pd.put("lastLoginEnd", lastLoginEnd+endTime);
        }
        page.setPd(pd);
        // 列出用户列表
        List<PageData> customerList = customerService.listCustomer(page);
        mv.setViewName("customer/customer_list");
        mv.addObject("customerList", customerList);
        mv.addObject("pd", pd);
        // 按钮权限
        mv.addObject("QX",Jurisdiction.getHC());
        return mv;
    }

    /**去新增客户页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/goAddCustomer")
    public ModelAndView goAddU()throws Exception{
        // 校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        // 定义用户id
        String role_id = "1";
        // 定义修改顾客信息Url
        String editCustomerUrl = "customer/customer_edit";
        // 定义变量saveCustonmer
        String saveCustomer = "saveCustomer";
        pd.put("ROLE_ID",role_id);
        mv.setViewName(editCustomerUrl);
        mv.addObject("msg",saveCustomer);
        mv.addObject("pd", pd);
        return mv;
    }

    /**保存客户
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/saveCustomer")
    public ModelAndView saveCustomer() throws Exception{
        // 定义新增Custommer变量
        String addCustomer = "新增Customer";
        // 校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        logBefore(logger, Jurisdiction.getUsername()+addCustomer);
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
        // 根据ID读取
        pd = customerService.findById(pd);
        // 定义customerViewUrl;
        String customerViewUrl = "customer/customer_view";
        mv.setViewName(customerViewUrl);
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
        // 根据ID读取
        pd = customerService.findById(pd);
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
        // 根据ID读取
        List<PageData> projectByIdlistPage = customerService.findProjectByIdlistPage(page);
        mv.setViewName("customer/projectList");
        mv.addObject("msg","editCustomer" );
        mv.addObject("projectByIdlistPage", projectByIdlistPage);
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 修改用户
     */
    @RequestMapping(value="/editCustomer")
    public ModelAndView editCustomer() throws Exception{
        // 定义修改customer变量
        String editCustomer = "修改customer";
        logBefore(logger, Jurisdiction.getUsername()+editCustomer);
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
        // 定义删除Customer变量
        String deleteCustomer = "删除Customer";
        // 校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
        logBefore(logger, Jurisdiction.getUsername()+deleteCustomer);
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
        // 校验权限
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
        // 定义变量批量删除customer
        String deleteCustomers = "批量删除customer";
        logBefore(logger, Jurisdiction.getUsername()+deleteCustomers);
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>(16);
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
