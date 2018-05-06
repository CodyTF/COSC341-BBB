package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;


import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.BookOnHand;
import edu.iup.cosc341.bbb.bo.BookOrder;
import edu.iup.cosc341.bbb.bo.OrderItem;
import edu.iup.cosc341.bbb.dao.BookOnHandDao;
import edu.iup.cosc341.bbb.dao.BookOrderDao;

/**
 * @author David T. Smith
 * 
 * Implementation of <strong>Action </strong> that completes an order
 * The order in the shopping cart is added to the database
 */
public final class ConfirmAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private String submit;
	private String useCC = "onfile";
	private String creditCardType;
	private String creditCardExprDateString;
	private String creditCardNumber;
	private HttpServletRequest request;	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

	public void validate() {
		ActionErrors actionErrors = new ActionErrors();

		if (submit.indexOf("Cancel") >=0) {
			return;			
		}
		
		if (submit.indexOf("Update") >=0) {
			return;			
		}

		if (useCC.equals("onFile")) {
			return;
		}
		
		if (creditCardType == null || creditCardType.trim().equals("<select>")) {
			addActionError(getText("error.regcust.cctyperequired"));
		}
		
		if (creditCardNumber == null || creditCardNumber.trim().length() == 0) {
			addActionError(getText("error.regcust.ccnumberrequired"));
		} else {
			if (creditCardNumber.trim().length() != 12) {
				addActionError(getText("error.regcust.ccnumberinvalid"));
			} else {
				creditCardNumber = creditCardNumber.trim();
				for (int i = 0; i < creditCardNumber.length(); i++) {
					if (!Character.isDigit(creditCardNumber.charAt(i))) {
						addActionError(getText("error.regcust.ccnumberinvalid"));
						break;
					}
				}
			}
		}
		
		if (creditCardExprDateString == null || creditCardExprDateString.trim().length() == 0) {
			addActionError(getText("error.regcust.ccexprdaterequired"));
		} else {
			try {
				Date exprDate = dateFormat.parse(creditCardExprDateString.trim());
				if (exprDate.compareTo(new Date()) < 0) {
					addActionError(getText("error.regcust.ccexprdateexpired"));
				}
			} catch (ParseException pe) {
				addActionError(getText("error.regcust.ccexprdateinvalid"));
			}
		}
	}
	
	public String getSubmit() {
		return submit;
	}
	
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	
	public String getCreditCardExprDateString() {
		return creditCardExprDateString;
	}
	
	public void setCreditCardExprDateString(String creditCardExprDateString) {
		this.creditCardExprDateString = creditCardExprDateString;
	}
	
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	public String getCreditCardType() {
		return creditCardType;
	}
	
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	
	public String getUseCC() {
		return useCC;
	}
	
	public void setUseCC(String useCC) {
		this.useCC = useCC;
	}

	public String execute()
			throws Exception {
		
		// redirect back to the manage cart page
		if (getSubmit().indexOf("Cancel") >=0) {
			return "getCart";			
		}
	
		// redirect to the update customer profile page
		if (getSubmit().indexOf("Update") >=0) {
			return "getUpdateCust";			
		} 

		HttpSession session = request.getSession();
		
		// get the book order in the shopping cart
		BookOrder bookOrder = (BookOrder) session.getAttribute(Constants.CART_KEY);

		if (bookOrder == null) {
			return "start";			
		}
		 
		// If other credit card is checked, then apply it to the order
		if (getUseCC().equals("other")) {
			BeanUtils.copyProperties(bookOrder, this);
		}
		
		// Set the date and time for the order
		bookOrder.setOrderDate(new Timestamp(System.currentTimeMillis()));
		
		// Need to decrement inventory with the books being ordered
		
		// First of all get all Order Items and sort them by ISBN.
		// This will prevent any deadlocks when updating the inventory

		ArrayList<OrderItem> list = new ArrayList<OrderItem>();
		for (OrderItem item : bookOrder.getOrderItems()) {
			list.add(item);
		}
		Collections.sort(list, new Comparator<OrderItem>() {
			public int compare(OrderItem o1, OrderItem o2) {
				return o1.getIsbn().compareTo(o2.getIsbn());
			}
		});
		
		Connection conn = null;
		
		try {
			conn = ConnectionPool.getTxWrConnection();

			// Insert the Book Order into the database
			BookOrderDao.insert(conn, bookOrder);
			
			// Update the inventory
			for (Iterator<OrderItem> iter = list.iterator(); iter.hasNext();) {
				OrderItem orderItem = iter.next();
				BookOnHand bookOnHand = BookOnHandDao.selectByIsbnLock(conn, orderItem.getIsbn());
				if (bookOnHand != null) {
					bookOnHand.setOnHand(bookOnHand.getOnHand() - orderItem.getQuantityOrdered());
					BookOnHandDao.update(conn, bookOnHand);
				} else {
					// TODO this case should not happen.
					// But if it does need to forward to an error page
				}
			}
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			
			// TODO
			// If something went wrong then need to forward to an error page
		} finally {
			ConnectionPool.returnConnection(conn);			
		}
				
		return "orderPlaced";							
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}