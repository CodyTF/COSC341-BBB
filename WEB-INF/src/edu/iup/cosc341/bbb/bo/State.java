package edu.iup.cosc341.bbb.bo;

import java.io.Serializable;

public class State implements Serializable {
	private static final long serialVersionUID = 1L;
	private String stateCode = "";
	private String stateName = "";
	
	public State(String stateCode, String stateName) {
		this.stateCode = stateCode;
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String value) {
		stateCode = value;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String value) {
		stateName = value;
	}

}
