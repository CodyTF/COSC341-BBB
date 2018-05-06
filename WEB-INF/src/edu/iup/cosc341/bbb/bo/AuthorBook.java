package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class AuthorBook implements Serializable {
	private static final long serialVersionUID = 1L;
	private String firstName = "";
	private String lastName = "";
	private String isbn = "";

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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String value) {
		isbn = value;
	}
}
