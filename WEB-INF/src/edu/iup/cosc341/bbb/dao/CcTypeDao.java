package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iup.cosc341.bbb.bo.CcType;

/**
 * @author David T. Smith
 *
 * DAO from accessing CcTypes from the CcType table
 */
public class CcTypeDao {

	/**
	 * Select all CcTypess
	 * 
	 * @param conn SQL Connection
	 * @return List of all CcTypes objects stored in the data base
	 * @throws SQLException
	 */
	public static List<CcType> selectAll(Connection conn) throws SQLException {
		ArrayList<CcType> ccTypes = new ArrayList<CcType>();
		
		ccTypes.add(new CcType("MasterCard"));
		ccTypes.add(new CcType("Visa"));
		
		return ccTypes;	
	}

}
