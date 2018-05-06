package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import edu.iup.cosc341.bbb.bo.Author;

/**
 * @author David T. Smith
 *
 * DAO from inserting Authors in the Author table.  
 */
public class AuthorDao {

	/**
	 * Insert Author
	 * 
	 * @param conn SQL Connection
	 * @param author the Author to be inserted into the database
	 * @throws SQLException
	 */
	public static void insert(Connection conn, Author author) throws SQLException {
		System.out.println("Author Added " + 
				author.getFirstName() + " " +
				author.getLastName());
	}	
}
