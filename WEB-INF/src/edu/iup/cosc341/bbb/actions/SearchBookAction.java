package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.util.ArrayList;
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.Administrator;
import edu.iup.cosc341.bbb.bo.Book;
import edu.iup.cosc341.bbb.dao.BookDao;

/**
 * Implementation of <strong>Action </strong> that executes a query to search for books
 * @author dtsmith
 */
public final class SearchBookAction extends ActionSupport {
	private String searchText;
	private String searchIn;
	private String category;
	private String submit;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSearchIn() {
		return searchIn;
	}
	public void setSearchIn(String searchIn) {
		this.searchIn = searchIn;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	
	public String execute()
			throws Exception {

		Map session = ActionContext.getContext().getSession();
			
		if (getSubmit().indexOf("EXIT") >=0) {
			session.remove(Constants.CUSTOMER_KEY);
			session.remove(Constants.ADMINISTRATOR_KEY);
			session.remove(Constants.CART_KEY);
			session.remove(Constants.BOOK_QUERY_KEY);
			return "start";			
		}
		
		// If Manage, then forward the the shopping cart page
		if (getSubmit().indexOf("Manage") >=0) {
			return "getCart";
		}
		
		if (getSubmit().indexOf("Cancel") >=0) {
			return "adminMenu";
		}
		
		// get the search criteria from the form and whether its an administrator that is logged in
		
		Administrator admin = (Administrator) session.get(Constants.ADMINISTRATOR_KEY);

		String keyword = getSearchText().trim();
		String category = getCategory().trim();
		boolean active = admin == null; 
						
		if (getCategory().equals("<select>")) {
			category = "";
		}
		
		Connection conn = null;
		List<Book> books = null;
		
		// Look up books using the criteria
		try {
			conn = ConnectionPool.getConnection();

			if (getSearchIn() == null || getSearchIn().indexOf("anywhere") >= 0) {
				books = BookDao.selectByKeywordCategoryActive(conn, keyword, category, active);				
			} else if (getSearchIn().equals("title")) {
				books = BookDao.selectByTitleCategoryActive(conn, keyword, category, active);
			} else if (getSearchIn().equals("author")) {
				books = BookDao.selectByAuthorCategoryActive(conn, keyword, category, active);
			} else if (getSearchIn().equals("publisher")) {
				books = BookDao.selectByPublisherCategoryActive(conn, keyword, category, active);
			} else if (getSearchIn().equals("isbn")) {
				books = new ArrayList<Book>();
				Book book = BookDao.selectByIsbn(conn, keyword.trim());
				if (book != null && (book.isActive() || !active) && category.length() == 0 ? true : (category.equals(book.getCategory()))) {
					books.add(book);
				}
			} 

		} finally {
			ConnectionPool.returnConnection(conn);
		}

		// If no books are found satisfying the criteria display no books found on the search page
		if (books == null || books.size() == 0) {
			ActionErrors errors = new ActionErrors();
			addActionError(getText("warning.nobooksfound"));
			return "input";
		} 
		
		// Otherwise we found books, add the list to the session scope
		session.put(Constants.BOOK_QUERY_KEY, books);
		
		// and forward to the display list
		return "displayList";			
	}
}