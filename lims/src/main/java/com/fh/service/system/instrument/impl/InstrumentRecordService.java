package com.fh.service.system.instrument.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.instrument.InstrumentRecord;
import com.fh.service.system.instrument.InstrumentRecordMapper;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
@Service("instrumentRecordService")
public class InstrumentRecordService implements InstrumentRecordMapper {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /**
    *@Desc 查询全部数据
    *@Author Wangjian
    *@Date 2018/10/31 14:26
    *@Params  * @param null
    */
    @Override
    public List<PageData> findAll(Page page) throws Exception {
        return (List<PageData>) dao.findForList("InstrumentRecordMapper.findAlllistPage",page);
    }
    /**
    *@Desc 保存记录
    *@Author Wangjian
    *@Date 2018/11/2 17:42
    *@Params  * @param null
    */
    @Override
    public void saveRecordMessage(PageData pd) throws Exception {
        dao.save("InstrumentRecordMapper.saveRecordMessage",pd);
    }
    /**
    *@Desc 删除记录
    *@Author Wangjian
    *@Date 2018/11/2 17:42
    *@Params  * @param null
    */
    @Override
    public void deleteRecordById(PageData pd) throws Exception {
        dao.delete("InstrumentRecordMapper.deleteRecordById",pd);
    }
    /**
    *@Desc 更新数据
    *@Author Wangjian
    *@Date 2018/11/2 17:49
    *@Params  * @param null
    */
    @Override
    public void updateMessage(PageData pd) throws Exception {
        dao.update("InstrumentRecordMapper.updateRecordMessage",pd);
    }
    /**
    *@Desc 查询单条数据
    *@Author Wangjian
    *@Date 2018/11/2 17:53
    *@Params  * @param null
    */
    @Override
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("InstrumentRecordMapper.findRecordById",pd);
    }
}
