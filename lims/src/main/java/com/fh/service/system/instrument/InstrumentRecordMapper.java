package com.fh.service.system.instrument;

import com.fh.entity.Page;
import com.fh.entity.system.instrument.InstrumentRecord;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Wangjian
 * @Description:仪器管理使用记录
 * @Date: $time$ $date$
 **/
public interface InstrumentRecordMapper {
    /**
    *@Desc 查询全部数据
    *@Author Wangjian
    *@Date 2018/10/31 14:22
    *@Params  * @param null
    */
    List<PageData> findAll(Page page) throws Exception ;
    /**
    *@Desc 新增仪器使用记录
    *@Author Wangjian
    *@Date 2018/11/2 17:09
    *@Params  * @param null
    */
    public void saveRecordMessage(PageData pd) throws Exception;
    /**
    *@Desc 根据id删除使用记录
    *@Author Wangjian
    *@Date 2018/11/2 17:41
    *@Params  * @param null
    */
    public void deleteRecordById(PageData pd) throws Exception;
    /**
    *@Desc 更新数据
    *@Author Wangjian
    *@Date 2018/11/2 17:48
    *@Params  * @param null
    */
    public void updateMessage(PageData pd) throws Exception;
    /**
    *@Desc 查询单条数据
    *@Author Wangjian
    *@Date 2018/11/2 17:53
    *@Params  * @param null
    */
    public PageData findById(PageData pd) throws Exception;

}
