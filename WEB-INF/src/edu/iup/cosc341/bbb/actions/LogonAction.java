package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.Book;
import edu.iup.cosc341.bbb.bo.BookOrder;
import edu.iup.cosc341.bbb.bo.Customer;
import edu.iup.cosc341.bbb.dao.BookDao;
import edu.iup.cosc341.bbb.dao.CustomerDao;

/**
 * Implementation of <strong>Action </strong> that validates a customer logon.
 * On success a Customer is placed into the session scope.  By having a customer
 * in session scope the Customer is logged on.
 * 
 * @author David T. Smith
 */
public final class LogonAction extends ActionSupport implements ServletRequestAware {
	private String submit;
	private String userName;
	private String pin;
	private HttpServletRequest request;
	
	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String execute()
			throws Exception {

		Map session = ActionContext.getContext().getSession();
	            
		// If New Customer button is used forward to the customer registration page
		if (getSubmit().indexOf("New") >= 0) {
			return "regCust";
		}

		// On cancel forward to start.  Note however this action can be called
		// by a delayed logon.  In this case need to forward to the location 
		// indentified by the CANCEL_KEY attribute in the session scope.
		if (getSubmit().indexOf("Cancel") >= 0) {
			String cancel = (String) session.get(Constants.CANCEL_KEY);

			if (cancel != null) {
				// make sure to that the cancel and pending positions are removed.
				session.remove(Constants.CANCEL_KEY);
				session.remove(Constants.PENDING_KEY);
				return cancel;
			} else {
				return "start";
			}
		}

		// At this point a Login button must be used.  If not forward to the 
		// start page
		if (getSubmit().indexOf("Login") < 0) {
			return "start";
		}

		// Extract attributes we will need
		Customer customer = null;

		// Attempt to look up the customer
		Connection conn = null;

		try {
			conn = ConnectionPool.getConnection();

			customer = CustomerDao.selectByUserNamePin(conn, userName, pin);

			if (customer != null) {
				// Save our logged-in customer in the session scope
				session.put(Constants.CUSTOMER_KEY, customer);
				
				// If a cart exists, then update it with customer information
				BookOrder bookOrder = (BookOrder) session
						.get(Constants.CART_KEY);
				
				if (bookOrder != null) {
					bookOrder.setCustomer(customer);
				}
			} else {
				addActionError(getText("error.password.mismatch"));
			}
		} catch (Exception e) {
			addActionError(getText("error.internalerror"));
		} finally {
			ConnectionPool.returnConnection(conn);
		}

		// If errors are encountered, redisplay the input page
		if (!getActionErrors().isEmpty()) {
			return "input";
		}

		// Remove the obsolete form bean
//		if (mapping.getAttribute() != null) {
//			if ("request".equals(mapping.getScope()))
//				request.removeAttribute(mapping.getAttribute());
//			else
//				session.removeAttribute(mapping.getAttribute());
//		}

		// If there is a pending forward position, then forward to that position
		String pending = (String) session.get(Constants.PENDING_KEY);

		if (pending != null) {
			session.remove(Constants.CANCEL_KEY);
			session.remove(Constants.PENDING_KEY);
			return pending;
		}

		// Attempt to lookup sugggested favorites
		List<Book> books = null;
		
		conn = null;
		
		try {
			conn = ConnectionPool.getConnection();
			books = BookDao.selectCustomerFavorites(conn, customer);
			 
		} finally {
			ConnectionPool.returnConnection(conn);
		}
		
		// Found some suggested books
		if (books != null && books.size() > 0) {
			// Forward straight to the display list page
			session.put(Constants.BOOK_QUERY_KEY, books);
			request.setAttribute(Constants.WELCOME_KEY, "List of suggested books for " + customer.getUserName());
			return "displayList";				
		} else {
			// Otherwise forward to the search page
			return "search";
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
}