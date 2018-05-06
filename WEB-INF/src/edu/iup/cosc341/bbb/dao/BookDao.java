package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.iup.cosc341.bbb.bo.AuthorBook;
import edu.iup.cosc341.bbb.bo.Book;
import edu.iup.cosc341.bbb.bo.Customer;
import edu.iup.cosc341.bbb.bo.Review;
import edu.iup.cosc341.bbb.bo.State;

/**
 * @author David T. Smith
 *
 * DAO from accessing/updating Books in the Book table.  This Dao
 * will also access/update Author, AuthorBook, and Review tables
 * with information joining Book
 */
public class BookDao {
	static ArrayList<Book> books = new ArrayList<Book>();
	 
//	 static {
//		Book book = new Book();
//
//		book.setTitle("An Action Book");
//	 	book.setCategory("Action");
//	 	book.setIsbn("12456");
//	 	book.setOnHand(5);
//	 	book.setOrderPoint(10);
//	 	book.setPrice(39.99);
//	 	book.setPublisher("Wiley");
//	 	book.setYearPublished(1701);
//	 	book.setActive(true);
//	 	
//	 	AuthorBook authorBook = new AuthorBook();
//	 	authorBook.setFirstName("Joe");
//	 	authorBook.setLastName("Writer");
//	 	
//	 	Review review = new Review();
//	 	review.setReview("Great Book");
//	 	
//	 	book.addAuthorBook(authorBook);
//	 	book.addReview(review);
//	 	
//	 	books.add(book);
//	 	
//		book = new Book();
//	 	
//		book.setTitle("Another Action Book");
//	 	book.setCategory("Action");
//	 	book.setIsbn("007");
//	 	book.setOnHand(5);
//	 	book.setOrderPoint(10);
//	 	book.setPrice(20.99);
//	 	book.setPublisher("Wiley");
//	 	book.setYearPublished(1701);
//	 	book.setActive(false);
//	 	
//	 	authorBook = new AuthorBook();
//	 	authorBook.setFirstName("Joe");
//	 	authorBook.setLastName("Writer");
//	 		 	
//	 	book.addAuthorBook(authorBook);
//	 	
//	 	books.add(book);
//	 	
//		book = new Book();
//
//		book.setTitle("A Good Drama Book");
//	 	book.setCategory("Drama");
//	 	book.setIsbn("999");
//	 	book.setOnHand(5);
//	 	book.setOrderPoint(10);
//	 	book.setPrice(40.99);
//	 	book.setPublisher("Wiley");
//	 	book.setYearPublished(1701);
//	 	book.setActive(true);
//	 	
//	 	authorBook = new AuthorBook();
//	 	authorBook.setFirstName("Joe");
//	 	authorBook.setLastName("Writer");
//	 		 	
//	 	book.addAuthorBook(authorBook);
//	 	
//	 	books.add(book);
//	 }


	/**
	 * Select count by ISBN
	 * 
	 * @param conn SQL Connection
	 * @param isbn ISBN to be used in selecting the count
	 * @return int count of Book objects with the given ISBN stored in the data base
	 * @throws SQLException
	 */
	public static int selectCountByIsbn(Connection conn, String isbn) throws SQLException {
	    int n = 0;
	    
	    for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
	    	Book book = iter.next();
	    	
	    	if (book.getIsbn().equals(isbn)) {
	    		n++;
	    	}
	    }
		return n;
	}
	
	/**
	 * Select all Books that are active and have their On Hand less than the
	 * Minumum Quantity
	 * 
	 * @param conn SQL Connection
	 * @return List of all Book objects that are active and have their On Hand less than the
	 *    Minumum Quantity
	 * @throws SQLException
	 */
	public static List<Book> selectOnHandLTOrderPoint(Connection conn) throws SQLException {
		List<Book> list = new ArrayList<Book>();
		for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
			Book book = iter.next();
			if (book.isActive() && book.getOnHand() <= book.getOrderPoint()) {
				list.add(book);
			}
		}
		return list;		
	}
	
	/**
	 * Select all Books have a keyword in the title, publisher, author, or isbn; are of a category, 
	 * and are (optionally) active only.
	 * 
	 * @param conn SQL Connection
	 * @param keyword - keyword to be found in the title, publisher, author, or isbn.
	 * @param category - the category of books - leave empty for any category
	 * @param active - if true then search active books only
	 * @return List of all Book objects that satisfy the mentioned conditions
	 * @throws SQLException
	 */
	public static List<Book> selectByKeywordCategoryActive(Connection conn, String keyword, String category, boolean active) throws SQLException {
		List<Book> list = new ArrayList<Book>();
		for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
			Book book = iter.next();
			if ((!active || book.isActive()) 
					&& book.getTitle().indexOf(keyword.trim()) >= 0
					&& book.getCategory().indexOf(category.trim()) >= 0) {
				list.add(book);
			}
		}
		return list;
	}
	/**
	 * Select all Books have a keyword in the title, are of a category, and are (optionally)
	 * active only.
	 * 
	 * @param conn SQL Connection
	 * @param titleKeyword - keyword to be found in the title
	 * @param category - the category of books - leave empty for any category
	 * @param active - if true then search active books only
	 * @return List of all Book objects that satisfy the mentioned conditions
	 * @throws SQLException
	 */
	public static List<Book> selectByTitleCategoryActive(Connection conn, String titleKeyword, String category, boolean active) throws SQLException {
		List<Book> list = new ArrayList<Book>();
		for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
			Book book = iter.next();
			if ((!active || book.isActive()) 
					&& book.getTitle().indexOf(titleKeyword.trim()) >= 0
					&& book.getCategory().indexOf(category.trim()) >= 0) {
				list.add(book);
			}
		}
		return list;
	}
	
	/**
	 * Select all Books have a keyword in the publisher, are of a category, and are (optionally)
	 * active only.
	 * 
	 * @param conn SQL Connection
	 * @param publisherKeyword - keyword to be found in the publisher
	 * @param category - the category of books - leave empty for any category
	 * @param active - if true then search active books only
	 * @return List of all Book objects that satisfy the mentioned conditions
	 * @throws SQLException
	 */
	public static List<Book> selectByPublisherCategoryActive(Connection conn, String publisherKeyword, String category, boolean active) throws SQLException {
		List<Book> list = new ArrayList<Book>();
		Book curBook;
		PreparedStatement stmt = conn.prepareStatement("Select * from book where publisher like '%" + publisherKeyword + "%' and category='" + category + "'");
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			curBook = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getLong(4), 
					rs.getString(5), rs.getDouble(6), rs.getLong(7), rs.getLong(8), rs.getString(9));
			if(curBook.isActive()) list.add(curBook);
		}
			rs.close();
//			stmt.close();
//		for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
//			Book book = iter.next();
//			if ((!active || book.isActive()) 
//					&& book.getPublisher().indexOf(publisherKeyword.trim()) >= 0
//					&& book.getCategory().indexOf(category.trim()) >= 0) {
//				list.add(book);
//			}
//		}
		return list;
	}
	
	/**
	 * Select all Book by isbn
	 * 
	 * @param conn SQL Connection
	 * @param isbn - isbn of the book to be found
	 * @return found Book, null if not found
	 * @throws SQLException
	 */
	public static Book selectByIsbn(Connection conn, String isbn) throws SQLException {
		
		for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
			Book book = iter.next();
			if (book.getIsbn().equals(isbn)) {
				return book;
			}
		}
		return null;
	}
	
	/**
	 * Select all Books have a keyword in the author of the book, are of a category, and are (optionally)
	 * active only.
	 * 
	 * @param conn SQL Connection
	 * @param authorKeyword - keyword to be found in the first name or last name of the author of a book
	 * @param category - the category of books - leave empty for any category
	 * @param active - if true then search active books only
	 * @return List of all Book objects that satisfy the mentioned conditions
	 * @throws SQLException
	 */
	public static List<Book> selectByAuthorCategoryActive(Connection conn, String authorKeyword, String category, boolean active) throws SQLException {
		List<Book> list = new ArrayList<Book>();
		for (Iterator<Book> iter = books.iterator(); iter.hasNext(); ) {
			Book book = iter.next();
			if ((!active || book.isActive()) 
					&& book.getCategory().indexOf(category.trim()) >= 0) {
				list.add(book);
			}
		}
		return list;
	}
	
	/**
	 * Select Books as Customer Favorites.  These will be books that are form
	 * the category of the customers most recently placed order.
	 * 
	 * @param conn SQL Connection
	 * @param customer Customer on which to perform the select
	 * @return List of all Book objects stored in the data base
	 * @throws SQLException
	 */
	public static List<Book> selectCustomerFavorites(Connection conn, Customer customer) throws SQLException {
		return selectByTitleCategoryActive(conn, "", "Drama", true);	
	}
	
	/**
	 * Insert Book
	 * 
	 * @param conn SQL Connection
	 * @param book the Book to be inserted into the database
	 * @throws SQLException
	 */
	public static void insert(Connection conn, Book book) throws SQLException {
		int inx = books.indexOf(book);
		
		if (inx >= 0) {
			throw new SQLException("book exists");
		} else {
			books.add(book);
		}
	}
	
	/**
	 * Update Book
	 * 
	 * @param conn SQL Connection
	 * @param book the Book to be updated in the database
	 * @throws SQLException
	 */
	public static void update(Connection conn, Book book) throws SQLException {
		int inx = books.indexOf(book);
		
		if (inx >= 0) {
			books.set(inx, book);
		} else {
			throw new SQLException("book not found");
		}
	}
	
	/**
	 * Select reviews for a book.  The reviews are added to the Book's 
	 * list of reviews
	 * 
	 * @param conn SQL Connection
	 * @param book the Book for which reviews will be selected
	 * @throws SQLException
	 */
	private static void selectReviewsForBook(Connection conn, Book book) throws SQLException {
		
	}

	/**
	 * Select authors for a book.  The authors are added to the Book's 
	 * list of reviews
	 * 
	 * @param conn SQL Connection
	 * @param book the Book for which authors will be selected
	 * @throws SQLException
	 */
	private static void selectAuthorsForBook(Connection conn, Book book) throws SQLException {
		
	}
	
	/**
	 * Delete reviews from a book. 
	 * 
	 * @param conn SQL Connection
	 * @param book the Book for which reviews will be removed
	 * @throws SQLException
	 */
	private static void deleteReviewsForBook(Connection conn, Book book) throws SQLException {
		
	}

	/**
	 * Delete authors for a book. 
	 * 
	 * @param conn SQL Connection
	 * @param book the Book for which authors will be selected
	 * @throws SQLException
	 */
	private static void deleteAuthorsForBook(Connection conn, Book book) throws SQLException {
		
	}

	/**
	 * Insert reviews of a Book into the database.  
	 * 
	 * @param conn SQL Connection
	 * @param book the Book for which reviews will be inserted into the database
	 * @throws SQLException
	 */
	private static void insertReviewsForBook(Connection conn, Book book) throws SQLException {
		
	}

	/**
	 * Insert authors of a book into the database.  
	 * 
	 * @param conn SQL Connection
	 * @param book the Book for which authors will be inserted into the database
	 * @throws SQLException
	 */
	private static void insertAuthorsForBook(Connection conn, Book book) throws SQLException {
		
	}

}
