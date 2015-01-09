package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class FechaCalendada {

	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String str_fecha ;
	
	@Persistent
	private Date fecha ;
	
	
	public Key getKey() {
		return key;
	}		

	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getStr_fecha () {
		return str_fecha ;
	}

	public void setStr_fecha (String str_fecha ) {
		this.str_fecha  = str_fecha ;
	}

	public Date getFecha () {
		return fecha ;
	}

	public void setFecha (Date fecha ) {
		this.fecha  = fecha ;
	}
}
