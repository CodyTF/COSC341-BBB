package edu.iup.cosc341.bbb.actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Implementation of <strong>Action </strong> that determines the intial
 * starting context. That is search, new customer, returning customer, or
 * adminstration.
 * 
 * @author David T. Smith
 */
public final class SelectStartAction extends ActionSupport {
	private String selection;
	
	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String execute() {
		if (selection.equals("Search")) {
			return "search";
		}
		if (selection.equals("New")) {
			return "regCust";
		}
		if (selection.equals("Returning")) {
			return "logon";
		}
		if (selection.equals("Admin")) {
			return "logonAdmin";
		}

		return "failed";
	}
}