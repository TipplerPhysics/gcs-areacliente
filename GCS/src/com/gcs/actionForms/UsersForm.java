package com.gcs.actionForms;

import org.apache.struts.action.ActionForm;

public class UsersForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7822254957429613005L;
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return Apellido1;
	}

	public void setApellido1(String apellido1) {
		Apellido1 = apellido1;
	}

	public String getApellido2() {
		return Apellido2;
	}

	public void setApellido2(String apellido2) {
		Apellido2 = apellido2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String mail;
	
	private String nombre;
	
	private String Apellido1;
	
	private String Apellido2;
	
	
	
}
