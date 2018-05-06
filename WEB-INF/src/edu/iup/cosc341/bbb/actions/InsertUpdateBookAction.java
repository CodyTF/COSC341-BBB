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
public class InsertUpdateBookAction extends BookAction {
	private static final long serialVersionUID = 1L;
	


	/**
	 * Validate contents
	 * 
	 * Required fields: isbn (on Insert Only), title, publisher, yearPublished
	 * category, price, minQuantity, at least one author that has either
	 * firstName or lastName non empty
	 * 
	 * Other validations: isbn - digits isbn - does not already exist in db
	 * (Insert button only) yearPublished - 4 digits price - digits with 1
	 * decimal point minQuantity - digits
	 * 
	 * Skip validations for: Cancel Add (author or review) Remove (author or
	 * review) buttons
	 */
	public void validate() {
		if (getSubmit().length() == 0) {
			return;
		}
		if (getSubmit().indexOf("Insert") < 0 && getSubmit().indexOf("Update") < 0) {
			return;
		}

		if (getSubmit().indexOf("Add") >= 0) {
			return;
		}

		if (getSubmit().indexOf("Remove") >= 0) {
			return;
		}

		if (getSubmit().indexOf("Insert") >= 0) {
			if (getIsbn() == null || getIsbn().trim().length() == 0) {
				addActionError(getText("error.book.isbnrequired"));
			} else {
				setIsbn(getIsbn().trim());
				for (int i = 0; i < getIsbn().length(); i++) {
					if (!Character.isDigit(getIsbn().charAt(i))) {
						addActionError(getText("error.book.isbninvalid"));
						break;
					}
				}
				Connection conn = null;
				try {
					conn = ConnectionPool.getConnection();
					if (BookDao.selectCountByIsbn(conn, getIsbn().trim()) != 0) {
						addActionError(getText("error.book.isbnexists"));
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

		if (getTitle() == null || getTitle().trim().length() == 0) {
			addActionError(getText("error.book.titlerequired"));
		}

		boolean hasAuthor = false;

		for (AuthorForm author : getAuthors()) {
			if (author.getFirstName() != null
					&& author.getFirstName().trim().length() > 0
					|| author.getLastName() != null
					&& author.getLastName().trim().length() > 0) {
				hasAuthor = true;
			}
		}

		if (!hasAuthor) {
			addActionError(getText("error.book.authorrequired"));
		}
		if (getPublisher() == null || getPublisher().equals("<select>")) {
			addActionError(getText("error.book.publisherrequired"));
		}

		if (getYearPublished() == null || getYearPublished().trim().length() == 0) {
			addActionError(getText("error.book.yearpublishedrequired"));
		} else if (getYearPublished().trim().length() != 4) {
			addActionError(getText("error.regcust.yearpublishedinvalid"));
		} else {
			setYearPublished(getYearPublished().trim());
			for (int i = 0; i < getYearPublished().length(); i++) {
				if (!Character.isDigit(getYearPublished().charAt(i))) {
					addActionError(getText("error.regcust.yearpublishedinvalid"));
					break;
				}
			}
		}

		if (getCategory() == null || getCategory().equals("<select>")) {
			addActionError(getText("error.book.categoryrequired"));
		}

		if (getPrice() == null || getPrice().trim().length() == 0) {
			addActionError(getText("error.book.pricerequired"));
		} else {
			try {
				Double.parseDouble(getPrice());
			} catch (Exception e) {
				addActionError(getText("error.book.priceinvalid"));
			}
		}

		if (getMinQuantity() == null || getMinQuantity().trim().length() == 0) {
			addActionError(getText("error.book.minqtyrequired"));
		} else {
			setMinQuantity(getMinQuantity().trim());
			for (int i = 0; i < getMinQuantity().length(); i++) {
				if (!Character.isDigit(getMinQuantity().charAt(i))) {
					addActionError(getText("error.book.minqtyinvalid"));
					break;
				}
			}
		}

		if (getOnHand() == null || getOnHand().trim().length() == 0) {
			addActionError(getText("error.book.onhandinvalid"));
		} else {
			setOnHand(getOnHand().trim());
			for (int i = 0; i < getOnHand().length(); i++) {
				if (!Character.isDigit(getOnHand().charAt(i))) {
					addActionError(getText("error.book.onhandinvalid"));
					break;
				}
			}
		}
	}

	public String insertUpdate() throws Exception {

		Map session = ActionContext.getContext().getSession();

		// Get the administrator from the session scope
		Administrator administrator = (Administrator) session
				.get(Constants.ADMINISTRATOR_KEY);

		// No administrator logged in - then forward to the start
		if (administrator == null) {
			return "start";
		}

		// If cancel, forward the the administrator menu
		if (getSubmit().indexOf("Cancel") >= 0) {
			return ("adminMenu");
		}

		// For add author - add a blank Author form to the array of authors
		// and redisplay the input form
		if (getSubmit().indexOf("Add Author") >= 0) {
			getAuthors().get(getAuthors().size());
			return "displayBook";
		}

		// For remove author - remove checked authors from the array or authors
		// and redisplay the input form
		if (getSubmit().indexOf("Remove Author") >= 0) {
			for (Iterator<AuthorForm> iter = getAuthors().iterator(); iter
					.hasNext();) {
				AuthorForm authorForm = iter.next();
				if (authorForm.isDelCheck()) {
					iter.remove();
				}
			}

			// Insure there is at a minimum a blank author
			getAuthors().get(0);

			return "displayBook";
		}

		// For add review - add a blank Review form to the array or reviews
		// and redisplay the input form
		if (getSubmit().indexOf("Add Review") >= 0) {
			getReviews().get(getReviews().size());
			return "displayBook";
		}

		// For remove review - remove checked reviews from the array or reviews
		// and redisplay the input form
		if (getSubmit().indexOf("Remove Review") >= 0) {
			for (Iterator<ReviewForm> iter = getReviews().iterator(); iter
					.hasNext();) {
				ReviewForm reviewForm = iter.next();
				if (reviewForm.isDelCheck()) {
					iter.remove();
				}
			}

			// Insure there is at a minimum a blank review
			getReviews().get(0);

			return "displayBook";
		}

		// Insert/update of a book

		// create a Book
		Book book = new Book();

		// apply data from the form to the Book
		book.setIsbn(getIsbn());
		book.setTitle(getTitle());
		book.setPublisher(getPublisher());
		book.setYearPublished(Integer.parseInt(getYearPublished()));
		book.setCategory(getCategory());
		book.setPrice(Double.parseDouble(getPrice()));
		book.setOrderPoint(Integer.parseInt(getMinQuantity()));
		book.setOnHand(Integer.parseInt(getOnHand()));

		if (getActionTitle().indexOf("INSERT") >= 0) {
			book.setOnHand(0);
			book.setActive(true);
		} else {
			book.setActive(isActive());
			book.setOnHand(Integer.parseInt(getOnHand()));
		}

		for (Iterator<AuthorForm> iter = getAuthors().iterator(); iter
				.hasNext();) {
			AuthorForm authorForm = iter.next();
			if ((authorForm.getLastName() != null && authorForm.getLastName()
					.trim().length() > 0)
					|| (authorForm.getLastName() != null && authorForm
							.getLastName().trim().length() > 0)) {
				AuthorBook authorBook = new AuthorBook();
				authorBook.setIsbn(book.getIsbn());
				authorBook.setFirstName(authorForm.getFirstName() == null ? ""
						: authorForm.getFirstName());
				authorBook.setLastName(authorForm.getLastName() == null ? ""
						: authorForm.getLastName());
				book.addAuthorBook(authorBook);
			}
		}

		int reviewInx = 0;
		for (Iterator<ReviewForm> iter = getReviews().iterator(); iter
				.hasNext();) {
			ReviewForm reviewForm = iter.next();
			if (reviewForm.getText() != null
					&& reviewForm.getText().trim().length() > 0) {
				Review review = new Review();
				review.setIsbn(book.getIsbn());
				review.setReviewNumber(reviewInx++);
				review.setReview(reviewForm.getText());
				book.addReview(review);
			}
		}

		Connection conn = null;

		try {
			conn = ConnectionPool.getTxWrConnection();

			// Make sure the authors have been defined.
			for (Iterator<AuthorBook> iter = book.getAuthorBooks(); iter
					.hasNext();) {
				AuthorBook authorBook = iter.next();
				Author author = new Author();
				BeanUtils.copyProperties(author, authorBook);
				try {
					AuthorDao.insert(conn, author);
				} catch (Exception e) {
				}
			}

			// Now do the insert or update of the Book
			if (getActionTitle().indexOf("INSERT") >= 0) {
				BookDao.insert(conn, book);
			} else {
				BookDao.update(conn, book);

				// For update, need to make sure the book in the result of book
				// query
				// has the updated data
				List<Book> bookQuery = (List<Book>) session
						.get(Constants.BOOK_QUERY_KEY);
				int inx = bookQuery.indexOf(book);
				bookQuery.set(inx, book);
			}

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}

			// TODO need to forward to an error page if something goes wrong

		} finally {
			ConnectionPool.returnConnection(conn);
		}

		// For inserts forward to the administrators menu,
		// otherwise forward back to the book list
		if (getActionTitle().indexOf("INSERT") >= 0) {
			return "adminMenu";
		} else {
			return "displayList";
		}
	}
}