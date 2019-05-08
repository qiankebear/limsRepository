package com.fh.service.rebuild;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Wangjian
 * @Description: 重扩
 * @Date: $time$ $date$
 **/
public interface RebuildService {
    /**
    *@Desc 查询全部数据列表
    *@Author Wangjian
    *@Date 2018/11/5 17:32
    *@Params  * @param null
    */
    List<PageData> findAllMessage(Page page)throws Exception ;
    /**
    *@Desc 查询孔板名称
    *@Author Wangjian
    *@Date 2018/11/14 10:42
    *@Params  * @param null
    */
    List<PageData> findPlateName (PageData pageData) throws Exception;
    /**
    *@Desc 查询版号，样本号
    *@Author Wangjian
    *@Date 2018/11/15 10:11
    *@Params  * @param null
    */
    List<PageData> findNumber (PageData pageData) throws Exception;
    /**
    *@Desc 查询孔类型，孔坐标
    *@Author Wangjian
    *@Date 2018/11/28 16:59
    *@Params  * @param null
    */
    List<PageData> findEntirety (String[] ids) throws Exception;
    /**
    *@Desc 通过项目id查询项目用户表
    *@Author Wangjian
    *@Date 2019/1/11 9:30
    *@Params  * @param null
    */
    List<PageData> findPuByPUId (PageData pageData) throws Exception;
    /**
    *@Desc 查询所有项目名
    *@Author Wangjian
    *@Date 2018/12/25 15:28
    *@Params  * @param null
    */
    List<PageData> findProjectName (PageData pageData) throws Exception;
    /**
     *@Desc 修改样本表轮数
     *@Author Wangjian
     *@Date 2019/1/2 15:24
     *@Params  * @param null
     */
    void updateSerial(PageData pageData)  throws Exception;
    /**
    *@Desc查询项目id
    *@Author Wangjian
    *@Date 2019/1/14 16:46
    *@Params  * @param null
    */
    List<String> findProjectId (PageData pageData) throws Exception;
}
