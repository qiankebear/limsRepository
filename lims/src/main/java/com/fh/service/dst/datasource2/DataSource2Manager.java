package com.fh.service.dst.datasource2;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 第2数据源例子接口
 * @author FH Q313596790
 * @date 2016-04-29
 * @version 1.0
 */
public interface DataSource2Manager{

	/**新增
	 * @param pd
	 * @return void
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @return void
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @return void
	 * @throws Exception
	 */
	void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @return java.util.List<com.fh.util.PageData>
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @return java.util.List<com.fh.util.PageData>
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return com.fh.util.PageData
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @return void
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

