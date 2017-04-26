package com.library.bean;

import java.io.Serializable;

/**
 * 书架信息
 * 
 * @author 张航
 *
 */
public class ShelfBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String shelfId;
	private String shelfName;
	private String createTime;

	public ShelfBean(String shelfId, String shelfName, String createTime) {
		super();
		this.shelfId = shelfId;
		this.shelfName = shelfName;
		this.createTime = createTime;
	}

	public String getShelfId() {
		return shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	public String getShelfName() {
		return shelfName;
	}

	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ShelfBean [shelfId=" + shelfId + ", shelfName=" + shelfName
				+ ", createTime=" + createTime + "]";
	}

}
