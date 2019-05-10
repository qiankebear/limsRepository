package com.fh.controller.app.sysuser;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;


/**@author FH Q313596790
  * @date:2016.5.8
 * @version 1.0
  * 系统用户-接口类 
  * 相关参数协议：
  * @param 00	请求失败
  * @param 01	请求成功
  * @param 02	返回空值
  * @param 03	请求协议参数不完整
  * @param 04  用户名或密码错误
  * @param 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appSysUser")
public class SysUserController extends BaseController {
    
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/**系统用户注册接口
	 * @return
	 */
	@RequestMapping(value="/registerSysUser")
	@ResponseBody
	public Object registerSysUser(){
		logBefore(logger, "系统用户注册接口");
		Map<String,Object> map = new HashMap<String,Object>(16);
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		try{
			// 检验请求key值是否合法
			if(Tools.checkKey("USERNAME", pd.getString("FKEY"))){
				// 检查参数
				if(AppUtil.checkParam("registerSysUser", pd)){
					
					Session session = Jurisdiction.getSession();
					// 获取session中的验证码
					String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);
					String rcode = pd.getString("rcode");
					// 判断登录验证码
					if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(rcode)){
						/* @param ROLE_ID 角色ID fhadminzhuche 为注册用户
						* @param NUMBER  编号
						* @param PHONE   手机号
						* @param BZ      备注
						* @param LAST_LOGIN 最后登录时间
						* @param IP      IP
						* @param STATUS   状态
						* @param SKIN    用户默认皮肤*/

						pd.put("USER_ID", this.get32UUID());
						pd.put("ROLE_ID", "fhadminzhuche");
						pd.put("NUMBER", "");
						pd.put("PHONE", "");
						pd.put("BZ", "注册用户");
						pd.put("LAST_LOGIN", "");
						pd.put("IP", "");
						pd.put("STATUS", "0");
						pd.put("SKIN", "no-skin");
						pd.put("RIGHTS", "");
						pd.put("ROLE_IDS", "");
						// 密码加密
						pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"),
								pd.getString("PASSWORD")).toString());
						// 判断用户名是否存在
						if(null == userService.findByUsername(pd)){
							// 执行保存
							userService.saveU(pd);
							FHLOG.save(pd.getString("USERNAME"), "新注册");
						}else{
							// 用户名已存在
							result = "04";
						}
					}else{
						// 验证码错误
						result = "06";
					}
				}else {
					result = "03";
				}
			}else{
				result = "05";
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	

	
}
	
 