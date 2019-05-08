package com.fh.service.statistical.statisticaltable.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.statistical.statisticaltable.StatisticalTableManager;

/** 
 * 说明： 统计表
 * 创建人：FH Q313596790
 * 创建时间：2018-11-13
 * @version
 */
@Service("statisticaltableService")
public class StatisticalTableService implements StatisticalTableManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;


	@Override
	public List<PageData> findNormalByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findNormalByPId", pd);
	}

	@Override
	public List<PageData> findMincroByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findMincroByPId", pd);
	}

	@Override
	public List<PageData> findRateByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findRateByPId", pd);
	}

	@Override
	public List<PageData> findThreeByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findThreeByPId", pd);
	}

	@Override
	public List<PageData> findReformByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findReformByPId", pd);
	}

	@Override
	public List<PageData> findEmptycardByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findEmptycardByPId", pd);
	}

	@Override
	public List<PageData> findExpByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findExpByPId", pd);	}

	@Override
	public List<PageData> findSumByPId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findSumByPId", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StatisticalTableMapper.statisticalTablelistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StatisticalTableMapper.listAll", pd);
	}

	@Override
	public List<PageData> findTableListByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findTableListByProjectId", pd);
	}

	@Override
	public List<PageData> findSecondByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findSecondByProjectId", pd);
	}

	@Override
	public List<PageData> findIssueByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findIssueByProjectId", pd);
	}

	@Override
	public List<PageData> findThreeByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findThreeByProjectId", pd);
	}

	@Override
	public List<PageData> findFourByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findFourByProjectId", pd);
	}

	@Override
	public List<PageData> findFiveByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findFiveByProjectId", pd);
	}

	@Override
	public List<PageData> findSpecialByPid(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StatisticalTableMapper.findSpecialByPid", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StatisticalTableMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StatisticalTableMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

