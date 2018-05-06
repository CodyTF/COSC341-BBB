package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.BookOrder;
import edu.iup.cosc341.bbb.bo.Customer;
import edu.iup.cosc341.bbb.dao.CustomerDao;

/**
 * Implementation of <strong>Action </strong> that registers a new customer.
 */
public class UpdateCustomerAction extends CustomerAction implements ServletRequestAware {
	/**
	 * Validate contents
	 * 
	 * Required fields:
	 *   userName (register only)
	 *   pin,
	 *   firstName,
	 *   lastName,
	 *   address,
	 *   city,
	 *   state,
	 *   zip,
	 *   creditCarType,
	 *   creditCardNumber,
	 *   creditCardExprDate
	 * 
	 * Other validations:
	 *   userName - does not exist in the db (register only)
	 *   pin - 4 to 8 characters in length
	 *   zip - 5 digits
	 *   creditCardNumber - 12 digits
	 *   creditCardExprDate - of form MM/YYYY and in the future
	 *   pin equals pin2
	 * 
	 * Skip validations for:
	 *   Cancel
	 *   Don't Register 
	 *   Remove (phone) buttons 
	 */
	public void validate() {
		if (getSubmit().indexOf("Don't") >= 0 || getSubmit().indexOf("Cancel") >= 0) {
			return;
		}

		if (getSubmit().indexOf("Register") >= 0) {
			if (getUserName() == null || getUserName().trim().length() == 0) {
				addActionError(getText("error.regcust.usernamerequired"));
			} else {
				Connection conn = null;
				try {
					conn = ConnectionPool.getConnection();
					if (CustomerDao.selectCountByUserName(conn, getUserName().trim()) != 0) {
						addActionError(getText("error.regcust.userexists"));
					}
				} catch (SQLException e) {
				} finally {
					try {
						ConnectionPool.returnConnection(conn);
					} catch (SQLException e) {
					}
				}
			}
		}
		
		if (getPin() == null || getPin().trim().length() == 0) {
			addActionError(getText("error.regcust.pinrequired"));
		} else if (getPin().trim().length() < 4 || getPin().trim().length() > 8) {
			addActionError(getText("error.regcust.pininvalid"));
		}

		if (getPin() != null && !getPin().equals(getPin2())) {
			addActionError(getText("error.regcust.pinmismatch"));
		}
		
		if (getFirstName() == null || getFirstName().trim().length() == 0) {
			addActionError(getText("error.regcust.firstnamerequired"));
		}

		if (getLastName() == null || getLastName().trim().length() == 0) {
			addActionError(getText("error.regcust.lastnamerequired"));
		}

		if (getAddress() == null || getAddress().trim().length() == 0) {
			addActionError(getText("error.regcust.addressrequired"));
		}

		if (getCity() == null || getCity().trim().length() == 0) {
			addActionError(getText("error.regcust.cityrequired"));
		}

		if (getState() == null || getState().trim().equals("--")) {
			addActionError(getText("error.regcust.staterequired"));
		}

		if (getZipcode() == null || getZipcode().trim().length() == 0) {
			addActionError(getText("error.regcust.zipcoderequired"));
		} else {
			if (getZipcode().trim().length() != 5) {
				addActionError(getText("error.regcust.zipcodeinvalid"));
			} else {
				setZipcode(getZipcode().trim());
				for (int i = 0; i < getZipcode().length(); i++) {
					if (!Character.isDigit(getZipcode().charAt(i))) {
						addActionError(getText("error.regcust.zipcodeinvalid"));
						break;
					}
				}
			}
		}

		if (getCreditCardType() == null || getCreditCardType().trim().equals("<select>")) {
			addActionError(getText("error.regcust.cctyperequired"));
		}

		if (getCreditCardNumber() == null || getCreditCardNumber().trim().length() == 0) {
			addActionError(getText("error.regcust.ccnumberrequired"));
		} else {
			if (getCreditCardNumber().trim().length() != 12) {
				addActionError(getText("error.regcust.ccnumberinvalid"));
			} else {
				setCreditCardNumber(getCreditCardNumber().trim());
				for (int i = 0; i < getCreditCardNumber().length(); i++) {
					if (!Character.isDigit(getCreditCardNumber().charAt(i))) {
						addActionError(getText("error.regcust.ccnumberinvalid"));
						break;
					}
				}
			}
		}

		if (getCreditCardExprDateString() == null
				|| getCreditCardExprDateString().trim().length() == 0) {
			addActionError(getText("error.regcust.ccexprdaterequired"));
		} else {
			try {
				java.util.Date exprDate = getDateFormat().parse(getCreditCardExprDateString()
						.trim());
				if (exprDate.compareTo(new java.util.Date()) < 0) {
					addActionError(getText("error.regcust.ccexprdateexpired"));
				}
			} catch (ParseException pe) {
				addActionError(getText("error.regcust.ccexprdateinvalid"));
			}
		}
	}

	public String regCustomer() throws Exception {

		HttpSession session = getRequest().getSession();
		
		// For Don't Register forward to start.  Note however this action can be called
		// by a delayed logon.  In this case need to forward to the location 
		// indentified by the CANCEL_KEY attribute in the session scope.
		if (getSubmit().indexOf("Don't") >=0) {
			// Forward control to the specified success URI
			String cancel = (String) session.getAttribute(Constants.CANCEL_KEY);
			
			if (cancel != null) {
				// make sure to that the cancel and pending positions are removed.
				session.removeAttribute(Constants.CANCEL_KEY);
				session.removeAttribute(Constants.PENDING_KEY);
				return cancel;
			} else {
				return "start";
			}
		}
		
		// Create the customer object
		Customer customer = new Customer();
		
		// Copy the data from the form into the object
		BeanUtils.copyProperties(customer, this);
		
		customer.setCreditCardExprDate(new Date(getDateFormat().parse(getCreditCardExprDateString()).getTime()));
		
		// Store the customer into the database
		Connection conn = null;
		
		try {
			conn = ConnectionPool.getTxWrConnection();
			CustomerDao.insert(conn, customer);
			conn.commit();
			
			// Also place the customer into the session scope.  
			// The customer is now logged in
			session.setAttribute(Constants.CUSTOMER_KEY, customer);
			
			// If a book order is currently under construction, apply the customer
			// information to that order
			BookOrder bookOrder = (BookOrder) session.getAttribute(Constants.CART_KEY);
			if (bookOrder != null) {
				bookOrder.setCustomer(customer);
			}
			
			// If there is a pending forward position, then forward to that position,
			// otherwise, forward to the search page
			String pending = (String) session.getAttribute(Constants.PENDING_KEY);
			
			if (pending != null) {
				session.removeAttribute(Constants.CANCEL_KEY);
				session.removeAttribute(Constants.PENDING_KEY);
				return pending;
			} else {
				return "search";	
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			
			System.out.println(e);
		} finally {
			ConnectionPool.returnConnection(conn);			
		}
		
		// will get here if the username has been taken by another customer
		
		addActionError(getText("error.regcust.userexists"));
		
		return "input";
	}

	public String updateCustomer()
			throws Exception {

		HttpSession session = getRequest().getSession();
		
		// on cancel forward to the check page
		if (getSubmit().indexOf("Cancel") >=0) {
			return "checkout";
		}
		
		// get the Customer
		Customer customer = (Customer) session.getAttribute(Constants.CUSTOMER_KEY);
		
		// If not logged in, forward to the start page
		if (customer == null) {
			return "start";	
		}
			
		// apply data in the form to the customer
		// Copy the data from the form into the object
		BeanUtils.copyProperties(customer, this);
		
		// Update the customer in the database
		
		Connection conn = null;
		
		try {
			conn = ConnectionPool.getTxWrConnection();
			CustomerDao.update(conn, customer);
			conn.commit();
			
			// Put the updated customer into the session scope
			session.setAttribute(Constants.CUSTOMER_KEY, customer);
			
			// Get the under construction book order from the shopping cart
			BookOrder bookOrder = (BookOrder) session.getAttribute(Constants.CART_KEY);
			
			// If found apply the updated customer to that order.
			if (bookOrder != null) {
				bookOrder.setCustomer(customer);
			}
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			
			System.out.println(e);
		} finally {
			ConnectionPool.returnConnection(conn);			
		}
		
		// forward to the checkout page
		return "checkout";	
	}

}