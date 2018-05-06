package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.iup.cosc341.bbb.bo.AdminPhone;
import edu.iup.cosc341.bbb.bo.Administrator;
import edu.iup.cosc341.bbb.bo.Book;

/**
 * @author Cody Fetterman
 *
 * DAO from accessing/updating Administrators in the Administrator table. 
 */
public class AdministratorDao {
	static Administrator admin;
	 
//	 static {
//	 	admin.setUserName("admin");
//	 	admin.setPin("9999");
//	 	admin.setFirstName("Joe");
//	 	admin.setLastName("Admin");
//	 	admin.setAddress("500 Main");
//	 	admin.setCity("Indiana");
//	 	admin.setState("PA");
//	 	admin.setZipcode(15701);
//	 	admin.setDateHired(new Date(System.currentTimeMillis()));
//	 	
//	 	AdminPhone phone = new AdminPhone();
//	 	phone.setPhoneNumber(9995551234l);
//	 	
//	 	admin.addAdminPhone(phone);
//	 	
//	 }

	/**
	 * Select Administrator by UserName and Pin
	 * 
	 * @param conn SQL Connection
	 * @param userName user name to be used in selecting the Administrator
	 * @param pin pin to be used in selecting the Administrator
	 * @return Customer objects of the given UserName and Pin
	 * @throws SQLException
	 */
	public static Administrator selectByUserNamePin(Connection conn, String userName, String pin) throws SQLException {
		ArrayList<Administrator> records = new ArrayList<Administrator>();
		PreparedStatement stmt = conn.prepareStatement("Select user_name, pin, first_name, last_name, address, city, state, zipcode from administrator");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
		    records.add(new Administrator(rs.getString(1), rs.getString(2), 
		    		rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), 
		    		rs.getString(7), rs.getLong(8)));
		}
		
		for (int i = 0; i < records.size(); i++) {
			admin = records.get(i);
			selectPhonesForAdministrator(conn, admin);
			if (admin.getUserName().equals(userName) && admin.getPin().equals(pin)) {
				rs.close();
				stmt.close();
				return admin;	
			}
		}
			rs.close();
			stmt.close();
			return null;
	}
	
	
	/**
	 * Update Administrator
	 * 
	 * @param conn SQL Connection
	 * @param administrator the Administrator to be updated in the database
	 * @throws SQLException
	 */
	public static void update(Connection conn, Administrator admin) throws SQLException {
		 AdministratorDao.admin = admin;
		 String sql = "Update administrator set first_name='" + admin.getFirstName() + "', last_name='" + admin.getLastName() +
				 "', address='" + admin.getAddress() + "', city='" + admin.getCity() + "', state='" + admin.getState() + "' where user_name='" + admin.getUserName() + "'";
		 insertPhonesForAdministrator(conn, admin);
		 deletePhonesForAdministrator(conn, admin);
		 PreparedStatement stmt = conn.prepareStatement(sql);
		 stmt.executeQuery();
		 stmt.close();
	}
	
	/**
	 * Select Phones for an Administrator. The phones are added to the Administrators's 
	 * list of Phones
	 * 
	 * @param conn SQL Connection
	 * @param administrator the Administrator to be updated in the database
	 * @throws SQLException
	 */
	private static void selectPhonesForAdministrator(Connection conn, Administrator administrator) throws SQLException {
		ArrayList<AdminPhone> adminPhones = new ArrayList<AdminPhone>();
		PreparedStatement stmt = conn.prepareStatement("Select user_name, phone_number from admin_phone where user_name='" + administrator.getUserName() + "'");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			adminPhones.add(new AdminPhone (rs.getString(1), rs.getLong(2)));
		}
		for(int i = 0; i < adminPhones.size(); i++) {
			admin.addAdminPhone(adminPhones.get(i));
		}
		rs.close();
		stmt.close();
	}

	/**
	 * Delete phones for an Administrator.  
	 * 
	 * @param conn SQL Connection
	 * @param administrator the Administrator for which phones will be selected
	 * @throws SQLException
	 */
	private static void deletePhonesForAdministrator(Connection conn, Administrator administrator) throws SQLException {
		String delete = "Delete from admin_phone where user_name = '" + administrator.getUserName() + "'";
		String restore = ""; 
		PreparedStatement stmt = conn.prepareStatement(delete);
		stmt.executeQuery();
		
		for(int i = 0; i < administrator.getAdminPhonesSize(); i++) {
			restore = "Insert into admin_phone values('" + administrator.getUserName() + "', " + administrator.getAdminPhone(i).getPhoneNumber() + ")";
			stmt = conn.prepareStatement(restore);
			stmt.executeQuery();
		}
		
		stmt.close();
	}

	/**
	 * Insert phones of a Administrator into the database.  
	 * 
	 * @param conn SQL Connection
	 * @param administrator the Administrator for which phones will be inserted into the database
	 * @throws SQLException
	 */
	private static void insertPhonesForAdministrator(Connection conn, Administrator administrator) throws SQLException {
		String delete = "Delete from admin_phone where user_name = '" + administrator.getUserName() + "'";
		String restore = ""; 
		PreparedStatement stmt = conn.prepareStatement(delete);
		stmt.executeQuery();
		
		for(int i = 0; i < administrator.getAdminPhonesSize(); i++) {
			restore = "Insert into admin_phone values('" + administrator.getUserName() + "', " + administrator.getAdminPhone(i).getPhoneNumber() + ")";
			stmt = conn.prepareStatement(restore);
			stmt.executeQuery();
		}
		
		stmt.close();
	}

}
   