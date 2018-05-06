package edu.iup.cosc341.bbb.bo;

public class CategoryBuyers extends Category {
	private static final long serialVersionUID = 1L;
	private long buyers = 0;

	public long getBuyers() {
		return buyers;
	}

	public void setBuyers(long value) {
		buyers = value;
	}
}
