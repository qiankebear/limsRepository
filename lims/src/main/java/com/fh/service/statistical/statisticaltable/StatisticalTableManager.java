package com.fh.service.statistical.statisticaltable;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 统计表接口
 * 创建人：FH Q313596790
 * 创建时间：2018-11-13
 * @version
 */
public interface StatisticalTableManager{

	/**
	 * 普通样本
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findNormalByPId(PageData pd)throws Exception;

	/**
	 * 微变异
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findMincroByPId(PageData pd)throws Exception;

	/**
	 * 稀有等位
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findRateByPId(PageData pd)throws Exception;

	/**
	 * 三等位
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findThreeByPId(PageData pd)throws Exception;

	/**
	 * 重做
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findReformByPId(PageData pd)throws Exception;

	/**
	 * 空卡
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findEmptycardByPId(PageData pd)throws Exception;

	/**
	 * 导出
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findExpByPId(PageData pd)throws Exception;

	/**
	 * 总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findSumByPId(PageData pd)throws Exception;

	
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

	/**通过项目id获取所有的孔板表信息
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> findTableListByProjectId(PageData pd)throws Exception;

	/**
	 * 第二轮要重做样本
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findSecondByProjectId(PageData pd)throws Exception;

	/**
	 * 问题样本
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findIssueByProjectId(PageData pd)throws Exception;

	/**
	 * 第三轮要重做的样本
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findThreeByProjectId(PageData pd)throws Exception;

	/**
	 * 第四轮要重做的样本
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findFourByProjectId(PageData pd)throws Exception;

	/**
	 * 第五轮要重做的样本
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findFiveByProjectId(PageData pd)throws Exception;

	/**
	 * 特殊样本
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findSpecialByPid(PageData pd)throws Exception;

	
}

