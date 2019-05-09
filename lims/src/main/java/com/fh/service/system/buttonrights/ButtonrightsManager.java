package com.fh.service.system.buttonrights;

import java.util.List;

import com.fh.util.PageData;

/** 
 * 说明：按钮权限 接口
 * @author FH Q313596790
 * @date 2016-01-16
 * @version 1.0
 */
public interface ButtonrightsManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	
	/**通过(角色ID和按钮ID)获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(全部)左连接按钮表,查出安全权限标识(主副职角色综合)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAllBrAndQxnameByZF(String[] ROLE_IDS)throws Exception;
	
	/**列表(全部)左连接按钮表,查出安全权限标识(主职角色)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAllBrAndQxname(PageData pd)throws Exception;
	
}

