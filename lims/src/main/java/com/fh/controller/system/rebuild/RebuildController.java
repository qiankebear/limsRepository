package com.fh.controller.system.rebuild;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.porePlate.PorePlateService;
import com.fh.service.rebuild.RebuildService;
import com.fh.service.system.instrument.impl.InstrumentService;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:布板模块
 * @Date: $time$ $date$
 **/
@Controller
@RequestMapping(value="/rebuild")
public class RebuildController extends BaseController {
    String menuUrl = "main/instrument/toinstrument"; //菜单地址(权限用)
    // 重扩接口
    @Resource(name = "rebuildService")
    private RebuildService rebuildService;

    @Resource(name="porePlateService")
    private PorePlateService porePlateService;
    @Resource(name="instrumentService")
    private InstrumentService instrumentService;
    // 用户
    @Resource(name = "userService")
    private UserManager userService;

    /**
    *@Desc 重扩模块数据列表查询展示
    *@Author Wangjian
    *@Date 2018/11/5 17:47
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
        pd.put("userId",user.getUSER_ID());
        List<String> projectId = rebuildService.findProjectId(pd); // 查询项目id

        User userAndRoleById = userService.getUserAndRoleById(id);
        String role_name = userAndRoleById.getRole().getRNUMBER();
        if(!"R20171231726481".equals(role_name)&&!"R20180131375361".equals(role_name)){
            String s = projectId.get(0);
            String[] a = s.split(",");
            List b = null;
            b = java.util.Arrays.asList(a);
//            for (int i = 0; i < a.length; i++) {
//                pd.put("projectId"+i,a[i]);
//            }
            pd.put("projectId",b);
            page.setPd(pd);
            logBefore(logger, "跳转页面");
            List<PageData> allMessage = rebuildService.findAllMessage(page);// 查询所有列表数据
            List<PageData> projectName = rebuildService.findProjectName(pd);// 查询所有项目名称
            List<PageData> reform = new ArrayList<>();
            for (int i = 0; i < allMessage.size(); i++) {
                Object projectID = allMessage.get(i).get("projectID");
                PageData pageData = new PageData();
                pageData.put("projectid",projectID);
                pageData.put("userid",userId);
                List<PageData> PuList = rebuildService.findPuByPUId(pageData);
                if (!PuList.isEmpty()){
                    for (int i1 = 0; i1 < PuList.size(); i1++) {
                        String aaa = PuList.get(i1).get("member_kind").toString();
                        if (!aaa.equals("3")){
                            reform.add(allMessage.get(i));
                            break;
                        }
                    }
                }
            }
            mv.addObject("QX",Jurisdiction.getHC()); // 权限
            mv.addObject("list",reform);
            mv.addObject("pd",pd);
            mv.addObject("projectNameList",projectName);
            mv.setViewName("system/rebuild/rebuildList");
            return mv;
        }else {
            page.setPd(pd);
            logBefore(logger, "跳转页面");
            List<PageData> allMessage = rebuildService.findAllMessage(page); //查询所有数据列表
            List<PageData> projectName = rebuildService.findProjectName(pd);// 查询所有项目名称
            mv.addObject("QX",Jurisdiction.getHC()); // 权限
            mv.addObject("list",allMessage);
            mv.addObject("projectNameList",projectName);
            mv.addObject("pd",pd);
            mv.setViewName("system/rebuild/rebuildList");
            return mv;
        }
    }
    /**
    *@Desc 重扩页面
    *@Author Wangjian
    *@Date 2018/11/14 10:43
    *@Params  * @param null
    */
    @RequestMapping(value="/goAddU")
    public ModelAndView rebuild()throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("system/rebuild/rebuildEdit");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
        return mv;
    }
    /**
    *@Desc 跳转智能布板页面
    *@Author Wangjian
    *@Date 2018/11/28 10:52
    *@Params  * @param null
    */
    @RequestMapping(value="/goToLayoutEdit")
    public ModelAndView goToLayoutEdit()throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改PorePlate");
        PageData pd = this.getPageData();
        String ids = pd.getString("ids");
        pd.put("plate_project_id",pd.getString("projectID"));
        pd.put("pore_plate_quality",3);
        pd.put("current_procedure",0);
        PageData pageData = new PageData();
        pageData.put("ids",ids);
        List<PageData> entiretyList = rebuildService.findEntirety(ids.split(","));// 查询孔类型，孔坐标
        String serial = pd.getString("serial");
        int i1 = Integer.parseInt(serial) + 1;
       /* int i1 = Integer.parseInt(serial);
        int j1 = 0;
        if (1<=i1 && i1<5){
             j1 = Integer.parseInt(serial)+1; // 重扩轮数加1
            pageData.put("serial",j1); // 修改重扩轮数
        }else {
            pageData.put("serial",serial); // 修改重扩轮数
        }*/
      /*  String[] split = ids.split(",");*/
        /*for (String s : split){
            pageData.put("id",s);
            rebuildService.updateSerial(pageData); // 修改样本表轮数
        }*/
        User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        pd.put("userId",user.getUSER_ID());
        PageData projectPermission = porePlateService.getProjectPermission(pd); // 项目权限查询
        pd.put("pore_plate_name",pd.getString("REBUILDNAME"));//孔板名称
        pd.put("plate_project_id",pd.getString("projectID"));//项目编号
        pd.put("fabric_swatch_people",user.getUSER_ID());//布板者
        pd.put("sample_sum",pd.getString("mun"));//重扩总数
        pd.put("pore_plate_type",1);//孔板类型
        pd.put("pore_plate_quality",3);//复核质检
        pd.put("pore_plate_entirety",3);//是否整版重扩
        pd.put("current_procedure",0);//步骤
        pd.put("lims_pore_serial",i1);//孔板轮数
        pd.put("quality_from_ids",pd.getString("ids"));//重扩来源板子id
        pd.remove("id");

        int i3 = porePlateService.savePorePlate(pd);//保存方法并做返回值
        String number = i3 + "";
        ModelAndView mv = this.getModelAndView();
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        mv.setViewName("porePlate/porePlate");
        mv.addObject("msg", "editPorePlate");

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = format.format(date);
        JSONArray jsonArray = JSONArray.fromObject(entiretyList);
        int j =1;
        for(int i=0;i<jsonArray.size();i++) {
            //开辟新线程保存数据
            Object o = jsonArray.get(i);
            JSONObject json1 = JSONObject.fromObject(o);
            String hole_number= json1.getString("hole_number");
            String numbers = "B01，C01，D01，E01，F01，G01，H01，" +
                    "A02，B02，C02，D02，E02，F02，G02，H02，" +
                    "A03，B03，C03，D03，E03，F03，G03，H03，" +
                    "A04，B04，C04，D04，E04，F04，G04，H04，" +
                    "A05，B05，C05，D05，E05，F05，G05，H05，" +
                    "A06，B06，C06，D06，E06，F06，G06，" +
                    "A07，B07，C07，D07，E07，F07，G07，H07，" +
                    "A08，B08，C08，D08，E08，F08，G08，H08，" +
                    "A09，B09，C09，D09，E09，F09，G09，H09，" +
                    "A10，B10，C10，D10，E10，F10，G10，H10，" +
                    "A11，B11，C11，D11，E11，F11，G11，H11，" +
                    "A12，B12，C12，D12，G12，H12";
            String[] splitNumbers = numbers.split("，");
            hole_number = splitNumbers[i];
            String poreNum= json1.getString("poreNum");
            String hole_type= json1.getString("hole_type");
            String sample_serial= json1.getString("sample_serial");
            int i2 = 0;
            if (StringUtils.isNotEmpty(number)){
                 i2 = Integer.parseInt(sample_serial) + 1;
            }
            //保存样本表
            PageData pdSample = new PageData();
           /* if("5".equals(hole_type)){
                pdSample.put("sample_number","LADDER");
            }else if("2".equals(hole_type)){
                pdSample.put("sample_number","P");
            }else if("3".equals(hole_type)){
                pdSample.put("sample_number","O");
            }else{
                pdSample.put("sample_number",poreNum);
            }
            pdSample.put("sample_generate_time",now);
            pdSample.put("sample_course",1);
            pdSample.put("sample_serial",i2);// 轮数
            pdSample.put("sample_project_id",pd.get("plate_project_id"));*/
            /*porePlateService.saveSample(pdSample);*/
            pdSample.put("hole_sampleid",json1.get("id"));
            pdSample.put("sample_serial",i2);
            porePlateService.updateSample(pdSample);
            //保存孔类型表
            PageData pageData1 = new PageData();
            pageData1.put("hole_number",hole_number);
            pageData1.put("hole_type",hole_type);
            pageData1.put("hole_special_sample",9);
            pageData1.put("hole_sample_remark","");
            pageData1.put("hole_sample_serial",1);
            pageData1.put("hole_poreid",pd.get("id"));
            pageData1.put("hole_sample_course",1);
            pageData1.put("hole_sampleid",json1.get("id"));
            porePlateService.savePorePlateDetailed(pageData1);
        }

        List<PageData> poreList = porePlateService.findPorePlateList(pd);
       // mv.addObject("mun",pd.getString("mun"));
        mv.addObject("REBUILDNAME",pd.getString("REBUILDNAME"));
        mv.addObject("pd",pd);
        mv.addObject("projectPermission", projectPermission);
        mv.addObject("poreList",JSONArray.fromObject(poreList));
        return mv;
    }
}
