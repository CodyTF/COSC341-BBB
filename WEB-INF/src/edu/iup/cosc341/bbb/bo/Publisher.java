package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class Publisher implements Serializable{
	private static final long serialVersionUID = 1L;
	private String publisher = "";

	public Publisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String value) {
		publisher = value;
	}
	
	public String toString() {
		return getPublisher();
	}
}
