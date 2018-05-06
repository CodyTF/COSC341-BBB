package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class CcType implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ccType = "";

	public CcType(String ccType) {
		this.ccType = ccType;
	}

	public String getCcType() {
		return ccType;
	}

	public void setCcType(String value) {
		ccType = value;
	}
}
