package edu.iup.cosc341.bbb.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.BookOrder;
import edu.iup.cosc341.bbb.bo.OrderItem;

/**
 * @author David T. Smith
 * 
 * Implementation of <strong>Action </strong> that populates an UpdateCartForm for
 * the purpose of viewing/updating the book order under construction
 */
public class GetCartAction extends ActionSupport implements ServletRequestAware {
	
	public static class CartItemForm implements Serializable {
 		private static final long serialVersionUID = 1L;
		String isbn;
    	String title;
    	String authorString;
    	String price;
    	String quantity;
    	String itemTotal;
    	String submit;
    	
		public String getAuthorString() {
			return authorString;
		}
		
		public void setAuthorString(String authorString) {
			this.authorString = authorString;
		}
		
		public String getIsbn() {
			return isbn;
		}
		
		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}
		
		public String getItemTotal() {
			return itemTotal;
		}
		
		public void setItemTotal(String itemTotal) {
			this.itemTotal = itemTotal;
		}
		
		public String getPrice() {
			return price;
		}
		
		public void setPrice(String price) {
			this.price = price;
		}
		
		public String getQuantity() {
			return quantity;
		}
		
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		
		public String getSubmit() {
			return submit;
		}
		
		public void setSubmit(String submit) {
			this.submit = submit;
		}
		
		public String getTitle() {
			return title;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
    }
    
	private String submit;
	private String isbn;
	private String subtotal;

	private HttpServletRequest request;

	private List<CartItemForm> items = ListUtils.lazyList(
			new ArrayList<CartItemForm>(), new Factory() {
				public Object create() {
					return new CartItemForm();
				}
			});

	public List<CartItemForm>  getItems() {
		return items;
	}

	public String getSubmit() {
		return submit;
	}
	
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public HttpServletRequest getServletRequest() {
		return request;		
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}
	
	public String execute()
			throws Exception {

		HttpSession session = request.getSession();

		// Get the under construction book order from the shopping cart
		BookOrder cart = (BookOrder) session.getAttribute(Constants.CART_KEY);
		
		// Apply the book order to the form
		items.clear();

		if (cart != null) {
			for (OrderItem orderItem : cart.getOrderItems()) {
				CartItemForm item = new CartItemForm();
				item.setIsbn(orderItem.getIsbn());
				item.setTitle(orderItem.getTitle());
				item.setAuthorString(orderItem.getAuthorString());
				item.setPrice("" + orderItem.getPriceAtPurchase());
				item.setQuantity("" + orderItem.getQuantityOrdered());
				item.setItemTotal(String.format("%.2f",(orderItem.getPriceAtPurchase() * orderItem.getQuantityOrdered())));
				items.add(item);
			}
		}

		setSubtotal(String.format("%.2f",(cart == null ? 0 : cart.getSubtotal())));
		setSubmit(null);
		setIsbn(null);

		return "displayCart";
	}
}