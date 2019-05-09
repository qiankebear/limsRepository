package com.fh.controller.statistical.confirmation;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.project.kitsmanager.impl.KitsManagerService;
import com.fh.service.project.projectmanager.ProjectManagerManager;
import com.fh.service.project.samplemanager.impl.SampleManagerService;
import com.fh.service.statistical.StatisticalService;
import com.fh.service.statistical.statisticalinfo.StatisticalInfoService;
import com.fh.service.system.instrument.impl.InstrumentService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RedisCacheManager;
import com.fh.util.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 确认函
 * @author wuan
 * @version 1.0
 */
@Controller
@Component
@RequestMapping(value="/confirmation")
public class ConfirmationController extends BaseController {

    Logger logger = Logger.getLogger(ConfirmationController.class);

    /**
     * 菜单地址(权限用)
     */
    String menuUrl = "confirmation/list.do";
    @Resource(name="projectmanagerService")
    private ProjectManagerManager projectmanagerService;
    @Resource(name="kitsmanagerService")
    private KitsManagerService kitsManagerService;
    @Resource(name = "samplemanagerService")
    private SampleManagerService sampleManagerService;
    @Resource(name = "statisticalService")
    private StatisticalService statisticalService;
    @Resource(name = "instrumentService")
    private InstrumentService instrumentService;
    @Resource(name = "statisticalInfoService")
    private StatisticalInfoService statisticalInfoService;
    private static final String SEP1 = ",";
    private static final String SEP2 = "|";
    private static final String SEP3 = "=";
    /**列表
     * @param
     * @throws Exception
     */
    @RequestMapping(value="/exp")
    public ModelAndView exp(HttpServletRequest request,
                             HttpServletResponse response) throws Exception{
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        String checked = pd.get("checked").toString();
        List<?> checkedList =  StringToList(checked);
        // 通过id获取该项目信息
        PageData pd1 = projectmanagerService.findById(pd);
        // 通过项目id获取所有试剂名称
        List<PageData> kitNameList = kitsManagerService.findNameById(pd);
        // 通过项目id查找该项目的各种状态的样本个数
        PageData sampleCountList = sampleManagerService.listSampleCount(pd);
        // 接收样本总数
        String allSample = sampleCountList.get("allsample").toString();
        // 检出样本总数
        String normalSample = sampleCountList.get("normalsample").toString();

        // 检出样本数
        String checkoutSample = sampleCountList.get("checkoutsample").toString();
        // 复检样本数
        String reformSample = sampleCountList.get("reformsample").toString();
        // 复检出样本
        String reformNormalSample = sampleCountList.get("reformnormalsample").toString();
        // 空样本数
        String emptySample = sampleCountList.get("emptysample").toString();
        // 未检出及问题样本
        String noCheckAndIssueSample = sampleCountList.get("nocheckandissuesample").toString();
        // 首轮检测样本
        int firstCheckSample= Integer.valueOf(allSample) - Integer.valueOf(noCheckAndIssueSample);

        // 通过项目id找到所有孔板下面的扩增仪器
        List<PageData> pcrList = statisticalService.findPcrByProjectId(pd);
        String pcrName = null;
        String pcrName1 = " ";
        for (int i1 = 0; i1 < pcrList.size(); i1++) {
            if (pcrList.get(i1)==null){
                if (pcrName==null) {
                    pcrName = "";
                }
                break;
            }
            pcrName1 += pcrList.get(i1).get("instrument_info").toString();
        }
        List<Object> pcrNameList = StringToList(pcrName1);
        String pcrRealName="";
        for (int i1 = 0; i1 < pcrNameList.size(); i1++) {
            PageData pd6 = new PageData();
            pd6.put("ID",pcrNameList.get(i1));
            PageData pcrName2 =instrumentService.findById(pd6);
            if (pcrName2 != null) {
                pcrRealName += pcrName2.get("instrument_type").toString()+",";
            }else{
                pcrRealName += " ";
            }
        }
        List<Object> insNameList = StringToList(pcrRealName);
        List<Object> insList = removeDuplicate(insNameList);
        String insName = listToString(insList);


        // 通过项目id找到所有孔板下面的检测仪器
        List<PageData> checkedList1 = statisticalService.findCheckedByProjectId(pd);
        String checkedName = null;
        String checkedName1 = " ";
        for (int i1 = 0; i1 < checkedList1.size(); i1++) {
            if (checkedList1.get(i1)==null){
                if (checkedName==null) {
                    checkedName = "";
                }
                break;
            }
            checkedName1 += checkedList1.get(i1).get("instrument_info").toString();
        }
        List<Object> checkedNameList = StringToList(checkedName1);
        String checkedRealName="";
        for (int i1 = 0; i1 < checkedNameList.size(); i1++) {
            PageData pd6 = new PageData();
            pd6.put("ID",checkedNameList.get(i1));
            PageData checkedName2 =instrumentService.findById(pd6);
            if (checkedName2 != null) {
                checkedRealName += checkedName2.get("instrument_type").toString()+",";
            }else{
                checkedRealName = " ";
            }
        }
        List<Object> cinsNameList = StringToList(checkedRealName);
        List<Object> cinsList = removeDuplicate(cinsNameList);
        String cinsName = listToString(cinsList);
        List<Object> kitNameList1 = new ArrayList<>();
        for (int i = 0; i < kitNameList.size(); i++) {
            if(kitNameList.get(i) != null && !kitNameList.isEmpty()){
                String kitName = kitNameList.get(i).get("kit_name").toString();
                kitNameList1.add(kitName);
            }

        }
        // 试剂盒名称
        String kitName = listToString(kitNameList1);
        // 项目名称
        String projectName = pd1.get("project_name").toString();
        // 项目编号
        String number = pd1.get("project_number").toString();
        // 客户名称
        String clientName = pd1.get("client_name").toString();
        // 项目起止时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDate = simpleDateFormat.format((Date) pd1.get("project_starttime"));
        String newDate1 = simpleDateFormat.format((Date) pd1.get("project_endtime"));
        String projectTime = newDate+"至"+newDate1;
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("xmmc",projectName);
        dataMap.put("jsybsj","");
        dataMap.put("khmc",clientName);
        dataMap.put("xmqzsj",projectTime);
        dataMap.put("sjhmc",kitName);
        dataMap.put("kzyq",insName);
        dataMap.put("jcyq",cinsName);
        dataMap.put("jsybzs",allSample);
        dataMap.put("jcybzs",normalSample);
        dataMap.put("sljcyb",firstCheckSample);
        dataMap.put("jcybs",checkoutSample);
        dataMap.put("fjybs",reformSample);
        dataMap.put("fjcyb",reformNormalSample);
        dataMap.put("sjrkyb","");
        dataMap.put("kybs",emptySample);
        if (checkedList != null && checkedList.contains("1")) {
            dataMap.put("yssj", "☑");
        }else{
            dataMap.put("yssj", "☒");
        }
        if (checkedList!=null && checkedList.contains("2")) {
            dataMap.put("project", "☑");
        }else{
            dataMap.put("project", "☒");
        }
        if (checkedList!=null &&checkedList.contains("3")) {
            dataMap.put("CODIS", "☑");
        }else{
            dataMap.put("CODIS", "☒");
        }
        if (checkedList!=null &&checkedList.contains("4")) {
            dataMap.put("zzbb", "☑");
        }else {
            dataMap.put("zzbb", "☒");
        }
        if (checkedList!=null &&checkedList.contains("5")) {
            dataMap.put("hzb", "☑");
        }else{
            dataMap.put("hzb", "☒");
        }
        if (checkedList != null &&checkedList.contains("6")) {
            dataMap.put("fhk", "☑");
        }else{
            dataMap.put("fhk", "☒");
        }
        dataMap.put("wjcjwtyb",noCheckAndIssueSample);
        WordUtils.exportMillCertificateWord(request,response,dataMap,number,projectName);

        mv.setViewName("statistical/confirmation/confirmation_list");
        // 按钮权限
        mv.addObject("QX",Jurisdiction.getHC());
        return mv;
    }
    /**去新增页面
     * @param
     * @throws Exception
     */

    @RequestMapping(value="/goExp")
    public ModelAndView goExp(Page page)throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> endProject = projectmanagerService.listEndProject(page);
        mv.setViewName("statistical/confirmation/confirmation_exp");
        mv.addObject("pd", pd);
        mv.addObject("endProject",endProject);
        return mv;
    }

    /**
     * 列表
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page) throws Exception{
        ModelAndView mv = new ModelAndView();PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        // 关键词检索条件
        String keywords = pd.getString("keywords");
        if(null != keywords && !"".equals(keywords)){
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);

        List projectAll = projectmanagerService.findprojectall(pd);
        List<PageData> list = statisticalInfoService.datalistPage(page);
        mv.setViewName("statistical/confirmation/confirmation_list");
        // 按钮权限
        mv.addObject("QX",Jurisdiction.getHC());
        mv.addObject("projectAll",projectAll);
        mv.addObject("list",list);
        return mv;
    }

    /**
     * 手动刷新
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/flush")
    public ModelAndView flush(Page page)throws Exception {
        ModelAndView mv = new ModelAndView();
        insertStatisticalInfo();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        // 关键词检索条件
        String keywords = pd.getString("keywords");
        if(null != keywords && !"".equals(keywords)){
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);
        List projectAll = projectmanagerService.findprojectall(pd);
        List<PageData> list = statisticalInfoService.datalistPage(page);

        mv.setViewName("statistical/confirmation/confirmation_list");
        // 按钮权限
        mv.addObject("QX",Jurisdiction.getHC());
        mv.addObject("projectAll",projectAll);
        mv.addObject("list",list);
        return mv;
    }

    /**
     * 定时器(每两小时更新一次)
     * @throws Exception
     */
    @Scheduled(cron="0 0 0/2 * * ? ")
    private void insertStatisticalInfo () throws Exception{
        logger.info("=======================执行开始==============================");
        Page page = new Page();
        PageData pd = new PageData();
        List<PageData> endProject = projectmanagerService.AllProjectlistPage(pd);
        for (int i = 0; i < endProject.size(); i++) {
            // 通过项目id获取所有试剂名称
            List<PageData> kitNameList = kitsManagerService.findNameById(endProject.get(i));
            if (kitNameList.size()==0 || kitNameList.get(0)==null){
                endProject.get(i).put("kitName"," ");
            }else {
                List<Object> kitNameList1 = new ArrayList<>();
                for (int i1 = 0; i1 < kitNameList.size(); i1++) {
                    String kitName = kitNameList.get(i1).get("kit_name").toString();
                    kitNameList1.add(kitName);
                }
                // 试剂盒名称
                String kitName = listToString(kitNameList1);
                endProject.get(i).put("kitName", kitName);
            }
            // 通过项目id找到所有孔板下面的扩增仪器
            List<PageData> pcrList = statisticalService.findPcrByProjectId(endProject.get(i));
            String pcrName = null;
            String pcrName1 = " ";
            for (int i1 = 0; i1 < pcrList.size(); i1++) {
                if (pcrList.get(i1)==null){
                    if (pcrName==null) {
                        pcrName = "";
                    }
                    break;
                }
                pcrName1 += pcrList.get(i1).get("instrument_info").toString();
            }
            List<Object> pcrNameList = StringToList(pcrName1);
            String pcrRealName="";
            for (int i1 = 0; i1 < pcrNameList.size(); i1++) {
                PageData pd6 = new PageData();
                pd6.put("ID",pcrNameList.get(i1));
                PageData pcrName2 =instrumentService.findById(pd6);
                if (pcrName2 != null) {
                    pcrRealName += pcrName2.get("instrument_type").toString()+",";
                }else{
                    pcrRealName += " ";
                }
            }
            List<Object> insNameList = StringToList(pcrRealName);
            List<Object> insList = removeDuplicate(insNameList);
            String insName = listToString(insList);
            // pcr扩增仪器
            endProject.get(i).put("kzyq",insName);

            // 通过项目id找到所有孔板下面的检测仪器
            List<PageData> checkedList = statisticalService.findCheckedByProjectId(endProject.get(i));
            String checkedName = null;
            String checkedName1 = " ";
            for (int i1 = 0; i1 < checkedList.size(); i1++) {
                if (checkedList.get(i1)==null){
                    if (checkedName==null) {
                        checkedName = "";
                    }
                    break;
                }
                checkedName1 += checkedList.get(i1).get("instrument_info").toString();
            }
            List<Object> checkedNameList = StringToList(checkedName1);
            String checkedRealName="";
            for (int i1 = 0; i1 < checkedNameList.size(); i1++) {
                PageData pd6 = new PageData();
                pd6.put("ID",checkedNameList.get(i1));
                PageData checkedName2 =instrumentService.findById(pd6);
                if (checkedName2 != null) {
                    checkedRealName += checkedName2.get("instrument_type").toString()+",";
                }else{
                    checkedRealName = " ";
                }
            }
            List<Object> cinsNameList = StringToList(checkedRealName);
            List<Object> cinsList = removeDuplicate(cinsNameList);
            String cinsName = listToString(cinsList);
            // 检测仪器
            endProject.get(i).put("jcyq",cinsName);

            // 通过项目id查找该项目的各种状态的样本个数
            PageData sampleCountList = sampleManagerService.listSampleCount(endProject.get(i));
            // 接收样本总数
            String allSample = sampleCountList.get("allsample").toString();
            endProject.get(i).put("allsample",allSample);
            // 检出样本总数
            String normalSample = sampleCountList.get("normalsample").toString();
            endProject.get(i).put("normalsample",normalSample);
            //TODO
            // 检出样本数
            String checkoutSample = sampleCountList.get("checkoutsample").toString();
            endProject.get(i).put("checkoutsample",checkoutSample);
            // 复检样本数
            String reformSample = sampleCountList.get("reformsample").toString();
            endProject.get(i).put("reformsample",reformSample);
            // 复检出样本
            String reformNormalSample = sampleCountList.get("reformnormalsample").toString();
            endProject.get(i).put("reformnormalsample",reformNormalSample);
            // 空样本数
            String emptySample = sampleCountList.get("emptysample").toString();
            endProject.get(i).put("emptysample",emptySample);
            // 未检出及问题样本
            String noCheckAndIssueSample = sampleCountList.get("nocheckandissuesample").toString();
            endProject.get(i).put("nocheckandissuesample",noCheckAndIssueSample);
            // 项目起止时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newDate = simpleDateFormat.format((Date)endProject.get(i).get("project_starttime"));
            String newDate1 = "--";
            if ( endProject.get(i).get("project_endtime")==null) {
                System.out.printf("1");
            }else{
                newDate1 = simpleDateFormat.format((Date) endProject.get(i).get("project_endtime"));
            }
            String projectTime = newDate+"至"+newDate1;
            endProject.get(i).put("projecttime",projectTime);
            // 首轮检测样本
            int firstCheckSample= Integer.valueOf(allSample) - Integer.valueOf(noCheckAndIssueSample);
            endProject.get(i).put("firstchecksample",firstCheckSample);
        }
        statisticalInfoService.delete(pd);
        statisticalInfoService.batchSave(endProject);
        logger.info("=====================执行结束==========================");
    }





    /**
     * for all jdk version
     * @param mList
     * @return
     */
    public static String listToString(List<Object> mList) {
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else{
            return "";
        }
    }
    /**
     * String转换List
     *
     * @param listText
     *            :需要转换的文本
     * @return List<?>
     */
    public static List<Object> StringToList(String listText) {
        if (listText == null || listText.equals("")) {
            return null;
        }
//        listText = listText.substring(1);

        List<Object> list = new ArrayList<Object>();
        String[] text = listText.split(",");
        String listStr = "";
        boolean flag = false;
        for (String str : text) {
            if (!str.equals("")) {
                if (str.charAt(0) == 'M') {
                    Map<?, ?> map = StringToMap(str);
                    list.add(map);
                } else if (str.charAt(0) == 'L' || flag) {
                    flag = true;
                    listStr += str + SEP1;
                } else {
                    list.add(str);
                }
            }
            if (str.equals("")) {
                flag = false;
                List<?> lists = StringToList(listStr);
                list.add(lists);
            }
        }
        return list;
    }
    /**
     * String转换Map
     *
     * @param mapText
     *            :需要转换的字符串
     * @return Map<?,?>
     */
    public static Map<String, Object> StringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1);

        Map<String, Object> map = new HashMap<String, Object>();
        // 转换为数组
        String[] text = mapText.split("\\" + SEP2);
        for (String str : text) {
            // 转换key与value的数组
            String[] keyText = str.split(SEP3);
            if (keyText.length < 1) {
                continue;
            }
            // key
            String key = keyText[0];
            // value
            String value = keyText[1];
            if (value.charAt(0) == 'M') {
                Map<?, ?> map1 = StringToMap(value);
                map.put(key, map1);
            } else if (value.charAt(0) == 'L') {
                List<?> list = StringToList(value);
                map.put(key, list);
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 去重
     * @param list
     * @return
     */
    public   static   List<Object>  removeDuplicate(List<Object> list)  {
        for  (int  i = 0; i < list.size() -  1 ; i ++)  {
            for  (int j = list.size() - 1; j > i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }


}
