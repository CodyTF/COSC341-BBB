package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
import edu.iup.cosc341.bbb.bo.Administrator;
import edu.iup.cosc341.bbb.bo.Book;
import edu.iup.cosc341.bbb.bo.BookOnHand;
import edu.iup.cosc341.bbb.bo.RestockOrder;
import edu.iup.cosc341.bbb.dao.BookDao;
import edu.iup.cosc341.bbb.dao.BookOnHandDao;
import edu.iup.cosc341.bbb.dao.RestockOrderDao;

/**
 * Implementation of <strong>Action </strong> that executes a query to find 
 * all books whose on hand is less than the minimum quantitiy.  These books
 * are candidates for restock.  The list is placed into session scope.  A
 * RestockOrder form is populated with data for the first restock order and
 * will cusor position information.
 * 
 * @author dtsmith
 */
public class RestockOrderAction extends ActionSupport implements ServletRequestAware {
	private String isbn;

	private String title;

	private String authorString;

	private String onHand;

	private String orderPoint;

	private String quantity;

	private String orderIndex;

	private String orderCount;

	private String orderNumber;

	private String placedCount;

	private String submit;

	private HttpServletRequest request;


	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getOrderPoint() {
		return orderPoint;
	}

	public void setOrderPoint(String orderPoint) {
		this.orderPoint = orderPoint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorString() {
		return authorString;
	}

	public void setAuthorString(String authorString) {
		this.authorString = authorString;
	}

	public String getOnHand() {
		return onHand;
	}

	public void setOnHand(String onHand) {
		this.onHand = onHand;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPlacedCount() {
		return placedCount;
	}

	public void setPlacedCount(String placedCount) {
		this.placedCount = placedCount;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}
	
	public HttpServletRequest getServletRequest() {
		return request;	
	}


	public String getRestockOrders()
			throws Exception {

		HttpSession session = request.getSession();
		
		int maxOrderNum = 0;


		Connection conn = null;
		List<Book> books = null;

		try {
			conn = ConnectionPool.getConnection();

			books = BookDao.selectOnHandLTOrderPoint(conn);
			
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery("select max(order_number) from restock_order");

			if (rs.next()) {
				maxOrderNum = rs.getInt(1);
			}
			
			rs.close();
			s.close();
			
		} finally {
			ConnectionPool.returnConnection(conn);
		}

		// Initialization of cursor position properties
		setOrderIndex("1");
		setOrderCount("" + (books == null ? 0 : books.size()));
		setOrderNumber("" + (maxOrderNum + 1));
		setPlacedCount("0");

		// apply info from the first book to the restockOrderForm
		if (books != null && books.size() > 0) {
			Book book = (Book) books.get(0);
			BeanUtils.copyProperties(this, book);
			setQuantity("");

			session.setAttribute(Constants.BOOK_QUERY_KEY, books);
		}
		
		return "displayRestock";
	}
	
	public String placeRestockOrder()
			throws Exception {

		// On cancel, forward to the aministrator menu
		if (getSubmit().indexOf("Cancel") >= 0) {
			return "adminMenu";
		}

		HttpSession session = request.getSession();
		
		// Get the administrator
		Administrator administrator = (Administrator) session.getAttribute(Constants.ADMINISTRATOR_KEY);
		
		// If administrator is not loggon on, forward to the start menu
		if (administrator == null) {
			return "start";	
		}
		
		// Get the list of books place in session scope by the restock order query
		List<Book> books = (List<Book>) session.getAttribute(Constants.BOOK_QUERY_KEY);
		
		// If no books found the, forward to the administrator menu
		if (books == null) {
			return "adminMenu";
		}
		
		// Extract the data from the form
		int orderIndex = Integer.parseInt(getOrderIndex());
		int orderNumber = Integer.parseInt(getOrderNumber());
		int placedCount = Integer.parseInt(getPlacedCount());

		// Process nagivation buttons
		if (getSubmit().indexOf("Previous") >= 0) {
			if (orderIndex > 1) {
				orderIndex--;
			}
		} else if (getSubmit().indexOf("Next") >= 0) {
			if (orderIndex < books.size()) {
				orderIndex++;
			}
		} else if (getSubmit().indexOf("Skip") >= 0) {
			// remove the book from the query
			books.remove(orderIndex - 1);
			if (orderIndex > books.size()) {
				orderIndex = books.size();
			}
		} else if (getSubmit().indexOf("Submit") >= 0) {
			// On submit, place the order
			RestockOrder restockOrder = new RestockOrder();
			restockOrder.setIsbn(getIsbn());
			restockOrder.setOrderNumber(orderNumber++);
			restockOrder.setQuantityOrdered(Integer.parseInt(getQuantity()));
			restockOrder.setUserName(administrator.getUserName());
			
			Connection conn = null;
			try {
				conn = ConnectionPool.getTxWrConnection();
				
				// Increment inventory
				BookOnHand bookOnHand = BookOnHandDao.selectByIsbnLock(conn, restockOrder.getIsbn());
				if (bookOnHand != null) {
					bookOnHand.setOnHand(bookOnHand.getOnHand() + restockOrder.getQuantityOrdered());
					BookOnHandDao.update(conn, bookOnHand);
				}
				
				// insert the Restock Order into the database
				RestockOrderDao.insert(conn, restockOrder);
				conn.commit();
				
				// increment the placed order count
				placedCount++;;			

			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			} finally {
				ConnectionPool.returnConnection(conn);
			}
			
			books.remove(orderIndex - 1);
			if (orderIndex > books.size()) {
				orderIndex = books.size();
			}
		}
		
		// No more books to process, forward the the administration menu
		if (books.size() == 0) {
			return "adminMenu";
		}

		setOrderIndex("" + orderIndex);
		setOrderCount("" + books.size());
		setOrderNumber("" + orderNumber);
		setPlacedCount("" + placedCount);

		// Copy the book at the current index position into the form
		Book book = (Book) books.get(orderIndex - 1);
		BeanUtils.copyProperties(this, book);
		
		// make sure the restock quantity in the form is empty
		setQuantity("");
		
		return "displayRestock";
	}

}