package com.fh.controller.app.appuser;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.Tools;


/**@author FH Q313596790
  * @date:2016.5.8
 * @version 1.0
  * 会员-接口类 
  * 相关参数协议：
  * @param 00	请求失败
  * @param 01	请求成功
  * @param 02	返回空值
  * @param 03	请求协议参数不完整
  * @param 04  用户名或密码错误
  * @param 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appuser")
public class IntAppuserController extends BaseController {
    
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	
	/**根据用户名获取会员信息
	 * @return 
	 */
	@RequestMapping(value="/getAppuserByUm")
	@ResponseBody
	public Object getAppuserByUsernmae(){
		logBefore(logger, "根据用户名获取会员信息");
		Map<String,Object> map = new HashMap<String,Object>(16);
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		try{
			//检验请求key值是否合法
			if(Tools.checkKey("USERNAME", pd.getString("FKEY"))){
				//检查参数
				if(AppUtil.checkParam("getAppuserByUsernmae", pd)){
					pd = appuserService.findByUsername(pd);
					map.put("pd", pd);
					result = (null == pd) ?  "02" :  "01";
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
	
 