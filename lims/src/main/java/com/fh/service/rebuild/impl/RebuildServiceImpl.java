package com.fh.service.rebuild.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.rebuild.RebuildService;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:重扩
 * @Date: $time$ $date$
 **/
@Service("rebuildService")
public class RebuildServiceImpl implements RebuildService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /**
    *@Desc 查询全部数据
    *@Author Wangjian
    *@Date 2018/11/5 17:34
    *@Params  * @param null
    */
    @Override
    public List<PageData> findAllMessage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PlateMapper.findAlllistPage",page);
    }
    /**
    *@Desc 查询全部孔板名称
    *@Author Wangjian
    *@Date 2018/11/14 10:42
    *@Params  * @param null
    */
    @Override
    public List<PageData> findPlateName(PageData pageData) throws Exception {
        return (List<PageData>) dao.findForList("PlateMapper.findPlateName",pageData);
    }
    /**
     *@Desc 查询版号，样本号
     *@Author Wangjian
     *@Date 2018/11/15 10:11
     *@Params  * @param null
     */
    @Override
    public List<PageData> findNumber(PageData pageData) throws Exception {
        return (List<PageData>) dao.findForList("PlateMapper.findNumber",pageData);
    }
    /**
    *@Desc 查询孔类型，孔坐标
    *@Author Wangjian
    *@Date 2018/11/28 17:01
    *@Params  * @param null
    */
    @Override
    public List<PageData> findEntirety(String[] ids) throws Exception {
        return (List<PageData>) dao.findForList("PlateMapper.findEntirety",ids);
    }
    /**
    *@Desc 通过项目id查询项目用户表
    *@Author Wangjian
    *@Date 2019/1/11 9:31
    *@Params  * @param null
    */
    @Override
    public List<PageData> findPuByPUId(PageData pageData) throws Exception {
        return (List<PageData>) dao.findForList("PlateMapper.findPuByPUId",pageData);
    }
    /**
    *@Desc 查询所有项目名称
    *@Author Wangjian
    *@Date 2018/12/25 15:29
    *@Params  * @param null
    */
    @Override
    public List<PageData> findProjectName(PageData pageData) throws Exception {
        return (List<PageData>) dao.findForList("PlateMapper.findProjectName",pageData);
    }
    /**
    *@Desc 修改样本表轮数
    *@Author Wangjian
    *@Date 2019/1/2 15:24
    *@Params  * @param null
    */
    @Override
    public void updateSerial(PageData pageData) throws Exception {
        dao.update("PlateMapper.updateSerial",pageData);
    }
    /**
    *@Desc 查询项目id
    *@Author Wangjian
    *@Date 2019/1/14 16:47
    *@Params  * @param null
    */
    @Override
    public List<String> findProjectId(PageData pageData) throws Exception {
        return (List<String>) dao.findForList("PlateMapper.findProjectId",pageData);
    }
}
