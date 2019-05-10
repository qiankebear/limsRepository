package com.fh.entity.weixin;

/** 二级菜单
 * @author FH
 * Q: 3 13596 79 0
 * 2016.11.1
 */
public class CommonButton extends Button{
	/**
	 * 菜单类型
	 */
	private String type;
	/**
	 * key值
	 */
	private String key;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
