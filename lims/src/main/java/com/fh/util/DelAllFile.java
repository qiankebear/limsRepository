package com.fh.util;

import java.io.File;

/**
 * java删除所有文件和文件夹
 * 创建人：FH Q313596790
 * 创建时间：2015年1月12日
 * @version
 */
public class DelAllFile {
	
	public static void main(String args[]) {
		// 只删除e下面a及a下面所有文件和文件夹,e不会被删掉
		delFolder("e:/e/a");
		//delFolder("D:/WEBSerser/apache-tomcat-8.0.15/me-webapps/UIMYSQL/WEB-INF/classes/../../admin00/ftl/code");	
		//delFolder("D:\\WEBSerser\\apache-tomcat-8.0.15\\me-webapps\\UIMYSQL\\admin00\\ftl\\code");
		//delFolder("D:/WEBSerser/apache-tomcat-8.0.15/me-webapps/UIMYSQL/WEB-INF/classes/../../admin00/ftl/code");
		System.out.println("deleted");
	}

	/**
	 * @param folderPath 文件路径 (只删除此路径的最末路径下所有文件和文件夹)
	 */
	public static void delFolder(String folderPath) {
		try {
			// 删除完里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				// 先删除文件夹里面的文件
				delAllFile(path + "/" + tempList[i]);
				// 再删除空文件夹
				delFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}
}
