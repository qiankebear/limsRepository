package com.fh.service.statistical.statisticalinfo;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface StatisticalInfoService {
    /**
     * 统计信息
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> datalistPage (Page page)throws Exception;

    /**新增
     * @param pd
     * @throws Exception
     */
    void batchSave(List<PageData> pd)throws Exception;

    void delete(PageData pd) throws Exception;

}
