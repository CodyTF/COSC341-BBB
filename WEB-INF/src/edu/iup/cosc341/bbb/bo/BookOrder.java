package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BookOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	private String userName = "";
	private Timestamp orderDate = new Timestamp(System.currentTimeMillis());
	private String shipToFirstName = "";
	private String shipToLastName = "";
	private String shipToAddress = "";
	private String shipToCity = "";
	private String shipToState = "";
	private long shipToZipcode = 0;
	private String creditCardType = "";
	private long creditCardNumber = 0;
	private Date creditCardExprDate = new Date(System.currentTimeMillis());
	private double subtotal = 0.0;
	private double shippingHandling = 0.0;
	private double total = 0.0;

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public int getOrderItemsSize() {
		return orderItems.size();
	}

	public OrderItem getOrderItem(int index) {
		return (OrderItem) orderItems.get(index);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItem.setBookOrder(this);
		orderItems.add(orderItem);
		recalculateTotals();
	}

	public void removeOrderItem(OrderItem orderItem) {
		orderItems.remove(orderItem);
		recalculateTotals();
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

	public String getShipToFirstName() {
		return shipToFirstName;
	}

	public void setShipToFirstName(String value) {
		shipToFirstName = value;
	}

	public String getShipToLastName() {
		return shipToLastName;
	}

	public void setShipToLastName(String value) {
		shipToLastName = value;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String value) {
		shipToAddress = value;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String value) {
		shipToCity = value;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToState(String value) {
		shipToState = value;
	}

	public long getShipToZipcode() {
		return shipToZipcode;
	}

	public void setShipToZipcode(long value) {
		shipToZipcode = value;
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

	public double getSubtotal() {
		return subtotal;
	}

	public String getSubtotalString() {
		return String.format("%.2f", subtotal);
	}

	public void setSubtotal(double value) {
		subtotal = value;
	}

	public double getShippingHandling() {
		return shippingHandling;
	}
	
	public String getShippingHandlingString() {
		return String.format("%.2f", shippingHandling);
	}

	public void setShippingHandling(double value) {
		shippingHandling = value;
	}

	public double getTotal() {
		return total;
	}
	
	public String getTotalString() {
		return String.format("%.2f", total);
	}

	public void setTotal(double value) {
		total = value;
	}

	public int getNumberItems() {
		int no = 0;
		
		for (OrderItem item : getOrderItems()) {
			no += item.getQuantityOrdered();
		}
		
		return no;
	}
	
	public void recalculateTotals() {
		int no = 0;
		double subtotal = 0.0;
		for (OrderItem item : getOrderItems()) {
			no += item.getQuantityOrdered();
			subtotal += item.getItemTotal(); 
		}
		setSubtotal(subtotal);
		setShippingHandling(no * 2.0);
		setTotal(getSubtotal() + getShippingHandling());		
	}
	
	public void setCustomer(Customer customer) {
		setUserName(customer.getUserName());
		setShipToFirstName(customer.getFirstName());
		setShipToLastName(customer.getLastName());
		setShipToAddress(customer.getAddress());
		setShipToCity(customer.getCity());
		setShipToState(customer.getState());
		setShipToZipcode(customer.getZipcode());
		setShipToFirstName(customer.getFirstName());
		setShipToFirstName(customer.getFirstName());
		setCreditCardType(customer.getCreditCardType());
		setCreditCardNumber(customer.getCreditCardNumber());
		setCreditCardExprDate(customer.getCreditCardExprDate());
	}
}
