package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class Review implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isbn = "";
	private long reviewNumber = 0;
	private String review = "";


	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String value) {
		isbn = value;
	}

	public long getReviewNumber() {
		return reviewNumber;
	}

	public void setReviewNumber(long value) {
		reviewNumber = value;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String value) {
		review = value;
	}
}
