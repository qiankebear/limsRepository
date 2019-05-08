package com.fh.service.Customer;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface CustomerService {
    List<PageData> listCustomer(Page page)throws Exception ;

    void saveCustomer(PageData pd) throws Exception;

    PageData findById(PageData pd) throws Exception;

    void update(PageData pd)  throws Exception;

    void deleteCustomer(PageData pd) throws Exception;

    void deleteAllCustomer(String[] arrayUSER_ids) throws Exception;

    List<PageData> findProjectByIdlistPage(Page page) throws Exception;
}
