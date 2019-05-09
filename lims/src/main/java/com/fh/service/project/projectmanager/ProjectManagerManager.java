package com.fh.service.project.projectmanager;

import java.util.List;
import java.util.Map;

import com.fh.entity.Page;
import com.fh.entity.project.KitRecord;
import com.fh.entity.project.ProjectManager;
import com.fh.entity.project.ProjectUser;
import com.fh.util.PageData;

/** 
 * 说明： 项目管理接口
 * @author FH Q313596790
 * @date 2018-11-02
 * @version 1.0
 */
public interface ProjectManagerManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	long save(PageData pd)throws Exception;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	void saveRep(PageData pd)throws Exception;

	/**
	 * 新增出库记录
	 * @param pd
	 * @throws Exception
	 */
	void saveoutput(KitRecord pd)throws  Exception;

	/**
	 * 新增出库记录
	 * @param pd
	 * @throws Exception
	 */
	void saveinput(KitRecord pd)throws  Exception;


	void savepu(PageData pd) throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;

	/**
	 * 删除项目用户关联表
	 * @param pd
	 * @throws Exception
	 */
	void deletepu(PageData pd)throws Exception;

	/**
	 * 出库
	 * @param pd
	 * @throws Exception
	 */
	void output(PageData pd)throws Exception;

	/**
	 * 入库
	 * @param pd
	 * @throws Exception
	 */
	void input(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	void edit(PageData pd)throws Exception;
	/**结束项目
	 * @param pd
	 * @throws Exception
	 */
	void endProject(PageData pd)throws Exception;

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	void editRep(PageData pd)throws Exception;

	/**修改项目用户关联数据
	 * @param pd
	 * @throws Exception
	 */
	void editPUser(PageData pd)throws Exception;

	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> list(Page page)throws Exception;
	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> list1(Page page)throws Exception;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	List<PageData> listRep(Long pd)throws Exception;


	List<ProjectManager> listAllProject(PageData pd) throws  Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listAll(PageData pd)throws Exception;

	/**获取所有试剂盒及耗材列表
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listKits1(Page pd)throws Exception;

	/**获取所有试剂盒及耗材列表
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> listKits2(Page pd)throws Exception;

	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findById(PageData pd)throws Exception;

	/**通过id获取项目负责人
	 * @param pd
	 * @throws Exception
	 */
	PageData findPUById(PageData pd)throws Exception;

    /**通过id获取实验员
     * @param pd
     * @throws Exception
     */
    List<PageData> findPU1ById(PageData pd)throws Exception;

    /**通过id获取访客
     * @param pd
     * @throws Exception
     */
    List<PageData> findPU2ById(PageData pd)throws Exception;

	/**通过id获取项目用户关联数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findPUByUserid(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> findAllPUById(long pd)throws Exception;


	/**
	 * 通过kitid找到库存
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findRepByKPId(PageData pd)throws Exception;

	/**
	 * 通过项目id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<PageData> findInputOutputById(Long id)throws Exception;


	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	PageData findByNumber(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> findRepById(Page pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> findRepByProjectId(PageData pd)throws Exception;


	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	void deleteAll(String[] ArrayDATA_IDS)throws Exception;

    List<PageData> listUserName(Page page) throws Exception;

	/**
	 * 通过用户id查找中间表中的数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findPUByUserIdlistPage(Page pd) throws Exception;

	/**
	 * 通过用户id查找中间表中的数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findPUByUserIdlistPage1(Page pd) throws Exception;

	/**
	 * 通过项目id查找项目对象
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findProjectByUserId(PageData pd) throws Exception;

	/**
	 * 通过项目id跟用户id查找中间表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findPUByProjectUser(PageData pd) throws Exception;

	/**
	 * 通过id获取用户与项目中间表数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findPUByid1(PageData pd) throws Exception;

	/**
	 * 通过项目id查找孔板表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findPPByProjectId(PageData pd) throws Exception;

	/**
	 * 通过项目id查找复核质检没有通过的普通孔板
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findnopassByProjectId(PageData pd) throws Exception;

	/**
	 * 获取所有已完成项目列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> listEndProject(Page page) throws Exception;
	/**
	 * 获取所有已完成项目列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> AllProjectlistPage(PageData page) throws Exception;
	/**
	 *
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> ProjectlistPage(Page page) throws Exception;


	/**
	 * 通过项目编号找项目
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findProjectByNumber(PageData pd) throws Exception;

	/**
	 * 找到所有项目(不带分页)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> findprojectall(PageData pd) throws Exception;

	/**
	 * 通过项目名称找项目
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData findProjectByName(PageData pd) throws Exception;

	void editInputRep(PageData pd) throws Exception;

}

