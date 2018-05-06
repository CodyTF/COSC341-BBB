package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;


import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.dao.BookDao;

public class OrderItem implements Serializable{
	private static final long serialVersionUID = 1L;
	private BookOrder bookOrder;
	private String userName = "";
	private Timestamp orderDate = new Timestamp(System.currentTimeMillis());
	private String isbn = "";
	private long quantityOrdered = 0;
	private double priceAtPurchase = 0.0;
	private String title = "";
	private String authorString = "";
	private String publisher = "";
	private long yearPublished = 0;
	private String category = "";

	public void setBookOrder(BookOrder bookOrder) {
		this.bookOrder = bookOrder;
	}
	
	public OrderItem(String isbn) {
		setIsbn(isbn);
		setQuantityOrdered(1);
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		userName = value;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp value) {
		orderDate = value;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String value) {
		isbn = value;
		lookupInfo();
	}

	public long getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(long value) {
		quantityOrdered = value;
		if (bookOrder != null) {
			bookOrder.recalculateTotals();
		}
	}

	public double getPriceAtPurchase() {
		return priceAtPurchase;
	}

	public void setPriceAtPurchase(double value) {
		priceAtPurchase = value;
	}
	
	private void lookupInfo() {
		
		Connection conn = null;
		
		try {
			conn = ConnectionPool.getConnection();
			Book book = BookDao.selectByIsbn(conn, getIsbn());
			
			if (book != null) {
				setTitle(book.getTitle());
				setAuthorString(book.getAuthorString());
				setCategory(book.getCategory());
				setPublisher(book.getPublisher());
				setYearPublished(book.getYearPublished());
				setPriceAtPurchase(book.getPrice());
				setQuantityOrdered(1);
			}
		} catch (SQLException e) {			
		} finally {
			try {
				ConnectionPool.returnConnection(conn);
			} catch (SQLException e) {
			}
		}
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		title = value;
	}

	public String getAuthorString() {
		return authorString;
	}

	public void setAuthorString(String value) {
		authorString = value;
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

	public String getCategoryField() {
		return category;
	}

	public double getItemTotal() {
		return getPriceAtPurchase() * getQuantityOrdered();
	}
	
	public String getItemTotalString() {
		return String.format("%.2f", getPriceAtPurchase() * getQuantityOrdered());
	}

}
