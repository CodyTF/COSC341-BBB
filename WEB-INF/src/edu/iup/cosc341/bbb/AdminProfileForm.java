package edu.iup.cosc341.bbb;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author David T. Smith
 *
 * Struts form used to hold the profile of an Administrator
 */
public class AdminProfileForm extends ActionForm {
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

	private PhoneForm[] phones;

	private String submit;

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
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors actionErrors = new ActionErrors();

		if (submit.indexOf("Cancel") >= 0) {
			return (actionErrors);
		}

		if (submit.indexOf("Add Phone") >= 0) {
			return (actionErrors);
		}

		if (submit.indexOf("Remove") >= 0) {
			return (actionErrors);
		}

		if (pin == null || pin.trim().length() == 0) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.pinrequired"));
		} else if (pin.trim().length() < 4 || pin.trim().length() > 8) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.pininvalid"));
		}

		if (pin != null && !pin.equals(pin2)) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.pinmismatch"));
		}

		if (firstName == null || firstName.trim().length() == 0) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.firstnamerequired"));
		}

		if (lastName == null || lastName.trim().length() == 0) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.lastnamerequired"));
		}

		if (address == null || address.trim().length() == 0) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.addressrequired"));
		}

		if (city == null || city.trim().length() == 0) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.cityrequired"));
		}

		if (state == null || state.trim().equals("--")) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.staterequired"));
		}

		if (zipcode == null || zipcode.trim().length() == 0) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.regcust.zipcoderequired"));
		} else {
			if (zipcode.trim().length() != 5) {
				actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"error.regcust.zipcodeinvalid"));
			} else {
				zipcode = zipcode.trim();
				for (int i = 0; i < zipcode.length(); i++) {
					if (!Character.isDigit(zipcode.charAt(i))) {
						actionErrors
								.add(ActionErrors.GLOBAL_ERROR,
										new ActionError(
												"error.regcust.zipcodeinvalid"));
						break;
					}
				}
			}
		}

		boolean hasPhone = false;
		
		for (int phoneInx = 0; phoneInx < getPhones().length; phoneInx++) {
			PhoneForm phone = getPhones()[phoneInx];
			if (phone.getPhone() != null && phone.getPhone().trim().length() > 0) {
				hasPhone = true;
				if (phone.getPhone().trim().length() != 10) {
					actionErrors.add(ActionErrors.GLOBAL_ERROR,
							new ActionError("error.updateadmin.phoneinvalid"));
				} else {
					String phoneString = phone.getPhone().trim();
					for (int i = 0; i < phoneString.length(); i++) {
						if (!Character.isDigit(phoneString.charAt(i))) {
							actionErrors.add(ActionErrors.GLOBAL_ERROR,
									new ActionError(
											"error.updateadmin.phoneinvalid"));
							break;
						}
					}
				}
			}
		}
		if (!hasPhone) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.updateadmin.phonerequired"));
		}

		return actionErrors;
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

	public PhoneForm[] getPhones() {
		return phones;
	}

	public void setPhones(PhoneForm[] phones) {
		this.phones = phones;
	}
}
