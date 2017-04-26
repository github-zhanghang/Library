package com.library.bean;

import java.io.Serializable;

/**
 * 分类信息
 * 
 * @author 张航
 * 
 */
public class TypeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String typeId;
	private String typeName;
	private String createTime;

	public TypeBean(String typeId, String typeName, String createTime) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
		this.createTime = createTime;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TypeBean [typeId=" + typeId + ", typeName=" + typeName
				+ ", createTime=" + createTime + "]";
	}
}
