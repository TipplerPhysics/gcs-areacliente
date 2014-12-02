package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

public class Implantacion {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Long clientekey;
	
	@Persistent
	private String clienteName;
	
	@Persistent
	private Long gestor_it;
	
	@Persistent
	private String gestor_it_name;
	
	@Persistent
	private String estadoSubida;
	
	@Persistent
	private String detalleSubida;
	
	@Persistent
	private String servicio;
	
	@Persistent
	private Date fecha_de_subida;
	
	@Persistent
	private String str_fecha_de_subida;
	
	@Persistent
	private String conectividad;
	@Persistent
	private Long gestor_negocio;
	
	public String getClienteName() {
		return clienteName;
	}

	public void setClienteName(String clienteName) {
		this.clienteName = clienteName;
	}
	
	public String getEstadoSubida() {
		return estadoSubida;
	}

	public void setEstadoSubida(String estadoSubida) {
		this.estadoSubida = estadoSubida;
	}
	public String getDetalleSubida() {
		return detalleSubida;
	}

	public void setDetalleSubida(String detalleSubida) {
		this.detalleSubida = detalleSubida;
	}
	
	public Long getGestor_it() {
		return gestor_it;
	}

	public void setGestor_it(Long gestor_it) {
		this.gestor_it = gestor_it;
	}
	public String getGestor_it_name() {
		return gestor_it_name;
	}

	public void setGestor_it_name(String gestor_it_name) {
		this.gestor_it_name = gestor_it_name;
	}	
	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Date getFecha_implantacion_subida() {
		return fecha_de_subida;
	}

	public void setFecha_implantacion_subida(Date fecha_implantacion_subida) {
		this.fecha_de_subida = fecha_implantacion_subida;
	}
	
	public String getStr_implantacion_subida() {
		return str_fecha_de_subida;
	}

	public void setStr_implantacion_subida(
			String str_fecha_de_subida) {
			this.str_fecha_de_subida = str_fecha_de_subida;
	}
	
	public String getConectividad() {
		return conectividad;
	}

	public void setConectividad(String conectividad) {
		this.conectividad = conectividad;
	}
	public Long getClientekey() {
		return clientekey;
	}

	public void setClientekey(Long clientekey) {
		this.clientekey = clientekey;
	}
	public Long getGestor_negocio() {
		return gestor_negocio;
	}

	public void setGestor_negocio(Long gestor_negocio) {
		this.gestor_negocio = gestor_negocio;
	}

}
