package edu.iup.cosc341.bbb.actions;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.Customer;

/**
 * @author David T. Smith
 * 
 * Implementation of <strong>Action </strong> that populates an CustomerAction
 */
public class CustomerAction extends ActionSupport implements ServletRequestAware {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

	private String userName;

	private String pin;

	private String pin2;

	private String firstName;

	private String lastName;

	private String address;

	private String city;

	private String state;

	private String zipcode;

	private String creditCardType;

	private String creditCardExprDateString;

	private String creditCardNumber;

	private String submit;

	private HttpServletRequest request;
	
	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreditCardExprDateString() {
		return creditCardExprDateString;
	}

	public void setCreditCardExprDateString(String creditCardExprDateString) {
		this.creditCardExprDateString = creditCardExprDateString;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getPin2() {
		return pin2;
	}

	public void setPin2(String pin2) {
		this.pin2 = pin2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zip) {
		this.zipcode = zip;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}
	
	public HttpServletRequest getRequest() {
		return request;		
	}

	public String getCustomer()
			throws Exception {

		HttpSession session = request.getSession();
		
		// Get the currently logged in Customer from the session scope
		Customer customer = (Customer) session.getAttribute(Constants.CUSTOMER_KEY);
		
		// If no customer logged in - then forward to the start
		if (customer == null) {
			return "start";	
		}
				
		// Apply the customer data to the form
		setUserName(customer.getUserName());
		setPin(customer.getPin());
		setPin2(customer.getPin());
		setFirstName(customer.getFirstName());
		setLastName(customer.getLastName());
		setAddress(customer.getAddress());
		setCity(customer.getCity());
		setState(customer.getState());
		setZipcode("" + customer.getZipcode());
		setCreditCardType(customer.getCreditCardType());
		setCreditCardNumber("" + customer.getCreditCardNumber());
		setCreditCardExprDateString(customer.getCreditCardExprDateFormatted());
				
		return "displayUpdateCust";	
	}
	
}