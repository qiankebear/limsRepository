package com.fh.service.pcr.impl;

import java.util.List;
import javax.annotation.Resource;

import com.fh.service.pcr.ManualManager;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.pcr.ManualManager;

/**
 * 产品说明说的上传与下载
 *
 * @author xiongyanbiao on 2018-11-02-上午 11:50
 * @version 1.0
 */
@Service("manualService")
public class ManualService implements ManualManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		dao.save("ManualMapper.save", pd);
	}

	/**新增个人文件
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void saveP(PageData pd)throws Exception{
		dao.save("ManualMapper.saveP", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("ManualMapper.delete", pd);
	}
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void deleteP(PageData pd)throws Exception{
		dao.delete("ManualMapper.deleteP", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd)throws Exception{
		dao.update("ManualMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ManualMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ManualMapper.listAll", pd);
	}
	/**个人文件列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	public List<PageData> datalistPagepersonalAll(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ManualMapper.datalistPagepersonalAll", page);
	}
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ManualMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ManualMapper.deleteAll", ArrayDATA_IDS);
	}
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAllP(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ManualMapper.deleteAllP", ArrayDATA_IDS);
	}
	
}

