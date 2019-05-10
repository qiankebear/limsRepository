package com.fh.controller.fhdb.timingbackup;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fh.controller.base.BaseController;
import com.fh.service.fhdb.brdb.impl.BRdbService;
import com.fh.service.fhdb.timingbackup.impl.TimingBackUpService;
import com.fh.util.DbFH;
import com.fh.util.FileUtil;
import com.fh.util.PageData;
import com.fh.util.Tools;

/** quartz 定时任务调度 数据库自动备份工作域
 * @author FH 3 13 59679 0 Q
 * @date 2016-4-10
 * @version 1.0
 * @paramFHDB_ID          主键
 * @paramUSERNAME    操作用户
 * @paramBACKUP_TIME    备份时间
 * @paramTABLENAME    表名or整库
 * @paramSQLPATH    存储位置
 * @paramDBSIZE    文件大小
 * @paramTYPE    1: 备份整库，2：备份某表
 * @paramBZ    备注
 * @update :修正不规范注释，处理捕获异常未处理问题，处理可能引发空指针问题
 */
public class DbBackupQuartzJob extends BaseController implements Job{

	@Override
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		//获取参数
		Map<String, Object> parameter = (Map<String, Object>)dataMap.get("parameterList");
		String TABLENAME = parameter.get("TABLENAME").toString();
		TABLENAME = "all".equals(TABLENAME)?"":TABLENAME;

		//普通类从spring容器中拿出service
		WebApplicationContext webctx=ContextLoader.getCurrentWebApplicationContext();
		BRdbService brdbService = (BRdbService)webctx.getBean("brdbService");
		PageData pd = new PageData();
		try {
			//调用数据库备份
			String kackupPath = DbFH.getDbFH().backup(TABLENAME).toString();
			if(Tools.notEmpty(kackupPath) && !"errer".equals(kackupPath)){
				pd.put("FHDB_ID", this.get32UUID());
				pd.put("USERNAME", "系统");
				pd.put("BACKUP_TIME", Tools.date2Str(new Date()));
				pd.put("TABLENAME", "".equals(TABLENAME)?"整库":TABLENAME);
				pd.put("SQLPATH", kackupPath);
				pd.put("DBSIZE", FileUtil.getFilesize(kackupPath));
				pd.put("TYPE", "".equals(TABLENAME)?1:2);
				pd.put("BZ", "定时备份操作");
				brdbService.save(pd);
			}else{
				shutdownJob(context, pd, parameter, webctx);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			try {
				shutdownJob(context, pd, parameter, webctx);
			} catch (Exception e1) {
				//捕获异常未处理
				logger.error(e1.toString(), e1);
			}
		}
	}

	/**把定时备份任务状态改为关闭
	 * @param pd
	 * @param parameter
	 * @param webctx
	 */
	public void shutdownJob(JobExecutionContext context, PageData pd, Map<String, Object> parameter, WebApplicationContext webctx){
		try {
			//备份异常时关闭任务
			context.getScheduler().shutdown();
			TimingBackUpService timingbackupService = (TimingBackUpService)webctx.getBean("timingbackupService");
			//改变定时运行状态为2，关闭
			pd.put("STATUS", 2);
			//定时备份ID
			pd.put("TIMINGBACKUP_ID", parameter.get("TIMINGBACKUP_ID").toString());
			timingbackupService.changeStatus(pd);
		} catch (Exception e) {
			//e.printStackTrace();  捕获异常未处理
			logger.error(e.toString(), e);
		}
	}

}
