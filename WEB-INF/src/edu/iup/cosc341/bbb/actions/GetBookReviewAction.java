package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;


import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.Book;
import edu.iup.cosc341.bbb.dao.BookDao;

/**
 * Implementation of <strong>Action </strong> that finds a book and places it into
 * request scope.
 * 
 * @author David T. Smith
 */
public  class GetBookReviewAction extends ActionSupport implements ServletRequestAware {
	private String isbn = "";
	private HttpServletRequest request;

	
	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}

	public String execute()
			throws Exception {

		
		Book book = null;

		Connection conn = null;
		
		try {
			conn = ConnectionPool.getConnection();
			book = BookDao.selectByIsbn(conn, getIsbn());
			if (book == null) {
				return "bookNotFound";
			}
		} catch (SQLException e) {
			
		} finally {
			ConnectionPool.returnConnection(conn);
		}
		if (!book.getIsbn().equals(getIsbn())) {
			return "bookNotFound";
		}

		request.setAttribute(Constants.BOOK_KEY, book);

		return "displayBookReview";
	}
}