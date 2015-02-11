package com.gcs.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

public class ServicioPorPais {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private long idServicio;
	
	@Persistent
	private long idPais;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public long getIdServicio() {
		return idServicio;
	} 

	public void setIdServicio(long key) {
		this.idServicio = key;
	}
	
	public long getIdPais() {
		return idPais;
	}

	public void setIdPais(long key) {
		this.idPais = key;
	}
}
