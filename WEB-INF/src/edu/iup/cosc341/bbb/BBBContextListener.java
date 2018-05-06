package edu.iup.cosc341.bbb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import edu.iup.cosc341.bbb.bo.Category;
import edu.iup.cosc341.bbb.bo.CcType;
import edu.iup.cosc341.bbb.bo.Publisher;
import edu.iup.cosc341.bbb.bo.State;
import edu.iup.cosc341.bbb.dao.CategoryDao;
import edu.iup.cosc341.bbb.dao.CcTypeDao;
import edu.iup.cosc341.bbb.dao.PublisherDao;
import edu.iup.cosc341.bbb.dao.StateDao;

/**
 * 
 * @author David T. Smith
 * 
 * Application listener for the BBB application
 * 
 * On start of the application: Initialize the Connection Pool Load list of
 * Categories into Application scope Load list of Publishers into Application
 * scope Load list of States into Appliction Scope Load list of CreditCardTypes
 * into Application scope
 * 
 * Also provides general log support for loggin messages, and logging changes
 * to the application context
 */
public final class BBBContextListener implements
		ServletContextAttributeListener, ServletContextListener {

	/**
	 * The servlet context with which we are associated.
	 */
	private ServletContext context = null;

	/**
	 * Record the fact that a servlet context attribute was added.
	 * 
	 * @param event
	 *            The servlet context attribute event
	 */
	public void attributeAdded(ServletContextAttributeEvent event) {
		log("attributeAdded('" + event.getName() + "', '" + event.getValue()
				+ "')");
	}

	/**
	 * Record the fact that a servlet context attribute was removed.
	 * 
	 * @param event
	 *            The servlet context attribute event
	 */
	public void attributeRemoved(ServletContextAttributeEvent event) {
		log("attributeRemoved('" + event.getName() + "', '" + event.getValue()
				+ "')");
	}

	/**
	 * Record the fact that a servlet context attribute was replaced.
	 * 
	 * @param event
	 *            The servlet context attribute event
	 */
	public void attributeReplaced(ServletContextAttributeEvent event) {
		log("attributeReplaced('" + event.getName() + "', '" + event.getValue()
				+ "')");
	}

	/**
	 * Record the fact that this web application has been destroyed.
	 * 
	 * @param event
	 *            The servlet context event
	 */
	public void contextDestroyed(ServletContextEvent event) {
		log("contextDestroyed()");
		this.context = null;
	}

	/**
	 * Record the fact that this web application has been initialized.
	 * 
	 * @param event
	 *            The servlet context event
	 */
	public void contextInitialized(ServletContextEvent event) {
		this.context = event.getServletContext();

		try {
			ConnectionPool.initializePool("oracle.jdbc.driver.OracleDriver",
					"jdbc:oracle:thin:@blackhawk.nsm.iup.edu:1521:CODB", "s18rrcv", "pw0867");

			Connection conn = ConnectionPool.getConnection();
			try {
				List<State> stateList = StateDao.selectAll(conn);
				stateList.add(0, new State("--", "<select>"));
				context.setAttribute("states", stateList);

				List<CcType> ccTypeList = CcTypeDao.selectAll(conn);
				ccTypeList.add(0, new CcType("<select>"));
				context.setAttribute("ccTypes", ccTypeList);

				List<Category> categoryList = CategoryDao.selectAll(conn);
				categoryList.add(0, new Category("<select>"));
				context.setAttribute("categories", categoryList);

				List<Publisher> publisherList = PublisherDao.selectAll(conn);
				publisherList.add(0, new Publisher("<select>"));
				context.setAttribute("publishers", publisherList);
			} catch (SQLException e) {
				log(e.toString());
			} finally {
				ConnectionPool.returnConnection(conn);
			}
		} catch (ClassNotFoundException e) {
			log(e.toString());
		} catch (SQLException e) {
			log(e.toString());
		}
		log("contextInitialized()");
	}

	// -------------------------------------------------------- Private Methods

	/**
	 * Log a message to the servlet context application log.
	 * 
	 * @param message
	 *            Message to be logged
	 */
	private void log(String message) {

		if (context != null)
			context.log("ContextListener: " + message);
		else
			System.out.println("ContextListener: " + message);

	}

}