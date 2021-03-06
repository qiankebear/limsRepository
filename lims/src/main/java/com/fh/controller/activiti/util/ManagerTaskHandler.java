package com.fh.controller.activiti.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.session.Session;

import com.fh.util.Jurisdiction;

/** 
 * 名称：指定下一任务待办人
 * @author ：FH Admin fh313596790qq(青苔)
 * @date：2018年2月4日
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		Session session = Jurisdiction.getSession();
		// 任务ID
		session.setAttribute("TASKID", delegateTask.getId());
		// 默认待办人
		session.setAttribute("YAssignee", delegateTask.getAssignee());
	}

}
