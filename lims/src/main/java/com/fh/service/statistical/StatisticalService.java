package com.fh.service.statistical;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Wangjian
 * @Description:修改记录
 * @Date: $time$ $date$
 **/
public interface StatisticalService {
    /**
    *@Desc 查询全部修改记录
    *@Author Wangjian
    *@Date 2018/11/9 10:59
    *@Params  page
    */
    List<PageData> findAllMessage (Page page)throws Exception;

    /**
     * 统计表
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> datalistPage (Page page)throws Exception;
    /**
    *@Desc 通过项目id查询PCR
    *@Author Wangjian
    *@Date 2019/1/11 9:29
    *@Params  * @param null
    */
    List<PageData> findPcrByProjectId (PageData pd)throws Exception;
    /**
    *@Desc 通过项目id查询
    *@Author Wangjian
    *@Date 2019/1/11 9:29
    *@Params  * @param null
    */
    List<PageData> findCheckedByProjectId (PageData pd)throws Exception;


}
