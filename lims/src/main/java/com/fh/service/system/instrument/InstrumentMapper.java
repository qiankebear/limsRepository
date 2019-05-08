package com.fh.service.system.instrument;

import com.fh.entity.Page;
import com.fh.entity.system.instrument.Instrument;
import com.fh.entity.system.instrument.InstrumentRecord;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Wangjian
 * @Description:仪器管理
 * @Date: $time$ $date$
 **/
public interface InstrumentMapper {

    /**
    *@Desc 查询全部数据
    *@Author Wangjian
    *@Date 2018/10/31 15:07
    *@Params  * @param null
    */
    List<PageData> findAll(Page page) throws Exception ;
    /**
    *@Desc 查询总数
    *@Author Wangjian
    *@Date 2018/10/31 15:07
    *@Params  * @param null
    */
    public PageData getCount( PageData pd)throws Exception;
    /**
    *@Desc 根据id删除数据
    *@Author Wangjian
    *@Date 2018/10/31 16:59
    *@Params  * @param null
    */
    public void deleteById(PageData pd) throws Exception;
    /**
    *@Desc 更新数据
    *@Author Wangjian
    *@Date 2018/10/31 17:03
    *@Params  * @param null
    */
    public void updateMessage(PageData pd) throws Exception;
    /**
    *@Desc 新增数据
    *@Author Wangjian
    *@Date 2018/10/31 17:05
    *@Params  * @param null
    */
    public void saveMessage(PageData pd) throws Exception;
    /**
    *@Desc 根据id查询单条数据
    *@Author Wangjian
    *@Date 2018/11/1 16:35
    *@Params  * @param null
    */
    public PageData findById(PageData pd) throws Exception;
    /**
    *@Desc 查询仪器型号
    *@Author Wangjian
    *@Date 2018/11/2 16:23
    *@Params  * @param null
    */
    List<Instrument> findType(PageData pageData) throws Exception ;


    /**
     * 查询运行状态的仪器
     * @return
     */
    List<PageData> getFunction(PageData pageData) throws Exception ;

    /**
     * 查询运行状态的仪器测序仪类仪器
     * @return
     */
    List<PageData> getFunctionForTest(PageData pd) throws Exception ;
}
