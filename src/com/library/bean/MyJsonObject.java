package com.library.bean;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 用来返回Json格式数据
 * 
 * @author 张航
 * 
 */
public class MyJsonObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private int status;
	private String message;
	private Object data;

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status);
		jsonObject.put("message", message);
		jsonObject.put("data", data);
		return jsonObject.toString();
	}
}
