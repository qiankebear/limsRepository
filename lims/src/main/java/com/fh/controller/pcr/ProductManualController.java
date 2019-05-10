package com.fh.controller.pcr;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.project.ProjectManager;
import com.fh.service.pcr.ManualManager;
import com.fh.service.project.projectmanager.ProjectManagerManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 产品说明说的上传与下载
 *
 * @author xiongyanbiao on 2018-11-02-上午 11:50
 * @version 1.0
 */
@Controller
@RequestMapping(value="/manual")
public class ProductManualController extends BaseController {

    /**
     *菜单地址(权限用)
     */

    String menuUrl = "manual/list.do";
    @Resource(name="manualService")
    private ManualManager manualManager;
    @Resource(name="roleService")
    private RoleManager roleService;
    @Resource(name="projectmanagerService")
    private ProjectManagerManager projectManagerManager;
    @Resource(name="userService")
    private UserManager userService;


   /**
   *@author xiongyanbiao
   *@$description: 查询说明书列表
   *@date 下午 14:35 2018/11/2 0002
   *@param  page
   *@return org.springframework.web.servlet.ModelAndView
   **/
    @RequestMapping(value="/list")
    public ModelAndView listManual(Page page){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            // 检索条件 关键词
            String keywords = pd.getString("keywords");
            if(null != keywords && !"".equals(keywords)){
                pd.put("keywords", keywords.trim());
            }
            page.setPd(pd);
            List<PageData> manualList = manualManager.list(page);
            // 列出ProjectManager列表
            List<ProjectManager> varList = projectManagerManager.listAllProject(pd);
            mv.addObject("varList", varList);
            mv.setViewName("pcr/manual/manual_list");
            mv.addObject("manualList", manualList);
            mv.addObject("pd", pd);
            // 按钮权限
            mv.addObject("QX", Jurisdiction.getHC());
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
    *@author xiongyanbiao
    *@$description: 去新增说明书页面
    *@date 下午 14:01 2018/11/5 0005
    *@param
    *@return org.springframework.web.servlet.ModelAndView
    **/
    @RequestMapping(value="/goAddU")
    public ModelAndView goAddU() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<ProjectManager>  projectlist = projectManagerManager.listAllProject(pd);
        mv.setViewName("pcr/manual/manual_edit");
        mv.addObject("msg", "saveU");
        mv.addObject("pd", pd);
        mv.addObject("projectlist", projectlist);
        return mv;
    }

    /**保存说明书
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/saveU")
    public ModelAndView saveU(
            @RequestParam(value="pdf", required=false) MultipartFile file,
            @RequestParam("PROJECT_NAME") String projectName
    ) throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"新增说明书");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        if (null != file && !file.isEmpty() && !StringUtils.isEmpty(projectName)) {
            // 文件名称
            String dateStr = DateUtil.getSdfTimes();
            String fileName = file.getOriginalFilename();
            String fileName2 = fileName.substring(0, fileName.indexOf("."))+dateStr;
            // 文件上传路径
            String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;
            try{
                // 执行上传
                String fileNamenew =  FileUpload.fileUp(file, filePath, fileName2);
                mv.addObject("msg", "success");
                pd.put("MANUAL_NAME", fileName);
                pd.put("MANUAL_URL", filePath+"/"+fileNamenew);
                pd.put("PROJECT_ID", Integer.parseInt(projectName));
                manualManager.save(pd);
            }catch (Exception e){
                e.printStackTrace();
                mv.addObject("msg", "failed");
            }
        }else {
            mv.addObject("msg", "failed");
        }
        mv.setViewName("save_result");
        return mv;
    }


    /**
    *@author xiongyanbiao
    *@$description: 导出说明书
    *@date 下午 17:00 2018/11/6 0006
    *@param
    *@return org.springframework.web.servlet.ModelAndView
    **/
    @RequestMapping(value="/toPdf")
    public ModelAndView toPdf(
            HttpServletResponse response
    ) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Boolean checkout = !StringUtils.isEmpty(pd.getString("MANUAL_NAME")) && !StringUtils.isEmpty(pd.getString("MANUAL_URL"));
        if(checkout){
            // 文件名称
            String fileName = pd.getString("MANUAL_NAME");
            try{
                // 文件下载路径
                String filePath = pd.getString("MANUAL_URL");
                // 执行下载
                FileDownload.fileDownload(response, filePath, fileName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mv.setViewName("manual/list.do");
        return mv;
    }


    /**删除说明书
     * @param out
     * @throws Exception
     */
    @RequestMapping(value="/delete")
    public void deleteU(PrintWriter out) throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"删除说明书");
        PageData pd = new PageData();
        pd = this.getPageData();
        manualManager.delete(pd);
        out.write("success");
        out.close();
        }

    /**批量删除说明书
     * @return
     */
    @RequestMapping(value="/deleteAll")
    @ResponseBody
    public Object deleteAllU() {
        logBefore(logger, Jurisdiction.getUsername()+"批量删除说明书");
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String IDS = pd.getString("IDS");
            if(null != IDS && !"".equals(IDS)){
                String ArrayUSER_IDS[] = IDS.split(",");
                manualManager.deleteAll(ArrayUSER_IDS);
                pd.put("msg", "ok");
            }else{
                pd.put("msg", "no");
            }
            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**显示个人文件列表
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/listfile")
    public ModelAndView listFile(Page page){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            // 检索条件 关键词
            String keywords = pd.getString("keywords");
            if(null != keywords && !"".equals(keywords)){
                pd.put("keywords", keywords.trim());
            }
            // 当前用户
            String user = Jurisdiction.getUsername();
            PageData pd2 = new PageData();
            pd2.put("USERNAME", user);
            PageData user2 = userService.findByUsername(pd2);
            PageData pd3 = new PageData();
            pd3.put("ROLE_ID", user2.get("ROLE_ID").toString());
            pd3 = roleService.findObjectById(pd3);
            if(!"R20170000000001".equals(pd3.getString("RNUMBER"))&&!"R20171231726481".equals(pd3.getString("RNUMBER"))){
                String userId = user2.get("USER_ID").toString();
                pd.put("userid", userId);
            }
            page.setPd(pd);
            List<PageData> personalfileList = manualManager.datalistPagepersonalAll(page);
            mv.setViewName("pcr/personalfile/personalfile_list");
            mv.addObject("personalfileList", personalfileList);
            mv.addObject("pd", pd);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     *@author xiongyanbiao
     *@$description: 去新增个人文件页面
     *@date 下午 14:01 2018/11/5 0005
     *@param
     *@return org.springframework.web.servlet.ModelAndView
     **/
    @RequestMapping(value="/goAddP")
    public ModelAndView goAddP() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("pcr/personalfile/personalfile_edit");
        mv.addObject("msg", "saveP");
        mv.addObject("pd", pd);
        return mv;
    }

    /**保存个人文件
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/saveP")
    public ModelAndView saveP(
            @RequestParam(value="personalfile", required=false) MultipartFile file,
            @RequestParam("lims_personalfile_explain") String lims_personalfile_explain
    ) throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"新增个人文件");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        // 文件名称
        String dateStr = DateUtil.getSdfTimes();
        String fileName = file.getOriginalFilename();
        String fileName2 = fileName.substring(0, fileName.indexOf("."))+dateStr;
        // 文件上传路径
        String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;
            try{
                if(StringUtils.isEmpty(lims_personalfile_explain)){
                    lims_personalfile_explain = "";
                }
                // 当前用户
                String user = Jurisdiction.getUsername();
                PageData pd2 = new PageData();
                pd2.put("USERNAME", user);
                PageData user2 = userService.findByUsername(pd2);
                String userid = user2.get("USER_ID").toString();
                // 执行上传
                String fileNamenew =  FileUpload.fileUp(file, filePath, fileName2);
                mv.addObject("msg", "success");
                pd.put("lims_personalfile_name", fileName);
                pd.put("lims_personalfile_path", filePath+"/"+fileNamenew);
                pd.put("lims_personalfile_explain", lims_personalfile_explain);
                pd.put("userid", userid);
                manualManager.saveP(pd);
            }catch (Exception e){
                e.printStackTrace();
                mv.addObject("msg", "failed");
            }
        mv.setViewName("save_result");
        return mv;
    }


    /**
     *@author xiongyanbiao
     *@$description: 导出个人文件
     *@date 下午 17:00 2018/11/6 0006
     *@param
     *@return org.springframework.web.servlet.ModelAndView
     **/
    @RequestMapping(value="/toPersonalfile")
    public ModelAndView toPersonalfile(HttpServletResponse response ) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Boolean checkout = !StringUtils.isEmpty(pd.getString("lims_personalfile_path")) && !
                StringUtils.isEmpty(pd.getString("lims_personalfile_name"));
        if(checkout){
            // 文件名称
            String fileName = pd.getString("lims_personalfile_name");
            try{
                // 文件下载路径
                String filePath = pd.getString("lims_personalfile_path");
                // 执行下载
                FileDownload.fileDownload(response, filePath, fileName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mv.setViewName("manual/listFile.do");
        return mv;
    }
    /**删除个人文件
     * @param out
     * @throws Exception
     */
    @RequestMapping(value="/deletep")
    public void deleteP(PrintWriter out) throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"删除个人文件");
        PageData pd = new PageData();
        pd = this.getPageData();
        manualManager.deleteP(pd);
        out.write("success");
        out.close();
    }

    /**批量删除个人文件
     * @return
     */
    @RequestMapping(value="/deleteAllp")
    @ResponseBody
    public Object deleteAllP() {
        logBefore(logger, Jurisdiction.getUsername()+"批量删除个人文件");
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String IDS = pd.getString("IDS");
            if(null != IDS && !"".equals(IDS)){
                String ArrayUSER_IDS[] = IDS.split(",");
                manualManager.deleteAllP(ArrayUSER_IDS);
                pd.put("msg", "ok");
            }else{
                pd.put("msg", "no");
            }
            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }
    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
