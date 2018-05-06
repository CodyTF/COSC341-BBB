package edu.iup.cosc341.bbb.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.iup.cosc341.bbb.Constants;

/**
 * Implementation of <strong>Action </strong> that determines a forward based upon
 * the button used.
 * 
 * @author David T. Smith
 */
public final class RepositionAction extends ActionSupport {
	private String submit;
	
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}	
	
	public String execute()
			throws Exception {

		Map session = ActionContext.getContext().getSession();

		// Clear and Search will clear the current shopping cart and book queries,
		// then forward to the search page
		if (getSubmit().indexOf("ClearAndSearch") >=0) {
			session.remove(Constants.CART_KEY);
			session.remove(Constants.BOOK_QUERY_KEY);
			return "search";
		}
		
		// If search, forward to the search page
		if (getSubmit().indexOf("Search") >=0) {
			return "search";
		}
		
		// If Exit, forward to the exit action
		if (getSubmit().indexOf("EXIT") >=0) {
			session.remove(Constants.CUSTOMER_KEY);
			session.remove(Constants.CART_KEY);
			session.remove(Constants.BOOK_QUERY_KEY);
			session.remove(Constants.ADMINISTRATOR_KEY);
			return "start";			
		}
		
		// If checkout, forward the the checkout page.  Note however if
		// the customer has not logged in, the forward is to the 
		// identify customer page and cancel and pending are set accordingly
		if (getSubmit().indexOf("Checkout") >=0) {
			if (session.get(Constants.CUSTOMER_KEY) == null) {
				session.put(Constants.CANCEL_KEY, "getCart");
				session.put(Constants.PENDING_KEY, "checkout");
			    return "identCust";
			} else {
			    return "checkout";
			}
		}

		// If Manage, then forward the the shopping cart page
		if (getSubmit().indexOf("Manage") >=0) {
			return "getCart";
		}
	
		// If Reports, then forward to the report menu
		if (getSubmit().indexOf("Reports") >=0) {
			return "reportMenu";
		}
		
		// If AdminProfile, then forward to the update admin profile page
		if (getSubmit().indexOf("AdminProfile") >=0) {
			return "getAdminProfile";
		}
		
		// If AdminMenu, then forward the the administration menu
		if (getSubmit().indexOf("AdminMenu") >=0 || getSubmit().indexOf("Done") >=0) {
			return "adminMenu";
		}
		
		// If InsertNewBook, then forward the the insert new book action
		// This will initialize the insert update form then forward to
		// a blank insert book page
		if (getSubmit().indexOf("InsertNewBook") >=0) {
			return "getInsertBook";
		}
	
		// If PlaceOrders, then forward to the getRestockOrders action.  This
		// will find all books needing reordering and then forward to the restock
		// order page
		if (getSubmit().indexOf("PlaceOrders") >=0) {
			return "getRestockOrders";
		}
				
		return "failed";
	}
}