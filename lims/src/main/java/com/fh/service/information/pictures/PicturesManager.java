package com.fh.service.information.pictures;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;


/**
 * 说明：图片管理接口
 * @author fh313596790qq(青苔)
 * @date 2015.11.2
 * @version 1.0
 */
public interface PicturesManager {
	
	/**列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;
	
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
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**批量获取
	 * @param ArrayDATA_IDS
	 * @return
	 * @throws Exception
	 */
	List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception;
	
	/**删除图片
	 * @param pd
	 * @throws Exception
	 */
	void delTp(PageData pd)throws Exception;
	
}

