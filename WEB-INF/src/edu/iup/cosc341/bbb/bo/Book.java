package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<AuthorBook> authorBooks = new ArrayList<AuthorBook>();
	private List<Review> reviews = new ArrayList<Review>();
	private String isbn = "";
	private String title = "";
	private String publisher = "";
	private long yearPublished = 0;
	private String category = "";
	private double price = 0.0;
	private long onHand = 0;
	private long orderPoint = 0;
	private boolean active = false;
	
	public Book () {
		
	}

	public Book(String isbn, String title, String publisher, long yearPublished, String category, double price, long onHand, long orderPoint, String active) {
		this.isbn = isbn;
		this.title = title;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.category = category;
		this.price = price;
		this.onHand = onHand;
		this.orderPoint = orderPoint;
		if(active.equals("T")) this.active = true;
	}
	
	public ListIterator<AuthorBook> getAuthorBooks() {
		return authorBooks.listIterator();
	}

	public int getAuthorBooksSize() {
		return authorBooks.size();
	}

	public AuthorBook getAuthorBook(int index) {
		return (AuthorBook) authorBooks.get(index);
	}

	public void addAuthorBook(AuthorBook authorBook) {
		authorBooks.add(authorBook);
	}

	public void removeAuthorBook(AuthorBook authorBook) {
		authorBooks.remove(authorBook);
	}

	public ListIterator<Review> getReviews() {
		return reviews.listIterator();
	}

	public int getReviewsSize() {
		return reviews.size();
	}

	public Review getReview(int index) {
		return (Review) reviews.get(index);
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	public void removeReview(Review review) {
		reviews.remove(review);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String value) {
		isbn = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		title = value;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String value) {
		publisher = value;
	}

	public long getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(long value) {
		yearPublished = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String value) {
		category = value;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double value) {
		price = value;
	}

	public long getOnHand() {
		return onHand;
	}

	public void setOnHand(long value) {
		onHand = value;
	}

	public long getOrderPoint() {
		return orderPoint;
	}

	public void setOrderPoint(long value) {
		orderPoint = value;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean value) {
		active = value;
	}
	
	public String getAuthorString() {
		StringBuffer sb = new StringBuffer();
		for (ListIterator<AuthorBook> iter = getAuthorBooks(); iter.hasNext(); ) {
			AuthorBook authorBook = iter.next();
			if (sb.length() != 0) {
				if (iter.nextIndex() == getAuthorBooksSize()) {
					if (iter.nextIndex() == 2) {
						sb.append(" and ");
					} else {
						sb.append(", and ");
					}
				} else {
					sb.append(", ");
				}
			}
		    if (authorBook.getFirstName().length() == 0) {
		    	sb.append(authorBook.getLastName());
		    } else if (authorBook.getLastName().length() == 0) {
		    	sb.append(authorBook.getFirstName());
		    } else {
		    	sb.append(authorBook.getFirstName());
		    	sb.append(" ");
		    	sb.append(authorBook.getLastName());	
		    }
		}
		
		return sb.toString();
	}
	
	public boolean equals(Object otherBook) {
		return otherBook instanceof Book && getIsbn().equals(((Book) otherBook).getIsbn());
	}

}
