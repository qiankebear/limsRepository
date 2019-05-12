package com.fh.util;

import org.springframework.context.ApplicationContext;
/**
 * 项目名称：
 * @author:fh qq313596790[青苔]
 * 修改日期：2015/11/2
*/
public class Const {
	/**
	 *	//验证码
	 */
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	/**
	 *	//session用的用户
	 */
	public static final String SESSION_USER = "sessionUser";
	/**
	 *
	 */
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	/**
	 *
	 */
	public static final String sSESSION_ROLE_RIGHTS = "sessionRoleRights";
	/**
	 *		//当前菜单
	 */
	public static final String SESSION_menuList = "menuList";
	/**
	 *		//全部菜单
	 */
	public static final String SESSION_allmenuList = "allmenuList";
	/**
	 *						//主职角色权限
	 */
	public static final String SESSION_QX = "QX";
	/**
	 *					//副职角色权限
	 */
	public static final String SESSION_QX2 = "QX2";
	/**
	 *
	 */
	public static final String SESSION_userpds = "userpds";
	/**
	 *				//用户对象
	 */
	public static final String SESSION_USERROL = "USERROL";
	/**
	 *			//用户名
	 */
	public static final String SESSION_USERNAME = "USERNAME";
	/**
	 *				//用户姓名
	 */
	public static final String SESSION_U_NAME = "U_NAME";
	/**
	 *			//角色编码数组
	 */
	public static final String SESSION_RNUMBERS = "RNUMBERS";
	/**
	 *				//角色编码
	 */
	public static final String SESSION_RNUMBER = "RNUMBER";
	/**
	 * 		//当前用户拥有的最高部门权限集合
	 */
	public static final String DEPARTMENT_IDS = "DEPARTMENT_IDS";
	/**
	 * 		//当前用户拥有的最高部门权限
	 */
	public static final String DEPARTMENT_ID = "DEPARTMENT_ID";
	/**
	 *
	 */
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	/**
	 * 	用户皮肤
	 */
	public static final String SKIN = "SKIN";
	/**
	 * 	登录地址
	 */
	public static final String LOGIN = "/login_toLogin.do";
	/**
	 * 系统名称路径
	 */
	public static final String SYSNAME = "admin/config/SYSNAME.txt";
	/**
	 * 	分页条数配置路径
	 */
	public static final String PAGE	= "admin/config/PAGE.txt";
	/**
	 * 	邮箱服务器配置路径
	 */
	public static final String EMAIL = "admin/config/EMAIL.txt";
	/**
	 * 短信账户配置路径1
	 */
	public static final String SMS1 = "admin/config/SMS1.txt";
	/**
	 * 	短信账户配置路径2
	 */
	public static final String SMS2 = "admin/config/SMS2.txt";
	/**
	 * 	文字水印配置路径
	 */
	public static final String FWATERM = "admin/config/FWATERM.txt";
	/**
	 * 图片水印配置路径
	 */
	public static final String IWATERM = "admin/config/IWATERM.txt";
	/**
	 * 微信配置路径
	 */
	public static final String WEIXIN	= "admin/config/WEIXIN.txt";
	/**
	 * WEBSOCKET配置路径
	 */
	public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";
	/**
	 * 登录页面配置
	 */
	public static final String LOGINEDIT = "admin/config/LOGIN.txt";
	/**
	 * 	图片上传路径
	 */
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";
	/**
	 * 文件上传路径
	 */
	public static final String FILEPATHFILE = "uploadFiles/file/";
	/**
	 * 	文件上传路径(oa管理)
	 */
	public static final String FILEPATHFILEOA = "uploadFiles/uploadFile/";
	/**
	 * 工作流生成XML和PNG目录
	 */
	public static final String FILEACTIVITI = "uploadFiles/activitiFile/";
	/**
	 * 二维码存放路径
	 */
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/";
	/**
	 * 	不对匹配该值的访问路径拦截（正则）
	 */
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)|(uploadImgs)).*";
	/**
	 * 该值会在web容器启动时由WebAppContextListener初始化
	 *
	 */
	public static ApplicationContext WEB_APP_CONTEXT = null;
	/**
	 * 任务组
	 */

	public static String JOB_GROUP_NAME = "DB_JOBGROUP_NAME";
	/**
	 * 	触发器组
	 */
	public static String TRIGGER_GROUP_NAME = "DB_TRIGGERGROUP_NAME";
	/**
	 * 系统用户注册接口_请求协议参数)
	 * APP Constants
	 */

	public static final String[] SYSUSER_REGISTERED_PARAM_ARRAY = new String[]{"USERNAME","PASSWORD","NAME","EMAIL","rcode"};
	public static final String[] SYSUSER_REGISTERED_VALUE_ARRAY = new String[]{"用户名","密码","姓名","邮箱","验证码"};
	/**
	 * app根据用户名获取会员信息接口_请求协议中的参数
	 */
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
	
}
