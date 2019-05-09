package com.fh.service.activiti.procdef;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 流程管理接口
 * @author FH Q313596790
 * @date 2018-01-06
 * @version 1.0
 */
public interface ProcdefManager{

	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;
	
}

