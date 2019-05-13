package com.fh.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.entity.system.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.fh.util.PageData;
import com.fh.util.Tools;
/**
 * 导入到EXCEL
 * 类名称：ObjectExcelView.java
 * @author FH Q313596790
 * @version 1.0
 */
public class ExportExcel extends AbstractExcelView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        Date date = new Date();
        PageData pd = (PageData) model.get("title");
        // String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
        String filename = pd.getString("pore_plate_name");
        HSSFSheet sheet;
        HSSFCell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
        sheet = workbook.createSheet("sheet1");
        HSSFRow row1 = sheet.createRow(0);
        // 目的是想把行高设置成25px
        row1.setHeight((short) 270);

        HSSFRow row2 = sheet.createRow(1);
        // 目的是想把行高设置成25px
        row2.setHeight((short) 270);

        HSSFRow row3 = sheet.createRow(2);
        // 目的是想把行高设置成25px
        row3.setHeight((short) 270);

        HSSFRow row4 = sheet.createRow(3);
        // 目的是想把行高设置成25px
        row4.setHeight((short) 270);

        // 合并单元格 GXHC-DH-02
        sheet.addMergedRegion(new CellRangeAddress(0,3,0,7));
        // 合并单元格 复核质检□
        sheet.addMergedRegion(new CellRangeAddress(0,1,8,9));
        // 合并单元格 数据导出:
        sheet.addMergedRegion(new CellRangeAddress(0,1,10,11));
        // 合并单元格重复样本□
        sheet.addMergedRegion(new CellRangeAddress(2,3,8,9));
        // 合并单元格重复样本□
        sheet.addMergedRegion(new CellRangeAddress(2,3,10,11));


        // 内容样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 下边框
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 左边框
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 上边框
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 右边框
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

       /* List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();*/

        HSSFFont font = workbook.createFont();
        font.setFontName("Bookman Old Style");
        // 设置字体大小
        font.setFontHeightInPoints((short) 26);
        titleStyle.setFont(font);
        cell = getCell(sheet, 0, 0);
        cell.setCellStyle(titleStyle);
        titleStyle.setFont(font);
        setText(cell,pd.getString("pore_plate_name"));

        // 内容样式 四个框的样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        // 下边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 左边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 上边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 右边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);

        cell = getCell(sheet, 1, 8);
        cell.setCellStyle(style);
        cell = getCell(sheet, 1, 9);
        cell.setCellStyle(style);

        cell = getCell(sheet, 3, 8);
        cell.setCellStyle(style);
        cell = getCell(sheet, 3, 9);
        cell.setCellStyle(style);
        cell = getCell(sheet, 1, 11);
        cell.setCellStyle(style);

        cell = getCell(sheet, 0, 11);
        cell.setCellStyle(style);
        cell = getCell(sheet, 2, 11);
        cell.setCellStyle(style);
        cell = getCell(sheet, 3, 11);
        cell.setCellStyle(style);




        HSSFFont font1 = workbook.createFont();
        font1.setFontName("宋体");
        // 设置字体大小
        font1.setFontHeightInPoints((short) 11);
        style.setFont(font1);
        cell = getCell(sheet, 0, 8);
        cell.setCellStyle(style);
        if ("1".equals(pd.getString("pore_plate_quality"))) {
            setText(cell,"复核质检☑");
        }else{
            setText(cell,"复核质检□");
        }
        cell = getCell(sheet, 0, 10);
        cell.setCellStyle(style);
        //User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        setText(cell,"数据导出:");
        cell = getCell(sheet, 2, 8);
        cell.setCellStyle(style);
        setText(cell,"重复样本□");
        cell = getCell(sheet, 2, 10);
        cell.setCellStyle(style);
        setText(cell," 审核人:");
        // 内容样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        List<PageData> varList = (List<PageData>) model.get("varList");
        for (int i = 4; i < 22; i++) {
            HSSFRow row = sheet.createRow(i);
            // 目的是想把行高设置成25px
            row.setHeight((short) 462);
        }

       for (int i = 3; i < 12; i++) {
            PageData vpd = varList.get(i-3);
            if (i == 12) {
                sheet.addMergedRegion(new CellRangeAddress(i,i,0,i));
                continue;
            }

            for (int j = 0; j <= 11; j++) {
                // 内容样式 四个框的样式
                HSSFCellStyle porestyle = workbook.createCellStyle();

                //下边框
                porestyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                // 左边框
                porestyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                // 上边框
                porestyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                // 右边框
                porestyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

                porestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                porestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                HSSFFont porefont = workbook.createFont();
                porefont.setFontName("宋体");
                // 设置字体大小
                porefont.setFontHeightInPoints((short) 11);
                porestyle.setFont(porefont);
                String varstr = StringUtils.isNotEmpty(vpd.getString("var"+(j+1))) ? vpd.getString("var"+(j+1)) : "";
                if (j%2 == 0) {
                    sheet.setColumnWidth((short) j, (short) 930);
                }else{
                    sheet.setColumnWidth((short) j, (short) 5025);
                }
                if("LADDER".equals(varstr)){
                    /* HSSFPalette palette = workbook.getCustomPalette();
                  palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                    /*porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
                    HSSFFont redFont = workbook.createFont();
                    redFont.setColor(Font.COLOR_RED);
                    porestyle.setFont(redFont);*/
                    cell = getCell(sheet, i+1, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                if (varstr.lastIndexOf("(4)") >= 0) {
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                    varstr = varstr.substring(0,varstr.lastIndexOf("(4)"));
                   /* porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());*/
                    cell = getCell(sheet, i+1, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                } if (varstr.lastIndexOf("(7)") >= 0) {
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                    varstr = varstr.substring(0,varstr.lastIndexOf("(7)"));
                   /* porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.RED.getIndex());*/
                    cell = getCell(sheet, i+1, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                if ("O".equals(varstr)) {
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                   /* porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());*/
                    cell = getCell(sheet, i+1, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                if ("P".equals(varstr)) {
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                   /* porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());*/
                    cell = getCell(sheet, i+1, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                cell = getCell(sheet, i+1, j);
                cell.setCellStyle(porestyle);
                setText(cell,varstr);
            }

        }
        HSSFRow row = sheet.getRow(12);
        //sheet.removeRow(row);
        sheet.addMergedRegion(new CellRangeAddress(12,12,0,12));
        cell = getCell(sheet, 12, 0);
        setText(cell,"");
        for (int i = 8; i < varList.size(); i++) {
            PageData vpd = varList.get(i);
            for (int j = 0; j <= 11; j++) {
                // 内容样式 四个框的样式
                HSSFCellStyle porestyle = workbook.createCellStyle();
                //下边框
                porestyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                // 左边框
                porestyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                // 上边框
                porestyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                // 右边框
                porestyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

                porestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                porestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                HSSFFont porefont = workbook.createFont();
                porefont.setFontName("宋体");
                // 设置字体大小
                porefont.setFontHeightInPoints((short) 11);
                porestyle.setFont(porefont);
                String varstr = StringUtils.isNotEmpty(vpd.getString("var"+(j+1))) ? vpd.getString("var"+(j+1)) : "";
                if (j%2 == 0) {
                    sheet.setColumnWidth((short) j, (short) 930);
                }else{
                    sheet.setColumnWidth((short) j, (short) 5025);
                }
                if("LADDER".equals(varstr)){
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                   /* porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());*/
                    HSSFFont redFont = workbook.createFont();
                    redFont.setColor(Font.COLOR_RED);
                    porestyle.setFont(redFont);
                    cell = getCell(sheet, i+5, j-1);
                    cell.setCellStyle(porestyle);

                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                if(varstr.lastIndexOf("(4)")>=0){
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                    varstr = varstr.substring(0,varstr.lastIndexOf("(4)"));
                  /*  porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());*/
                    cell = getCell(sheet, i+5, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }if(varstr.lastIndexOf("(7)")>=0){
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                    varstr = varstr.substring(0,varstr.lastIndexOf("(7)"));
                   /* porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.RED.getIndex());*/
                    cell = getCell(sheet, i+5, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                if("O".equals(varstr)){
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                  /*  porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());*/
                    cell = getCell(sheet, i+5, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                if("P".equals(varstr)){
                   /* HSSFPalette palette = workbook.getCustomPalette();
                    palette.setColorAtIndex((short)9, (byte) 9, (byte)9, (byte) 9);*/
                  /*  porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    porestyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());*/
                    cell = getCell(sheet, i+5, j-1);
                    cell.setCellStyle(porestyle);
                    String varstr1 = StringUtils.isNotEmpty(vpd.getString("var"+(j))) ? vpd.getString("var"+(j)) : "";
                    setText(cell,varstr1);
                }
                cell = getCell(sheet, i+5, j);
                cell.setCellStyle(porestyle);
                setText(cell,varstr);
            }
        }
        // 内容样式 四个框的样式
        HSSFCellStyle styleEnd = workbook.createCellStyle();
        styleEnd.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleEnd.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleEnd.setFont(font1);
        sheet.addMergedRegion(new CellRangeAddress(21,21,0,1));
        cell = getCell(sheet, 21, 0);
        cell.setCellStyle(styleEnd);
        if (StringUtils.isNotEmpty(pd.getString("dakongren"))) {
            setText(cell,"打孔人:"+pd.getString("dakongren"));
        }else{
            setText(cell,"打孔人:");
        }

        sheet.addMergedRegion(new CellRangeAddress(21,21,2,3));
        cell = getCell(sheet, 21, 2);
        cell.setCellStyle(styleEnd);
        if (StringUtils.isNotEmpty(pd.getString("kuozhengren"))) {
            setText(cell,"扩增人:"+pd.getString("kuozhengren"));
        }else{
            setText(cell,"扩增人:");
        }

        sheet.addMergedRegion(new CellRangeAddress(21,21,4,5));
        cell = getCell(sheet, 21, 4);
        cell.setCellStyle(styleEnd);
        if (StringUtils.isNotEmpty(pd.getString("fenxiren"))) {
            setText(cell,"分析人:"+pd.getString("fenxiren"));
        }else{
            setText(cell,"分析人:");
        }
        sheet.addMergedRegion(new CellRangeAddress(21,21,6,7));
        cell = getCell(sheet, 21, 6);
        cell.setCellStyle(styleEnd);
        setText(cell,"CODIS导出:");
        sheet.addMergedRegion(new CellRangeAddress(21,21,8,11));
        cell = getCell(sheet, 21, 8);
        cell.setCellStyle(styleEnd);
        setText(cell,"备注：");
        // 内容样式 四个框的样式
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 下边框
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 左边框
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 上边框
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 右边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);


        cell = getCell(sheet, 21, 0);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 1);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 2);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 3);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 4);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 5);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 6);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 7);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 8);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 9);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 10);
        cell.setCellStyle(style1);
        cell = getCell(sheet, 21, 11);
        cell.setCellStyle(style1);
        //表单2
        sheet = workbook.createSheet("sheet2");
        JSONArray entirety = (JSONArray)model.get("entirety");
        if (entirety != null) {
            cell = getCell(sheet, 0, 0);
            setText(cell,"来源板子");
            cell = getCell(sheet, 0, 1);
            setText(cell,"原始坐标");
            cell = getCell(sheet, 0, 2);
            setText(cell,"原始孔编号");
            cell = getCell(sheet, 0, 3);
            setText(cell,"重做原因");
            for (int i =1; i<entirety.size()+1; i++) {
                JSONObject jsonObject =(JSONObject) entirety.get(i - 1);
                cell = getCell(sheet, i, 0);
                setText(cell,jsonObject.getString("pore_plate_name"));
                cell = getCell(sheet, i, 1);
                setText(cell,jsonObject.getString("hole_number"));
                cell = getCell(sheet, i, 2);
                setText(cell,jsonObject.getString("poreNum"));
                cell = getCell(sheet, i, 3);
                setText(cell,jsonObject.getString("hole_sample_remark"));
            }
            // 调整第一列宽度
            sheet.autoSizeColumn((short)0);
            // 调整第二列宽度
            sheet.autoSizeColumn((short)1);
            // 调整第三列宽度
            sheet.autoSizeColumn((short)2);
            sheet = workbook.createSheet("sheet3");
            JSONArray json = (JSONArray)model.get("json");
            cell = getCell(sheet, 0, 0);
            setText(cell,"孔坐标");
            cell = getCell(sheet, 0, 1);
            setText(cell,"样本编号");
            cell = getCell(sheet, 0, 2);
            setText(cell,"孔类型");
            int rowNum = 1;

            for(int i = 1; i < json.size()+1; i++) {
                // 内容样式 四个框的样式
                HSSFCellStyle porestyle = workbook.createCellStyle();
                JSONObject jsonObject =(JSONObject) json.get(i - 1);
                if (StringUtils.isNotEmpty(jsonObject.getString("poreNum"))) {
                    cell = getCell(sheet, rowNum, 0);
                    setText(cell,jsonObject.getString("hole_number"));
                    cell = getCell(sheet, rowNum, 1);
                    setText(cell,jsonObject.getString("poreNum"));
                    cell = getCell(sheet, rowNum, 2);
                    if ("1".equals(jsonObject.getString("hole_type"))) {
                        setText(cell,"普通样本");
                    }
                    if ("6".equals(jsonObject.getString("hole_type"))) {
                        setText(cell,"空孔");
                    } if ("2".equals(jsonObject.getString("hole_type"))) {
                        porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        porestyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                        cell.setCellStyle(porestyle);
                        setText(cell,"P");
                        cell = getCell(sheet, rowNum, 0);
                        cell.setCellStyle(porestyle);
                        cell = getCell(sheet, rowNum, 1);
                        cell.setCellStyle(porestyle);
                    } if ("3".equals(jsonObject.getString("hole_type"))) {
                        porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        porestyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                        cell.setCellStyle(porestyle);
                        setText(cell,"O");
                        cell = getCell(sheet, rowNum, 0);
                        cell.setCellStyle(porestyle);
                        cell = getCell(sheet, rowNum, 1);
                        cell.setCellStyle(porestyle);
                    } if ("7".equals(jsonObject.getString("hole_type"))) {
                        porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        porestyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        cell.setCellStyle(porestyle);
                        setText(cell,"QC-");
                        cell = getCell(sheet, rowNum, 0);
                        cell.setCellStyle(porestyle);
                        cell = getCell(sheet, rowNum, 1);
                        cell.setCellStyle(porestyle);
                    } if ("4".equals(jsonObject.getString("hole_type"))) {
                        porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        porestyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                        cell.setCellStyle(porestyle);
                        setText(cell,"QC");
                        cell = getCell(sheet, rowNum, 0);
                        cell.setCellStyle(porestyle);
                        cell = getCell(sheet, rowNum, 1);
                        cell.setCellStyle(porestyle);
                    } if ("5".equals(jsonObject.getString("hole_type"))) {
                        porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        porestyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
                        cell.setCellStyle(porestyle);
                        HSSFFont redFont = workbook.createFont();
                        redFont.setColor(Font.COLOR_RED);
                        porestyle.setFont(redFont);
                        setText(cell,"LADDER");
                        cell = getCell(sheet, rowNum, 0);
                        cell.setCellStyle(porestyle);
                        cell = getCell(sheet, rowNum, 1);
                        cell.setCellStyle(porestyle);
                    }
                    rowNum++;
                }
            }
        }else{
            JSONArray json = (JSONArray)model.get("json");
            cell = getCell(sheet, 0, 0);
            setText(cell,"孔坐标");
            cell = getCell(sheet, 0, 1);
            setText(cell,"样本编号");
            cell = getCell(sheet, 0, 2);
            setText(cell,"孔类型");
            int rowNum1 = 1;
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

            for (String poleNumber:splitNumbers) {
                for (int i = 1; i<json.size()+1; i++) {
                    JSONObject jsonObject =(JSONObject) json.get(i - 1);
                    if (poleNumber.equals(jsonObject.getString("hole_number"))) {
                        // 内容样式 四个框的样式
                        HSSFCellStyle porestyle = workbook.createCellStyle();
                            cell = getCell(sheet, rowNum1, 0);
                            setText(cell, jsonObject.getString("hole_number"));
                            cell = getCell(sheet, rowNum1, 1);
                            if (StringUtils.isNotEmpty(jsonObject.getString("poreNum"))) {
                                setText(cell, jsonObject.getString("poreNum"));
                                cell = getCell(sheet, rowNum1, 2);
                                if ("1".equals(jsonObject.getString("hole_type"))) {
                                    setText(cell,"普通样本");
                                }
                                if ("6".equals(jsonObject.getString("hole_type"))) {
                                    setText(cell,"空孔");
                                }
                                if ("2".equals(jsonObject.getString("hole_type"))) {
                                    porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                    porestyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                                    cell.setCellStyle(porestyle);
                                    setText(cell,"P");
                                    cell.setCellStyle(porestyle);
                                    cell = getCell(sheet, rowNum1, 0);
                                    cell.setCellStyle(porestyle);
                                    cell = getCell(sheet, rowNum1, 1);
                                    cell.setCellStyle(porestyle);
                                } if ("3".equals(jsonObject.getString("hole_type"))) {
                                    porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                    porestyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                                    cell.setCellStyle(porestyle);
                                    setText(cell,"O");
                                    cell = getCell(sheet, rowNum1, 0);
                                    cell.setCellStyle(porestyle);
                                    cell = getCell(sheet, rowNum1, 1);
                                    cell.setCellStyle(porestyle);
                                } if ("7".equals(jsonObject.getString("hole_type"))) {
                                    porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                    porestyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                                    cell.setCellStyle(porestyle);
                                    setText(cell,"QC-");
                                    cell = getCell(sheet, rowNum1, 0);
                                    cell.setCellStyle(porestyle);
                                    cell = getCell(sheet, rowNum1, 1);
                                    cell.setCellStyle(porestyle);
                                } if ("4".equals(jsonObject.getString("hole_type"))) {
                                    porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                    porestyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                                    cell.setCellStyle(porestyle);
                                    setText(cell,"QC");
                                    cell = getCell(sheet, rowNum1, 0);
                                    cell.setCellStyle(porestyle);
                                    cell = getCell(sheet, rowNum1, 1);
                                    cell.setCellStyle(porestyle);
                                } if ("5".equals(jsonObject.getString("hole_type"))) {
                                    porestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                    porestyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
                                    cell.setCellStyle(porestyle);
                                    HSSFFont redFont = workbook.createFont();
                                    redFont.setColor(Font.COLOR_RED);
                                    porestyle.setFont(redFont);
                                    setText(cell,"LADDER");
                                    cell = getCell(sheet, rowNum1, 0);
                                    cell.setCellStyle(porestyle);
                                    cell = getCell(sheet, rowNum1, 1);
                                    cell.setCellStyle(porestyle);
                                }
                            }else{
                                 setText(cell, "");
                            }
                        rowNum1++;
                            break;
                    }
                }



            }
            // 调整第一列宽度
            sheet.autoSizeColumn((short)0);
            // 调整第二列宽度
            sheet.autoSizeColumn((short)1);
            // 调整第三列宽度
            sheet.autoSizeColumn((short)2);
        }

    }

}
