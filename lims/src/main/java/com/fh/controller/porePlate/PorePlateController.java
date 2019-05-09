package com.fh.controller.porePlate;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.porePlate.PorePlateService;
import com.fh.service.rebuild.RebuildService;
import com.fh.service.system.instrument.impl.InstrumentService;
import com.fh.service.system.user.UserManager;
import com.fh.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:$
 * @Param:$
 * @return:$
 * @Author:Mr.Wang
 * @Date:$
 */
@Controller
@RequestMapping(value = "/porePlate")
public class PorePlateController extends BaseController {
    String menuUrl = "porePlate/list.do"; //菜单地址(权限用)
    @Resource(name = "porePlateService")
    private PorePlateService porePlateService;
    @Resource(name = "instrumentService")
    private InstrumentService instrumentService;
    @Resource(name = "rebuildService")
    private RebuildService rebuildService;
    // 用户
    @Resource(name = "userService")
    private UserManager userService;

    /**
     * 去孔板管理页面
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView toPorePlate(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        User userAndRoleById = userService.getUserAndRoleById(user.getUSER_ID());
        String role_name = userAndRoleById.getRole().getROLE_NAME();
        if (!"经理".equals(role_name) && !"副经理".equals(role_name)) {
            pd.put("userId", user.getUSER_ID());
        }
        page.setPd(pd);
        // 查出所有的孔板
        List<PageData> porePlateList = porePlateService.porePlatelistPage(page);
        // 查询所有项目名称
        List<PageData> projectName = rebuildService.findProjectName(pd);
        mv.addObject("porePlateList", porePlateList);
        mv.setViewName("porePlate/porePlate_list");
        mv.addObject("pd", pd);
        mv.addObject("projectNameList", projectName);
        // 按钮权限
        mv.addObject("QX", Jurisdiction.getHC());
        return mv;
    }


    /**
     * 去新增孔板页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goAddPorePlate")
    public ModelAndView goAddPorePlate() throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("project_status", "3");
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        pd.put("userId", user.getUSER_ID());
        // 查询所有的项目
        List<PageData> projectList = porePlateService.listProject(pd);
        mv.setViewName("porePlate/porePlate_edit");
        mv.addObject("msg", "savePorePlate");
        mv.addObject("pore_plate_type", pd.getString("pore_plate_type"));
        mv.addObject("projectList", projectList);
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 完成pcr扩增去选择仪器页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goSelectInstrument")
    public ModelAndView goSelectInstrument() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        pd.put("userId", user.getUSER_ID());
        // 查询所有仪器
        List<PageData> instrumentList = instrumentService.getFunction(pd);
        // 获取当前孔板pcr扩增记录
        PageData pcrRecord = porePlateService.getPcrRecord(pd);
        // 查询扩增记录 回显
        if (pcrRecord != null) {
            // 仪器id
            String ROLE_IDS = pcrRecord.getString("instrument_info");
            List<PageData> froleList = new ArrayList<PageData>();
            // 判断当前选中的仪器id 页面回显选中
            if (Tools.notEmpty(ROLE_IDS)) {
                String arryROLE_ID[] = ROLE_IDS.split(",");
                for (int i = 0; i < instrumentList.size(); i++) {
                    PageData instrument = instrumentList.get(i);
                    String roleId = instrument.get("id").toString();
                    for (int n = 0; n < arryROLE_ID.length; n++) {
                        if (arryROLE_ID[n].equals(roleId)) {
                            instrument.put("right", "1");
                            break;
                        }
                    }
                    froleList.add(instrument);
                }
            } else {
                froleList = instrumentList;
            }
        }
        mv.setViewName("porePlate/selectInstrument");
        mv.addObject("pd", pd);
        mv.addObject("pcrRecord", pcrRecord);
        mv.addObject("instrumentList", instrumentList);
        return mv;
    }

    /**
     * 完成检测去选择仪器页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goSelectInstrumentForTest")
    public ModelAndView goSelectInstrumentForTest() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        pd.put("userId", user.getUSER_ID());
        List<PageData> instrumentList = instrumentService.getFunctionForTest(pd);
        pd.put("pcr_pore", pd.getString("id"));
        // 获取当前孔板检测记录
        PageData pcrRecord = porePlateService.selectKit_record(pd);
        // 查询记录回显
        if (pcrRecord != null) {
            String ROLE_IDS = pcrRecord.getString("instrument_info");                    //仪器id
            List<PageData> froleList = new ArrayList<PageData>();
            if (Tools.notEmpty(ROLE_IDS)) {
                String arryROLE_ID[] = ROLE_IDS.split(",");
                for (int i = 0; i < instrumentList.size(); i++) {
                    PageData instrument = instrumentList.get(i);
                    String roleId = instrument.get("id").toString();
                    for (int n = 0; n < arryROLE_ID.length; n++) {
                        // 判断当前选中的仪器id  页面选中
                        if (arryROLE_ID[n].equals(roleId)) {
                            instrument.put("right", "1");
                            break;
                        }
                    }
                    froleList.add(instrument);
                }
            } else {
                froleList = instrumentList;
            }
        }
        mv.setViewName("porePlate/selectInstrumentForTest");
        mv.addObject("pd", pd);
        mv.addObject("pcrRecord", pcrRecord);
        mv.addObject("instrumentList", instrumentList);
        return mv;
    }

    /**
     * 保存孔板
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savePorePlate")
    public ModelAndView savePorePlate() throws Exception {
        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }

        logBefore(logger, Jurisdiction.getUsername() + "新增PorePlate");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        // 获取当前字符串格式时间
        String plate_createtime = DateUtil.getTime();
        pd.put("fabric_swatch_people", user.getUSER_ID());
        pd.put("sample_sum", 0);
        pd.put("pore_plate_quality", 3);
        pd.put("pore_plate_entirety", 2);
        pd.put("current_procedure", 0);
        pd.put("lims_pore_serial", 1);
        pd.put("plate_createtime", plate_createtime);
        pd.remove("id");
        porePlateService.savePorePlate(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 查看孔板
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goEditPorePlate")
    public ModelAndView goEditPorePlate() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        pd = porePlateService.goEditPorePlate(pd);
        // 根据ID读取
        mv.setViewName("porePlate/porePlate_edit");
        mv.addObject("msg", "editPorePlate");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 修改孔板
     */
    @RequestMapping(value = "/editPorePlate")
    public ModelAndView editPorePlate() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "修改PorePlate");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        porePlateService.editPorePlate(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }


    /**
     * 删除孔板
     */
    @RequestMapping(value = "/deletePorePlate")
    @ResponseBody
    public Object deletePorePlate() throws Exception {
        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        }
        logBefore(logger, Jurisdiction.getUsername() + "删除PorePlate");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        // 查询是否有样本  有不可以删除
        PageData sapmle = porePlateService.findSample(pd);
        if ("0".equals(sapmle.get("userCount").toString())) {
            porePlateService.deletePorePlate(pd);
            map.put("msg", "ok");
        } else {
            map.put("msg", "no");
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 批量删除
     *
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAllPorePlate")
    @ResponseBody
    public Object deleteAllPorePlate() throws Exception {
        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        }

        logBefore(logger, Jurisdiction.getUsername() + "批量删除PorePlate");
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String ids = pd.getString("ids");
        //判断是否选中孔板
        if (null != ids && !"".equals(ids)) {
            String ArrayUSER_IDS[] = ids.split(",");
            String arrayUSER_IDS = "";
            String noDelete = "";
            PageData pageData = new PageData();
            //删除 判断是否有样本 有不可删除
            for (String id : ArrayUSER_IDS) {
                pageData.put("id", id);
                PageData poreName = porePlateService.findSampleAndPoreName(pageData);
                String name = poreName.getString("poreName");
                if (StringUtils.isNotEmpty(name)) {
                    noDelete += name + ",";
                } else {
                    arrayUSER_IDS += id + ",";
                }
            }
            //如果没有可以删除的id则不删除
            if (null != arrayUSER_IDS && !"".equals(arrayUSER_IDS)) {
                String USER_IDS[] = arrayUSER_IDS.split(",");
                porePlateService.deleteAllPorePlate(USER_IDS);
                pd.put("msg", "ok");
            }
            //不能删除的有哪些 返回页面提示
            if (StringUtils.isNotEmpty(noDelete)) {
                map.put("noDelete", noDelete.substring(0, noDelete.length() - 1));
            } else {
                map.put("noDelete", "");
            }

        } else {
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }


    /**
     * 去智能布板板页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goToLayout")
    public ModelAndView goToLayout() throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "新增PorePlate");
        PageData pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        //获取当前字符串格式时间
        String plate_createtime = DateUtil.getTime();
        //保存孔板
        if (pd.size() != 0) {
            pd.put("fabric_swatch_people", user.getUSER_ID());
            pd.put("sample_sum", 0);
            pd.put("pore_plate_quality", 3);
            pd.put("pore_plate_entirety", 2);
            pd.put("current_procedure", 0);
            pd.put("lims_pore_serial", 1);
            pd.remove("id");
            pd.put("plate_createtime", plate_createtime);
            porePlateService.savePorePlate(pd);
        }
        ModelAndView mv = this.getModelAndView();
        //查询出复核孔个数 回显
        PageData recheck_hole_amount = porePlateService.findProjectRecheck_hole_amount(pd);
        //查询所有复核孔 回显
        List<PageData> checkHoleList = porePlateService.findProjectCheckHoleList(pd);
        mv.addObject("checkHoleList", JSONArray.fromObject(checkHoleList));
        pd.put("project_status", "3");
        pd.put("userId", user.getUSER_ID());
        // List<PageData> projectList = porePlateService.listProject(pd);
        //如果是质检孔 则跳转质检孔页面
        if ("2".equals(pd.getString("pore_plate_type"))) {
            mv.setViewName("porePlate/qualityPorePlate");
        } else {
            mv.setViewName("porePlate/porePlate");
        }
        mv.addObject("msg", "savePorePlate");
        mv.addObject("pore_plate_type", pd.getString("pore_plate_type"));
        pd.put("current_procedure", 0);
        mv.addObject("pd", pd);
        //判断是否有复核孔
        if (recheck_hole_amount != null) {
            mv.addObject("recheck_hole_amount", recheck_hole_amount.get("recheck_hole_amount"));
        }
        return mv;
    }


    /**
     * 去智能布板板页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/go")
    public ModelAndView go() throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "新增PorePlate");
        PageData pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        //获取当前字符串格式时间
        String plate_createtime = DateUtil.getTime();
        //保存孔板
        if (pd.size() != 0) {
            pd.put("fabric_swatch_people", user.getUSER_ID());
            pd.put("sample_sum", 0);
            pd.put("pore_plate_quality", 3);
            pd.put("pore_plate_entirety", 2);
            pd.put("current_procedure", 0);
            pd.put("lims_pore_serial", 1);
            pd.remove("id");
            pd.put("plate_createtime", plate_createtime);
            porePlateService.savePorePlate(pd);
        }
        ModelAndView mv = this.getModelAndView();
        //查询出复核孔个数 回显
        PageData recheck_hole_amount = porePlateService.findProjectRecheck_hole_amount(pd);
        //查询所有复核孔 回显
        List<PageData> checkHoleList = porePlateService.findProjectCheckHoleList(pd);
        mv.addObject("checkHoleList", JSONArray.fromObject(checkHoleList));
        pd.put("project_status", "3");
        pd.put("userId", user.getUSER_ID());
        if ("2".equals(pd.getString("pore_plate_type"))) {
            mv.setViewName("porePlate/qualityPorePlate");
        } else {
            mv.setViewName("porePlate/porePlate1");
        }

        mv.addObject("msg", "savePorePlate");
        mv.addObject("pore_plate_type", pd.getString("pore_plate_type"));
        pd.put("current_procedure", 0);
        mv.addObject("pd", pd);
        //判断是否有复核孔
        if (recheck_hole_amount != null) {
            mv.addObject("recheck_hole_amount", recheck_hole_amount.get("recheck_hole_amount"));
        }
        return mv;
    }

    /**
     * 去智能布板板页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goToLayoutEdit")
    public ModelAndView goToLayoutEdit() throws Exception {
        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }
        logBefore(logger, Jurisdiction.getUsername() + "修改PorePlate");
        PageData pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        porePlateService.editPorePlate(pd);
        ModelAndView mv = this.getModelAndView();
        pd.put("project_status", "3");
        pd.put("userId", user.getUSER_ID());
        pd.put("userName", user.getNAME());
        PageData projectPermission = porePlateService.getProjectPermission(pd);
        PageData pageDataCurrentProcedure = porePlateService.selectPoreCurrentProcedure(pd);
        pd.put("current_procedure", pageDataCurrentProcedure.get("current_procedure"));
        pd.put("lims_pore_serial", pageDataCurrentProcedure.get("lims_pore_serial"));
        List<PageData> poreList = porePlateService.findPorePlateList(pd);
        // 查询出所有复核孔  生成下拉框
        List<PageData> checkHoleList = porePlateService.findProjectCheckHoleList(pd);
        mv.addObject("checkHoleList", JSONArray.fromObject(checkHoleList));
        // 查询出复核孔个数 回显
        PageData recheck_hole_amount = porePlateService.findProjectRecheck_hole_amount(pd);
        mv.addObject("recheck_hole_amount", recheck_hole_amount.get("recheck_hole_amount"));

        if ("2".equals(pd.getString("pore_plate_type"))) {
            mv.setViewName("porePlate/qualityPorePlate");
        } else {
            mv.setViewName("porePlate/porePlate");
        }
        pd.put("pore_plate_procedure", 2);
        // 查询完成步骤人员
        String dakongren = porePlateService.dakongren(pd);
        pd.put("dakongren", dakongren);
        pd.put("pore_plate_procedure", 3);
        String kuozhengren = porePlateService.dakongren(pd);
        pd.put("kuozhengren", kuozhengren);
        pd.put("pore_plate_procedure", 4);
        String fenxiren = porePlateService.dakongren(pd);
        pd.put("fenxiren", fenxiren);
        mv.addObject("msg", "editPorePlate");
        mv.addObject("poreList", JSONArray.fromObject(poreList));
        mv.addObject("pore_plate_type", pd.getString("pore_plate_type"));
        mv.addObject("pd", pd);
        mv.addObject("projectPermission", projectPermission);
        return mv;
    }

    /**
     * 保存布板
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savePorePlateDetailed")
    @ResponseBody
    public Object savePorePlateDetailed() throws Exception {
        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }
        logBefore(logger, Jurisdiction.getUsername() + "新增PorePlate");
        PageData pd = this.getPageData();
        String json = pd.getString("json");
        logBefore(logger, Jurisdiction.getUsername() + "保存布板页面传递,参数为"+json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("programmers");
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = format.format(date);
        System.out.println(now);
        String poreNumAll = "";
        /********校验重复样本编号开始********/
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o = jsonArray.get(i);
            JSONObject json1 = JSONObject.fromObject(o);
            // 2.P 3.O不用判断   5.ladder 6.空孔不用判断
            if (!"5".equals(json1.getString("hole_type")) && !"6".equals(json1.getString("hole_type")) && !"2".equals(json1.getString("hole_type")) && !"3".equals(json1.getString("hole_type"))) {
                String poreNum = json1.getString("poreNum");
                poreNumAll += poreNum + ",";
            }

        }
        List<PageData> repeatNumber = new ArrayList<>();
        // 通过页面传递样本编号查询数据库中是否存在
        if (StringUtils.isNotEmpty(poreNumAll)) {
            pd.put("item", poreNumAll.split(","));
            repeatNumber = porePlateService.selectRepeatNumber(pd);
        }
        // 如果存在  获取到样本编号  返回页面提示
        if (repeatNumber.size() > 0) {
            Map<Object, Object> map = new HashMap<>();
            String NUM = "";
            for (int i = 0; i < repeatNumber.size(); i++) {
                NUM += repeatNumber.get(i).getString("sample_number") + ",";
            }
            map.put("msg", "有重复的样本编号,编号为" + NUM);
            return AppUtil.returnObject(pd, map);
        }
        /********校验重复样本编号结束********/
        ArrayList<PageData> sampleList = new ArrayList<PageData>();
        ArrayList<PageData> porePlateList = new ArrayList<PageData>();
        // 开始准备数据保存样本编号
        for (int i = 0; i < jsonArray.size(); i++) {
            // 开辟新线程保存数据
            Object o = jsonArray.get(i);
            JSONObject json1 = JSONObject.fromObject(o);
            String hole_number = json1.getString("hole_number");
            String poreNum = json1.getString("poreNum");
            String hole_type = json1.getString("hole_type");
            String fuhekongId = json1.getString("fuhekongId");
            // 准备样本数据
            PageData pdSample = new PageData();
            if ("5".equals(hole_type)) {
                pdSample.put("sample_number", "LADDER");
            } else if ("2".equals(hole_type)) {
                pdSample.put("sample_number", "P");
            } else if ("3".equals(hole_type)) {
                pdSample.put("sample_number", "O");
            } else {
                pdSample.put("sample_number", poreNum);
            }
            pdSample.put("sample_generate_time", now);
            pdSample.put("sample_course", 5);
            pdSample.put("sample_serial", 1);
            pdSample.put("sample_project_id", pd.get("plate_project_id"));
            String uuid = UUID.randomUUID().toString();
            pdSample.put("id", uuid);
            sampleList.add(pdSample);

            // 保存孔类型表
            PageData pageData = new PageData();
            pageData.put("hole_number", hole_number);
            pageData.put("hole_type", hole_type);
            pageData.put("hole_special_sample", 9);
            pageData.put("hole_sample_remark", "");
            pageData.put("hole_sample_serial", 1);
            pageData.put("hole_poreid", pd.getString("id"));
            pageData.put("hole_sample_course", 1);
            pageData.put("hole_sampleid", uuid);
            // 如果是质检孔  更新复核孔的是否被质检状态
            if ("7".equals(hole_type) && StringUtils.isNotEmpty(fuhekongId)) {
                PageData pageData1 = new PageData();
                pageData1.put("hole_sampleid", fuhekongId);
                pageData1.put("isselect", 1);
                porePlateService.updateSample(pageData1);
            }
            porePlateList.add(pageData);

        }
        logBefore(logger, Jurisdiction.getUsername() + "保存布板sample循环之后,参数为"+sampleList.toString()+"长度为:"+sampleList.size());
        // 保存sample表 一次性插入
        porePlateService.saveSampleList(sampleList);
        // 保存pore_plate表 一次性插入
        porePlateService.savePorePlateDetailedList(porePlateList);
        logBefore(logger, Jurisdiction.getUsername() + "保存布板porePlate循环之后,参数为"+porePlateList.toString()+"长度为:"+porePlateList.size());
        pd.put("current_procedure", 1);
        pd.put("slotting_complete", now);
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        pd.put("slotting_userid", user.getUSER_ID());
        // 修改孔板状态
        porePlateService.editPorePlate(pd);
        // 保存修改记录
        porePlateService.svarlims_pore_user(pd);
        Map<Object, Object> map = new HashMap<>();
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 修改布板
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editPorePlateDetailed")
    @ResponseBody
    public Object editPorePlateDetailed() throws Exception {
        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = format.format(date);
        logBefore(logger, Jurisdiction.getUsername() + "修改PorePlate");
        PageData pd = this.getPageData();
        // 页面所有的样本
        String json = pd.getString("json");
        logBefore(logger, Jurisdiction.getUsername() + "修改布板页面传递,参数为"+json);
        // 页面传递全部参数
        JSONObject jsonObject = JSONObject.fromObject(json);
        // 页面传递参数programmers
        JSONArray jsonArrayNew = jsonObject.getJSONArray("programmers");
        // 准备容器
        List<PageData> sampleList = new ArrayList<PageData>();
        // 页面新增的样本
        List<PageData> newsampleList = new ArrayList<PageData>();
        // 页面与数据库比对是更新的孔类型
        List<PageData> updateHoleTypeList = new ArrayList<PageData>();
        // 页面与数据库比对是更新的孔类型
        List<PageData> updateSampleList = new ArrayList<PageData>();
        // 页面与数据库比对是更新的样本
        List<PageData> updateSampleListNew = new ArrayList<PageData>();
        // 放修改记录
        List<PageData> updateRecordPorePlateList = new ArrayList<PageData>();
        // 当前孔板信息
        List<PageData> porePlateList = new ArrayList<PageData>();


        /****************保存修改记录开始******************/
        // 查询出现在所有的孔板跟修改之后的孔板对比  保存到修改记录


        // 查询当前所有孔板的样本
        List<PageData> poreList = porePlateService.findPorePlateList(pd);
        JSONArray jsonArrayOld = JSONArray.fromObject(poreList);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环页面传递参数
                    for (int i = 0; i < jsonArrayNew.size(); i++) {
                        // 获取页面传递孔板信息
                        Object o = jsonArrayNew.get(i);
                        JSONObject json1 = JSONObject.fromObject(o);
                        String hole_number = json1.getString("hole_number");
                        // 孔类型
                        String hole_type = json1.getString("hole_type");
                        // 复核孔id
                        String fuhekongId = json1.getString("fuhekongId");
                        // 坐标
                        String sample_number = json1.getString("poreNum");
                        // 孔类型表id
                        String htId = json1.getString("htId");
                        // 标识当前孔板是否是新添加
                        boolean isNewAdd = true;
                        // 循环 数据库查询出来的参数
                        for (int j = 0; j < jsonArrayOld.size(); j++) {
                            Object newO = jsonArrayOld.get(j);
                            JSONObject jsonOld = JSONObject.fromObject(newO);
                            String jsonOld_hole_number = "";
                            String old_sample_number = "";
                            String old_hole_type = "";
                            String old_hole_special_sample = "";
                            // 如果有样本编号 则获取内容去判断
                            if (jsonOld.has("hole_number")) {
                                jsonOld_hole_number = jsonOld.getString("hole_number");
                                old_sample_number = jsonOld.getString("sample_number");
                                old_hole_type = jsonOld.getString("hole_type");
                                old_hole_special_sample = jsonOld.getString("hole_special_sample");
                            }
                            //如果孔坐标(A01)相同  比较内容
                            if (hole_number.equals(jsonOld_hole_number)) {
                                isNewAdd = false;
                                //如果内容修改
                                if (!old_hole_type.equals(hole_type) || !sample_number.equals(old_sample_number)) {
                                    //不相同先保存新的lims_sample
                                    PageData newSsample = new PageData();
                                    newSsample.put("sample_number", sample_number);
                                    newSsample.put("sample_generate_time", now);
                                    newSsample.put("sample_course", 5);
                                    newSsample.put("sample_serial", 1);
                                    newSsample.put("sample_project_id", pd.get("plate_project_id"));
                                    String uuid = UUID.randomUUID().toString();
                                    newSsample.put("id", uuid);
                                    sampleList.add(newSsample);
                                    // 保存修改为批量保存
                                    // int i1 = porePlateService.saveSample(newSsample);
                                    // 获取到新的newSampleId
                                    Object newSampleId = newSsample.get("id");
                                    // 然后修改lims_hole_type表中的hole_sampleid和内容
                                    PageData updateHoleType = new PageData();
                                    updateHoleType.put("id", htId);
                                    updateHoleType.put("hole_type", hole_type);
                                    updateHoleType.put("hole_sampleid", uuid);
                                    // 执行更新 holetype  改为批量更新
                                    // porePlateService.updateHoleType(updateHoleType);
                                    updateHoleTypeList.add(updateHoleType);
                                    // 插入更新记录
                                    PageData updateRecordPorePlate = new PageData();
                                    updateRecordPorePlate.put("hole_number", hole_number);
                                    updateRecordPorePlate.put("hole_poreid", pd.get("id"));
                                    updateRecordPorePlate.put("oldhole_type", old_hole_type);
                                    updateRecordPorePlate.put("newhole_type", hole_type);
                                    // 如果是质检孔 更新复核孔选中状态
                                    if ("7".equals(hole_type)) {
                                        PageData pageData = new PageData();
                                        pageData.put("hole_sampleid", fuhekongId);
                                        pageData.put("isselect", 1);
                                        updateSampleList.add(pageData);
                                        // 改为批量更新
                                        // porePlateService.updateSample(pageData);

                                    }
                                    updateRecordPorePlate.put("old_sampleid", jsonOld.getString("id"));
                                    // 新的sampleId
                                    updateRecordPorePlate.put("new_sampleid", uuid);
                                    updateRecordPorePlate.put("oldspecial_sample", old_hole_special_sample);
                                    updateRecordPorePlate.put("newspecial_sample", 9);
                                    updateRecordPorePlate.put("update_type", 1);
                                    updateRecordPorePlate.put("update_people", user.getUSER_ID());
                                    updateRecordPorePlate.put("update_time", now);
                                    // 保存更新记录  修改为批量保存
                                    updateRecordPorePlateList.add(updateRecordPorePlate);
                                    // porePlateService.saveUpdateRecordPorePlate(updateRecordPorePlate);

                                    break;
                                }
                            }
                        }
                        // 如果循环完毕没有空坐标相等 则是新添加内容
                        if (isNewAdd) {
                            PageData newSsample = new PageData();
                            newSsample.put("sample_number", sample_number);
                            newSsample.put("sample_generate_time", now);
                            // 样本进程 轮数  不知道存什么  先存 1
                            newSsample.put("sample_course", 5);
                            newSsample.put("sample_serial", 1);
                            String uuid = UUID.randomUUID().toString();
                            newSsample.put("id", uuid);
                            newSsample.put("sample_project_id", pd.get("plate_project_id"));
                            newsampleList.add(newSsample);
                            // 修改为批量保存
                            // porePlateService.saveSample(newSsample);
                            // 保存孔类型表
                            PageData pageData = new PageData();
                            pageData.put("hole_number", hole_number);
                            pageData.put("hole_type", hole_type);
                            pageData.put("hole_special_sample", 9);
                            pageData.put("hole_sample_remark", "");
                            pageData.put("hole_sample_serial", 1);
                            pageData.put("hole_poreid", pd.getString("id"));
                            pageData.put("hole_sample_course", 1);
                            pageData.put("hole_sampleid", uuid);
                            porePlateList.add(pageData);
                            // porePlateService.savePorePlateDetailed(pageData);
                            // 如果是质检孔 更新复核孔选中状态
                            if ("7".equals(hole_type)) {
                                PageData pageData1 = new PageData();
                                pageData1.put("hole_sampleid", fuhekongId);
                                pageData1.put("isselect", 1);
                                updateSampleListNew.add(pageData1);
                                // 改为批量
                                // porePlateService.updateSample(pageData1);
                            }
                        }
                    }
                    // 修改布板保存孔类型表 如果有 则去保存
                    if(porePlateList.size()>0){
                        logBefore(logger, Jurisdiction.getUsername() + "修改布板保存孔类型表,参数为"+porePlateList.toString()+"长度为:"+porePlateList.size());
                        porePlateService.savePorePlateDetailedList(porePlateList);
                    }

                    // 批量保存  //修改样本表 如果有 则去修改
                    if (updateSampleList.size() > 0) {
                        porePlateService.updateSampleList(updateSampleList);
                    }
                    // 需要更新的样本
                    if (updateSampleListNew.size() > 0) {
                        porePlateService.updateSampleList(updateSampleListNew);
                    }
                    // 新增的样本
                    if (sampleList.size() > 0) {
                        logBefore(logger, Jurisdiction.getUsername() + "修改布板sampleList,参数为"+sampleList.toString()+"长度为:"+sampleList.size());
                        porePlateService.saveSampleList(sampleList);
                    }
                    // 需要更新的holetype
                    if (updateHoleTypeList.size() > 0) {
                        porePlateService.updateHoleTypeList(updateHoleTypeList);
                    }
                    // 新增的样本
                    if (newsampleList.size() > 0) {
                        logBefore(logger, Jurisdiction.getUsername() + "修改布板新增样本newsampleList,参数为"+newsampleList.toString()+"长度为:"+newsampleList.size());
                        porePlateService.saveSampleList(newsampleList);
                    }
                    // 保存修改记录
                    if (updateRecordPorePlateList.size() > 0) {
                        porePlateService.saveUpdateRecordPorePlateList(updateRecordPorePlateList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /****************保存修改记录结束******************/
        // 修改步骤 需要根据当前步骤判断 所以先查询出当前步骤
        PageData pageData = porePlateService.selectPoreCurrentProcedure(pd);
        if (pageData != null) {
            String current_procedureNow = pageData.get("current_procedure").toString();
            // 如果是0  则修改为1
            if ("0".equals(current_procedureNow)) {
                pd.put("current_procedure", 1);
            }
        }
        pd.put("slotting_complete", now);
        pd.put("slotting_userid", user.getUSER_ID());
        porePlateService.editPorePlate(pd);

        Map<Object, Object> map = new HashMap<>();
        return AppUtil.returnObject(pd, map);
    }


    /**
     * 导出到excel
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        // 查询出完成步骤人员
        pd.put("pore_plate_procedure", 2);
        String dakongren = porePlateService.dakongren(pd);
        pd.put("dakongren", dakongren);
        pd.put("pore_plate_procedure", 3);
        String kuozhengren = porePlateService.dakongren(pd);
        pd.put("kuozhengren", kuozhengren);
        pd.put("pore_plate_procedure", 4);
        String fenxiren = porePlateService.dakongren(pd);
        pd.put("fenxiren", fenxiren);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArrayNew = new JSONArray();
        String json = pd.getString("json");
        // 拼接参数
        if (StringUtils.isNotEmpty(json)) {
            jsonObject = JSONObject.fromObject(json);
            jsonArrayNew = jsonObject.getJSONArray("programmers");
        } else {
            String array = "{'programmers':[";
            // 拼接 a01 -a12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'A0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'A" + z + "','poreNum':'','hole_type':''},";
                }
            }
            // 拼接 b01 -b12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'B0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'B" + z + "','poreNum':'','hole_type':''},";
                }
            }
            // 拼接 c01 -c12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'C0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'C" + z + "','poreNum':'','hole_type':''},";
                }
            }
            // 拼接 d01 -d12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'D0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'D" + z + "','poreNum':'','hole_type':''},";
                }
            }
            // 拼接 e01 -e12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'E0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'E" + z + "','poreNum':'','hole_type':''},";
                }
            }
            // 拼接 f01 -f12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'F0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'F" + z + "','poreNum':'','hole_type':''},";
                }
            }
            // 拼接 g01 -g12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'G0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'G" + z + "','poreNum':'','hole_type':''},";
                }
            }
            // 拼接 h01 -h12
            for (int z = 1; z <= 12; z++) {
                if (z < 10) {
                    array += "{'hole_number':'H0" + z + "','poreNum':'','hole_type':''},";
                } else {
                    array += "{'hole_number':'H" + z + "','poreNum':'','hole_type':''},";
                }
            }
            array = array.substring(0, array.length() - 1);
            array += "]}";
            JSONObject jsonO = JSONObject.fromObject(array);
            jsonArrayNew = jsonO.getJSONArray("programmers");
            // 查询出所有样本
            List<PageData> poreList = porePlateService.findPorePlateList(pd);
            JSONArray jsonArray = JSONArray.fromObject(poreList);
            // 循环放入准备好的参数中 也就是jsonArrayNew
            for (int i = 0; i < jsonArrayNew.size(); i++) {
                JSONObject newJson = (JSONObject) jsonArrayNew.get(i);
                for (int j = 0; j < jsonArray.size(); j++) {
                    JSONObject o = (JSONObject) jsonArray.get(j);
                    if (newJson.getString("hole_number").equals(o.getString("hole_number"))) {
                        newJson.put("poreNum", o.getString("poreNum"));
                        newJson.put("hole_type", o.getString("hole_type"));
                    }
                }
            }
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<PageData> varList = new ArrayList<PageData>();
        // 循环拼接好的参数  生成list  以便生成excel文件
        for (int i = 0; i < jsonArrayNew.size(); i++) {
            PageData vpd = new PageData();
            JSONObject jsonObject1 = (JSONObject) jsonArrayNew.get(i);
            vpd.put("var1", jsonObject1.getString("hole_number"));        //1
            // 如果是复核孔或者质检孔   则需要多将hole_type传入  以方便变色
            if ("4".equals(jsonObject1.getString("hole_type")) || "7".equals(jsonObject1.getString("hole_type"))) {
                vpd.put("var2", jsonObject1.getString("poreNum") + "(" + jsonObject1.getString("hole_type") + ")");
            } else {
                vpd.put("var2", jsonObject1.getString("poreNum"));
            }
            i++;
            JSONObject jsonObject2 = (JSONObject) jsonArrayNew.get(i);
            vpd.put("var3", jsonObject2.getString("hole_number"));        //2
            // 如果是复核孔或者质检孔   则需要多将hole_type传入  以方便变色
            if ("4".equals(jsonObject2.getString("hole_type")) || "7".equals(jsonObject2.getString("hole_type"))) {
                vpd.put("var4", jsonObject2.getString("poreNum") + "(" + jsonObject2.getString("hole_type") + ")");
            } else {
                vpd.put("var4", jsonObject2.getString("poreNum"));
            }
            i++;
            JSONObject jsonObject3 = (JSONObject) jsonArrayNew.get(i);
            vpd.put("var5", jsonObject3.getString("hole_number"));        //3
            // 如果是复核孔或者质检孔   则需要多将hole_type传入  以方便变色
            if ("4".equals(jsonObject3.getString("hole_type")) || "7".equals(jsonObject3.getString("hole_type"))) {
                vpd.put("var6", jsonObject3.getString("poreNum") + "(" + jsonObject3.getString("hole_type") + ")");
            } else {
                vpd.put("var6", jsonObject3.getString("poreNum"));
            }
            i++;
            JSONObject jsonObject4 = (JSONObject) jsonArrayNew.get(i);
            vpd.put("var7", jsonObject4.getString("hole_number"));        //4
            // 如果是复核孔或者质检孔   则需要多将hole_type传入  以方便变色
            if ("4".equals(jsonObject4.getString("hole_type")) || "7".equals(jsonObject4.getString("hole_type"))) {
                vpd.put("var8", jsonObject4.getString("poreNum") + "(" + jsonObject4.getString("hole_type") + ")");
            } else {
                vpd.put("var8", jsonObject4.getString("poreNum"));
            }
            i++;
            JSONObject jsonObject5 = (JSONObject) jsonArrayNew.get(i);
            vpd.put("var9", jsonObject5.getString("hole_number"));        //5
            // 如果是复核孔或者质检孔   则需要多将hole_type传入  以方便变色
            if ("4".equals(jsonObject5.getString("hole_type")) || "7".equals(jsonObject5.getString("hole_type"))) {
                vpd.put("var10", jsonObject5.getString("poreNum") + "(" + jsonObject5.getString("hole_type") + ")");
            } else {
                vpd.put("var10", jsonObject5.getString("poreNum"));
            }
            i++;
            JSONObject jsonObject6 = (JSONObject) jsonArrayNew.get(i);
            vpd.put("var11", jsonObject6.getString("hole_number"));        //6
            // 如果是复核孔或者质检孔   则需要多将hole_type传入  以方便变色
            if ("4".equals(jsonObject6.getString("hole_type")) || "7".equals(jsonObject6.getString("hole_type"))) {
                vpd.put("var12", jsonObject6.getString("poreNum") + "(" + jsonObject6.getString("hole_type") + ")");
            } else {
                vpd.put("var12", jsonObject6.getString("poreNum"));
            }
            varList.add(vpd);
            i += 6;

        }
        for (int j = 6; j < jsonArrayNew.size(); j++) {
            PageData vpd1 = new PageData();
            JSONObject jsonObject1b = (JSONObject) jsonArrayNew.get(j);
            vpd1.put("var1", jsonObject1b.getString("hole_number"));        //1
            if ("4".equals(jsonObject1b.getString("hole_type")) || "7".equals(jsonObject1b.getString("hole_type"))) {
                vpd1.put("var2", jsonObject1b.getString("poreNum") + "(" + jsonObject1b.getString("hole_type") + ")");
            } else {
                vpd1.put("var2", jsonObject1b.getString("poreNum"));
            }
            j++;
            JSONObject jsonObject2b = (JSONObject) jsonArrayNew.get(j);
            vpd1.put("var3", jsonObject2b.getString("hole_number"));        //2
            if ("4".equals(jsonObject2b.getString("hole_type")) || "7".equals(jsonObject2b.getString("hole_type"))) {
                vpd1.put("var4", jsonObject2b.getString("poreNum") + "(" + jsonObject2b.getString("hole_type") + ")");
            } else {
                vpd1.put("var4", jsonObject2b.getString("poreNum"));
            }
            j++;
            JSONObject jsonObject3b = (JSONObject) jsonArrayNew.get(j);
            vpd1.put("var5", jsonObject3b.getString("hole_number"));        //3
            if ("4".equals(jsonObject3b.getString("hole_type")) || "7".equals(jsonObject3b.getString("hole_type"))) {
                vpd1.put("var6", jsonObject3b.getString("poreNum") + "(" + jsonObject3b.getString("hole_type") + ")");
            } else {
                vpd1.put("var6", jsonObject3b.getString("poreNum"));
            }
            j++;
            JSONObject jsonObject4b = (JSONObject) jsonArrayNew.get(j);
            vpd1.put("var7", jsonObject4b.getString("hole_number"));        //4
            if ("4".equals(jsonObject4b.getString("hole_type")) || "7".equals(jsonObject4b.getString("hole_type"))) {
                vpd1.put("var8", jsonObject4b.getString("poreNum") + "(" + jsonObject4b.getString("hole_type") + ")");
            } else {
                vpd1.put("var8", jsonObject4b.getString("poreNum"));
            }
            j++;
            JSONObject jsonObject5b = (JSONObject) jsonArrayNew.get(j);
            vpd1.put("var9", jsonObject5b.getString("hole_number"));        //5
            if ("4".equals(jsonObject5b.getString("hole_type")) || "7".equals(jsonObject5b.getString("hole_type"))) {
                vpd1.put("var10", jsonObject5b.getString("poreNum") + "(" + jsonObject5b.getString("hole_type") + ")");
            } else {
                vpd1.put("var10", jsonObject5b.getString("poreNum"));
            }
            j++;
            JSONObject jsonObject6b = (JSONObject) jsonArrayNew.get(j);
            vpd1.put("var11", jsonObject6b.getString("hole_number"));        //6
            if ("4".equals(jsonObject6b.getString("hole_type")) || "7".equals(jsonObject6b.getString("hole_type"))) {
                vpd1.put("var12", jsonObject6b.getString("poreNum") + "(" + jsonObject6b.getString("hole_type") + ")");
            } else {
                vpd1.put("var12", jsonObject6b.getString("poreNum"));
            }
            varList.add(vpd1);
            j += 6;
        }
        dataMap.put("varList", varList);
        dataMap.put("title", pd);
        dataMap.put("json", jsonArrayNew);
        // 查询出所有重做样本id
        String ids = porePlateService.selectQuality_from_ids(pd);
        // 重做样本列表
        if (StringUtils.isNotEmpty(ids)) {
            List<PageData> entirety = rebuildService.findEntirety(ids.split(","));
            dataMap.put("entirety", JSONArray.fromObject(entirety));
        }
        ExportExcel erv = new ExportExcel();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }


    /**
     * 打印预览页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/printPage")
    public ModelAndView printPage() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String json = pd.getString("json");
        JSONObject jsonObject = JSONObject.fromObject(json);

        mv.addObject("poreList", JSONArray.fromObject(jsonObject.get("programmers")));
        mv.setViewName("porePlate/printPage");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 打开上传EXCEL页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goUploadExcel")
    public ModelAndView goUploadExcel() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("/porePlate/uploadexcel");
        return mv;
    }


    /**
     * 从EXCEL导入到数据库
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/readExcel")
    @ResponseBody
    public Object readExcel(
            @RequestParam(value = "excel", required = false) MultipartFile file
    ) throws Exception {

        PageData pd = new PageData();
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }
        Map<Object, Object> map = new HashMap<>();
        if (null != file && !file.isEmpty()) {
            String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
            String fileName = FileUpload.fileUp(file, filePath, "poreexcel");                            //执行上传
            List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 0, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
            map.put("data", JSONArray.fromObject(listPd));
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 完成其他步骤
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/completeOther")
    @ResponseBody
    public Object completeOther() throws Exception {
        PageData pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        pd.put("slotting_userid", user.getUSER_ID());
        // 修改步骤 需要根据当前步骤判断
        PageData pageData = porePlateService.selectPoreCurrentProcedure(pd);
        if (pageData != null) {
            // 页面传来的步骤
            String current_procedure = pd.getString("current_procedure");
            // 当前数据库所在步骤
            String current_procedureNow = pageData.get("current_procedure").toString();
            // 数据库为检测状态  页面传递分析状态 更新
            if ("5".equals(current_procedureNow) && "4".equals(current_procedure)) {
                porePlateService.completeOther(pd);
            }
            // 当前为pcr扩增  页面传递检测  更新
            if ("3".equals(current_procedureNow) && "5".equals(current_procedure)) {
                porePlateService.completeOther(pd);
            }
            // 当前为 打孔  页面传递pcr  更新
            if ("2".equals(current_procedureNow) && "3".equals(current_procedure)) {
                porePlateService.completeOther(pd);
            }
            // 当前为布板 页面传递 打孔
            if ("1".equals(current_procedureNow) && "2".equals(current_procedure)) {
                porePlateService.completeOther(pd);
            }
        }

        Map<Object, Object> map = new HashMap<>();
        String current_procedure = pd.getString("current_procedure");
        // 如果是扩增步骤
        if ("3".equals(current_procedure)) {
            PageData pcrRecord = new PageData();
            pcrRecord.put("pcr_pore", pd.getString("id"));
            // 获取当前扩增记录
            pcrRecord = porePlateService.selectPcr_record(pcrRecord);
            pd.put("pcr_experimenter_userid", user.getUSER_ID());
            // 如果没有扩增记录  则去新增
            if (pcrRecord == null) {
                // 保存
                porePlateService.savePcr_record(pd);
                porePlateService.svarlims_pore_user(pd);
            } else {
                // 更新
                pd.put("thisId", pcrRecord.get("id"));
                porePlateService.updatePcr_record(pd);
                porePlateService.svarlims_pore_user(pd);
            }
            return AppUtil.returnObject(pd, map);
        }
        // 如果是检测步骤 保存或修改对应对应记录
        if ("5".equals(current_procedure)) {
            pd.put("detection_experimenter_userid", user.getUSER_ID());
            PageData kitRecord = new PageData();
            kitRecord.put("pcr_pore", pd.getString("id"));
            kitRecord = porePlateService.selectKit_record(kitRecord);
            // 如果没有检测记录  则去新增
            if (kitRecord == null) {
                porePlateService.saveKit_record(pd);
                porePlateService.svarlims_pore_user(pd);
            } else {
                // 更新
                pd.put("thisId", kitRecord.get("id"));
                porePlateService.updateKit_record(pd);
                porePlateService.svarlims_pore_user(pd);
            }
            return AppUtil.returnObject(pd, map);
        }
        String json = pd.getString("json");
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray jsonArrayNew = jsonObject.getJSONArray("programmers");

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = format.format(date);

        // 查询出现在所有的孔板跟修改之后的孔板对比  保存到修改记录
        List<PageData> poreList = porePlateService.findPorePlateList(pd);
        JSONArray jsonArrayOld = JSONArray.fromObject(poreList);
        List<PageData> updateRecordPorePlateList = new ArrayList<PageData>();
        List<PageData> updateHoleTypeList = new ArrayList<PageData>();
        List<PageData> sampleList = new ArrayList<PageData>();
        // 如果是打孔步骤
        if ("2".equals(current_procedure)) {
            // 完成打孔 有修改  记录
            /****************保存修改记录开始******************/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 循环页面传递所有样本
                        for (int i = 0; i < jsonArrayNew.size(); i++) {
                            // 获取单个样本
                            Object o = jsonArrayNew.get(i);
                            JSONObject json1 = JSONObject.fromObject(o);
                            String hole_number = json1.getString("hole_number");
                            String hole_type = json1.getString("hole_type");
                            String hole_special_sample = json1.getString("hole_special_sample");
                            String hole_sample_remark = json1.getString("hole_sample_remark");
                            String sample_number = json1.getString("poreNum");
                            String htId = json1.getString("htId");
                            // 循环数据库所有样本
                            for (int j = 0; j < jsonArrayOld.size(); j++) {
                                Object newO = jsonArrayOld.get(j);
                                JSONObject jsonOld = JSONObject.fromObject(newO);
                                String jsonOld_hole_number = "";
                                String old_sample_number = "";
                                String old_hole_type = "";
                                String old_hole_special_sample = "";
                                if (jsonOld.has("hole_number")) {
                                    jsonOld_hole_number = jsonOld.getString("hole_number");
                                    old_sample_number = jsonOld.getString("sample_number");
                                    old_hole_type = jsonOld.getString("hole_type");
                                    old_hole_special_sample = jsonOld.getString("hole_special_sample");
                                }
                                // String hole_sample_remark= jsonOld.getString("hole_sample_remark");
                                // 如果孔坐标(A01)相同  比较内容
                                if (hole_number.equals(jsonOld_hole_number)) {
                                    // 如果内容修改
                                    if (!"7".equals(hole_type) && !old_hole_special_sample.equals(hole_special_sample) &&
                                            ("7".equals(hole_special_sample) || "8".equals(hole_special_sample) || "9".equals(hole_special_sample))) {
                                        // 然后修改lims_hole_type表中的hole_sampleid和内容
                                        PageData updateHoleType = new PageData();
                                        updateHoleType.put("id", htId);
                                        updateHoleType.put("hole_type", hole_type);
                                        updateHoleType.put("hole_special_sample", hole_special_sample);
                                        updateHoleType.put("hole_sample_remark", hole_sample_remark);
                                        // 如果是问题样本 或者 空卡
                                        if ("7".equals(hole_special_sample) || "8".equals(hole_special_sample)) {
                                            // TODO: 2019/3/27
                                            // 根据holetype id 查找sampleid 去更新
                                            PageData sampleId = porePlateService.getSampleid(updateHoleType);
                                            //执行更新 sample
                                            updateHoleType.put("hole_sampleid", sampleId.get("hole_sampleid"));
                                            updateHoleType.put("hole_sample_course", 3);
                                            sampleList.add(updateHoleType);
                                            // 修改为批量更新
                                            // porePlateService.updateSample(updateHoleType);
                                        }


                                        // 执行更新 holetype 改为批量更新
                                        // porePlateService.updateHoleType(updateHoleType);
                                        // 插入更新记录
                                        PageData updateRecordPorePlate = new PageData();
                                        updateRecordPorePlate.put("hole_number", hole_number);
                                        updateRecordPorePlate.put("hole_poreid", pd.get("id"));
                                        updateRecordPorePlate.put("oldhole_type", old_hole_type);
                                        updateRecordPorePlate.put("newhole_type", hole_type);
                                        updateRecordPorePlate.put("old_sampleid", jsonOld.getString("id"));
                                        // 新的sampleId
                                        updateRecordPorePlate.put("new_sampleid", jsonOld.getString("id"));
                                        updateRecordPorePlate.put("oldspecial_sample", old_hole_special_sample);
                                        updateRecordPorePlate.put("newspecial_sample", hole_special_sample);
                                        updateRecordPorePlate.put("update_type", current_procedure);
                                        updateRecordPorePlate.put("update_people", user.getUSER_ID());
                                        updateRecordPorePlate.put("update_time", now);
                                        // 改为批量更新
                                        updateRecordPorePlateList.add(updateRecordPorePlate);
                                        // porePlateService.saveUpdateRecordPorePlate(updateRecordPorePlate);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (sampleList.size() > 0) {
                            porePlateService.updateSampleListForManyParam(sampleList);
                            porePlateService.updateHoleTypeList(sampleList);
                        }
                        if (updateRecordPorePlateList.size() > 0) {
                            porePlateService.saveUpdateRecordPorePlateList(updateRecordPorePlateList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            /****************保存修改记录结束******************/
            pd.put("slotting_complete", now);
            pd.put("slotting_userid", user.getUSER_ID());
            // 保存打孔记录
            porePlateService.savePorePlateRecord(pd);
            porePlateService.svarlims_pore_user(pd);
        }
        // 完成分析
        if ("4".equals(current_procedure)) {
            // 完成分析 有修改  记录
            /****************保存修改记录开始******************/
            // 循环页面传递参数
            for (int i = 0; i < jsonArrayNew.size(); i++) {
                Object o = jsonArrayNew.get(i);
                JSONObject json1 = JSONObject.fromObject(o);
                String hole_number = json1.getString("hole_number");
                String hole_type = json1.getString("hole_type");
                String hole_special_sample = json1.getString("hole_special_sample");
                String hole_sample_remark = json1.getString("hole_sample_remark");
                String sample_number = json1.getString("poreNum");
                String htId = json1.getString("htId");
                // 循环数据库获取参数
                for (int j = 0; j < jsonArrayOld.size(); j++) {
                    Object newO = jsonArrayOld.get(j);
                    JSONObject jsonOld = JSONObject.fromObject(newO);
                    String jsonOld_hole_number = jsonOld.getString("hole_number");
                    //String old_sample_number= jsonOld.getString("sample_number");
                    String old_hole_type = jsonOld.getString("hole_type");
                    String old_hole_special_sample = jsonOld.getString("hole_special_sample");
                    //如果孔坐标比如(A01)相同  比较内容
                    if (hole_number.equals(jsonOld_hole_number)) {
                        //如果内容修改  如果是这些孔类型才需要判断  其他不需要判断内容修改
                        if (!"7".equals(hole_type) && !old_hole_special_sample.equals(hole_special_sample) && !"2".equals(hole_type) && !"3".equals(hole_type) && !"5".equals(hole_type)) {
                            //然后修改lims_hole_type表中的hole_sampleid和内容
                            PageData updateHoleType = new PageData();
                            updateHoleType.put("id", htId);
                            updateHoleType.put("hole_type", hole_type);
                            updateHoleType.put("hole_special_sample", hole_special_sample);
                            updateHoleType.put("hole_sample_remark", hole_sample_remark);
                            //如果是 4-重复分型、5-失败  则 样本进程修改为 2.重做
                            if ("4".equals(hole_special_sample) || "5".equals(hole_special_sample)) {
                                updateHoleType.put("hole_sample_course", 2);
                            }
                            //如果是 7-空卡 、8-其它问题  则 样本进程修改为3.问题样本
                            if ("7".equals(hole_special_sample) || "8".equals(hole_special_sample)) {
                                updateHoleType.put("hole_sample_course", 3);
                            }
                            //如果是0-正常,1-微变异 则 样本进程修改为1.成功
                            if ("0".equals(hole_special_sample) || "1".equals(hole_special_sample) ||
                                    "2".equals(hole_special_sample) || "3".equals(hole_special_sample)) {
                                updateHoleType.put("hole_sample_course", 1);
                            }
                            // 执行更新 holetype 改为批量
                            updateHoleTypeList.add(updateHoleType);
                            // porePlateService.updateHoleType(updateHoleType);
                            // TODO: 2019/3/27
                            // 根据holetype id 查找sampleid 去更新
                            PageData sampleId = porePlateService.getSampleid(updateHoleType);
                            // 执行更新 sample//改成批量
                            updateHoleType.put("hole_sampleid", sampleId.get("hole_sampleid"));
                            sampleList.add(updateHoleType);
                            // porePlateService.updateSample(updateHoleType);
                            // 7 8 问题样本分析时候不存更新记录
                            if (!"8".equals(hole_special_sample) && !"7".equals(hole_special_sample)) {
                                // 插入更新记录
                                PageData updateRecordPorePlate = new PageData();
                                updateRecordPorePlate.put("hole_number", hole_number);
                                updateRecordPorePlate.put("hole_poreid", pd.get("id"));
                                updateRecordPorePlate.put("oldhole_type", old_hole_type);
                                updateRecordPorePlate.put("newhole_type", hole_type);
                                updateRecordPorePlate.put("old_sampleid", jsonOld.getString("id"));
                                //新的sampleId
                                updateRecordPorePlate.put("new_sampleid", jsonOld.getString("id"));
                                updateRecordPorePlate.put("oldspecial_sample", old_hole_special_sample);
                                updateRecordPorePlate.put("newspecial_sample", hole_special_sample);
                                updateRecordPorePlate.put("update_type", current_procedure);
                                updateRecordPorePlate.put("update_people", user.getUSER_ID());
                                updateRecordPorePlate.put("update_time", now);
                                // 改为批量
                                updateRecordPorePlateList.add(updateRecordPorePlate);
                                // porePlateService.saveUpdateRecordPorePlate(updateRecordPorePlate);
                                break;
                            }

                        }
                        break;
                    }
                }
            }
            if (updateHoleTypeList.size() > 0) {
                porePlateService.updateHoleTypeList(updateHoleTypeList);
            }

            if (sampleList.size() > 0) {
                // 获取所有的需要更新的samplelist
                List<PageData> sampleidAndHoltTypeId = porePlateService.getSampleidAndHoltTypeId(updateHoleTypeList);
                // 循环准备参数
                for (PageData p : sampleidAndHoltTypeId) {
                    for (PageData newP : sampleList) {
                        if (p.get("id").equals(newP.getString("id"))) {
                            newP.put("hole_sampleid", p.getString("hole_sampleid"));
                        }
                    }
                }
                porePlateService.updateSampleListForManyParamForId(sampleList);
            }
            if (updateRecordPorePlateList.size() > 0) {
                porePlateService.saveUpdateRecordPorePlateList(updateRecordPorePlateList);
            }

            /****************保存修改记录结束******************/
            pd.put("slotting_complete", now);
            pd.put("slotting_userid", user.getUSER_ID());
            porePlateService.svarlims_pore_user(pd);
            porePlateService.saveAnalyzeRecord(pd);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     *
     *完成复核质检
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/completeOtherFuhe")
    @ResponseBody
    public Object completeOtherFuhe() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        porePlateService.completeOtherFuhe(pd);
        String pore_plate_quality = pd.getString("pore_plate_quality");
        if ("2".equals(pore_plate_quality)) {
            pd.put("pore_plate_entirety", "1");
            porePlateService.entiretyPore(pd);
            pd.put("hole_sample_course", 2);
            porePlateService.entiretyPoreType(pd);
            ArrayList sampleid = porePlateService.getSampleidList(pd);
            if (sampleid != null) {
                porePlateService.updateSampleCourseFor2(sampleid.get(0).toString().split(","));
            }
        }
        Map<Object, Object> map = new HashMap<>();
        return AppUtil.returnObject(pd, map);
    }


    /**
     *
     * 整版重扩方法修改孔板
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/entirety")
    @ResponseBody
    public Object entirety() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        porePlateService.entiretyPore(pd);
        if ("1".equals(pd.getString("pore_plate_entirety"))) {
            pd.put("hole_sample_course", 2);
            porePlateService.entiretyPoreType(pd);
            ArrayList sampleid = porePlateService.getSampleidList(pd);
            if (sampleid != null) {
                porePlateService.updateSampleCourseFor2(sampleid.get(0).toString().split(","));
            }
        }
        Map<Object, Object> map = new HashMap<>();
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 打开上传EXCEL页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showCourse")
    public ModelAndView showCourse() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pageData = this.getPageData();
        List<PageData> sampleCourse = porePlateService.findSampleCourse(pageData);
        mv.setViewName("/porePlate/showCourse");
        mv.addObject("sampleCourse", sampleCourse);
        return mv;
    }

    /**
     * 批量完成复核质检
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/completeAllFuHe")
    @ResponseBody
    public Object completeAllFuHe() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        String ids = pd.getString("ids");
        if (null != ids && !"".equals(ids)) {
            String ArrayUSER_IDS[] = ids.split(",");
            porePlateService.completeAllFuHe(ArrayUSER_IDS);
        }
        Map<Object, Object> map = new HashMap<>();
        return AppUtil.returnObject(pd, map);
    }


    /**
     * 查询项目缩写 和 第几个项目
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setPorePlateName")
    @ResponseBody
    public Object setPorePlateName() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData pageData = porePlateService.setPorePlateName(pd);
        Map<Object, Object> map = new HashMap<>();
        map.put("project_number_abbreviation", pageData.getString("project_number_abbreviation"));
        map.put("count", pageData.get("c"));
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 保存PRC扩增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveInstrumentRecord")
    public ModelAndView saveInstrumentRecord() throws Exception {
        // 校验权限
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }
        logBefore(logger, Jurisdiction.getUsername() + "PCR扩增");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        // 获取多个roleid
        String[] roleIds = pd.getString("instrument_info").split(",");
        for (int i = 0; i < roleIds.length; i++) {
            pd.put("instrument_id", roleIds[i]);
            pd.put("instrument_user", user.getUSER_ID());
            pd.put("instrument_time", pd.getString("operation_time"));
            porePlateService.saveInstrumentRecord(pd);
        }
        mv.addObject("msg", "success");
        //mv.setViewName("save_result");
        return mv;
    }

}
