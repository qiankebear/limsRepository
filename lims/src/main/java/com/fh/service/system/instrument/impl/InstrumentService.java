package com.fh.service.system.instrument.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.instrument.Instrument;
import com.fh.entity.system.instrument.InstrumentRecord;
import com.fh.service.system.instrument.InstrumentMapper;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:仪器管理
 * @Date: $time$ $date$
 **/
@Service("instrumentService")
public class InstrumentService implements InstrumentMapper {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /**
    *@Desc 查询全部数据
    *@Author Wangjian
    *@Date 2018/10/31 15:08
    *@Params  * @param null
    */
    @Override
    public List<PageData> findAll(Page page) throws Exception {
        return (List<PageData>) dao.findForList("InstrumentMapper.findAlllistPage", page);
    }
    /**
    *@Desc 查询总数
    *@Author Wangjian
    *@Date 2018/10/31 15:08
    *@Params  * @param null
    */
    @Override
    public PageData getCount(PageData pd) throws Exception {
        return (PageData) dao.findForObject("InstrumentMapper.getCount", pd);
    }
    /**
    *@Desc 根据id删除数据
    *@Author Wangjian
    *@Date 2018/10/31 16:59
    *@Params  * @param null
    */
    @Override
    public void deleteById(PageData pd) throws Exception {
        dao.delete("InstrumentMapper.deleteById", pd);
    }
    /**
    *@Desc 根据id修改数据
    *@Author Wangjian
    *@Date 2018/10/31 17:03
    *@Params  * @param null
    */
    @Override
    public void updateMessage(PageData pd) throws Exception {
        dao.update("InstrumentMapper.updateMessage", pd);
    }
    /**
    *@Desc 新增数据
    *@Author Wangjian
    *@Date 2018/10/31 17:05
    *@Params  * @param null
    */
    @Override
    public void saveMessage(PageData pd) throws Exception {
        dao.save("InstrumentMapper.saveMessage", pd);
    }
    /**
    *@Desc 根据id查询单条记录
    *@Author Wangjian
    *@Date 2018/11/1 16:35
    *@Params  * @param null
    */
    @Override
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("InstrumentMapper.findById", pd);
    }
    /**
    *@Desc 查询单条数据
    *@Author Wangjian
    *@Date 2018/11/2 17:54
    *@Params  * @param null
    */
    @Override
    public List<Instrument> findType(PageData pageData) throws Exception {
        return (List<Instrument>) dao.findForList("InstrumentMapper.findType", pageData);
    }

    /**
     * 查询运行状态的仪器
     * @return
     */
    @Override
    public List<PageData> getFunction(PageData pageData) throws Exception {
        return (List<PageData>) dao.findForList("InstrumentMapper.getFunction", pageData);
    }

    @Override
    public List<PageData> getFunctionForTest(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("InstrumentMapper.getFunctionForTest", pd);
    }
}
