package edu.iup.cosc341.bbb.bo;

public class CategoryStock extends Category {
	private static final long serialVersionUID = 1L;
	private long stock = 0;

	public long getStock() {
		return stock;
	}

	public void setStock(long value) {
		stock = value;
	}
}
