package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Equipo {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Integer contador;
	
	@Persistent
	private String nombre;
	
	@Persistent
	private Date ultima_escritura_coste;
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}


	public Date getUltima_escritura() {
		return ultima_escritura_coste;
	}

	public void setUltima_escritura(Date ultima_escritura) {
		this.ultima_escritura_coste = ultima_escritura;
	}

	public Equipo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
