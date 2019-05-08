package com.fh.service.project.kitrecord.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.project.kitrecord.KitRecordManager;

/** 
 * 说明： 出入库记录
 * 创建人：FH Q313596790
 * 创建时间：2018-11-08
 * @version
 */
@Service("kitrecordService")
public class KitRecordService implements KitRecordManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("KitRecordMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("KitRecordMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("KitRecordMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("KitRecordMapper.datalistPage", page);
	}

	@Override
	public List<PageData> showkitRep(PageData page) throws Exception {
		return (List<PageData>)dao.findForList("KitRecordMapper.showkitRep", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("KitRecordMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("KitRecordMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("KitRecordMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> findkitall(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("KitRecordMapper.findkitall", pd);
	}

}

