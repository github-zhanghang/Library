package com.library.bean;

import java.io.Serializable;

public class ReaderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String readerId;
	private String readerAccount;
	private String readerPassword;
	private String readerPhone;
	private String readerName;
	private String registerTime;
	private boolean isPermitted;
	private boolean isEnable;

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public String getReaderAccount() {
		return readerAccount;
	}

	public void setReaderAccount(String readerAccount) {
		this.readerAccount = readerAccount;
	}

	public String getReaderPassword() {
		return readerPassword;
	}

	public void setReaderPassword(String readerPassword) {
		this.readerPassword = readerPassword;
	}

	public String getReaderPhone() {
		return readerPhone;
	}

	public void setReaderPhone(String readerPhone) {
		this.readerPhone = readerPhone;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public boolean isPermitted() {
		return isPermitted;
	}

	public void setPermitted(boolean isPermitted) {
		this.isPermitted = isPermitted;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public ReaderBean(String readerId, String readerAccount,
			String readerPassword, String readerPhone, String readerName,
			String registerTime, boolean isPermitted, boolean isEnable) {
		super();
		this.readerId = readerId;
		this.readerAccount = readerAccount;
		this.readerPassword = readerPassword;
		this.readerPhone = readerPhone;
		this.readerName = readerName;
		this.registerTime = registerTime;
		this.isPermitted = isPermitted;
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "ReaderBean [readerId=" + readerId + ", readerAccount="
				+ readerAccount + ", readerPassword=" + readerPassword
				+ ", readerPhone=" + readerPhone + ", readerName=" + readerName
				+ ", registerTime=" + registerTime + ", isPermitted="
				+ isPermitted + ", isEnable=" + isEnable + "]";
	}

}
