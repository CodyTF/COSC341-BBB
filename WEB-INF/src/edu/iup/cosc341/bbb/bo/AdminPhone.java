package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class AdminPhone implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName = "";
	private long phoneNumber = 0;
	
	public AdminPhone() {
		
	}
	
	public AdminPhone(String userName, long phoneNumber) {
		this.userName = userName;
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		userName = value;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long value) {
		phoneNumber = value;
	}
}
