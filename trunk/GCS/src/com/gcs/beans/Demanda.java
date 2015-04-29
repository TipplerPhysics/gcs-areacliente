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

	@Persistent(valueStrategy = IdGeneratorStrategy.SEQUENCE)
	private Long sequence;

	@Persistent
	private String cod_peticion;

	@Persistent
	private Date fecha_entrada_peticion;
	
	@Persistent
	private String hora_comunicacion_asignacion;
	
	public String getHora_comunicacion_asignacion() {
		return hora_comunicacion_asignacion;
	}

	public void setHora_comunicacion_asignacion(String hora_comunicacion_asignacion) {
		this.hora_comunicacion_asignacion = hora_comunicacion_asignacion;
	}

	public String getStr_fecha_comunicacion_asignacion() {
		return str_fecha_comunicacion_asignacion;
	}

	public void setStr_fecha_comunicacion_asignacion(
			String str_fecha_comunicacion_asignacion) {
		this.str_fecha_comunicacion_asignacion = str_fecha_comunicacion_asignacion;
	}

	public Date getFecha_comunicacion_asignacion() {
		return fecha_comunicacion_asignacion;
	}

	public void setFecha_comunicacion_asignacion(Date fecha_comunicacion_asignacion) {
		this.fecha_comunicacion_asignacion = fecha_comunicacion_asignacion;
	}

	@Persistent
	private String str_fecha_comunicacion_asignacion;
	
	@Persistent
	private Date fecha_comunicacion_asignacion;
	
	
	
	public Date getFecha_comunicacion() {
		return fecha_comunicacion;
	}

	public void setFecha_comunicacion(Date fecha_comunicacion) {
		this.fecha_comunicacion = fecha_comunicacion;
	}

	public String getStr_fecha_comunicacion() {
		return str_fecha_comunicacion;
	}

	public void setStr_fecha_comunicacion(String str_fecha_comunicacion) {
		this.str_fecha_comunicacion = str_fecha_comunicacion;
	}

	public String getHora_comunicacion() {
		return hora_comunicacion;
	}

	public void setHora_comunicacion(String hora_comunicacion) {
		this.hora_comunicacion = hora_comunicacion;
	}

	@Persistent
	private Date fecha_comunicacion;
	
	@Persistent
	private String str_fecha_comunicacion;
	
	@Persistent
	private String hora_comunicacion;	

	@Persistent
	private String str_fecha_entrada_peticion;

	@Persistent
	private String hora_entrada_peticion;

	@Persistent
	private Date fecha_solicitud_asignacion;

	@Persistent
	private String str_fecha_solicitud_asignacion;

	@Persistent
	private String hora_solicitud_asignacion;

	@Persistent
	private String motivo_catalogacion;

	@Persistent
	private String comentarios;

	@Persistent
	private Long gestor_negocio;

	@Persistent
	private Long gestor_it;

	@Persistent
	private String tipo;

	@Persistent
	private Boolean devuelta;

	@Persistent
	private String estado;

	@Persistent
	private String catalogacion;

	@Persistent
	private Long clientekey;
	
	@Persistent
	private String clienteName;
	
	@Persistent
	private String detalle;
	
	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String det) {
		this.detalle = det;
	}

	public String getClienteName() {
		return clienteName;
	}

	public void setClienteName(String clienteName) {
		this.clienteName = clienteName;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getCod_peticion() {
		return cod_peticion;
	}

	public void setCod_peticion(String cod_peticion) {
		this.cod_peticion = cod_peticion;
	}

	public Date getFecha_entrada_peticion() {
		return fecha_entrada_peticion;
	}

	public void setFecha_entrada_peticion(Date fecha_entrada_peticion) {
		this.fecha_entrada_peticion = fecha_entrada_peticion;
	}

	public String getStr_fecha_entrada_peticion() {
		return str_fecha_entrada_peticion;
	}

	public void setStr_fecha_entrada_peticion(String str_fecha_entrada_peticion) {
		this.str_fecha_entrada_peticion = str_fecha_entrada_peticion;
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

	public String getStr_fecha_solicitud_asignacion() {
		return str_fecha_solicitud_asignacion;
	}

	public void setStr_fecha_solicitud_asignacion(
			String str_fecha_solicitud_asignacion) {
		this.str_fecha_solicitud_asignacion = str_fecha_solicitud_asignacion;
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

	public Long getGestor_negocio() {
		return gestor_negocio;
	}

	public void setGestor_negocio(Long gestor_negocio) {
		this.gestor_negocio = gestor_negocio;
	}

	public Long getGestor_it() {
		return gestor_it;
	}

	public void setGestor_it(Long gestor_it) {
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

	public Long getClientekey() {
		return clientekey;
	}

	public void setClientekey(Long clientekey) {
		this.clientekey = clientekey;
	}

}
