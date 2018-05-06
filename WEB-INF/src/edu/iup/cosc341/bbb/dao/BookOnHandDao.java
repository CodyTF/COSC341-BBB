package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import edu.iup.cosc341.bbb.bo.Book;
import edu.iup.cosc341.bbb.bo.BookOnHand;

/**
 * @author David T. Smith
 *
 * DAO from accessing/updating the On Hand value of Books via the BookOnHand view.
 */
public class BookOnHandDao {

	/**
	 * Select BookOnHand by ISBN
	 * 
	 * NOTE: For Update will be appled to the query.  Thus exclusive locks will
	 * be placed until the transaction on the connection is commited.
	 * 
	 * @param conn SQL Connection
	 * @param isbn ISBN to be used in selecting the BookOnHand objects
	 * @return BookOnHand object with the given ISBN, null if not found
	 * @throws SQLException
	 */
	public static BookOnHand selectByIsbnLock(Connection conn, String isbn) throws SQLException {
		Book book = BookDao.selectByIsbn(conn, isbn);
		BookOnHand bookOnHand = new BookOnHand();
		bookOnHand.setIsbn(isbn);
		bookOnHand.setOnHand(book.getOnHand());
		return bookOnHand;
	}
	
	/**
	 * Update BookOnHand
	 * 
	 * @param conn SQL Connection
	 * @param bookOnHand the BookOnHand to be updated in the database
	 * @throws SQLException
	 */
	public static void update(Connection conn, BookOnHand bookOnHand) throws SQLException {
		Book book = BookDao.selectByIsbn(conn, bookOnHand.getIsbn());
		book.setOnHand(bookOnHand.getOnHand());
	}
}
