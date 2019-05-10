package com.fh.service.customer.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.customer.CustomerService;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:$用户管理业务层
 * @Param:$
 * @return:$
 * @Author:jdf
 * @Date:$
 */
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> listCustomer(Page page) throws Exception{
        return (List<PageData>) dao.findForList("CustomerMapper.listCustomerlistPage", page);
    }

    @Override
    public void saveCustomer(PageData pd) throws Exception {
        dao.save("CustomerMapper.saveCustomer", pd);
    }

    @Override
    public PageData findById(PageData pd) throws Exception {
        return (PageData)dao.findForObject("CustomerMapper.findById", pd);
    }

    @Override
    public void update(PageData pd) throws Exception {
        dao.update("CustomerMapper.editCustomer", pd);
    }

    @Override
    public void deleteCustomer(PageData pd) throws Exception {
        dao.delete("CustomerMapper.deleteCustomer", pd);
    }

    @Override
    public void deleteAllCustomer(String[] arrayUSER_ids) throws Exception {
        dao.delete("CustomerMapper.deleteAllCustomer", arrayUSER_ids);
    }

    @Override
    public List<PageData> findProjectByIdlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("CustomerMapper.findProjectByIdlistPage", page);
    }
}
