package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.iup.cosc341.bbb.bo.BookOrder;

/**
 * @author Cody Fetterman
 *
 * DAO for inserting Book Orders into the Book Order table.  This DAO
 * will also insert the Order Items of a Book Order
 */
public class BookOrderDao {

	/**
	 * Insert an BookOrder into the database
	 * 
	 * @param conn SQL Connection
	 * @param bookOrder BookOrder to be inserted into the database
	 * @throws SQLException
	 */
	public static void insert(Connection conn, BookOrder bookOrder) throws SQLException {
		String sql = "Insert into book_order values('" + bookOrder.getUserName() + 
				"', " + bookOrder.getOrderDate() + ", " + System.currentTimeMillis() + 
				", '" + bookOrder.getShipToFirstName() + "', '" + bookOrder.getShipToLastName() +
				"', '" + bookOrder.getShipToAddress() + "', '" + bookOrder.getShipToCity() +
				"', '" + bookOrder.getShipToState() + "', " + bookOrder.getShipToZipcode() +
				", '" + bookOrder.getCreditCardType() + ", " + bookOrder.getCreditCardNumber() +
				", " + bookOrder.getCreditCardExprDate() + ", " + bookOrder.getSubtotal() + 
				", " + bookOrder.getShippingHandling() + ", " + bookOrder.getTotal() +")";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.executeQuery();
		stmt.close();
		System.out.println("Book Order Placed\n" + 
				bookOrder.getUserName() + " " +
				bookOrder.getOrderDate());
	}
	
	/**
	 * Insert an BookOrder into the database
	 * 
	 * @param conn SQL Connection
	 * @param bookOrder BookOrder to be inserted into the database
	 * @throws SQLException
	 */
	private static void insertOrderItemsForBookOrder(Connection conn, BookOrder bookOrder) throws SQLException {
	}
}
