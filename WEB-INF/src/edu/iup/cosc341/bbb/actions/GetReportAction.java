package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;


import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.Administrator;
import edu.iup.cosc341.bbb.bo.Statistics;
import edu.iup.cosc341.bbb.dao.ReportDao;

/**
 * Implementation of <strong>Action </strong> that get the data from the report dao
 * an puts it into the request scope.
 * 
 * @author David T. Smith
 */
public final class GetReportAction extends ActionSupport  implements ServletRequestAware {
	private String selection;
	
	private String submit;
	
	private HttpServletRequest request;
	
	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}

	public String execute()
			throws Exception {
	
		HttpSession session = request.getSession();
		
		// Get the administrator from the session scope
		Administrator administrator = (Administrator) session.getAttribute(Constants.ADMINISTRATOR_KEY);
		
		// No administrator logged in - then forward to the start
		if (administrator == null) {
			return "start";	
		}
		
		// No administrator logged in - then forward to the start
		if (getSubmit().indexOf("Cancel") >= 0) {
			return "adminMenu";	
		}
		
		Object results = null;

		Connection conn = null;
		
		try {
			conn = ConnectionPool.getConnection();
			
			if (getSelection().equals("SalesByCategory")) {
				Date date = new Date(System.currentTimeMillis());
			    results = ReportDao.selectSalesByCategory(conn, date);
			} else if (getSelection().equals("StockByCategory")) {
			    results = ReportDao.selectStockByCategory(conn);
			} else if (getSelection().equals("DistinctBuyersByCategory")) {
				Date date = new Date(System.currentTimeMillis());
			    results = ReportDao.selectBuyersByCategory(conn, date);
			} else if (getSelection().equals("TopSellers")) {
				Date date = new Date(System.currentTimeMillis());
			    results = ReportDao.selectTopSellingBooks(conn, date);
			} else if (getSelection().equals("ExpensiveBooks")) {
				Date date = new Date(System.currentTimeMillis());
			    results = ReportDao.selectExpensiveBooksByCategory(conn, date);
			} else if (getSelection().equals("Statistics")) {
				Date date = new Date(System.currentTimeMillis());
				Statistics statistics = new Statistics();
				statistics.setMonth(date);
				statistics.setAverageSalePerCustomer(ReportDao.selectAverageSalePerCustomer(conn, date));
				statistics.setAverageNoBooksPerOrder(ReportDao.selectAverageNoBooksPerOrder(conn, date));
				statistics.setAverageNoCustomersPerDay(ReportDao.selectAverageNoCustomersPerDay(conn, date));
				results = statistics;
			} 
		} catch (SQLException e) {
			
		} finally {
			ConnectionPool.returnConnection(conn);
		}

		request.setAttribute(Constants.RESULTS_KEY, results);

		if (getSelection().equals("SalesByCategory")) {
			return "displaySalesByCategory";
		} else if (getSelection().equals("StockByCategory")) {
			return "displayStockByCategory";
		} else if (getSelection().equals("DistinctBuyersByCategory")) {
			return "displayBuyersByCategory";
		} else if (getSelection().equals("TopSellers")) {
			return "displayTopSellers";
		} else if (getSelection().equals("ExpensiveBooks")) {
			return "displayExpensiveBooks";
		} else if (getSelection().equals("Statistics")) {
			return "displayStatistics";
		} 
		
		return "start";	
	}
}