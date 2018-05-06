package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iup.cosc341.bbb.bo.Category;

/**
 * @author David T. Smith
 *
 * DAO from accessing Categories from the Category table
 */
public class CategoryDao {

	/**
	 * Select all Categories
	 * 
	 * @param conn SQL Connection
	 * @return List of all Category objects stored in the data base
	 * @throws SQLException
	 */
	public static List<Category> selectAll(Connection conn) throws SQLException {
		ArrayList<Category> categories = new ArrayList<Category>();
		
		categories.add(new Category("Drama"));
		categories.add(new Category("Action"));
		
		return categories;	
	}

}
