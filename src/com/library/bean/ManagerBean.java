package com.library.bean;

import java.io.Serializable;

public class ManagerBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String managerId;
	private String managerAccount;
	private String managerPassword;
	private String managerName;
	private String managerPhone;
	private String managerDuty;
	private String registerTime;

	public ManagerBean(String managerId, String managerAccount,
			String managerPassword, String managerName, String managerPhone,
			String managerDuty, String registerTime) {
		super();
		this.managerId = managerId;
		this.managerAccount = managerAccount;
		this.managerPassword = managerPassword;
		this.managerName = managerName;
		this.managerPhone = managerPhone;
		this.managerDuty = managerDuty;
		this.registerTime = registerTime;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerAccount() {
		return managerAccount;
	}

	public void setManagerAccount(String managerAccount) {
		this.managerAccount = managerAccount;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getManagerDuty() {
		return managerDuty;
	}

	public void setManagerDuty(String managerDuty) {
		this.managerDuty = managerDuty;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public String toString() {
		return "ManagerBean [managerId=" + managerId + ", managerAccount="
				+ managerAccount + ", managerPassword=" + managerPassword
				+ ", managerName=" + managerName + ", managerPhone="
				+ managerPhone + ", managerDuty=" + managerDuty
				+ ", registerTime=" + registerTime + "]";
	}

}
