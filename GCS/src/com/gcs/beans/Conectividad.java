package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Conectividad {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private String nombre_proyecto;
	
	@Persistent
	private Long key_proyecto;
	
	@Persistent
	private Date fecha_ini_infraestructura;
	
	@Persistent
	private Date fecha_fin_infraestructura;
	
	@Persistent
	private String str_fecha_ini_infraestructura;
	
	@Persistent
	private String str_fecha_fin_infraestructura;
	
	@Persistent
	private Date fecha_implantacion;
	
	@Persistent
	private String str_fecha_implantacion;
	
	@Persistent
	private Date reglas_firewall;	

	@Persistent
	private String str_reglas_firewall;
	
	@Persistent
	private Date fecha_fin_certificado;
	
	@Persistent
	private String str_fecha_fin_certificado;
	
	@Persistent
	private String seguridad;
	
	@Persistent
	private Date fecha_ini_seguridad;
	
	@Persistent
	private Date fecha_fin_seguridad;
	
	@Persistent
	private String str_fecha_ini_seguridad;
	
	@Persistent
	private String str_fecha_fin_seguridad;
	
	public Conectividad() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStr_fecha_implantacion() {
		return str_fecha_implantacion;
	}

	public void setStr_fecha_implantacion(String str_fecha_implantacion) {
		this.str_fecha_implantacion = str_fecha_implantacion;
	}

	public String getStr_reglas_firewall() {
		return str_reglas_firewall;
	}

	public void setStr_reglas_firewall(String str_reglas_firewall) {
		this.str_reglas_firewall = str_reglas_firewall;
	}

	public String getStr_fecha_fin_certificado() {
		return str_fecha_fin_certificado;
	}

	public void setStr_fecha_fin_certificado(String str_fecha_fin_certificado) {
		this.str_fecha_fin_certificado = str_fecha_fin_certificado;
	}

	public String getStr_fecha_ini_seguridad() {
		return str_fecha_ini_seguridad;
	}

	public void setStr_fecha_ini_seguridad(String str_fecha_ini_seguridad) {
		this.str_fecha_ini_seguridad = str_fecha_ini_seguridad;
	}

	public String getStr_fecha_fin_seguridad() {
		return str_fecha_fin_seguridad;
	}

	public void setStr_fecha_fin_seguridad(String str_fecha_fin_seguridad) {
		this.str_fecha_fin_seguridad = str_fecha_fin_seguridad;
	}

	public String getNombre_proyecto() {
		return nombre_proyecto;
	}

	public void setNombre_proyecto(String nombre_proyecto) {
		this.nombre_proyecto = nombre_proyecto;
	}

	public Long getKey_proyecto() {
		return key_proyecto;
	}

	public void setKey_proyecto(Long key_proyecto) {
		this.key_proyecto = key_proyecto;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Date getFecha_ini_infraestructura() {
		return fecha_ini_infraestructura;
	}

	public void setFecha_ini_infraestructura(Date fecha_ini_infraestructura) {
		this.fecha_ini_infraestructura = fecha_ini_infraestructura;
	}

	public Date getFecha_fin_infraestructura() {
		return fecha_fin_infraestructura;
	}

	public void setFecha_fin_infraestructura(Date fecha_fin_infraestructura) {
		this.fecha_fin_infraestructura = fecha_fin_infraestructura;
	}

	public String getStr_fecha_ini_infraestructura() {
		return str_fecha_ini_infraestructura;
	}

	public void setStr_fecha_ini_infraestructura(
			String str_fecha_ini_infraestructura) {
		this.str_fecha_ini_infraestructura = str_fecha_ini_infraestructura;
	}

	public String getStr_fecha_fin_infraestructura() {
		return str_fecha_fin_infraestructura;
	}

	public void setStr_fecha_fin_infraestructura(
			String str_fecha_fin_infraestructura) {
		this.str_fecha_fin_infraestructura = str_fecha_fin_infraestructura;
	}

	public Date getFecha_implantacion() {
		return fecha_implantacion;
	}

	public void setFecha_implantacion(Date fecha_implantacion) {
		this.fecha_implantacion = fecha_implantacion;
	}

	public Date getReglas_firewall() {
		return reglas_firewall;
	}

	public void setReglas_firewall(Date reglas_firewall) {
		this.reglas_firewall = reglas_firewall;
	}

	public Date getFecha_fin_certificado() {
		return fecha_fin_certificado;
	}

	public void setFecha_fin_certificado(Date fecha_fin_certificado) {
		this.fecha_fin_certificado = fecha_fin_certificado;
	}

	public String getSeguridad() {
		return seguridad;
	}

	public void setSeguridad(String seguridad) {
		this.seguridad = seguridad;
	}

	public Date getFecha_ini_seguridad() {
		return fecha_ini_seguridad;
	}

	public void setFecha_ini_seguridad(Date fecha_ini_seguridad) {
		this.fecha_ini_seguridad = fecha_ini_seguridad;
	}

	public Date getFecha_fin_seguridad() {
		return fecha_fin_seguridad;
	}

	public void setFecha_fin_seguridad(Date fecha_fin_seguridad) {
		this.fecha_fin_seguridad = fecha_fin_seguridad;
	}

	public Date getFecha_fin_conectividad() {
		return fecha_fin_conectividad;
	}

	public void setFecha_fin_conectividad(Date fecha_fin_conectividad) {
		this.fecha_fin_conectividad = fecha_fin_conectividad;
	}

	@Persistent
	private Date fecha_fin_conectividad;
	
	
}
