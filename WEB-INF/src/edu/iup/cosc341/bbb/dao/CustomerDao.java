package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import edu.iup.cosc341.bbb.bo.Customer;

/**
 * @author David T. Smith
 *
 * DAO from accessing/updating Customers in the Customer table. 
 */
@SuppressWarnings("deprecation")
public class CustomerDao {
	static ArrayList<Customer> customers = new ArrayList<Customer>();
	 
	 static {
		Customer customer = new Customer();
	 	customer.setUserName("cust");
	 	customer.setPin("1111");
	 	customer.setFirstName("Sue");
	 	customer.setLastName("Customer");
	 	customer.setAddress("500 Main");
	 	customer.setCity("Indiana");
	 	customer.setState("PA");
	 	customer.setZipcode(15701);
	 	customer.setCreditCardType("Visa");
	 	customer.setCreditCardNumber(1234567891012l);
	 	customer.setCreditCardExprDate(new Date(2008 - 1900,1 - 1,1));
	 	
	 	customers.add(customer);
	 	
	 }

	/**
	 * Select count by User Name
	 * 
	 * @param conn SQL Connection
	 * @param userName user name to be used in selecting the count
	 * @return int count of Customer objects with the given user name stored in the data base
	 * @throws SQLException
	 */
	public static int selectCountByUserName(Connection conn, String userName) throws SQLException {
	    int n = 0;
	    
	    for (Iterator<Customer> iter = customers.iterator(); iter.hasNext(); ) {
	    	Customer customer = iter.next();
	    	
	    	if (customer.getUserName().equals(userName)) {
	    		n++;
	    	}
	    }
		return n;
	}
	
	/**
	 * Select Customer by UserName and Pin
	 * 
	 * @param conn SQL Connection
	 * @param userName user name to be used in selecting the Customer
	 * @param pin pin to be used in selecting the Customer
	 * @return Customer objects of the given UserName and Pin
	 * @throws SQLException
	 */
	public static Customer selectByUserNamePin(Connection conn, String userName, String pin) throws SQLException {
	    for (Iterator<Customer> iter = customers.iterator(); iter.hasNext(); ) {
	    	Customer customer = iter.next();
	    	
	    	if (customer.getUserName().equals(userName) && customer.getPin().equals(pin)) {
	    		return customer;
	    	}
	    }
		return null;
	}
	
	/**
	 * Insert a new Customer
	 * 
	 * @param conn SQL Connection
	 * @param customer the Customer to be inserted into the database
	 * @throws SQLException
	 */
	public static void insert(Connection conn, Customer customer) throws SQLException {
		customers.add(customer);
	}
	
	/**
	 * Update Customer
	 * 
	 * @param conn SQL Connection
	 * @param customer the Customer to be updated in the database
	 * @throws SQLException
	 */
	public static void update(Connection conn, Customer customer) throws SQLException {
	    for (ListIterator<Customer> iter = customers.listIterator(); iter.hasNext(); ) {
	    	Customer customer2 = iter.next();
	    	
	    	if (customer2.getUserName().equals(customer.getUserName())) {
	    		customers.set(iter.previousIndex(), customer);
	    		return;
	    	}
	    }
	}
}
