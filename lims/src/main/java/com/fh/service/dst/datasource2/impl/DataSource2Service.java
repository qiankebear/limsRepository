package com.fh.service.dst.datasource2.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport2;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.dst.datasource2.DataSource2Manager;

/** 
 * 说明： 第2数据源例子
 * @author FH Q313596790
 * @date 2016-04-29
 * @version 1.0
 */
@Service("datasource2Service")
public class DataSource2Service implements DataSource2Manager{

	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;
	
	/**新增
	 * @param pd
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		dao.save("DataSource2Mapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("DataSource2Mapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd)throws Exception{
		dao.update("DataSource2Mapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @return java.util.List<com.fh.util.PageData>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DataSource2Mapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @return java.util.List<com.fh.util.PageData>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DataSource2Mapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @return com.fh.util.PageData
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DataSource2Mapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DataSource2Mapper.deleteAll", ArrayDATA_IDS);
	}
	
}

