package com.fh.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker 模版引擎类
 * 创建人：FH Q313596790
 * 创建时间：2015年2月8日
 * @version
 */
public class Freemarker {

	/**
	 * 打印到控制台(测试用)
	 *  @param ftlName
	 */
	public static void print(String ftlName, Map<String,Object> root, String ftlPath) throws Exception{
		try {
			// 通过Template可以将模板文件输出到相应的流
			Template temp = getTemplate(ftlName, ftlPath);
			temp.process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出到输出到文件
	 * @param ftlName   ftl文件名
	 * @param root		传入的map
	 * @param outFile	输出后的文件全部路径
	 * @param filePath	输出前的文件上部路径
	 */
	public static void printFile(String ftlName, Map<String,Object> root, String outFile, String filePath, String ftlPath)
			throws Exception{
		Writer out =null;
		try {
			File file = new File(PathUtil.getClasspath() + filePath + outFile);
			// 判断有没有父路径，就是判断文件整个路径是否存在
			if (!file.getParentFile().exists()) {
				// 不存在就全部创建
				file.getParentFile().mkdirs();
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			Template template = getTemplate(ftlName, ftlPath);
			// 模版输出
			template.process(root, out);
			out.flush();
			//out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (out != null) {
				try {
					out.close();
				}catch (IOException e){
					e.printStackTrace();
				}


			}
		}
	}
	
	/**
	 * 通过文件名加载模版
	 * @param ftlName
	 */
	public static Template getTemplate(String ftlName, String ftlPath) throws Exception{
		try {
			// 通过Freemaker的Configuration读取相应的ftl
			Configuration cfg = new Configuration();
			cfg.setEncoding(Locale.CHINA, "utf-8");
			// 设定去哪里读取相应的ftl模板文件
			cfg.setDirectoryForTemplateLoading(new File(PathUtil.getClassResources()+"/ftl/"+ftlPath));
			// 在模板文件目录中找到名称为name的文件
			Template temp = cfg.getTemplate(ftlName);
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
