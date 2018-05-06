package edu.iup.cosc341.bbb.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.AdminPhone;
import edu.iup.cosc341.bbb.bo.Administrator;

/**
 * @author David T. Smith
 * 
 * Implementation of <strong>Action </strong> that populates an AdminProfileForm
 */
public class AdminAction  extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 1L;

	public static class PhoneForm implements Serializable {
		private static final long serialVersionUID = 1L;
		String phone;
		boolean delCheck;
		
		public boolean isDelCheck() {
			return delCheck;
		}
		public void setDelCheck(boolean delCheck) {
			this.delCheck = delCheck;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}		
	}
	private String userName;

	private String pin;

	private String pin2;

	private String firstName;

	private String lastName;

	private String address;

	private String city;

	private String state;

	private String zipcode;

	private String submit;

	private HttpServletRequest request;

	private List<PhoneForm> phones = ListUtils.lazyList(
			new ArrayList<PhoneForm>(), new Factory() {
				public Object create() {
					return new PhoneForm();
				}
			});


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

	public List<PhoneForm> getPhones() {
		return phones;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}
	
	public HttpServletRequest getRequest() {
		return request;		
	}
	
	public String getAdminProfile()
			throws Exception {

		HttpSession session = request.getSession();
		
		// Get the administrator from the session scope
		Administrator administrator = (Administrator) session.getAttribute(Constants.ADMINISTRATOR_KEY);
		
		// No administrator logged in - then forward to the start
		if (administrator == null) {
			return "start";	
		}
				
		// Apply data from the administrator to the form
		setUserName(administrator.getUserName());
		setPin(administrator.getPin());
		setPin2(administrator.getPin());
		setFirstName(administrator.getFirstName());
		setLastName(administrator.getLastName());
		setAddress(administrator.getAddress());
		setCity(administrator.getCity());
		setState(administrator.getState());
		setZipcode("" + administrator.getZipcode());
	
		phones.clear();
		
		if (administrator.getAdminPhonesSize() == 0) {
			PhoneForm phone = new PhoneForm();
			phone.setPhone("");
			phones.add(phone);
		} else {
			for (ListIterator<AdminPhone> iter = administrator.getAdminPhones(); iter.hasNext(); ) {
				AdminPhone adminPhone = iter.next();
				PhoneForm phone = new PhoneForm();
				phone.setPhone("" + adminPhone.getPhoneNumber());
				phones.add(phone);
			}
		}
						
		return "displayUpdateAdmin";	
	}
}