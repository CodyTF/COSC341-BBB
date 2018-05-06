package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.iup.cosc341.bbb.bo.RestockOrder;

/**
 * @author David T. Smith
 *
 * DAO for inserting Restock Orders into the Restock Order table.  
 */
public class RestockOrderDao {

	/**
	 * Select count by ISBN
	 * 
	 * @param conn SQL Connection
	 * @param restockOrder RestockOrder to be inserted into the database
	 * @throws SQLException
	 */
	public static void insert(Connection conn, RestockOrder restockOrder) throws SQLException {
		String sql = "Insert into restock_order values('" + restockOrder.getUserName() + "', '" + restockOrder.getIsbn() 
					+ "', " + restockOrder.getOrderNumber() + ", " + restockOrder.getQuantityOrdered() + ")";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.executeQuery(); 
		stmt.close();
		
		System.out.println("Restock Order Placed" + 
		           			restockOrder.getUserName() + " " +
							restockOrder.getOrderNumber() + " " +
							restockOrder.getIsbn() + " " +
						   	restockOrder.getQuantityOrdered());
	}
}
