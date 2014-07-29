package com.gcs.actionForms;

import org.apache.struts.action.ActionForm;

public class ClientsForm extends ActionForm {
	
	private static final long serialVersionUID = 6961537593860406805L;
	
	private String message;
	
	public String getMessage() {
		return message;
	}
	 
	public void setMessage(String message) {
		this.message = message;
	}

}
