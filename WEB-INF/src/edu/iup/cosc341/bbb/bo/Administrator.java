package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Administrator implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<AdminPhone> adminPhones = new ArrayList<AdminPhone>();
	private String userName = "";
	private String pin = "";
	private String firstName = "";
	private String lastName = "";
	private String address = "";
	private String city = "";
	private String state = "";
	private long zipcode = 0;
	private Date dateHired = new Date(System.currentTimeMillis());
	
	public Administrator() {
	}
	
	public Administrator (String userName, String pin, String firstName, String lastName, String address, String city, String state, Long zipcode) {
		this.userName = userName;
		this.pin = pin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pin = pin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	public int getAdminPhonesSize() {
		return adminPhones.size();
	}

	public AdminPhone getAdminPhone(int index) {
		return (AdminPhone) adminPhones.get(index);
	}

	public void addAdminPhone(AdminPhone adminPhone) {
		adminPhones.add(adminPhone);
	}

	public void removeAdminPhone(AdminPhone adminPhone) {
		adminPhones.remove(adminPhone);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		userName = value;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String value) {
		pin = value;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String value) {
		firstName = value;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		lastName = value;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String value) {
		address = value;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String value) {
		city = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String value) {
		state = value;
	}

	public long getZipcode() {
		return zipcode;
	}

	public void setZipcode(long value) {
		zipcode = value;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date value) {
		dateHired = value;
	}

	public ListIterator<AdminPhone> getAdminPhones() {
		return adminPhones.listIterator();
	}
}
