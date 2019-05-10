package com.fh.service.activiti.fhmodel;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 工作流模型管理接口
 * @author FH Q313596790
 * @date 2017-12-26
 * @version 1.0
 */
public interface FHModelManager{

	/**修改
	 * @param pd
	 * @return void
	 * @throws Exception
	 */
	void edit(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return com.fh.util.PageData
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @return java.util.List<com.fh.util.PageData>
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;
	
}

