package com.fh.util;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 导出excel
 * @author wuan
 * @version 1.0
 */
public class JxlsExcelView extends AbstractView {
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    private String exportFileName;

    /**
     * @param exportFileName 导出文件名
     */
    public JxlsExcelView(String exportFileName) {
            if (exportFileName != null) {
            try {
                exportFileName = URLEncoder.encode(exportFileName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        this.exportFileName = exportFileName;
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Context context = new Context(model);
        response.setContentType(getContentType());
        response.setHeader("content-disposition",
                "attachment;filename=" + exportFileName + ".xls");
        ServletOutputStream os = response.getOutputStream();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("/template/template.xls");
        JxlsHelper.getInstance().processTemplate(is, os, context);
        // 备用
//        Transformer transformer = TransformerFactory.createTransformer(is, os);
//        AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
//        List<Area> xlsAreaList = areaBuilder.build();
//        Area xlsArea = xlsAreaList.get(0);
//        xlsArea.applyAt(new CellRef("汇总!A1"), context);
//        Area xlsArea1 = xlsAreaList.get(1);
//        xlsArea1.applyAt(new CellRef("第二轮!A1"), context);
//        Area xlsArea2 = xlsAreaList.get(2);
//        xlsArea2.applyAt(new CellRef("第三轮!A1"), context);
//        Area xlsArea3 = xlsAreaList.get(3);
//        xlsArea3.applyAt(new CellRef("第四轮!A1"), context);
//        Area xlsArea4 = xlsAreaList.get(4);
//        xlsArea4.applyAt(new CellRef("第五轮!A1"), context);
//        Area xlsArea5 = xlsAreaList.get(5);
//        xlsArea5.applyAt(new CellRef("问题!A1"), context);
//        transformer.write();
        is.close();
    }
}