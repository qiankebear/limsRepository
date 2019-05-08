package com.fh.service.punching;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Wangjian
 * @Description:打孔
 * @Date: $time$ $date$
 **/
public interface PunchingService {
    /**
    *@Desc 查询全部打孔记录
    *@Author Wangjian
    *@Date 2018/11/8 10:13
    *@Params  * @param null
    */
    List<PageData> findAllMessage(Page page)throws Exception ;

}
