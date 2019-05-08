package com.fh.service.system.Resources.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.Resources.ResourcesService;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /**
    *@Desc 查询全部数据
    *@Author Wangjian
    *@Date 2018/11/12 15:03
    *@Params  * @param null
    */
    @Override
    public List<PageData> findAll(Page page) throws Exception {
        return (List<PageData>) dao.findForList("ResourcesMapper.findAlllistPage",page);
    }
    /**
    *@Desc 保存信息
    *@Author Wangjian
    *@Date 2018/11/12 15:57
    *@Params  * @param null
    */
    @Override
    public void saveMessage(PageData pd) throws Exception {
        dao.save("ResourcesMapper.saveMessage",pd);
    }
    /**
    *@Desc 查询单条记录
    *@Author Wangjian
    *@Date 2018/11/12 15:57
    *@Params  * @param null
    */
    @Override
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ResourcesMapper.findById",pd);
    }
    /**
    *@Desc 修改数据
    *@Author Wangjian
    *@Date 2018/11/12 16:15
    *@Params  * @param null
    */
    @Override
    public void updateMessage(PageData pd) throws Exception {
        dao.update("ResourcesMapper.updateMessage",pd);
    }
    /**
    *@Desc 删除数据
    *@Author Wangjian
    *@Date 2018/11/12 16:16
    *@Params  * @param null
    */
    @Override
    public void deleteById(PageData pd) throws Exception {
        dao.delete("ResourcesMapper.deleteById",pd);
    }
}
