package com.fh.service.punching.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.punching.PunchingService;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Wangjian
 * @Description:
 * @Date: $time$ $date$
 **/
@Service("punchingService")
public class PunchingServiceImpl implements PunchingService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /**
    *@Desc 查询全部打孔记录
    *@Author Wangjian
    *@Date 2018/11/8 10:15
    *@Params  * @param null
    */
    @Override
    public List<PageData> findAllMessage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PunchingMapper.findAlllistPage",page);
    }
}
