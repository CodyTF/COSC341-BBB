package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import edu.iup.cosc341.bbb.AdminProfileForm;
import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.AdminProfileForm.PhoneForm;
import edu.iup.cosc341.bbb.actions.BookAction.AuthorForm;
import edu.iup.cosc341.bbb.bo.AdminPhone;
import edu.iup.cosc341.bbb.bo.Administrator;
import edu.iup.cosc341.bbb.dao.AdministratorDao;

/**
 * Implementation of <strong>Action </strong> that updates an administration profile.
 * Only administrators can perform this action.
 * 
 * @author David T. Smith
 */
public final class UpdateAdminAction extends AdminAction {
	
	/**
	 * Validate contents
	 * 
	 * Required fields:
	 *   pin,
	 *   firstName,
	 *   lastName,
	 *   address,
	 *   city,
	 *   state,
	 *   zip,
	 *   at least one phone non empty
	 * 
	 * Other validations:
	 *   pin - 4 to 8 characters in length
	 *   zip - 5 digits
	 *   phone - 10 digits (when entered)
	 *   pin equals pin2
	 * 
	 * Skip validations for:
	 *   Cancel
	 *   Add Phone 
	 *   Remove (phone) buttons 
	 */
	public void validate() {
		if (getSubmit().indexOf("Cancel") >= 0) {
			return;
		}

		if (getSubmit().indexOf("Add Phone") >= 0) {
			return;
		}

		if (getSubmit().indexOf("Remove") >= 0) {
			return;
		}

		if (getPin() == null || getPin().trim().length() == 0) {
			addActionError(getText(
					"error.regcust.pinrequired"));
		} else if (getPin().trim().length() < 4 || getPin().trim().length() > 8) {
			addActionError(getText(
					"error.regcust.pininvalid"));
		}

		if (getPin() != null && !getPin().equals(getPin2())) {
			addActionError(getText(
					"error.regcust.pinmismatch"));
		}

		if (getFirstName() == null || getFirstName().trim().length() == 0) {
			addActionError(getText(
					"error.regcust.firstnamerequired"));
		}

		if (getLastName() == null || getLastName().trim().length() == 0) {
			addActionError(getText(
					"error.regcust.lastnamerequired"));
		}

		if (getAddress() == null || getAddress().trim().length() == 0) {
			addActionError(getText(
					"error.regcust.addressrequired"));
		}

		if (getCity() == null || getCity().trim().length() == 0) {
			addActionError(getText("error.regcust.cityrequired"));
		}

		if (getState() == null || getState().trim().equals("--")) {
			addActionError(getText("error.regcust.staterequired"));
		}

		if (getZipcode() == null || getZipcode().trim().length() == 0) {
			addActionError(getText("error.regcust.zipcoderequired"));
		} else {
			if (getZipcode().trim().length() != 5) {
				addActionError(getText("error.regcust.zipcodeinvalid"));
			} else {
				setZipcode(getZipcode().trim());
				for (int i = 0; i < getZipcode().length(); i++) {
					if (!Character.isDigit(getZipcode().charAt(i))) {
						addActionError(getText("error.regcust.zipcodeinvalid"));
						break;
					}
				}
			}
		}

		boolean hasPhone = false;
		
		for (PhoneForm phone : getPhones()) {
			if (phone.getPhone() != null && phone.getPhone().trim().length() > 0) {
				hasPhone = true;
				if (phone.getPhone().trim().length() != 10) {
					addActionError(getText("error.updateadmin.phoneinvalid"));
				} else {
					String phoneString = phone.getPhone().trim();
					for (int i = 0; i < phoneString.length(); i++) {
						if (!Character.isDigit(phoneString.charAt(i))) {
							addActionError(getText("error.updateadmin.phoneinvalid"));
							break;
						}
					}
				}
			}
		}
		if (!hasPhone) {
			addActionError(getText(
					"error.updateadmin.phonerequired"));
		}
	}

	public String updateAdminProfile()
			throws Exception {

		HttpSession session = getRequest().getSession();
		
		// Get the administrator from the session scope
		Administrator administrator = (Administrator) session.getAttribute(Constants.ADMINISTRATOR_KEY);
		
		// No administrator logged in - then forward to the start
		if (administrator == null) {
			return "start";	
		}
		
		// On cancel, forward to the administration menu
		if (getSubmit().indexOf("Cancel") >=0) {
			return "adminMenu";
		}
		
		// For add phone - add a blank Phone form to the array of phones
        // and redisplay the input form
		if (getSubmit().indexOf("Add Phone") >=0) {
	        PhoneForm phone = new PhoneForm();
	        phone.setPhone("");
	        getPhones().add(phone);
			return "input";
		}
		
		// For remove phone - remove checked phones from the array or phones
        // and redisplay the input form
		if (getSubmit().indexOf("Remove") >=0) {
			for (Iterator<PhoneForm> iter = getPhones().iterator(); iter
			.hasNext();) {
				PhoneForm phoneForm = iter.next();
				if (phoneForm.isDelCheck()) {
					iter.remove();
				}
			}
			
		    // Insure there is at a minimum a blank phone
		    if (getPhones().size() == 0) {

		        PhoneForm phone = new PhoneForm();
		        phone.setPhone("");
		        getPhones().add(phone);
		    }
		    
			return "input";
		}		
		
		// Apply data from the form to the adminstrator
		// Copy the data from the form into the object
		BeanUtils.copyProperties(administrator, this);
		
		while (administrator.getAdminPhonesSize() > 0) {
			administrator.removeAdminPhone(administrator.getAdminPhone(0));
		}
		
		for (PhoneForm phone : getPhones()) {
			if (phone.getPhone() != null && phone.getPhone().trim().length() > 0) {
				AdminPhone adminPhone = new AdminPhone();
				adminPhone.setUserName(administrator.getUserName());
			    adminPhone.setPhoneNumber(Long.parseLong(phone.getPhone()));
			    administrator.addAdminPhone(adminPhone);
			}
		}
		
		// Update the administrator in the data base
		Connection conn = null;
		
		try {
			conn = ConnectionPool.getTxWrConnection();
			AdministratorDao.update(conn, administrator);
			conn.commit();
			
			// Place the updated administrator back into session scope
			session.setAttribute(Constants.ADMINISTRATOR_KEY, administrator);			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			
			System.out.println(e);
		} finally {
			ConnectionPool.returnConnection(conn);			
		}
		
		// Forward to the adminstation menu
		return "adminMenu";	
	}
}