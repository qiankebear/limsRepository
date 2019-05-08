package com.fh.service.porePlate.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.porePlate.PorePlateService;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:$
 * @Param:$
 * @return:$
 * @Author:Mr.Wang
 * @Date:$
 */
@Service("porePlateService")
public class PorePlateServiceImpl implements PorePlateService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> porePlatelistPage(Page page) throws Exception {
         return (List<PageData>) dao.findForList("PorePlateMapper.porePlatelistPage", page);
    }

    @Override
    public  int savePorePlate(PageData pd)throws Exception  {
       return  (int)dao.save("PorePlateMapper.savePorePlate", pd);
    }

    @Override
    public PageData goEditPorePlate(PageData pd) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.findById", pd);
    }

    @Override
    public void editPorePlate(PageData pd) throws Exception {
        dao.update("PorePlateMapper.editPorePlate",pd);
    }

    @Override
    public void deletePorePlate(PageData pd) throws Exception{
        dao.delete("PorePlateMapper.deletePorePlate", pd);
    }

    @Override
    public PageData findSample(PageData pd) throws Exception{
        return (PageData)dao.findForObject("PorePlateMapper.findSample", pd);
    }

    @Override
    public void deleteAllPorePlate(String[] arrayUSER_ids) throws Exception {
        dao.delete("PorePlateMapper.deleteAllPorePlate", arrayUSER_ids);
    }

    @Override
    public PageData findSampleAndPoreName(PageData pageData)throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.findSampleAndPoreName", pageData);
    }

    @Override
    public List<PageData> listProject(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("PorePlateMapper.listProject", pd);
    }

    @Override
    public void savePorePlateDetailed(PageData pd) throws Exception {
        dao.save("PorePlateMapper.savePorePlateDetailed",pd);
    }

    @Override
    public int saveSample(PageData pd) throws Exception {
        return  (int)dao.save("PorePlateMapper.saveSample", pd);
    }

    @Override
    public List<PageData> findPorePlateList(PageData pd)throws Exception  {
        return (List<PageData>)dao.findForList("PorePlateMapper.findPorePlateList", pd);
    }

    @Override
    public List<PageData> findAllSampleId(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("PorePlateMapper.findAllSampleId", pd);
    }

    @Override
    public void deleteAllSample(String[] arrayUSER_ids) throws Exception {
        dao.delete("PorePlateMapper.deleteAllSample", arrayUSER_ids);
    }

    @Override
    public void deleteAllHoleType(PageData pd) throws Exception {
        dao.delete("PorePlateMapper.deleteAllHoleType", pd);
    }

    @Override
    public void saveUpdateRecordPorePlate(PageData updateRecordPorePlate) throws Exception{
        dao.save("PorePlateMapper.saveUpdateRecordPorePlate", updateRecordPorePlate);
    }

    @Override
    public void completeOther(PageData pd)throws Exception{
        dao.update("PorePlateMapper.completeOther",pd);
    }

    @Override
    public void completeOtherFuhe(PageData pd) throws Exception {
        dao.update("PorePlateMapper.completeOtherFuhe",pd);
    }

    @Override
    public void updateHoleType(PageData updateHoleType) throws Exception {
        dao.update("PorePlateMapper.updateHoleType",updateHoleType);
    }

    @Override
    public PageData getProjectPermission(PageData pd) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.getProjectPermission", pd);
    }

    @Override
    public PageData selectPoreCurrentProcedure(PageData pd) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.selectPoreCurrentProcedure", pd);
    }

    @Override
    public PageData selectPcr_record(PageData pd) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.selectPcr_record", pd);
    }

    @Override
    public void savePcr_record(PageData pd) throws Exception {
        dao.save("PorePlateMapper.savePcr_record", pd);
    }

    @Override
    public void updatePcr_record(PageData pd) throws Exception {
        dao.update("PorePlateMapper.updatePcr_record", pd);
    }

    @Override
    public PageData selectKit_record(PageData kitRecord) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.selectKit_record", kitRecord);
    }

    @Override
    public void saveKit_record(PageData pd) throws Exception {
        dao.save("PorePlateMapper.saveKit_record", pd);
    }

    @Override
    public void updateKit_record(PageData pd) throws Exception {
        dao.update("PorePlateMapper.updateKit_record", pd);
    }

    @Override
    public PageData findProjectRecheck_hole_amount(PageData pd) throws Exception{
        return (PageData)dao.findForObject("PorePlateMapper.findProjectRecheck_hole_amount", pd);
    }

    @Override
    public void savePorePlateRecord(PageData pd) throws Exception {
        dao.save("PorePlateMapper.savePorePlateRecord", pd);
    }

    @Override
    public PageData getPcrRecord(PageData pd) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.getPcrRecord", pd);
    }

    @Override
    public void saveAnalyzeRecord(PageData pd) throws Exception {
        dao.save("PorePlateMapper.saveAnalyzeRecord", pd);
    }

    @Override
    public List<PageData> findProjectCheckHoleList(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("PorePlateMapper.findProjectCheckHoleList", pd);
    }

    @Override
    public void entiretyPore(PageData pd) throws Exception {
        dao.update("PorePlateMapper.entiretyPore", pd);
    }

    @Override
    public void entiretyPoreType(PageData pd) throws Exception {
        dao.update("PorePlateMapper.entiretyPoreType", pd);
    }

    @Override
    public List<PageData> findSampleCourse(PageData pageData) throws Exception  {
        return (List<PageData>)dao.findForList("PorePlateMapper.findSampleCourse", pageData);
    }

    @Override
    public void completeAllFuHe(String[] arrayUSER_ids) throws Exception {
        dao.update("PorePlateMapper.completeAllFuHe", arrayUSER_ids);
    }

    @Override
    public List<PageData> selectRepeatNumber(PageData pageData) throws Exception {
        return (List<PageData>)dao.findForList("PorePlateMapper.selectRepeatNumber", pageData);
    }

    @Override
    public PageData setPorePlateName(PageData pd) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.setPorePlateName", pd);

    }

    @Override
    public void updateSample(PageData updateHoleType) throws Exception {
        dao.update("PorePlateMapper.updateSample", updateHoleType);
    }

    @Override
    public PageData getSampleid(PageData updateHoleType) throws Exception {
        return (PageData)dao.findForObject("PorePlateMapper.getSampleid", updateHoleType);

    }

    @Override
    public ArrayList getSampleidList(PageData pd) throws Exception{
        return (ArrayList)dao.findForList("PorePlateMapper.getSampleidList", pd);
    }

    @Override
    public void updateSampleCourseFor2(String[] split) throws Exception{
        dao.delete("PorePlateMapper.updateSampleCourseFor2", split);
    }

    @Override
    public void svarlims_pore_user(PageData pd) throws Exception {
        dao.save("PorePlateMapper.svarlims_pore_user", pd);
    }

    @Override
    public String dakongren(PageData pd) throws Exception {
        return (String)dao.findForObject("PorePlateMapper.dakongren", pd);
    }

    @Override
    public String selectQuality_from_ids(PageData pd) throws Exception {
        return (String)dao.findForObject("PorePlateMapper.selectQuality_from_ids", pd);
    }

    @Override
    public int saveInstrumentRecord(PageData pd) throws Exception {
        return (int)dao.save("PorePlateMapper.saveInstrumentRecord",pd);
    }

    @Override
    public void saveSampleList(List<PageData> pdSample)  throws Exception {
        dao.save("PorePlateMapper.saveSampleList",pdSample);
    }

    @Override
    public void savePorePlateDetailedList(List<PageData> porePlateList)throws Exception {
        dao.save("PorePlateMapper.savePorePlateDetailedList",porePlateList);
    }


    @Override
    public void updateHoleTypeList(List<PageData> updateHoleTypeList) throws Exception {
        dao.update("PorePlateMapper.updateHoleTypeList", updateHoleTypeList);
    }

    @Override
    public void updateSampleList(List<PageData> updateSampleList) throws Exception {
        dao.update("PorePlateMapper.updateSampleList", updateSampleList);
    }

    @Override
    public void saveUpdateRecordPorePlateList(List<PageData> updateRecordPorePlateList) throws Exception {
        dao.save("PorePlateMapper.saveUpdateRecordPorePlateList",updateRecordPorePlateList);
    }

    @Override
    public void updateSampleListForManyParam(List<PageData> sampleList) throws Exception {
        dao.update("PorePlateMapper.updateSampleListForManyParam",sampleList);
    }

    @Override
    public List<PageData> getSampleidAndHoltTypeId(List<PageData> updateHoleTypeList) throws Exception {
        return  (List)dao.findForList("PorePlateMapper.getSampleidAndHoltTypeId", updateHoleTypeList);
    }

    @Override
    public void updateSampleListForManyParamForId(List<PageData> sampleList) throws Exception {
        dao.update("PorePlateMapper.updateSampleListForManyParamForId",sampleList);
    }
}
