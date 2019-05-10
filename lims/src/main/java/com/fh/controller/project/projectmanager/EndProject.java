package com.fh.controller.project.projectmanager;

import com.fh.controller.activiti.AcStartController;
import com.fh.service.project.projectmanager.ProjectManagerManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 结束项目工作流
 *
 * @author xiongyanbiao on 2018-11-13-下午 14:58
 * @version 1.0
 */
@Controller
@RequestMapping(value="/endProject")
public class EndProject extends AcStartController {


    @Resource(name="projectmanagerService")
    private ProjectManagerManager projectmanagerService;


    /**
     *@author xiongyanbiao
     *@$description: 结束项目
     *@date 下午 14:54 2018/11/13 0013
     *@param
     *@return void
     **/
    @RequestMapping(value="/save")
    public ModelAndView endProject() throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"结束ProjectManager");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        PageData pd1 = new PageData();
        pd = this.getPageData();
        // pd.put("id",Long.valueOf(pd.get("id").toString()));
        try {
            // 根据ID读取
            pd1 = projectmanagerService.findById(pd);
            /** 工作流的操作 **/
            Map<String,Object> map = new LinkedHashMap<String, Object>();
            // 指派代理人为当前用户
            String project_number = pd1.get("project_number").toString();
            String project_name =pd1.get("project_name").toString();
            String project_status = pd1.get("project_status").toString();
            if(!StringUtils.isEmpty(project_number)){
                map.put("项目编号", project_number);
            }
            if(!StringUtils.isEmpty(project_name)){
                map.put("项目名称", project_name);
            }
            if(!StringUtils.isEmpty(project_status)){
                switch (project_status){
                    case "1":
                        map.put("项目状态", "准备中");
                        break;
                    case "2":map.put("项目状态", "进行中");
                        break;
                    case "3":map.put("项目状态", "已完成");
                        break;
                    case "4":map.put("项目状态", "其它");
                        break;
                     default:map.put("项目状态", "未知");
                         map.put("项目状态", project_status);
                         break;
                }
            }
            System.out.print("ss");

            map.put("projectid", pd.get("id").toString());
            map.put("USERNAME", Jurisdiction.getUsername());
            // 启动
            startProcessInstanceByKeyHasVariables("KEY_leave", map);
            // 用于给待办人发送新任务消息
            mv.addObject("ASSIGNEE_", Jurisdiction.getUsername());
            mv.addObject("msg", "success");
        } catch (Exception e) {
            mv.addObject("errer", "errer");
            mv.addObject("msgContent", "请联系管理员部署相应业务流程!");
        }
        mv.setViewName("save_result");
        return mv;
    }


    /**验证该项目下是否有么诶呀复核质检通过的板子
     * @param
     * @throws Exception
     */
    @RequestMapping(value="/findNopassplate")
    @ResponseBody
    public Object findNopassplate() throws Exception{
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<>();
        pd = this.getPageData();
        String projectId = pd.get("projectId").toString();
        pd.put("projectId", Long.valueOf(pd.get("projectId").toString()));
        if(StringUtils.isEmpty(projectId)){
            return AppUtil.returnObject(pd, map);
        }
        List<PageData> list = projectmanagerService.findnopassByProjectId(pd);
        Boolean status = false;
        if(list.isEmpty()){
            status = true;
        }
        map.put("status", status);
        return AppUtil.returnObject(pd,map);
    }



}
