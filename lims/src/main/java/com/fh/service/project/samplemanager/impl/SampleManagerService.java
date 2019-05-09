package com.fh.service.project.samplemanager.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.project.samplemanager.SampleManagerManager;

/** 
 * 说明： 样本表
 * @author FH Q313596790
 * @date 2018-11-08
 * @version 1.0
 */
@Service("samplemanagerService")
public class SampleManagerService implements SampleManagerManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SampleManagerMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SampleManagerMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SampleManagerMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SampleManagerMapper.datalistPage", page);
	}

	@Override
	public List<PageData> list1(PageData page) throws Exception {
		return (List<PageData>)dao.findForList("SampleManagerMapper.allsample", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SampleManagerMapper.listAll", pd);
	}

	@Override
	public PageData listSampleCount(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SampleManagerMapper.listSampleCount", pd);
	}

	@Override
	public PageData listSampleCountIndex(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SampleManagerMapper.listSampleCountIndex", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SampleManagerMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SampleManagerMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

