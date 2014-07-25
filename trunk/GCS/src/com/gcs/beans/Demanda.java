package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable

public class Demanda {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private Date fecha_entrada_peticion;
	
	@Persistent
	private String hora_entrada_peticion;
	
	@Persistent
	private Date fecha_solicitud_asignacion;
	
	@Persistent
	private String hora_solicitud_asignacion;
	
	@Persistent
	private String motivo_catalogacion;
	
	@Persistent
	private String comentarios;	
	
	@Persistent
	private User gestor_negocio;
	
	@Persistent
	private User gestor_it;
	
	@Persistent
	private String tipo;
	
	@Persistent
	private Boolean devuelta;
	
	@Persistent
	private String estado;
	
	@Persistent
	private String catalogacion;
	
	@Persistent
	private String clientekey;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Date getFecha_entrada_peticion() {
		return fecha_entrada_peticion;
	}

	public void setFecha_entrada_peticion(Date fecha_entrada_peticion) {
		this.fecha_entrada_peticion = fecha_entrada_peticion;
	}

	public String getHora_entrada_peticion() {
		return hora_entrada_peticion;
	}

	public void setHora_entrada_peticion(String hora_entrada_peticion) {
		this.hora_entrada_peticion = hora_entrada_peticion;
	}

	public Date getFecha_solicitud_asignacion() {
		return fecha_solicitud_asignacion;
	}

	public void setFecha_solicitud_asignacion(Date fecha_solicitud_asignacion) {
		this.fecha_solicitud_asignacion = fecha_solicitud_asignacion;
	}

	public String getHora_solicitud_asignacion() {
		return hora_solicitud_asignacion;
	}

	public void setHora_solicitud_asignacion(String hora_solicitud_asignacion) {
		this.hora_solicitud_asignacion = hora_solicitud_asignacion;
	}

	public String getMotivo_catalogacion() {
		return motivo_catalogacion;
	}

	public void setMotivo_catalogacion(String motivo_catalogacion) {
		this.motivo_catalogacion = motivo_catalogacion;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public User getGestor_negocio() {
		return gestor_negocio;
	}

	public void setGestor_negocio(User gestor_negocio) {
		this.gestor_negocio = gestor_negocio;
	}

	public User getGestor_it() {
		return gestor_it;
	}

	public void setGestor_it(User gestor_it) {
		this.gestor_it = gestor_it;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getDevuelta() {
		return devuelta;
	}

	public void setDevuelta(Boolean devuelta) {
		this.devuelta = devuelta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCatalogacion() {
		return catalogacion;
	}

	public void setCatalogacion(String catalogacion) {
		this.catalogacion = catalogacion;
	}

	public String getClientekey() {
		return clientekey;
	}

	public void setClientekey(String clientekey) {
		this.clientekey = clientekey;
	}
	
	
	
	
	
	
	

}
