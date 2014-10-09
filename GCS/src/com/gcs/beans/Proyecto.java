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
	
	@Persistent
	private String producto;
	
	@Persistent
	private String conectividad;
	
	@Persistent
	private Date fecha_inicio_valoracion;
	
	@Persistent
	private String str_fecha_inicio_valoracion;
	
	@Persistent
	private Date fecha_fin_valoracion;
	
	@Persistent
	private String str_fecha_fin_valoracion;
	
	@Persistent
	private Date fecha_inicio_viabilidad;
	
	@Persistent
	private String str_fecha_inicio_viabilidad;
	
	@Persistent
	private Date fecha_fin_viabilidad;
	
	@Persistent
	private String str_fecha_fin_viabilidad;
	
	@Persistent
	private String servicio;

	public Proyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getConectividad() {
		return conectividad;
	}

	public void setConectividad(String conectividad) {
		this.conectividad = conectividad;
	}

	public Date getFecha_inicio_valoracion() {
		return fecha_inicio_valoracion;
	}

	public void setFecha_inicio_valoracion(Date fecha_inicio_valoracion) {
		this.fecha_inicio_valoracion = fecha_inicio_valoracion;
	}

	public String getStr_fecha_inicio_valoracion() {
		return str_fecha_inicio_valoracion;
	}

	public void setStr_fecha_inicio_valoracion(String str_fecha_inicio_valoracion) {
		this.str_fecha_inicio_valoracion = str_fecha_inicio_valoracion;
	}

	public Date getFecha_fin_valoracion() {
		return fecha_fin_valoracion;
	}

	public void setFecha_fin_valoracion(Date fecha_fin_valoracion) {
		this.fecha_fin_valoracion = fecha_fin_valoracion;
	}

	public String getStr_fecha_fin_valoracion() {
		return str_fecha_fin_valoracion;
	}

	public void setStr_fecha_fin_valoracion(String str_fin_valoracion) {
		this.str_fecha_fin_valoracion = str_fin_valoracion;
	}

	public Date getFecha_inicio_viabilidad() {
		return fecha_inicio_viabilidad;
	}

	public void setFecha_inicio_viabilidad(Date fecha_inicio_viabilidad) {
		this.fecha_inicio_viabilidad = fecha_inicio_viabilidad;
	}

	public String getStr_fecha_inicio_viabilidad() {
		return str_fecha_inicio_viabilidad;
	}

	public void setStr_fecha_inicio_viabilidad(String str_fecha_inicio_viabilidad) {
		this.str_fecha_inicio_viabilidad = str_fecha_inicio_viabilidad;
	}

	public Date getFecha_fin_viabilidad() {
		return fecha_fin_viabilidad;
	}

	public void setFecha_fin_viabilidad(Date fecha_fin_viabilidad) {
		this.fecha_fin_viabilidad = fecha_fin_viabilidad;
	}

	public String getStr_fecha_fin_viabilidad() {
		return str_fecha_fin_viabilidad;
	}

	public void setStr_fecha_fin_viabilidad(String str_fecha_fin_viabilidad) {
		this.str_fecha_fin_viabilidad = str_fecha_fin_viabilidad;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
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