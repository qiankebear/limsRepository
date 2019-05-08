package com.fh.service.project.samplemanager;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 样本表接口
 * 创建人：FH Q313596790
 * 创建时间：2018-11-08
 * @version
 */
public interface SampleManagerManager {

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;

	/**列表全部
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list1(PageData page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;

	/**列表项目样本状态
	 * @param pd
	 * @throws Exception
	 */
	public PageData listSampleCount(PageData pd)throws Exception;
	/**列表项目样本状态（首页用）
	 * @param pd
	 * @throws Exception
	 */
	public PageData listSampleCountIndex(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

