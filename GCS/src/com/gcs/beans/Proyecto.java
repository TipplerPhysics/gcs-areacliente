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
	private String clienteName;
	
	@Persistent
	private int clasificacion;
	
	@Persistent
	private Long gestor_it;
	
	@Persistent
	private String gestor_it_name;
	
	@Persistent
	private Long gestor_negocio;
	
	@Persistent
	private String gestor_negocio_name;
	
	@Persistent
	private String coste;

	@Persistent
	private String tipo;
	
	@Persistent
	private String cod_proyecto;
	
	/*
	@Persistent
	private Set<Servicio> servicios;
	*/

	public Proyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCod_proyecto() {
		return cod_proyecto;
	}

	public void setCod_proyecto(String cod_proyecto) {
		this.cod_proyecto = cod_proyecto;
	}

	public String getGestor_it_name() {
		return gestor_it_name;
	}

	public void setGestor_it_name(String gestor_it_name) {
		this.gestor_it_name = gestor_it_name;
	}

	public String getGestor_negocio_name() {
		return gestor_negocio_name;
	}

	public void setGestor_negocio_name(String gestor_negocio_name) {
		this.gestor_negocio_name = gestor_negocio_name;
	}

	public String getClienteName() {
		return clienteName;
	}

	public void setClienteName(String clienteName) {
		this.clienteName = clienteName;
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

	public String getCoste() {
		return coste;
	}

	public void setCoste(String coste) {
		this.coste = coste;
	}
	
	@Override
	public boolean equals(Object object_b) {
		 if (!(object_b instanceof Proyecto)) {
		        return false;
		    }

		    Proyecto object_a = (Proyecto) object_b;
		    
		 // Custom equality check here.
		    return this.key.equals(object_a.key);
		      
	}	
	
}
