package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	private String category = "";

	public Category() {
	}

	public Category(String category) {
		super();
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String value) {
		category = value;
	}
	
	public String toString() {
		return getCategory();
	}

}
