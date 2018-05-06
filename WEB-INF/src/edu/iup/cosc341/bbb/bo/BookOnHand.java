package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class BookOnHand implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isbn = "";
	private long onHand = 0;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String value) {
		isbn = value;
	}

	public long getOnHand() {
		return onHand;
	}

	public void setOnHand(long value) {
		onHand = value;
	}
}
