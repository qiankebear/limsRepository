package com.fh.service.system.Resources;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
public interface ResourcesService {
    /**
    *@Desc 查询全部数据
    *@Author Wangjian
    *@Date 2018/11/12 15:01
    *@Params  * @param null
    */
    List<PageData> findAll(Page page) throws Exception ;
    /**
    *@Desc 保存资源路径
    *@Author Wangjian
    *@Date 2018/11/12 15:48
    *@Params  * @param null
    */
    void saveMessage(PageData pd) throws Exception;
    /**
    *@Desc 查询单条记录
    *@Author Wangjian
    *@Date 2018/11/12 15:56
    *@Params  * @param null
    */
    PageData findById(PageData pd) throws Exception;
    /**
    *@Desc 修改数据
    *@Author Wangjian
    *@Date 2018/11/12 16:14
    *@Params  * @param null
    */
    void updateMessage(PageData pd) throws Exception;
    /**
    *@Desc 删除数据
    *@Author Wangjian
    *@Date 2018/11/12 16:15
    *@Params  * @param null
    */
    void deleteById(PageData pd) throws Exception;

}
