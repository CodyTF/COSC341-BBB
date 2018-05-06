package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iup.cosc341.bbb.bo.Publisher;
import edu.iup.cosc341.bbb.bo.State;

/**
 * @author David T. Smith
 *
 * DAO from accessing Publishers from the Publisher table
 */
public class PublisherDao {

	/**
	 * Select all Publishers
	 * 
	 * @param conn SQL Connection
	 * @return List of all Publisher objects stored in the data base
	 * @throws SQLException
	 */
	public static List<Publisher> selectAll(Connection conn) throws SQLException {
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		PreparedStatement stmt = conn.prepareStatement("Select publisher from publisher");
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			publishers.add(new Publisher(rs.getString(1)));
		}
			rs.close();
			stmt.close();
			return publishers;
		
//		publishers.add(new Publisher("MS Press"));
//		publishers.add(new Publisher("Wiley"));
//		publishers.add(new Publisher("Other"));
	}

}
