package edu.iup.cosc341.bbb.actions;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.iup.cosc341.bbb.ConnectionPool;
import edu.iup.cosc341.bbb.Constants;
import edu.iup.cosc341.bbb.bo.Administrator;
import edu.iup.cosc341.bbb.dao.AdministratorDao;

/**
 * Implementation of <strong>Action </strong> that validates an administrator
 * logon. On success an Administrator is placed into the session scope. By
 * having an administrator in session scope the Administrator is logged on.
 * 
 * @author David T. Smith
 */
public final class LogonAdminAction extends ActionSupport implements
		ServletRequestAware {
	private String submit;
	private String userName;
	private String pin;
	private HttpServletRequest request;

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String execute() throws Exception {

		Map session = ActionContext.getContext().getSession();

		// At this point a Login button must be used. If not forward to the
		// start page
		if (getSubmit().indexOf("Login") < 0) {
			return "start";
		}

		// Extract attributes we will need
		Administrator administrator = null;

		// Attempt to look up the administrator
		Connection conn = ConnectionPool.getConnection();

		try {
			conn = ConnectionPool.getConnection();

			administrator = AdministratorDao.selectByUserNamePin(conn,
					userName, pin);

			if (administrator != null) {
				// Save our logged-in user in the session
				session.put(Constants.ADMINISTRATOR_KEY, administrator);
			} else {
				addActionError(getText("error.password.mismatch"));
			}
		} catch (Exception e) {
			addActionError(getText("error.internalerror"));
		} finally {
			ConnectionPool.returnConnection(conn);
		}

		// If errors are encounterd, redisplay the input page
		if (!getActionErrors().isEmpty()) {
			return "input";
		}

		// Remove the obsolete form bean
		// if (mapping.getAttribute() != null) {
		// if ("request".equals(mapping.getScope()))
		// request.removeAttribute(mapping.getAttribute());
		// else
		// session.removeAttribute(mapping.getAttribute());
		// }

		// forward the to the administration menu
		return "adminMenu";
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}
}