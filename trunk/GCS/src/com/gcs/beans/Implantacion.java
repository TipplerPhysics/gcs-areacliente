package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Implantacion {
	
	
	@Persistent
	private Long conectividadkey;
	
	@Persistent
	private Long serviciokey;
	
	@Persistent
	private Long proyectokey;
	
	@Persistent
	private Date fecha_implantacion;
	
	@Persistent
	private String fecha_implantacion_str;
	
	@Persistent
	private String clienteName;
	
	/*
	@Persistent
	private Long gestor_it;*/
	
	@Persistent
	private String gestor_it_name;
	
	@Persistent
	private String estado;
	
	@Persistent
	private String estadoSubida;
	
	@Persistent
	private String detalleSubida;
	
	@Persistent
	private String servicio;
	
	@Persistent
	private String conectividad;
	
	@Persistent
	private String detalle;
	
	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String det) {
		this.detalle = det;
	}
	
	/*@Persistent
	private Long gestor_negocio;*/
	
	public String getClienteName() {
		return clienteName;
	}

	public void setClienteName(String clienteName) {
		this.clienteName = clienteName;
	}
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	
	public String getConectividad() {
		return conectividad;
	}

	public void setConectividad(String conectividad) {
		this.conectividad = conectividad;
	}

	public Long getConectividadkey() {
		return conectividadkey;
	}

	public void setConectividadkey(Long conectividadkey) {
		this.conectividadkey = conectividadkey;
	}
	public Long getServiciokey() {
		return serviciokey;
	}

	public void setServiciokey(Long serviciokey) {
		this.serviciokey = serviciokey;
	}
	
	public Long getProyectokey() {
		return proyectokey;
	}

	public void setProyectokey(Long proyectokey) {
		this.proyectokey = proyectokey;
	}
	
	public Date getFecha_implantacion() {
		return fecha_implantacion;
	}

	public void setFecha_implantacion(Date fecha_implantacion) {
		this.fecha_implantacion = fecha_implantacion;
	}
	
	public String getFecha_implantacion_str() {
		return fecha_implantacion_str;
	}

	public void setFecha_implantacion_str(String fecha_implantacion_str) {
		this.fecha_implantacion_str = fecha_implantacion_str;
	}
	
	/*public Long getGestor_negocio() {
		return gestor_negocio;
	}

	public void setGestor_negocio(Long gestor_negocio) {
		this.gestor_negocio = gestor_negocio;
	}*/

}
