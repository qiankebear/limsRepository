package com.fh.controller.system.statistical;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.statistical.StatisticalService;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
@Controller
@RequestMapping(value="/statistical")
public class StatisticalController extends BaseController {
    // 修改记录
    @Resource(name = "statisticalService")
    private StatisticalService statisticalService;
    // 用户
    @Resource(name = "userService")
    private UserManager userService;
    /**
    *@Desc 查询全部修改记录
    *@Author Wangjian
    *@Date 2018/11/9 11:06
    *@Params  * @param null
    */
    @RequestMapping(value = "/list")
    public ModelAndView List(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        Object userId = user.getUSER_ID();
        String id = userId.toString();
        User userAndRoleById = userService.getUserAndRoleById(id);
        String role_name = userAndRoleById.getRole().getRNUMBER();
        if (!"R20171231726481".equals(role_name) && !"R20180131375361".equals(role_name)) {
            pd.put("userId",user.getUSER_ID());
        }
        page.setPd(pd);
        List<PageData> allMessage = statisticalService.findAllMessage(page);
        // 权限
        mv.addObject("QX",Jurisdiction.getHC());
        mv.addObject("list",allMessage);
        mv.addObject("pd",pd);
        mv.setViewName("system/statistical/statisticalList");
        return mv;
    }
}
