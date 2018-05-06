package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName = "";
	private String pin ="";
	private String firstName = "";
	private String lastName = "";
	private String address = "";
	private String city = "";
	private String state = "";
	private long zipcode = 0;
	private String creditCardType = "";
	private long creditCardNumber = 0;
	private Date creditCardExprDate = new Date(System.currentTimeMillis());

	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		userName =value;
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

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String value) {
		creditCardType = value;
	}

	public long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(long value) {
		creditCardNumber = value;
	}

	public Date getCreditCardExprDate() {
		return creditCardExprDate;
	}

	public void setCreditCardExprDate(Date value) {
		creditCardExprDate = value;
	}
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
	
	public String getCreditCardExprDateFormatted(){
		return dateFormat.format(getCreditCardExprDate());	
	}
}
