package com.fh.service.project.samplemanager;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 样本表接口
 * @author FH Q313596790
 * @date 2018-11-08
 * @version 1.0
 */
public interface SampleManagerManager {

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;

	/**列表全部
	 * @param page
	 * @throws Exception
	 */
	List<PageData> list1(PageData page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;

	/**列表项目样本状态
	 * @param pd
	 * @throws Exception
	 */
	PageData listSampleCount(PageData pd)throws Exception;
	/**列表项目样本状态（首页用）
	 * @param pd
	 * @throws Exception
	 */
	PageData listSampleCountIndex(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

