package edu.iup.cosc341.bbb.bo;

public class BookSales extends Book {
	private static final long serialVersionUID = 1L;
	private double sales = 0.0;

	public double getSales() {
		return sales;
	}

	public void setSales(double value) {
		sales = value;
	}
}
