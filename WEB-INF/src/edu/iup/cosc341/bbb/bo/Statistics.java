package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;
import java.sql.Date;

public class Statistics implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date month;
	private double averageSalePerCustomer;
	private double averageNoBooksPerOrder;
	private long   averageNoCustomersPerDay;
	
	public double getAverageNoBooksPerOrder() {
		return averageNoBooksPerOrder;
	}
	public void setAverageNoBooksPerOrder(double averageNoBooksPerOrder) {
		this.averageNoBooksPerOrder = averageNoBooksPerOrder;
	}
	public long getAverageNoCustomersPerDay() {
		return averageNoCustomersPerDay;
	}
	public void setAverageNoCustomersPerDay(long averageNoCustomersPerDay) {
		this.averageNoCustomersPerDay = averageNoCustomersPerDay;
	}
	public double getAverageSalePerCustomer() {
		return averageSalePerCustomer;
	}
	public void setAverageSalePerCustomer(double averageSalePerCustomer) {
		this.averageSalePerCustomer = averageSalePerCustomer;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	
	
}
