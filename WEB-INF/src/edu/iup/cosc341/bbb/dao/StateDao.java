package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iup.cosc341.bbb.bo.State;

/**
 * @author Cody Fetterman
 * 
 * DAO from accessing States from the State table
 */
public class StateDao {

	/**
	 * Select all States
	 * 
	 * @param conn
	 *            SQL Connection
	 * @return List of all State objects stored in the data base
	 * @throws SQLException
	 */
	public static List<State> selectAll(Connection conn) throws SQLException {
		ArrayList<State> states = new ArrayList<State>(52);
		
		PreparedStatement stmt = conn.prepareStatement("Select state_code, state_name from state");
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			states.add(new State(rs.getString(1), rs.getString(2)));
		}
			rs.close();
			stmt.close();
			return states;
	}

}
