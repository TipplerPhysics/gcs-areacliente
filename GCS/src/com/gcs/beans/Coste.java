package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Coste {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long clienteKey;
	
	@Persistent
	private String cliente_name;
	
	@Persistent
	private Long projectKey;
	
	@Persistent
	private String project_name;
	
	@Persistent
	private String num_control;
	
	@Persistent
	private String equipos;
	
	@Persistent
	private Date fecha_alta;
	
	@Persistent
	private String str_fecha_alta;

	@Persistent
	private Long gestor_it_key;
	
	@Persistent
	private String gestor_it_name;
	
	@Persistent
	private String num_valoracion;
	
	@Persistent
	private String comentarios;
	
	@Persistent
	private Date fecha_solicitud_valoracion;
	
	@Persistent
	private Date fecha_recepcion_valoracion;
	
	@Persistent
	private String str_fecha_solicitud_valoracion;
	
	@Persistent
	private String str_fecha_recepcion_valoracion;
	
	@Persistent
	private String horas_analisis;
	
	@Persistent
	private String coste_analisis;
	
	@Persistent
	private String horas_diseño;
	
	@Persistent
	private String coste_diseño;
	
	@Persistent
	private String horas_construccion;
	
	@Persistent
	private String coste_construccion;
	
	@Persistent
	private String horas_pruebas;
	
	@Persistent
	private String coste_pruebas;
	
	@Persistent
	private String horas_gestion;
	
	@Persistent
	private String coste_total;
	
	@Persistent
	private String horas_total;
	
	@Persistent
	private String detalle;


	@Persistent
	private String componentes_modificados;
	
	@Persistent
	private Date fecha_implantacion;
	
	@Persistent
	private String str_fecha_implantacion;
	
	@Persistent
	private String str_fecha_garantia;
	
	
	
	@Persistent
	private String control_presupuestario;
	
	@Persistent
	private String tipo_coste;
	
	@Persistent
	private String tipo_desarrollo;
	
	@Persistent
	private String asunto;
	
	@Persistent
	private Date plazo_estimado;
	
	@Persistent
	private String str_plazo_estimado;
	
	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String det) {
		this.detalle = det;
	}
	
	public String getCoste_total() {
		return coste_total;
	}

	public void setCoste_total(String coste_total) {
		this.coste_total = coste_total;
	}

	public String getHoras_total() {
		return horas_total;
	}

	public void setHoras_total(String horas_total) {
		this.horas_total = horas_total;
	}

	public Long getProjectKey() {
		return projectKey;
	}

	public String getGestor_it_name() {
		return gestor_it_name;
	}

	public void setGestor_it_name(String gestor_it_name) {
		this.gestor_it_name = gestor_it_name;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getCliente_name() {
		return cliente_name;
	}

	public void setCliente_name(String cliente_name) {
		this.cliente_name = cliente_name;
	}

	public void setProjectKey(Long projectKey) {
		this.projectKey = projectKey;
	}


	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	
	public Long getClienteKey() {
		return clienteKey;
	}

	public void setClienteKey(Long clienteKey) {
		this.clienteKey = clienteKey;
	}

	public String getNum_control() {
		return num_control;
	}

	public void setNum_control(String num_control) {
		this.num_control = num_control;
	}

	public String getEquipos() {
		return equipos;
	}

	public void setEquipos(String equipos) {
		this.equipos = equipos;
	}

	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public String getStr_fecha_alta() {
		return str_fecha_alta;
	}

	public void setStr_fecha_alta(String str_fecha_alta) {
		this.str_fecha_alta = str_fecha_alta;
	}

	public Long getGestor_it_key() {
		return gestor_it_key;
	}

	public void setGestor_it_key(Long gestor_it_key) {
		this.gestor_it_key = gestor_it_key;
	}

	

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Date getFecha_solicitud_valoracion() {
		return fecha_solicitud_valoracion;
	}

	public void setFecha_solicitud_valoracion(Date fecha_solicitud_valoracion) {
		this.fecha_solicitud_valoracion = fecha_solicitud_valoracion;
	}

	public String getStr_fecha_solicitud_valoracion() {
		return str_fecha_solicitud_valoracion;
	}

	public void setStr_fecha_solicitud_valoracion(
			String str_fecha_solicitud_valoracion) {
		this.str_fecha_solicitud_valoracion = str_fecha_solicitud_valoracion;
	}

	
	public Date getFecha_recepcion_valoracion() {
		return fecha_recepcion_valoracion;
	}

	public void setFecha_recepcion_valoracion(Date fecha_recepcion_valoracion) {
		this.fecha_recepcion_valoracion = fecha_recepcion_valoracion;
	}

	public String getStr_fecha_recepcion_valoracion() {
		return str_fecha_recepcion_valoracion;
	}

	public void setStr_fecha_recepcion_valoracion(
			String str_fecha_recepcion_valoracion) {
		this.str_fecha_recepcion_valoracion = str_fecha_recepcion_valoracion;
	}

	public String getHoras_analisis() {
		return horas_analisis;
	}

	public void setHoras_analisis(String horas_analisis) {
		this.horas_analisis = horas_analisis;
	}

	public String getCoste_analisis() {
		return coste_analisis;
	}

	public void setCoste_analisis(String coste_analisis) {
		this.coste_analisis = coste_analisis;
	}

	public String getHoras_diseño() {
		return horas_diseño;
	}

	public void setHoras_diseño(String horas_diseño) {
		this.horas_diseño = horas_diseño;
	}

	public String getCoste_diseño() {
		return coste_diseño;
	}

	public void setCoste_diseño(String coste_diseño) {
		this.coste_diseño = coste_diseño;
	}

	public String getHoras_construccion() {
		return horas_construccion;
	}

	public String getNum_valoracion() {
		return num_valoracion;
	}

	public void setNum_valoracion(String num_valoracion) {
		this.num_valoracion = num_valoracion;
	}

	public void setHoras_construccion(String horas_construccion) {
		this.horas_construccion = horas_construccion;
	}

	public String getCoste_construccion() {
		return coste_construccion;
	}

	public void setCoste_construccion(String coste_construccion) {
		this.coste_construccion = coste_construccion;
	}

	public String getHoras_pruebas() {
		return horas_pruebas;
	}

	public void setHoras_pruebas(String horas_pruebas) {
		this.horas_pruebas = horas_pruebas;
	}

	public String getCoste_pruebas() {
		return coste_pruebas;
	}

	public void setCoste_pruebas(String coste_pruebas) {
		this.coste_pruebas = coste_pruebas;
	}

	public String getHoras_gestion() {
		return horas_gestion;
	}

	public void setHoras_gestion(String horas_gestion) {
		this.horas_gestion = horas_gestion;
	}

	public String getCoste_gestion() {
		return coste_gestion;
	}

	public void setCoste_gestion(String coste_gestion) {
		this.coste_gestion = coste_gestion;
	}

	@Persistent
	private String coste_gestion;

	public String getControl_presupuestario() {
		return control_presupuestario;
	}

	public void setControl_presupuestario(String control_presupuestario) {
		this.control_presupuestario = control_presupuestario;
	}

	
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Date getPlazo_estimado() {
		return plazo_estimado;
	}

	public void setPlazo_estimado(Date plazo_estimado) {
		this.plazo_estimado = plazo_estimado;
	}

	public String getStr_plazo_estimado() {
		return str_plazo_estimado;
	}

	public void setStr_plazo_estimado(String str_plazo_estimado) {
		this.str_plazo_estimado = str_plazo_estimado;
	}

	public String getTipo_coste() {
		return tipo_coste;
	}

	public void setTipo_coste(String tipo_coste) {
		this.tipo_coste = tipo_coste;
	}

	public String getTipo_desarrollo() {
		return tipo_desarrollo;
	}

	public void setTipo_desarrollo(String tipo_desarrollo) {
		this.tipo_desarrollo = tipo_desarrollo;
	}
	
	public Date getFecha_implantacion() {
		return fecha_implantacion;
	}

	public void setFecha_implantacion(Date fecha_implantacion) {
		this.fecha_implantacion = fecha_implantacion;
	}

	public String getStr_fecha_implantacion() {
		return str_fecha_implantacion;
	}

	public void setStr_fecha_implantacion(
			String str_fecha_implantacion) {
		this.str_fecha_implantacion = str_fecha_implantacion;
	}
	
	public String getStr_fecha_garantia() {
		return str_fecha_garantia;
	}

	public void setStr_fecha_garantia(
			String str_fecha_garantia) {
		this.str_fecha_garantia = str_fecha_garantia;
	}
	public String getComponentes_modificados() {
		return componentes_modificados;
	}

	public void setComponentes_modificados(String componentes_modificados) {
		this.componentes_modificados = componentes_modificados;
	}
	
	
	
	
	
	

	
	
	
	
	
	

}
