package com.fh.service.statistical.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.statistical.StatisticalService;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
@Service("statisticalService")
public class StatisticalServiceImpl implements StatisticalService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /**
    *@Desc 查询全部修改记录
    *@Author Wangjian
    *@Date 2018/11/9 11:00
    *@Params  * @param null
    */
    @Override
    public List<PageData> findAllMessage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StatisticalMapper.findAlllistPage", page);
    }

    /**
     * 统计表
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StatisticalMapper.datalistPage", page);
    }

    /**
     * 通过项目id找到pcr扩增记录表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> findPcrByProjectId(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticalMapper.findPcrByProjectId", pd);
    }

    @Override
    public List<PageData> findCheckedByProjectId(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticalMapper.findCheckedByProjectId",pd);
    }
}
