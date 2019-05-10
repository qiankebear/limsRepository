package com.fh.controller.system.template;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.service.rebuild.RebuildService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.text.MessageFormat;
import java.util.*;


/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
@Controller
@RequestMapping(value="/template")
public class TemplateController extends BaseController {
    @Resource(name = "rebuildService")
    private RebuildService rebuildService;
    /**
     *@Desc 查询全部孔板名称
     *@Author Wangjian
     *@Date 2018/11/14 10:44
     *@Params  * @param null
     */
    @RequestMapping(value="/home")
    public ModelAndView findPlateName()throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        // 查询所有项目名称
        List<PageData> projectName = rebuildService.findProjectName(pd);


        // 权限
        mv.addObject("QX",Jurisdiction.getHC());
        mv.addObject("projectNameList",projectName);
        mv.setViewName("system/template/template_List");
        return mv;
    }
    /**
    *@Desc 获取孔板名称
    *@Author Wangjian
    *@Date 2018/12/26 11:34
    *@Params  * @param null
    */
    @RequestMapping(value="/getPalateName")
    @ResponseBody
    public Object getPalateName() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map = new HashMap<String,Object>();
        // 查询全部孔板
        List<PageData> plateName = rebuildService.findPlateName(pd);
        map.put("list",plateName);
        return AppUtil.returnObject(pd, map);
    }
    /**
    *@Desc 导出模板
    *@Author Wangjian
    *@Date 2018/11/15 10:13
    *@Params  * @param null
    */
    @RequestMapping(value="/export")
    public void findNumber(HttpServletResponse response)throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String template = pd.getString("template");
        List<PageData> plateName = rebuildService.findNumber(pd);
        /*List<PageData> list = new ArrayList<>();
        list.add(pd);*/
        StringBuffer text = new StringBuffer();
        if ("3130".equals(template)){
            text.append("Container Name");
            // 空格
            text.append("\t");
            text.append("Description");
            // 空格
            text.append("\t");
            text.append("ContainerType");
            // 空格
            text.append("\t");
            text.append("AppType");
            // 空格
            text.append("\t");
            text.append("Owner");
            // 回车
            text.append("\t");
            text.append("Operator");
            // 换行字符
            text.append("\r\n");
            text.append(pd.getString("plateName"));
            // 空格
            text.append("\t");
            // 空格
            text.append("\t");
            text.append("96-well");
            // 空格
            text.append("\t");
            text.append("Reguler");
            // 空格
            text.append("\t");
            text.append("MR");
            // 空格
            text.append("\t");
            text.append("MR");
            // 换行字符
            text.append("\r\n");
            text.append("AppServer");
            // 空格
            text.append("\t");
            text.append("AppInstance");
            // 换行字符
            text.append("\r\n");
            text.append("GeneMapper");
            // 空格
            text.append("\t");
            text.append("GeneMapper_Generic_Instance");
            // 换行字符
            text.append("\r\n");
            text.append("Well");
            // 空格
            text.append("\t");
            text.append("Sample Name");
            // 空格
            text.append("\t");
            text.append("Comment");
            // 空格
            text.append("\t");
            text.append("Priority");
            // 空格
            text.append("\t");
            text.append("Size Standard");
            // 空格
            text.append("\t");
            text.append("Snp Set");
            // 空格
            text.append("\t");
            text.append("User-Defined 3");
            // 空格
            text.append("\t");
            text.append("User-Defined 2");
            // 空格
            text.append("\t");
            text.append("User-Defined 1");
            // 空格
            text.append("\t");
            text.append("Panel");
            // 空格
            text.append("\t");
            text.append("Study");
            // 空格
            text.append("\t");
            text.append("Sample Type");
            // 空格
            text.append("\t");
            text.append("Analysis Method");
            // 空格
            text.append("\t");
            text.append("Results Group 1");
            // 空格
            text.append("\t");
            text.append("Instrument Protocol");
            // 换行字符
            text.append("\r\n");
            String numbers = "A01，B01，C01，D01，E01，F01，G01，H01，" +
                    "A02，B02，C02，D02，E02，F02，G02，H02，" +
                    "A03，B03，C03，D03，E03，F03，G03，H03，" +
                    "A04，B04，C04，D04，E04，F04，G04，H04，" +
                    "A05，B05，C05，D05，E05，F05，G05，H05，" +
                    "A06，B06，C06，D06，E06，F06，G06，H06，" +
                    "A07，B07，C07，D07，E07，F07，G07，H07，" +
                    "A08，B08，C08，D08，E08，F08，G08，H08，" +
                    "A09，B09，C09，D09，E09，F09，G09，H09，" +
                    "A10，B10，C10，D10，E10，F10，G10，H10，" +
                    "A11，B11，C11，D11，E11，F11，G11，H11，" +
                    "A12，B12，C12，D12，E12，F12，G12，H12";
            String[] splitNumbers = numbers.split("，");
            for (String split:splitNumbers){
                text.append(split);
                // 空格
                text.append("\t");
                PageData pageData = new PageData();
                for (PageData log:plateName) {
                    if (split.equals(log.getString("hole_number"))) {
                        pageData = log;
                        pageData.put("sample_number",log.getString("sample_number"));
                        break;
                    }
                }
                if (pageData.getString("sample_number") == null || pageData.getString("sample_number") == "") {
                    text.append("K");
                } else {
                    text.append(pageData.getString("sample_number"));
                }

                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 写死
                    text.append("100");
                    // 空格
                    text.append("\t");
                     // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    // 空格
                    text.append("\t");
                    text.append(pd.getString("results_group1"));
                    // 空格
                    text.append("\t");
                    text.append(pd.getString("instrument_protocol"));
                    // 换行字符
                    text.append("\r\n");
            }
            String template1 = pd.getString("template");
            String plateName1 = pd.getString("plateName");
            exportTxt(response,text.toString(),template1,plateName1);
        }
        if ("3500".equals(template)){
            text.append("3500 Plate Layout File Version 1.0");
            // 换行字符
            text.append("\r\n");
            // 换行字符
            text.append("\r\n");
            text.append("Plate Name");
            // 空格
            text.append("\t");
            text.append("Application Type");
            // 空格
            text.append("\t");
            text.append("Capillary Length (cm)");
            // 空格
            text.append("\t");
            text.append("Polymer");
            // 空格
            text.append("\t");
            text.append("Number of Wells");
            // 空格
            text.append("\t");
            text.append("Owner Name");
            // 空格
            text.append("\t");
            text.append("Barcode Number");
            // 空格
            text.append("\t");
            text.append("Comments");
            // 换行字符
            text.append("\r\n");
            text.append(pd.getString("plateName"));
            // 空格
            text.append("\t");
            text.append(pd.getString("HID"));
            // 空格
            text.append("\t");
            text.append(pd.getString("Capillary"));
            // 空格
            text.append("\t");
            text.append(pd.getString("Polymer"));
            // 空格
            text.append("\t");
            text.append(96);
            // 换行字符
            text.append("\r\n");
            // 换行字符
            text.append("\r\n");
            text.append("Well");
            // 空格
            text.append("\t");
            text.append("Sample Name");
            // 空格
            text.append("\t");
            text.append("Assay");
            // 空格
            text.append("\t");
            text.append("File Name Convention");
            // 空格
            text.append("\t");
            text.append("Results Group");
            // 空格
            text.append("\t");
            text.append("Sample Type");
            // 空格
            text.append("\t");
            text.append("User Defined Field 1");
            // 空格
            text.append("\t");
            text.append("User Defined Field 2");
            // 换行字符
            text.append("\t");
            text.append("User Defined Field 3");
            // 空格
            text.append("\t");
            text.append("User Defined Field 4");
            // 空格
            text.append("\t");
            text.append("User Defined Field 5");
            // 空格
            text.append("\t");
            text.append("Comments");
            // 换行字符
            text.append("\r\n");
            String numbers ="A01，B01，C01，D01，E01，F01，G01，H01，" +
                    "A02，B02，C02，D02，E02，F02，G02，H02，" +
                    "A03，B03，C03，D03，E03，F03，G03，H03，" +
                    "A04，B04，C04，D04，E04，F04，G04，H04，" +
                    "A05，B05，C05，D05，E05，F05，G05，H05，" +
                    "A06，B06，C06，D06，E06，F06，G06，H06，" +
                    "A07，B07，C07，D07，E07，F07，G07，H07，" +
                    "A08，B08，C08，D08，E08，F08，G08，H08，" +
                    "A09，B09，C09，D09，E09，F09，G09，H09，" +
                    "A10，B10，C10，D10，E10，F10，G10，H10，" +
                    "A11，B11，C11，D11，E11，F11，G11，H11，" +
                    "A12，B12，C12，D12，E12，F12，G12，H12";
            String[] splitNumbers = numbers.split("，");
            for (String split:splitNumbers){
                text.append(split);
                // 空格
                text.append("\t");
                PageData pageData = new PageData();
                for (PageData log:plateName) {
                    if (split.equals(log.getString("hole_number"))) {
                        pageData = log;
                        pageData.put("sample_number",log.getString("sample_number"));
                        break;
                    }
                }
                if (pageData.getString("sample_number") == null || pageData.getString("sample_number") == "") {
                    text.append("K");
                } else {
                    text.append(pageData.getString("sample_number"));
                }
                // 空格
                text.append("\t");
                // assay
                text.append(pd.getString("assay"));
                // 空格
                text.append("\t");
                // file_name
                text.append(pd.getString("file_name"));
                // 空格
                text.append("\t");
                // Results_Group
                text.append(pd.getString("Results_Group"));
                // 空格
                text.append("\t");
                // Sample
                text.append("Sample");
                // 换行字符
                text.append("\r\n");
            }
            String template1 = pd.getString("template");
           /* text.append("\t");//空格*/
            String plateName1 = pd.getString("plateName");
            exportTxt(response,text.toString(),template1,plateName1);
        }
        if ("3730".equals(template)){
            text.append("Container Name");
            // 空格
            text.append("\t");
            text.append("Plate ID");
            // 空格
            text.append("\t");
            text.append("Description");
            // 空格
            text.append("\t");
            text.append("ContainerType");
            // 空格
            text.append("\t");
            text.append("AppType");
            // 空格
            text.append("\t");
            text.append("Owner");
            // 空格
            text.append("\t");
            text.append("Operator");
            // 空格
            text.append("\t");
            text.append("PlateSealing");
            // 空格
            text.append("\t");
            text.append("SchedulingPref");
            // 换行字符
            text.append("\r\n");
            text.append(pd.getString("plateName"));
            // 空格
            text.append("\t");
            text.append(pd.getString("plateName"));
            // 空格
            text.append("\t");
            // 空格
            text.append("\t");
            text.append("96-Well");
            // 空格
            text.append("\t");
            text.append("Regular");
            // 空格
            text.append("\t");
            text.append("1");
            // 空格
            text.append("\t");
            text.append("1");
            // 空格
            text.append("\t");
            text.append("Septa");
            // 空格
            text.append("\t");
            text.append("1234");
            // 换行字符
            text.append("\r\n");
            text.append("AppServer");
            // 空格
            text.append("\t");
            text.append("AppInstance");
            // 换行字符
            text.append("\r\n");
            text.append("GeneMapper");
            // 空格
            text.append("\t");
            text.append("GeneMapper_Generic_Instance");
            // 换行字符
            text.append("\r\n");
            text.append("Well");
            // 空格
            text.append("\t");
            text.append("Sample Name");
            // 空格
            text.append("\t");
            text.append("Comment");
            // 空格
            text.append("\t");
            text.append("Size Standard");
            // 空格
            text.append("\t");
            text.append("Snp Set");
            // 空格
            text.append("\t");
            text.append("User-Defined 3");
            // 空格
            text.append("\t");
            text.append("User-Defined 2");
            // 空格
            text.append("\t");
            text.append("User-Defined 1");
            // 空格
            text.append("\t");
            text.append("Panel");
            // 空格
            text.append("\t");
            text.append("Study");
            // 空格
            text.append("\t");
            text.append("Sample Type");
            // 空格
            text.append("\t");
            text.append("Analysis Method");
            // 空格
            text.append("\t");
            text.append("Results Group 1");
            // 空格
            text.append("\t");
            text.append("Instrument Protocol 1");
            // 换行字符
            text.append("\r\n");
            String numbers = "A01，B01，C01，D01，E01，F01，G01，H01，" +
                    "A02，B02，C02，D02，E02，F02，G02，H02，" +
                    "A03，B03，C03，D03，E03，F03，G03，H03，" +
                    "A04，B04，C04，D04，E04，F04，G04，H04，" +
                    "A05，B05，C05，D05，E05，F05，G05，H05，" +
                    "A06，B06，C06，D06，E06，F06，G06，H06，" +
                    "A07，B07，C07，D07，E07，F07，G07，H07，" +
                    "A08，B08，C08，D08，E08，F08，G08，H08，" +
                    "A09，B09，C09，D09，E09，F09，G09，H09，" +
                    "A10，B10，C10，D10，E10，F10，G10，H10，" +
                    "A11，B11，C11，D11，E11，F11，G11，H11，" +
                    "A12，B12，C12，D12，E12，F12，G12，H12";
            String[] splitNumbers = numbers.split("，");
            for (String split:splitNumbers){
                text.append(split);
                // 空格
                text.append("\t");
                PageData pageData = new PageData();
                for (PageData log:plateName) {
                    if (split.equals(log.getString("hole_number"))) {
                        pageData = log;
                        pageData.put("sample_number",log.getString("sample_number"));
                        break;
                    }
                }
                if (pageData.getString("sample_number") == null || pageData.getString("sample_number") == "") {
                    text.append("K");
                } else {
                    text.append(pageData.getString("sample_number"));
                }
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                // 空格
                text.append("\t");
                text.append(pd.getString("results_group1"));
                // 空格
                text.append("\t");
                text.append(pd.getString("instrument_protocol"));
                // 换行字符
                text.append("\r\n");
            }
            String template1 = pd.getString("template");
            String plateName1 = pd.getString("plateName");
            exportTxt(response,text.toString(),template1,plateName1);
        }
    }
    /**
    *@Desc 防止中文文件名显示出错
    *@Author Wangjian
    *@Date 2018/11/15 15:59
    *@Params  * @param null
    */
    public  String genAttachmentFileName(String cnName, String defaultName) {
        try {
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }
    /**
    *@Desc 导出TXT文件
    *@Author Wangjian
    *@Date 2018/11/15 15:59
    *@Params  * @param null
    */
    public void exportTxt(HttpServletResponse response, String text,String name,String Template){
        response.setCharacterEncoding("utf-8");
        // 设置响应的内容类型
        response.setContentType("text/plain");
        // 设置文件的名称和格式,设置名称格式，没有这个中文名称无法显示
        response.addHeader("Content-Disposition","attachment;filename="
                + genAttachmentFileName(Template+ "-"+name, "JSON_FOR_UCC_")
                + ".txt");
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(text.getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            // LOGGER.error("导出文件文件出错:{}",e);
        } finally {
                try {
                buff.close();
                outStr.close();
            } catch (Exception e) {
                // LOGGER.error("关闭流对象出错 e:{}",e);
            }
        }
    }
}
