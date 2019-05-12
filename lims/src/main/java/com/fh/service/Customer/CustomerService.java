package com.fh.service.Customer;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @author jdf
 * @ClassName com.fh.service.Customer
 * @Description 用户管理接口
 */
public interface CustomerService {
    /**
     * 列出用户列表
     * @param page
     * @return java.util.List<com.fh.util.PageData>
     * @throws Exception
     */
    List<PageData> listCustomer(Page page)throws Exception ;

    /**
     * 保存用户
     * @param pd
     * @return void
     * @throws Exception
     */
    void saveCustomer(PageData pd) throws Exception;

    /**
     * 通过id查找用户
     * @param pd
     * @return com.fh.util.PageData
     * @throws Exception
     */
    PageData findById(PageData pd) throws Exception;

    /**
     * 修改用户
     * @param pd
     * @return void
     * @throws Exception
     */
    void update(PageData pd)  throws Exception;

    /**
     * 删除用户
     * @param pd
     * @return void
     * @throws Exception
     */
    void deleteCustomer(PageData pd) throws Exception;

    /**
     * 批量删除用户
     * @param arrayUSER_ids
     * @return void
     * @throws Exception
     */
    void deleteAllCustomer(String[] arrayUSER_ids) throws Exception;

    /**
     * 查看项目列表
     * @param page
     * @return java.util.List<com.fh.util.PageData>
     * @throws Exception
     */
    List<PageData> findProjectByIdlistPage(Page page) throws Exception;
}
