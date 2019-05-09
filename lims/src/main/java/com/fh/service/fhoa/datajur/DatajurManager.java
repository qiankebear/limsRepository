package com.fh.service.fhoa.datajur;

import com.fh.util.PageData;

/** 
 * 说明： 组织数据权限接口
 * @author FH Q313596790
 * @date 2016-04-26
 * @version 1.0
 */
public interface DatajurManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	void edit(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**取出某用户的组织数据权限
	 * @param pd
	 * @throws Exception
	 */
	PageData getDEPARTMENT_IDS(String USERNAME)throws Exception;
	
}

