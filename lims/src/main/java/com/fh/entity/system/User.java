package com.fh.entity.system;

import com.fh.entity.Page;

/**
 * 
* 类名称：用户
* 类描述： 
* @author FH QQ 313596790[青苔]
* 作者单位： 
* 联系方式：
* 创建时间：2014年6月28日
* @version 1.0
 *
 */
public class User {
	/**
	 * 用户id
	 */
	private String USER_ID;
	/**
	 * 用户名
	 */
	private String USERNAME;
	/**
	 * 密码
	 */
	private String PASSWORD;
	/**
	 *姓名
	 */

	private String NAME;
	/**
	 * 权限
	 */
	private String RIGHTS;
	/**
	 * 角色id
	 */
	private String ROLE_ID;
	/**
	 * 副职角色id
	 */
	private String ROLE_IDS;
	/**
	 * 最后登录时间
	 */
	private String LAST_LOGIN;
	/**
	 * 用户登录ip地址
	 */
	private String IP;
	/**
	 * 状态
	 */
	private String STATUS;
	/**
	 * 角色对象
	 */
	private Role role;
	/**
	 * 分页对象
	 */
	private Page page;
	/**
	 * 皮肤
	 */
	private String SKIN;

	/**
	 * 在职状态(1.在职 2.离职)
	 */
	private int  position_status;
	/**
	 * 访问次数
	 */
	private int visit_times;
	/**
	 * 职位类别(1.法医 2.临床 )
	 */
	private int prositon_category;


	
	public String getSKIN() {
		return SKIN;
	}
	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}
	
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_IDS() {
		return ROLE_IDS;
	}
	public void setROLE_IDS(String rOLE_IDS) {
		ROLE_IDS = rOLE_IDS;
	}
	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}
	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Page getPage() {
		if (page == null) {
			page = new Page();
		}
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	public int getPosition_status() {
		return position_status;
	}

	public void setPosition_status(int position_status) {
		this.position_status = position_status;
	}

	public int getVisit_times() {
		return visit_times;
	}

	public void setVisit_times(int visit_times) {
		this.visit_times = visit_times;
	}

	public int getPrositon_category() {
		return prositon_category;
	}

	public void setPrositon_category(int prositon_category) {
		this.prositon_category = prositon_category;
	}
}
