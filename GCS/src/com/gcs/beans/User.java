package com.gcs.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class User {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private String nombre;
	
	@Persistent
	private String apellido1;
	
	@Persistent
	private String apellido2;
	
	@Persistent
	private String email;
	
	public String getPermisoStr() {
		return permisoStr;
	}

	public void setPermisoStr(String permisoStr) {
		this.permisoStr = permisoStr;
	}

	@Persistent
	private int permiso;
	
	@Persistent 
	private String permisoStr;
	
		public User(String nombre, String apellido1, String apellido2,
			String email, int permiso, String permisoStr) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.permiso = permiso;
		this.permisoStr = permisoStr;
	}
		
		public User() {
			super();
		};

		public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}

		public Key getKey() {
		return key;
	}		

	public void setKey(Key key) {
		this.key = key;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

		
}
