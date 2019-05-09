package com.fh.service.pcr;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 产品说明说的上传与下载
 *
 * @author xiongyanbiao on 2018-11-02-上午 11:50
 * @version 1.0
 */
public interface ManualManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;

	/**新增个人文件
	 * @param pd
	 * @throws Exception
	 */
	void saveP(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void deleteP(PageData pd)throws Exception;
	
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
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;

	/**个人文件列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> datalistPagepersonalAll(Page page) throws Exception;

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
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAllP(String[] ArrayDATA_IDS)throws Exception;
	
}

