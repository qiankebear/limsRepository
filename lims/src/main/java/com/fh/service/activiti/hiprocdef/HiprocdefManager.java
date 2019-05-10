package com.fh.service.activiti.hiprocdef;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 历史流程任务接口
 * @author FH Q313596790
 * @date 2018-01-28
 * @version 1.0
 */
public interface HiprocdefManager{

	/**列表
	 * @param page
	 * @return java.util.List<com.fh.util.PageData>
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;

	/**历史流程变量列表
	 * @param pd
	 * @return java.util.List<com.fh.util.PageData>
	 * @throws Exception
	 */
	List<PageData> hivarList(PageData pd)throws Exception;
	
}

