package com.fh.controller.system.punching;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.punching.PunchingService;
import com.fh.service.rebuild.RebuildService;
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
 * @Description:打孔记录
 * @Date: $time$ $date$
 **/
@Controller
@RequestMapping(value="/punching")
public class PunchingController extends BaseController {
    // 打孔接口
    @Resource(name = "punchingService")
    private PunchingService punchingService;
    // 用户
    @Resource(name = "userService")
    private UserManager userService;
    // 重扩接口
    @Resource(name = "rebuildService")
    private RebuildService rebuildService;
    /**
    *@Desc 打孔待办全部记录
    *@Author Wangjian
    *@Date 2018/11/8 11:08
    *@Params  * @param null
    */
    @RequestMapping(value = "/list")
    public ModelAndView List(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        Object userId = user.getUSER_ID();
        String id = userId.toString();
        logBefore(logger, "跳转页面");
        User userAndRoleById = userService.getUserAndRoleById(id);
        String role_name = userAndRoleById.getRole().getRNUMBER();
        if(!"R20171231726481".equals(role_name)&&!"R20180131375361".equals(role_name)){
            pd.put("userId",user.getUSER_ID());
        }
        page.setPd(pd);
        List<PageData> allMessage = punchingService.findAllMessage(page);
        List<PageData> projectName = rebuildService.findProjectName(pd);// 查询所有项目名称
        mv.addObject("QX",Jurisdiction.getHC()); // 权限
        mv.addObject("list",allMessage);
        mv.addObject("projectNameList",projectName);
        mv.addObject("pd",pd);
        mv.setViewName("system/punching/punchingList");
        return mv;
    }
}
