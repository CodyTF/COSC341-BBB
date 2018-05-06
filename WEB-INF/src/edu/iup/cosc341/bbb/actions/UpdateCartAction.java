package edu.iup.cosc341.bbb.actions;

import java.util.ListIterator;

import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.BookOrder;
import edu.iup.cosc341.bbb.bo.Customer;
import edu.iup.cosc341.bbb.bo.OrderItem;

/**
 * Implementation of <strong>Action </strong> that updates the shopping cart.
 * 
 * @author David T. Smith
 */
public final class UpdateCartAction extends GetCartAction implements ServletRequestAware {

	public String execute()
			throws Exception {
		
		HttpSession session = getServletRequest().getSession();
		
		if (getSubmit() != null && getSubmit().indexOf("EXIT") >=0) {
			session.removeAttribute(Constants.CUSTOMER_KEY);
			session.removeAttribute(Constants.CART_KEY);
			session.removeAttribute(Constants.BOOK_QUERY_KEY);
			session.removeAttribute(Constants.ADMINISTRATOR_KEY);
			return "start";			
		}
		
		// get the under constuction book order from the shopping cart
		
		BookOrder bookOrder = (BookOrder) session.getAttribute(Constants.CART_KEY);
		
		// If it does not exist then create it.
		if (bookOrder == null) {
			bookOrder = new BookOrder();
			Customer customer = (Customer) session.getAttribute(Constants.CUSTOMER_KEY);
			
			// if the customer is logged in, apply customer info to the new book order
			if (customer != null) {
				bookOrder.setCustomer(customer);
			}
			
			// Put the order into session scope as the shopping cart
			session.setAttribute(Constants.CART_KEY, bookOrder);		
		}
		
		boolean found = false;
				
		// See if a remove button in the list of Items on the form was used
		if (getIsbn() == null && getSubmit() == null) {
			for (CartItemForm cartItemForm : getItems() ) {
				if ("Remove".equals(cartItemForm.getSubmit())) {
					// if so, grab its isbn and set the overall submit to "Remove"
					setIsbn(cartItemForm.getIsbn());
					setSubmit("Remove");
				}
			}
		}
		
		// Seach for the order item by the identified isbn number,  If found apply the
		// update.  
		// In the process also update the quantity of the line items with values found in
		// the form
		for (ListIterator<OrderItem> iter = bookOrder.getOrderItems().listIterator(); iter.hasNext(); ) {
			OrderItem item = iter.next();
			
			if (item.getIsbn().equals(getIsbn())) {
				found = true;
				if (getSubmit().indexOf("Add") >= 0) {
					item.setQuantityOrdered(item.getQuantityOrdered() + 1);
				} else if (getSubmit().indexOf("Remove") >= 0) {
					// this is ugly, but need to shift value in the items list of the form
					getItems().remove(iter.previousIndex());
					iter.remove();
					// this is also ugly
					bookOrder.recalculateTotals();
				}
			} else {
				// if NOT an Add submit, update the quantity from the form
				if (getSubmit().indexOf("Add") < 0 && iter.previousIndex() < getItems().size()) {
					item.setQuantityOrdered(Long.parseLong(getItems().get(iter.previousIndex()).getQuantity()));
                    
					// if quantify was set to 0, remove that item from the order
					if (item.getQuantityOrdered() == 0) {
    					// this is ugly, but need to shift values in the items list of the form
						getItems().remove(iter.previousIndex());
   					iter.remove();                    	
                    }
				}
			}
		}
		
		// If the book was not in the list and submit is an Add, then add the item to then end of the order
		if (!found && getSubmit().indexOf("Add") >= 0) {
			bookOrder.addOrderItem(new OrderItem(getIsbn().trim()));
		}
		
		// If submit is order then forward to the checkout page.  Note however, if the 
		// customer has not logged then set up a pending action and forward to the login
		// page
		if (getSubmit().indexOf("Checkout") >=0) {
			if (session.getAttribute(Constants.CUSTOMER_KEY) == null) {
				session.setAttribute(Constants.CANCEL_KEY, "getCart");
				session.setAttribute(Constants.PENDING_KEY, "checkout");
			    return "identCust";
			} else {
			    return "checkout";
			}
		} else if (getSubmit().indexOf("Recalculate") >= 0) {
			// for recalculate forward back the the shopping cart page
			return "getCart";
		} else if (getSubmit().indexOf("Remove") >= 0) {
			// for remove, if the cart is now empty, forward to the seach page
			// otherwise, redisplay the shopping cart
			if (bookOrder.getOrderItemsSize() == 0) {
				return "search";
			} else {
				return "getCart";
			}
		} else if (getSubmit().indexOf("Search") >= 0) {
			// if search is selected, forward to the search page
		    return "search";
		} else {
			// otherwise its a cancel, go back to the list of books
		    return "displayList";
		}
	}
	

}