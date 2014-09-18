package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Proyecto {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String nombre;
	
	@Persistent
	private String fecha_alta_str;
	
	@Persistent
	private Date fecha_alta;
	
	@Persistent
	private Long clienteKey;
	
	@Persistent
	private int clasificacion;
	
	@Persistent
	private Long gestor_it;
	
	@Persistent
	private Long gestor_negocio;
	
	@Persistent
	private Long coste;

	@Persistent
	private String tipo;
	
	/*
	@Persistent
	private Set<Servicio> servicios;
	*/

	public Proyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getGestor_negocio() {
		return gestor_negocio;
	}

	public void setGestor_negocio(Long gestor_negocio) {
		this.gestor_negocio = gestor_negocio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getFecha_alta_str() {
		return fecha_alta_str;
	}

	public void setFecha_alta_str(String fecha_alta_str) {
		this.fecha_alta_str = fecha_alta_str;
	}

	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public Long getClienteKey() {
		return clienteKey;
	}

	public void setClienteKey(Long clienteKey) {
		this.clienteKey = clienteKey;
	}

	public int getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(int clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Long getGestor_it() {
		return gestor_it;
	}

	public void setGestor_it(Long gestor_it) {
		this.gestor_it = gestor_it;
	}

	public Long getCoste() {
		return coste;
	}

	public void setCoste(Long coste) {
		this.coste = coste;
	}
	
}
