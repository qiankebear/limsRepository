package com.fh.service.statistical.statisticalinfo.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.statistical.statisticalinfo.StatisticalInfoService;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("statisticalInfoService")
public class StatisticalServiceInfoImpl implements StatisticalInfoService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public List<PageData> datalistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StatisticalInfoMapper.datalistPage", page);
    }

    @Override
    public void batchSave(List<PageData> pd) throws Exception {
        dao.batchSave("StatisticalInfoMapper.insertStatistics", pd);
    }

    @Override
    public void delete(PageData pd) throws Exception {
        dao.delete("StatisticalInfoMapper.deleteAll", pd);
    }
}
