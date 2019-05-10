package com.fh.service.project.projectmanager.impl;

import java.util.List;
import javax.annotation.Resource;

import com.fh.entity.project.KitRecord;
import com.fh.entity.project.ProjectManager;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.project.projectmanager.ProjectManagerManager;

/** 
 * 说明： 项目管理
 * @author FH Q313596790
 * @date 2018-11-02
 * @version 1.0
 */
@Service("projectmanagerService")
public class ProjectManagerService implements ProjectManagerManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public long save(PageData pd)throws Exception{
		return (Integer)dao.save("ProjectManagerMapper.save", pd);
	}

	@Override
	public void saveRep(PageData pd) throws Exception {
		dao.save("ProjectManagerMapper.saveRep", pd);
	}

	@Override
	public void saveoutput(KitRecord pd) throws Exception {
		dao.save("ProjectManagerMapper.saveoutput", pd);
	}

	@Override
	public void saveinput(KitRecord pd) throws Exception {
		dao.save("ProjectManagerMapper.saveinput", pd);
	}

	@Override
	public void savepu(PageData pd) throws Exception {
		dao.save("ProjectManagerMapper.savepu", pd);
	}

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ProjectManagerMapper.delete", pd);
	}

	@Override
	public void deletepu(PageData pd) throws Exception {
		dao.delete("ProjectManagerMapper.deletepu", pd);
	}

	@Override
	public void output(PageData pd) throws Exception {
		dao.save("ProjectManagerMapper.output", pd);
	}

	@Override
	public void input(PageData pd) throws Exception {
		dao.save("ProjectManagerMapper.input", pd);
	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ProjectManagerMapper.edit", pd);
	}

	@Override
	public void endProject(PageData pd) throws Exception {
		dao.update("ProjectManagerMapper.endProject", pd);
	}

	@Override
	public void editRep(PageData pd) throws Exception {
		dao.update("ProjectManagerMapper.editRep", pd);
	}

	@Override
	public void editPUser(PageData pd) throws Exception {
		dao.update("ProjectManagerMapper.editPUser", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectManagerMapper.datalistPage", page);
	}

	@Override
	public List<PageData> list1(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.datalistPage1", page);
	}

	/**
	 * 列表库存
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public List<PageData> listRep(Long pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.listRep", pd);
	}

	@Override
	public List<ProjectManager> listAllProject(PageData pd) throws Exception {
		return (List<ProjectManager>)dao.findForList("ProjectManagerMapper.listAllProject",pd);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectManagerMapper.listAll", pd);
	}

	@Override
	public List<PageData> listKits1(Page pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.listKits1", pd);
	}

	@Override
	public List<PageData> listKits2(Page pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.listKits2", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProjectManagerMapper.findById", pd);
	}

	@Override
	public PageData findPUById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProjectManagerMapper.findPUById", pd);
	}

    @Override
    public List<PageData> findPU1ById(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("ProjectManagerMapper.findPU1ById", pd);
    }

    @Override
    public List<PageData> findPU2ById(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("ProjectManagerMapper.findPU2ById", pd);
    }

    @Override
	public PageData findPUByUserid(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProjectManagerMapper.findPUByUserid", pd);
	}

	@Override
	public List<PageData> findAllPUById(long pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.findAllPUById", pd);
	}

	@Override
	public PageData findRepByKPId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ProjectManagerMapper.findRepByKitId", pd);
	}

	@Override
	public List<PageData> findInputOutputById(Long id) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.findInputOutputById", id);
	}

	@Override
	public PageData findByNumber(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProjectManagerMapper.findByNumber", pd);
	}

	@Override
		public List<PageData> findRepById(Page pd) throws Exception {
			return (List<PageData>)dao.findForList("ProjectManagerMapper.findReplistPage", pd);
	}

	@Override
	public List<PageData> findRepByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.findRepByProjectId", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProjectManagerMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> listUserName(Page page) throws Exception {
		return  (List<PageData>)dao.findForList("ProjectManagerMapper.listUserName", page);
	}

	@Override
	public List<PageData> findPUByUserIdlistPage(Page pd) throws Exception {
		return  (List<PageData>)dao.findForList("ProjectManagerMapper.findPUByUserIdlistPage", pd);
	}

	@Override
	public List<PageData> findPUByUserIdlistPage1(Page pd) throws Exception {
		return  (List<PageData>)dao.findForList("ProjectManagerMapper.findPUByUserIdlistPage1", pd);
	}

	@Override
	public PageData findProjectByUserId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProjectManagerMapper.findProjectByUserId", pd);
	}

	@Override
	public List<PageData> findPUByProjectUser(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.findPUByProjectUser", pd);
	}

	@Override
	public PageData findPUByid1(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProjectManagerMapper.findPUByid1", pd);
	}

    @Override
    public List<PageData> findPPByProjectId(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("ProjectManagerMapper.findPPByProjectId", pd);
    }
	@Override
	public List<PageData> findnopassByProjectId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.findnopassByProjectId", pd);
	}


	@Override
	public List<PageData> listEndProject(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.EndProjectlistPage", page);
	}

	@Override
	public List<PageData> AllProjectlistPage(PageData page) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.AllProjectlist", page);
	}

	@Override
	public List<PageData> ProjectlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.ProjectlistPage", page);
	}

	@Override
	public PageData findProjectByNumber(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProjectManagerMapper.findProjectByNumber", pd);
	}

	@Override
	public List<PageData> findprojectall(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProjectManagerMapper.findprojectall", pd);
	}

	@Override
	public PageData findProjectByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProjectManagerMapper.findProjectByName", pd);
	}

	@Override
	public void editInputRep(PageData pd) throws Exception {
		dao.update("ProjectManagerMapper.editInputRep", pd);
	}

}

