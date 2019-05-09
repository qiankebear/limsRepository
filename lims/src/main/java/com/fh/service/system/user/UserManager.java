package com.fh.service.system.user;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.PageData;


/** 用户接口类
 * @author fh313596790qq(青苔)
 * @date 2015.11.2
 * @version 1.0
 */
public interface UserManager {
	
	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getUserByNameAndPwd(PageData pd)throws Exception;
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	void updateLastLogin(PageData pd)throws Exception;
	
	/**保存用户皮肤
	 * @param pd
	 * @throws Exception
	 */
	void saveSkin(PageData pd)throws Exception;
	
	/**通过用户ID获取用户信息和角色信息
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	User getUserAndRoleById(String USER_ID) throws Exception;
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findByUsername(PageData pd)throws Exception;
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> listAllUserByRoldId(PageData pd) throws Exception;
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	void saveIP(PageData pd)throws Exception;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> listUsers(Page page)throws Exception;
	
	/**用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> listUsersBystaff(Page page)throws Exception;
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findByUE(PageData pd)throws Exception;
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findByUN(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findNameById(String pd)throws Exception;


	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	void editU(PageData pd)throws Exception;
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	void saveU(PageData pd)throws Exception;
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	void deleteU(PageData pd)throws Exception;
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	void deleteAllU(String[] USER_IDS)throws Exception;
	
	/**用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> listAllUser(PageData pd)throws Exception;
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	PageData getUserCount(String value)throws Exception;
	/**
	*@Desc 查询全部人员姓名
	*@Author Wangjian
	*@Date 2018/11/2 16:53
	*@Params  * @param null
	*/
	List<PageData> findName(PageData pd)throws Exception;

	List<PageData> findAllName(Page page) throws Exception;
	
}
