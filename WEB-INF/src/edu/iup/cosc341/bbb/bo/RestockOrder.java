package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class RestockOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName = "";
	private String isbn = "";
	private long orderNumber = 0;
	private long quantityOrdered = 0;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		userName = value;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String value) {
		isbn = value;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long value) {
		orderNumber = value;
	}

	public long getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(long value) {
		quantityOrdered = value;
	}
}
