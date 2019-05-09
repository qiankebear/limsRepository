package com.fh.controller.system.head;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Role;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.fhsms.FhsmsManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.userphoto.UserPhotoManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SmsUtil;
import com.fh.util.Tools;
import com.fh.util.Watermark;
import com.fh.util.mail.SimpleMailSender;

/** 
 * 类名称：HeadController
 * 创建人：FH 313596790Q
 * 修改时间：2018年2月4日
 * @version
 */
@Controller
@RequestMapping(value="/head")
public class HeadController extends BaseController {
	
	@Resource(name="userService")
	private UserManager userService;	
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="fhsmsService")
	private FhsmsManager fhsmsService;
	@Resource(name="userphotoService")
	private UserPhotoManager userphotoService;
	@Resource(name="roleService")
	private RoleManager roleService;
	
	/**去编辑头像页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editPhoto")
	public ModelAndView editPhoto() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/userphoto/userphoto_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**获取基本信息
	 * @return
	 */
	@RequestMapping(value="/getList")
	@ResponseBody
	public Object getList() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			Session session = Jurisdiction.getSession();
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			if(null == pds){
				// 当前登录者用户名
				pd.put(Const.SESSION_USERNAME, Jurisdiction.getUsername());
				pds = userService.findByUsername(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}
			pdList.add(pds);
			map.put("list", pdList);
			PageData pdPhoto = userphotoService.findById(pds);
			map.put("userPhoto", null == pdPhoto?"static/ace/avatars/user.jpg":pdPhoto.getString("PHOTO2"));//用户头像
			map.put("fhsmsCount", fhsmsService.findFhsmsCount(Jurisdiction.getUsername()).get("fhsmsCount").toString());//站内信未读总数
			String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//读取WEBSOCKET配置
			if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
				String strIW[] = strWEBSOCKET.split(",fh,");
				if(strIW.length == 7){
					// 即时聊天服务器IP和端口
					map.put("wimadress", strIW[0]+":"+strIW[1]);
					// 在线管理和站内信服务器IP和端口
					map.put("oladress", strIW[2]+":"+strIW[3]);
					// 站内信提示音效配置
					map.put("FHsmsSound", strIW[4]);
				}
			}
			Object RNUMBERS = session.getAttribute(Const.SESSION_RNUMBERS);
			Object RNUMBER = session.getAttribute(Const.SESSION_RNUMBER);
			if(null == RNUMBERS){
				// 把当前用户的角色编码数组放入session
				session.setAttribute(Const.SESSION_RNUMBERS, getRnumbers());
			}
			if(null == RNUMBER){
				// 把当前用户的角色编码放入session
				session.setAttribute(Const.SESSION_RNUMBER, getRnumber());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**判断当前用户角色编码符合过滤条件(用于新任务消息通知)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/isNowRole")
	@ResponseBody
	public Object isNowRole() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		if(Jurisdiction.getRnumbers().indexOf(pd.getString("RNUMBER"))!=-1){
			map.put("msg", "yes");
		}else{
			map.put("msg", "no");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**通过角色ID数组获取角色列表拼接角色编码
	 * @return
	 * @throws Exception
	 */
	public String getRnumbers() throws Exception{
		PageData userpd = new PageData();
		userpd.put(Const.SESSION_USERNAME, Jurisdiction.getUsername());
		// 通过用户名获取用户信息
		userpd = userService.findByUsername(userpd);
		String ZROLE_ID = userpd.get("ROLE_ID").toString()+",fh,"+userpd.getString("ROLE_IDS");
		String arryROLE_ID[] = ZROLE_ID.split(",fh,");
		List<Role> rlist = roleService.getRoleByArryROLE_ID(arryROLE_ID);
		StringBuffer RNUMBERS = new StringBuffer();
		RNUMBERS.append("(");
		for(Role role:rlist){
			RNUMBERS.append("'"+role.getRNUMBER()+"'");
		}
		RNUMBERS.append(")");
		return RNUMBERS.toString();
	}

	/**
	*@author xiongyanbiao
	*@$description:
	*@date 下午 14:01 2019/1/8 0008
	*@param
	*@return java.lang.String
	**/
	public String getRnumber() throws Exception{
		PageData userpd = new PageData();
		userpd.put(Const.SESSION_USERNAME, Jurisdiction.getUsername());
		// 通过用户名获取用户信息
		userpd = userService.findByUsername(userpd);
		String ZROLE_ID = userpd.get("ROLE_ID").toString();
		PageData pd3 = new PageData();
		pd3.put("ROLE_ID", ZROLE_ID);
		pd3 = roleService.findObjectById(pd3);
		StringBuffer RNUMBER = new StringBuffer();
		RNUMBER.append("(");
		RNUMBER.append("'"+pd3.getString("RNUMBER")+"'");
		RNUMBER.append(")");
		return RNUMBER.toString();
	}
	
	/**保存用户皮肤
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveSkin")
	@ResponseBody
	public Object saveSkin() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		// 当前登录者用户名
		pd.put(Const.SESSION_USERNAME, Jurisdiction.getUsername());
		userService.saveSkin(pd);
		Session session = Jurisdiction.getSession();
		session.setAttribute(Const.SKIN, pd.getString("SKIN"));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**获取站内信未读总数
	 * @return
	 */
	@RequestMapping(value="/getFhsmsCount")
	@ResponseBody
	public Object getFhsmsCount() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			// 站内信未读总数
			map.put("fhsmsCount", fhsmsService.findFhsmsCount(Jurisdiction.getUsername()).get("fhsmsCount").toString());
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**去发送邮箱页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editEmail")
	public ModelAndView editEmail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/head/edit_email");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去发送短信页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goSendSms")
	public ModelAndView goSendSms() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/head/send_sms");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**发送短信
	 * @return
	 */
	@RequestMapping(value="/sendSms")
	@ResponseBody
	public Object sendSms(){
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		// 发送状态
		String msg = "ok";
		// 统计发送成功条数
		int count = 0;
		// 理论条数
		int zcount = 0;
		List<PageData> pdList = new ArrayList<PageData>();
		// 对方邮箱
		String PHONEs = pd.getString("PHONE");
		// 内容
		String CONTENT = pd.getString("CONTENT");
		// 是否发送给全体成员 yes or no
		String isAll = pd.getString("isAll");
		// 类型 1：短信接口1   2：短信接口2
		String TYPE = pd.getString("TYPE");
		// 判断是系统用户还是会员 "appuser"为会员用户
		String fmsg = pd.getString("fmsg");
		if("yes".endsWith(isAll)){
			try {
				List<PageData> userList = new ArrayList<PageData>();
				userList = "appuser".equals(fmsg) ? appuserService.listAllUser(pd):userService.listAllUser(pd);
				zcount = userList.size();
				try {
					for(int i=0;i<userList.size();i++){
						// 手机号格式不对就跳过
						if(Tools.checkMobileNumber(userList.get(i).getString("PHONE"))){
							if("1".equals(TYPE)){
								// 调用发短信函数1
								SmsUtil.sendSms1(userList.get(i).getString("PHONE"), CONTENT);
							}else{
								// 调用发短信函数2
								SmsUtil.sendSms2(userList.get(i).getString("PHONE"), CONTENT);
							}
							count++;
						}else{
							continue;
						}
					}
					msg = "ok";
				} catch (Exception e) {
					msg = "error";
				}
			} catch (Exception e) {
				msg = "error";
			}
		}else{
			PHONEs = PHONEs.replaceAll("；", ";");
			PHONEs = PHONEs.replaceAll(" ", "");
			String[] arrTITLE = PHONEs.split(";");
			zcount = arrTITLE.length;
			try {
				for(int i=0;i<arrTITLE.length;i++){
					// 手机号式不对就跳过
					if(Tools.checkMobileNumber(arrTITLE[i])){
						if("1".equals(TYPE)){
							// 调用发短信函数1
							SmsUtil.sendSms1(arrTITLE[i], CONTENT);
						}else{
							// 调用发短信函数2
							SmsUtil.sendSms2(arrTITLE[i], CONTENT);
						}
						count++;
					}else{
						continue;
					}
				}
				msg = "ok";
			} catch (Exception e) {
				msg = "error";
			} 
		}	
		pd.put("msg", msg);
		// 成功数
		pd.put("count", count);
		// 失败数
		pd.put("ecount", zcount-count);
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**去发送电子邮件页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goSendEmail")
	public ModelAndView goSendEmail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/head/send_email");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**发送电子邮件
	 * @return
	 */
	@RequestMapping(value="/sendEmail")
	@ResponseBody
	public Object sendEmail(){
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		// 发送状态
		String msg = "ok";
		// 统计发送成功条数
		int count = 0;
		// 理论条数
		int zcount = 0;
		// 读取邮件配置
		String strEMAIL = Tools.readTxtFile(Const.EMAIL);
		List<PageData> pdList = new ArrayList<PageData>();
		// 对方邮箱
		String toEMAIL = pd.getString("EMAIL");
		// 标题
		String TITLE = pd.getString("TITLE");
		// 内容
		String CONTENT = pd.getString("CONTENT");
		// 类型
		String TYPE = pd.getString("TYPE");
		// 是否发送给全体成员 yes or no
		String isAll = pd.getString("isAll");
		// 判断是系统用户还是会员 "appuser"为会员用户
		String fmsg = pd.getString("fmsg");
		if(null != strEMAIL && !"".equals(strEMAIL)){
			String strEM[] = strEMAIL.split(",fh,");
			if(strEM.length == 4){
				if("yes".endsWith(isAll)){
					try {
						List<PageData> userList = new ArrayList<PageData>();
						userList = "appuser".equals(fmsg) ? appuserService.listAllUser(pd):userService.listAllUser(pd);
						zcount = userList.size();
						try {
							for(int i=0;i<userList.size();i++){
								if(Tools.checkEmail(userList.get(i).getString("EMAIL"))){		//邮箱格式不对就跳过
									SimpleMailSender.sendEmail(strEM[0], strEM[1], strEM[2], strEM[3], userList.get(i).getString("EMAIL"), TITLE, CONTENT, TYPE);//调用发送邮件函数
									count++;
								}else{
									continue;
								}
							}
							msg = "ok";
						} catch (Exception e) {
							msg = "error";
						}
					} catch (Exception e) {
						msg = "error";
					}
				}else{
					toEMAIL = toEMAIL.replaceAll("；", ";");
					toEMAIL = toEMAIL.replaceAll(" ", "");
					String[] arrTITLE = toEMAIL.split(";");
					zcount = arrTITLE.length;
					try {
						for(int i=0;i<arrTITLE.length;i++){
							if(Tools.checkEmail(arrTITLE[i])){		//邮箱格式不对就跳过
								SimpleMailSender.sendEmail(strEM[0], strEM[1], strEM[2], strEM[3], arrTITLE[i], TITLE, CONTENT, TYPE);//调用发送邮件函数
								count++;
							}else{
								continue;
							}
						}
						msg = "ok";
					} catch (Exception e) {
						msg = "error";
					} 
				}	
			}else{
				msg = "error";
			}
		}else{
			msg = "error";
		}
		pd.put("msg", msg);
		// 成功数
		pd.put("count", count);
		// 失败数
		pd.put("ecount", zcount-count);
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**去系统设置页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goSystem")
	public ModelAndView goEditEmail() throws Exception{
		if(!"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 读取系统名称
		pd.put("YSYNAME", Tools.readTxtFile(Const.SYSNAME));
		// 读取每页条数
		pd.put("COUNTPAGE", Tools.readTxtFile(Const.PAGE));
		// 读取邮件配置
		String strEMAIL = Tools.readTxtFile(Const.EMAIL);
		// 读取短信1配置
		String strSMS1 = Tools.readTxtFile(Const.SMS1);
		// 读取短信2配置
		String strSMS2 = Tools.readTxtFile(Const.SMS2);
		// 读取文字水印配置
		String strFWATERM = Tools.readTxtFile(Const.FWATERM);
		// 读取图片水印配置
		String strIWATERM = Tools.readTxtFile(Const.IWATERM);
		// 读取微信配置
		pd.put("Token", Tools.readTxtFile(Const.WEIXIN));
		// 读取WEBSOCKET配置
		String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);
		// 读取登录页面配置
		String strLOGINEDIT = Tools.readTxtFile(Const.LOGINEDIT);
		if(null != strEMAIL && !"".equals(strEMAIL)){
			String strEM[] = strEMAIL.split(",fh,");
			if(strEM.length == 4){
				pd.put("SMTP", strEM[0]);
				pd.put("PORT", strEM[1]);
				pd.put("EMAIL", strEM[2]);
				pd.put("PAW", strEM[3]);
			}
		}
		if(null != strSMS1 && !"".equals(strSMS1)){
			String strS1[] = strSMS1.split(",fh,");
			if(strS1.length == 2){
				pd.put("SMSU1", strS1[0]);
				pd.put("SMSPAW1", strS1[1]);
			}
		}
		if(null != strSMS2 && !"".equals(strSMS2)){
			String strS2[] = strSMS2.split(",fh,");
			if(strS2.length == 2){
				pd.put("SMSU2", strS2[0]);
				pd.put("SMSPAW2", strS2[1]);
			}
		}
		if(null != strFWATERM && !"".equals(strFWATERM)){
			String strFW[] = strFWATERM.split(",fh,");
			if(strFW.length == 5){
				pd.put("isCheck1", strFW[0]);
				pd.put("fcontent", strFW[1]);
				pd.put("fontSize", strFW[2]);
				pd.put("fontX", strFW[3]);
				pd.put("fontY", strFW[4]);
			}
		}
		if(null != strIWATERM && !"".equals(strIWATERM)){
			String strIW[] = strIWATERM.split(",fh,");
			if(strIW.length == 4){
				pd.put("isCheck2", strIW[0]);
				pd.put("imgUrl", strIW[1]);
				pd.put("imgX", strIW[2]);
				pd.put("imgY", strIW[3]);
			}
		}
		if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
			String strIW[] = strWEBSOCKET.split(",fh,");
			if(strIW.length == 7){
				pd.put("WIMIP", strIW[0]);
				pd.put("WIMPORT", strIW[1]);
				pd.put("OLIP", strIW[2]);
				pd.put("OLPORT", strIW[3]);
				pd.put("FHsmsSound", strIW[4]);
				pd.put("VIDEOIP", strIW[5]);
				pd.put("VIDEOPORT", strIW[6]);
			}
		}
		if(null != strLOGINEDIT && !"".equals(strLOGINEDIT)){
			String strLo[] = strLOGINEDIT.split(",fh,");
			if(strLo.length == 2){
				pd.put("isZhuce", strLo[0]);
				pd.put("isMusic", strLo[1]);
			}
		}
		mv.setViewName("system/head/sys_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**保存系统设置1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveSys")
	public ModelAndView saveSys() throws Exception{
		if(!"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Tools.writeFile(Const.SYSNAME,pd.getString("YSYNAME"));	//写入系统名称
		Tools.writeFile(Const.PAGE,pd.getString("COUNTPAGE"));	//写入每页条数
		// 写入邮件服务器配置
		Tools.writeFile(Const.EMAIL,pd.getString("SMTP")+",fh,"+pd.getString("PORT")+",fh,"+
				pd.getString("EMAIL")+",fh,"+pd.getString("PAW"));
		// 写入短信1配置
		Tools.writeFile(Const.SMS1,pd.getString("SMSU1")+",fh,"+pd.getString("SMSPAW1"));
		// 写入短信2配置
		Tools.writeFile(Const.SMS2,pd.getString("SMSU2")+",fh,"+pd.getString("SMSPAW2"));
		mv.addObject("msg","OK");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**保存系统设置2
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveSys2")
	public ModelAndView saveSys2() throws Exception{
		// 非admin用户不能修改
		if(!"admin".equals(Jurisdiction.getUsername())){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 文字水印配置
		Tools.writeFile(Const.FWATERM,pd.getString("isCheck1")+",fh,"+pd.getString("fcontent")+",fh,"+
				pd.getString("fontSize")+",fh,"+pd.getString("fontX")+",fh,"+pd.getString("fontY"));
		// 图片水印配置
		Tools.writeFile(Const.IWATERM,pd.getString("isCheck2")+",fh,"+pd.getString("imgUrl")+",fh,"+
				pd.getString("imgX")+",fh,"+pd.getString("imgY"));
		Watermark.fushValue();
		mv.addObject("msg","OK");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**保存系统设置3
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveSys3")
	public ModelAndView saveSys3() throws Exception{
		// 非admin用户不能修改
		if(!"admin".equals(Jurisdiction.getUsername())){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 即时通讯配置
		String winStr = pd.getString("WIMIP")+",fh,"+pd.getString("WIMPORT")+",fh,";
		// 站内信配置
		String olStr = pd.getString("OLIP")+",fh,"+pd.getString("OLPORT")+",fh,"+pd.getString("FHsmsSound")+",fh,";
		// 视频弹幕配置
		String videoStr = pd.getString("VIDEOIP")+",fh,"+pd.getString("VIDEOPORT");
		// websocket配置
		Tools.writeFile(Const.WEBSOCKET,winStr+olStr+videoStr);
		mv.addObject("msg","OK");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**保存系统设置4
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveSys4")
	public ModelAndView saveSys4() throws Exception{
		// 非admin用户不能修改
		if(!"admin".equals(Jurisdiction.getUsername())){
			return null;
		}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 写入微信配置
		Tools.writeFile(Const.WEIXIN,pd.getString("Token"));
		// 登录页面配置
		Tools.writeFile(Const.LOGINEDIT,pd.getString("isZhuce")+",fh,"+pd.getString("isMusic"));
		mv.addObject("msg","OK");
		mv.setViewName("save_result");
		return mv;
	}
	
}


// F-H Q  3-135-9679-0 