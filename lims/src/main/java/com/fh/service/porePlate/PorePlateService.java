package com.fh.service.porePlate;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public interface PorePlateService {

    /**
     * 分页方法
     *
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> porePlatelistPage(Page page) throws Exception;

    /**
     * 保存
     *
     * @param pd
     */
    int savePorePlate(PageData pd) throws Exception;


    /**
     * x修改查询信息
     *
     * @param pd
     * @return
     * @throws Exception
     */
    PageData goEditPorePlate(PageData pd) throws Exception;

    /**
     * 修改
     *
     * @param
     * @throws Exception
     */
    void editPorePlate(PageData pd) throws Exception;

    /**
     * 删除
     *
     * @param pd
     * @throws Exception
     */
    void deletePorePlate(PageData pd) throws Exception;

    /**
     * 批量删除
     *
     * @param arrayUSER_ids
     * @throws Exception
     */
    void deleteAllPorePlate(String[] arrayUSER_ids) throws Exception;

    /**
     * @param pd
     * @return
     * @throws Exception
     */
    PageData findSample(PageData pd) throws Exception;

    /**
     * @param pageData
     * @return
     * @throws Exception
     */
    PageData findSampleAndPoreName(PageData pageData) throws Exception;

    /**
     * 查询所属项目
     *
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> listProject(PageData pd) throws Exception;

    /**
     * 保存打孔信息
     *
     * @param pd
     * @throws Exception
     */
    void savePorePlateDetailed(PageData pd) throws Exception;

    /**
     * 保存打孔信息样本
     *
     * @param pd
     * @return
     * @throws Exception
     */
    int saveSample(PageData pd) throws Exception;


    /**
     * 修改完成布板页面查询
     *
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> findPorePlateList(PageData pd) throws Exception;

    /**
     * 查询出所有sampleid
     *
     * @param pd
     * @return
     */
    List<PageData> findAllSampleId(PageData pd) throws Exception;

    /**
     * 删除所有sampleid
     *
     * @param
     * @return
     */
    void deleteAllSample(String[] arrayUSER_ids) throws Exception;

    void deleteAllHoleType(PageData pd) throws Exception;


    /**
     * 保存更新记录
     *
     * @param updateRecordPorePlate
     */
    void saveUpdateRecordPorePlate(PageData updateRecordPorePlate) throws Exception;

    /**
     * 修改孔板状态
     *
     * @param pd
     */
    void completeOther(PageData pd) throws Exception;

    /**
     * 修改孔板复合通过状态
     *
     * @param pd
     */
    void completeOtherFuhe(PageData pd)throws Exception;

    /**执行更新 holetype
     *
     * @param updateHoleType
     * @throws Exception
     */
    void updateHoleType(PageData updateHoleType) throws Exception;

    /**
     *获取项目修改权限
     * @param
     * @throws Exception
     */
    PageData getProjectPermission(PageData pd) throws Exception;


    /**
     *
     * 查询项目进程
     * @param pd
     * @return
     * @throws Exception
     */
    PageData selectPoreCurrentProcedure(PageData pd) throws Exception;


    /**
     * 查询pcr扩增记录
     *
     * @param pd
     * @return
     */
    PageData selectPcr_record(PageData pd) throws Exception;


    /**
     * 保存pcr扩增记录
     * @param pd
     */
    void savePcr_record(PageData pd) throws Exception;

    /**
     * 更新pcr扩增记录
     * @param pd
     */
    void updatePcr_record(PageData pd) throws Exception;

    /**
     * 查询kit扩增记录
     *
     * @param kitRecord
     * @return
     */
    PageData selectKit_record(PageData kitRecord) throws Exception;

    /**
     * 保存kit记录
     * @param pd
     */
    void saveKit_record(PageData pd) throws Exception;

    /**
     * 更新kit记录
     * @param pd
     */
    void updateKit_record(PageData pd) throws Exception;


    /**
     * 查询项目复核孔个数
     *
     * @param pd
     * @return
     */
    PageData findProjectRecheck_hole_amount(PageData pd) throws Exception;

    /**
     * 保存打孔记录
     *
     * @param pd
     * @return
     */
    void savePorePlateRecord(PageData pd) throws Exception;

    /**
     * 查询pcr扩增记录
     *
     * @param pd
     * @return
     */
    PageData getPcrRecord(PageData pd) throws Exception;


    /**
     * 保存分析记录
     *
     * @param pd
     * @return
     */
    void saveAnalyzeRecord(PageData pd) throws Exception;


    /**
     * 查询当前项目的复核孔lists
     *
     * @param pd
     * @return
     */
    List<PageData> findProjectCheckHoleList(PageData pd) throws Exception;


    /**
     * 整版重扩方法修改孔板
     *
     * @param pd
     */
    void entiretyPore(PageData pd)  throws Exception;

    /**
     * 整版重扩方法修改孔板类型
     * @param pd
     */
    void entiretyPoreType(PageData pd)  throws Exception;

    /**
     * 查找该项目的重做列表
     *
     * @param pageData
     * @return
     */
    List<PageData> findSampleCourse(PageData pageData) throws Exception ;

    void completeAllFuHe(String[] arrayUSER_ids) throws Exception ;


    /**
     * 查询重复的样本编号
     *
     * @param
     * @return
     * @throws Exception
     */
    List<PageData> selectRepeatNumber(PageData pageData) throws Exception ;

    /**
     * 查询项目缩写
     *
     * @param pd
     * @return
     */
    PageData setPorePlateName(PageData pd)throws Exception ;

    /**
     * 更新sample表样本进程
     *
     * @param
     * @return
     */
    void updateSample(PageData updateHoleType) throws Exception;

    /**
     * 根据holetypeid查找sampleid
     *
     * @param
     * @return
     */
    PageData getSampleid(PageData updateHoleType) throws Exception;

    /**
     * 根据hole_poreid查找sampleid
     *
     * @param
     * @return
     */
    ArrayList getSampleidList(PageData pd) throws Exception;

    /**
     * 修改样本进程为重做
     *
     * @param
     * @return
     */
    void updateSampleCourseFor2(String[] split) throws Exception;

    /**
     * 保存用户操作
     *
     * @param
     * @return
     */
    void svarlims_pore_user(PageData pd)  throws Exception;

    String dakongren(PageData pd) throws Exception;

    String selectQuality_from_ids(PageData pd) throws Exception;

    /**
     * 保存instrument记录
     * @param pd
     * @return
     * @throws Exception
     */
    int saveInstrumentRecord(PageData pd) throws Exception;

    /**
     * 批量保存sample
     * @param pd
     * @return
     * @throws Exception
     */
    void saveSampleList(List<PageData> pdSample)  throws Exception;

    /**
     * 批量保存PorePlate
     * @param pd
     * @return
     * @throws Exception
     */
    void savePorePlateDetailedList(List<PageData> porePlateList)throws Exception;

    /**
     * 批量更新HoleType
     * @param pd
     * @return
     * @throws Exception
     */
    void updateHoleTypeList(List<PageData> updateHoleTypeList) throws Exception;

    /**
     * 批量更新Sample
     * @param pd
     * @return
     * @throws Exception
     */
    void updateSampleList(List<PageData> updateSampleList) throws Exception;
    /**
     * 批量保存打孔记录
     * @param pd
     * @return
     * @throws Exception
     */
    void saveUpdateRecordPorePlateList(List<PageData> updateRecordPorePlateList) throws Exception;
    /**
     * 批量更新Sample多参数
     * @param pd
     * @return
     * @throws Exception
     */
    void updateSampleListForManyParam(List<PageData> sampleList)  throws Exception;
    /**
     * 获取sampleid和holttypeid
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> getSampleidAndHoltTypeId(List<PageData> updateHoleTypeList)  throws Exception;
    /**
     * 批量更新Sample多个参数 通过id
     * @param pd
     * @return
     * @throws Exception
     */
    void updateSampleListForManyParamForId(List<PageData> sampleList)throws Exception;
}
