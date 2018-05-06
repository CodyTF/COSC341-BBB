package edu.iup.cosc341.bbb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.iup.cosc341.bbb.bo.AuthorBook;
import edu.iup.cosc341.bbb.bo.BookSales;
import edu.iup.cosc341.bbb.bo.CategoryBuyers;
import edu.iup.cosc341.bbb.bo.CategorySales;
import edu.iup.cosc341.bbb.bo.CategoryStock;

/**
 * @author David T. Smith
 *
 * DAO for accessint summary data used in reports
 */
public class ReportDao {
	static ArrayList<CategorySales> categorySales = new ArrayList<CategorySales>();
	static ArrayList<CategoryBuyers> categoryBuyers = new ArrayList<CategoryBuyers>();
	static ArrayList<CategoryStock> categoryStocks = new ArrayList<CategoryStock>();
	static ArrayList<BookSales> bookSales = new ArrayList<BookSales>();
	 
	static {
		BookSales book = new BookSales();

		book.setTitle("An Action Book");
	 	book.setCategory("Action");
	 	book.setIsbn("12456");
	 	book.setPrice(10.99);
	 	book.setSales(1202.34);
	 	
	 	AuthorBook authorBook = new AuthorBook();
	 	authorBook.setFirstName("Joe");
	 	authorBook.setLastName("Writer");
	 		 	
	 	book.addAuthorBook(authorBook);
	 	
	 	bookSales.add(book);
	 	
		book = new BookSales();

		book.setTitle("A Good Drama Book");
	 	book.setCategory("Drama");
	 	book.setIsbn("999");
	 	book.setPrice(40.99);
	 	book.setSales(666702.34);
	 	
	 	authorBook = new AuthorBook();
	 	authorBook.setFirstName("Joe");
	 	authorBook.setLastName("Writer");
	 		 	
	 	book.addAuthorBook(authorBook);
	 	
	 	bookSales.add(book);
	 

		 CategorySales categorySale = new CategorySales();
		 categorySale.setCategory("Romance");
		 categorySale.setSales(56043.50);
		 categorySales.add(categorySale);
		 categorySale = new CategorySales();
		 categorySale.setCategory("Drama");
		 categorySale.setSales(22043.50);
		 categorySales.add(categorySale);

		 CategoryBuyers categoryBuyer = new CategoryBuyers();
		 categoryBuyer.setCategory("Romance");
		 categoryBuyer.setBuyers(60l);
		 categoryBuyers.add(categoryBuyer);
		 categoryBuyer = new CategoryBuyers();
		 categoryBuyer.setCategory("Drama");
		 categoryBuyer.setBuyers(204);
		 categoryBuyers.add(categoryBuyer);	
		 
		 CategoryStock categoryStock = new CategoryStock();
		 categoryStock.setCategory("Romance");
		 categoryStock.setStock(560l);
		 categoryStocks.add(categoryStock);
		 categoryStock = new CategoryStock();
		 categoryStock.setCategory("Drama");
		 categoryStock.setStock(2204);
		 categoryStocks.add(categoryStock);	 
	 }


	/**
	 * Select category sales for a given month
	 * 
	 * @param conn SQL Connection
	 * @param month date  to be used in selecting the data
	 * @return List of CategorySales for the given month
	 * @throws SQLException
	 */
	public static List<CategorySales> selectSalesByCategory(Connection conn, Date month) throws SQLException {
		return categorySales;		
	}
	
	/**
	 * Select category buyers for a given month
	 * 
	 * @param conn SQL Connection
	 * @param month date  to be used in selecting the data
	 * @return List of CategoryBuyers for the given month
	 * @throws SQLException
	 */
	public static List<CategoryBuyers> selectBuyersByCategory(Connection conn, Date month) throws SQLException {
		return categoryBuyers;		
	}
	
	/**
	 * Select current category stock
	 * 
	 * @param conn SQL Connection
	 * @return List of CategoryStocks
	 * @throws SQLException
	 */
	public static List<CategoryStock> selectStockByCategory(Connection conn) throws SQLException {
		return categoryStocks;		
	}

	/**
	 * Select top selling books
	 * 
	 * @param conn SQL Connection
	 * @param month date  to be used in selecting the data
	 * @return List of BookSales for the given month
	 * @throws SQLException
	 */
	public static List<BookSales> selectTopSellingBooks(Connection conn, Date month) throws SQLException {
		return bookSales;		
	}
	
	/**
	 * Select expensive books for a given month
	 * 
	 * @param conn SQL Connection
	 * @param month date  to be used in selecting the data
	 * @return List of BookSales for the given month
	 * @throws SQLException
	 */
	public static List<BookSales> selectExpensiveBooksByCategory(Connection conn, Date month) throws SQLException {
		return bookSales;		
	}
	
	/**
	 * Select average amount of sale per customer for a given month
	 * 
	 * @param conn SQL Connection
	 * @param month date  to be used in selecting the data
	 * @return List of BookSales for the given month
	 * @throws SQLException
	 */
	public static double selectAverageSalePerCustomer(Connection conn, Date month) throws SQLException {
		return 45.00;		
	}

	/**
	 * Select average number of books sold per order for a given month
	 * 
	 * @param conn SQL Connection
	 * @param month date  to be used in selecting the data
	 * @return List of BookSales for the given month
	 * @throws SQLException
	 */
	public static double selectAverageNoBooksPerOrder(Connection conn, Date month) throws SQLException {
		return 7.3;		
	}

	/**
	 * Select average number of customers per day for a given month
	 * 
	 * @param conn SQL Connection
	 * @param month date  to be used in selecting the data
	 * @return List of BookSales for the given month
	 * @throws SQLException
	 */
	public static long selectAverageNoCustomersPerDay(Connection conn, Date month) throws SQLException {
		return 2500;		
	}
}
