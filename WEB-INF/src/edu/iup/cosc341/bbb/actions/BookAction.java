package edu.iup.cosc341.bbb.actions;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.Administrator;
import edu.iup.cosc341.bbb.bo.Author;
import edu.iup.cosc341.bbb.bo.AuthorBook;
import edu.iup.cosc341.bbb.bo.Book;
import edu.iup.cosc341.bbb.bo.Review;
import edu.iup.cosc341.bbb.dao.AuthorDao;
import edu.iup.cosc341.bbb.dao.BookDao;

/**
 * Implementation of <strong>Action </strong> that inserts a new book. Only
 * administrators can perform this action.
 * 
 * @author David T. Smith
 * 
 */
@SuppressWarnings("unchecked")
public  class BookAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private static Map<String,String> activeValues;
	
	static {
		activeValues = new TreeMap<String,String>();
		activeValues.put("true", "Active");
		activeValues.put("false", "Deleted");
	}

	public static class AuthorForm implements Serializable {
		private static final long serialVersionUID = 1L;
		String firstName = "";
		String lastName = "";
		boolean delCheck = false;

		public boolean isDelCheck() {
			return delCheck;
		}

		public void setDelCheck(boolean delCheck) {
			this.delCheck = delCheck;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}
	
	public Map<String,String> getActiveValues() {
		return activeValues;
	}

	public static class ReviewForm implements Serializable {
		private static final long serialVersionUID = 1L;
		String text = "";
		boolean delCheck = false;

		public boolean isDelCheck() {
			return delCheck;
		}

		public void setDelCheck(boolean delCheck) {
			this.delCheck = delCheck;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
	private String submit = "";

	private String actionTitle;

	private String isbn = "";

	private boolean active = true;

	private String title = "";

	private String publisher = "";

	private String yearPublished = "";

	private String category = "";

	private String price = "";

	private String onHand = "0";

	private String minQuantity = "";

	private List<AuthorForm> authors = ListUtils.lazyList(
			new ArrayList<AuthorForm>(), new Factory() {
				public Object create() {
					return new AuthorForm();
				}
			});

	private List<ReviewForm> reviews = ListUtils.lazyList(
			new ArrayList<ReviewForm>(), new Factory() {
				public Object create() {
					return new ReviewForm();
				}
			});

	public BookAction() {
		authors.get(0);
		reviews.get(0);
	}


	public String getActionTitle() {
		return actionTitle;
	}

	public void setActionTitle(String actionTitle) {
		this.actionTitle = actionTitle;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(String minQuantity) {
		this.minQuantity = minQuantity;
	}

	public String getOnHand() {
		return onHand;
	}

	public void setOnHand(String onHand) {
		this.onHand = onHand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(String yearPublished) {
		this.yearPublished = yearPublished;
	}

	public List<AuthorForm> getAuthors() {
		return authors;
	}

	public List<ReviewForm> getReviews() {
		return reviews;
	}

	public String getForInsert() throws Exception {

		Map session = ActionContext.getContext().getSession();

		Administrator administrator = (Administrator) session
				.get(Constants.ADMINISTRATOR_KEY);

		if (administrator == null) {
			return "start";
		}

		setActionTitle("INSERT NEW");

		return "displayBook";
	}

	public String getForUpdate() throws Exception {

		Map session = ActionContext.getContext().getSession();

		Administrator administrator = (Administrator) session
				.get(Constants.ADMINISTRATOR_KEY);

		if (administrator == null) {
			return "start";
		}

		Book book = null;

		Connection conn = null;

		try {
			conn = ConnectionPool.getConnection();
			book = BookDao.selectByIsbn(conn, getIsbn());
			if (book == null) {
				return "Search";
			}
		} catch (SQLException e) {

		} finally {
			ConnectionPool.returnConnection(conn);
		}

		setActionTitle("MODIFY/DELETE");
		setTitle(book.getTitle());
		setCategory(book.getCategory());
		setPublisher(book.getPublisher());
		setYearPublished("" + book.getYearPublished());
		setPrice("" + book.getPrice());
		setMinQuantity("" + book.getOrderPoint());
		setOnHand("" + book.getOnHand());
		setActive(book.isActive());

		if (book.getAuthorBooksSize() > 0) {
			for (int i = 0; i < book.getAuthorBooksSize(); i++) {
				getAuthors().get(i).setFirstName(
						book.getAuthorBook(i).getFirstName() == null ? ""
								: book.getAuthorBook(i).getFirstName());
				getAuthors().get(i).setLastName(
						book.getAuthorBook(i).getLastName() == null ? "" : book
								.getAuthorBook(i).getLastName());
			}
		}

		if (book.getReviewsSize() > 0) {
			for (int i = 0; i < book.getReviewsSize(); i++) {
				getReviews().get(i).setText(book.getReview(i).getReview());
			}
		}

		return "displayBook";
	}
}