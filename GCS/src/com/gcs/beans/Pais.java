package com.gcs.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Pais {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String detalle;
	
	@Persistent
	private String name;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getName() {
		return name;
	}

	public void setNme(String name) {
		this.name = name;
	}
	  public String getDetalle() {
			return detalle;
	}
	public void setDetalle(String det) {
		this.detalle = det;
	}
	
}
