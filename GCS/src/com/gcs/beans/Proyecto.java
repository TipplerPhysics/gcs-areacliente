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
	private String subtipo;
	
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
	
	private Date fecha_disponible_conectividad;
	
	@Persistent
	private String str_fecha_disponible_conectividad;
	
	@Persistent
	private Date fecha_plan_trabajo;
	
	@Persistent
	private String str_fecha_plan_trabajo;
	
	@Persistent
	private String servicio;
	
	@Persistent
	private Date envioC100;
	
	@Persistent
	private String str_envioC100;
	
	@Persistent
	private Date okNegocio;
	
	@Persistent
	private String str_OKNegocio;
	
	@Persistent
	private String str_OKNegocioCheck;
	
	@Persistent
	private String url_doc_google_drive;

	public Proyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getEnvioC100() {
		return envioC100;
	}

	public void setEnvioC100(Date envioC100) {
		this.envioC100 = envioC100;
	}

	public String getStr_envioC100() {
		return str_envioC100;
	}

	public void setStr_envioC100(String str_envioC100) {
		this.str_envioC100 = str_envioC100;
	}

	public Date getOkNegocio() {
		return okNegocio;
	}

	public void setOkNegocio(Date okNegocio) {
		this.okNegocio = okNegocio;
	}

	public String getStr_OKNegocio() {
		return str_OKNegocio;
	}

	public void setStr_OKNegocio(String str_OKNegocio) {
		this.str_OKNegocio = str_OKNegocio;
	}
	
	public String getStr_OKNegocioCheck() {
		return str_OKNegocioCheck;
	}

	public void setStr_OKNegocioCheck(String str_OKNegocioCheck) {
		this.str_OKNegocioCheck = str_OKNegocioCheck;
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
	
	public Date getFecha_disponible_conectividad() {
		return fecha_disponible_conectividad;
	}

	public void setFecha_disponible_conectividad(Date fecha_disponible_conectividad) {
		this.fecha_disponible_conectividad = fecha_disponible_conectividad;
	}

	public String getStr_fecha_disponible_conectividad() {
		return str_fecha_disponible_conectividad;
	}

	public void setStr_fecha_disponible_conectividad(String str_fecha_disponible_conectividad) {
		this.str_fecha_disponible_conectividad = str_fecha_disponible_conectividad;
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
	
	public Date getFecha_plan_trabajo() {
		return fecha_plan_trabajo;
	}

	public void setFecha_plan_trabajo(Date fecha_plan_trabajo) {
		this.fecha_plan_trabajo = fecha_plan_trabajo;
	}

	public String getStr_fecha_plan_trabajo() {
		return str_fecha_plan_trabajo;
	}

	public void setStr_fecha_plan_trabajo(String str_fecha_plan_trabajo) {
		this.str_fecha_plan_trabajo = str_fecha_plan_trabajo;
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
	
	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
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

	public String getUrl_doc_google_drive() {
		return url_doc_google_drive;
	}

	public void setUrl_doc_google_drive(String url_doc_google_drive) {
		this.url_doc_google_drive = url_doc_google_drive;
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

	@Override
	public String toString() {
		return " key: " + key + "\r\n fecha_alta_str: " + fecha_alta_str
				+ "\r\n fecha_alta: " + fecha_alta + "\r\n clienteKey: " + clienteKey
				+ "\r\n clienteName: " + clienteName + "\r\n clasificacion: "
				+ clasificacion + "\r\n gestor_it: " + gestor_it
				+ "\r\n gestor_it_name: " + gestor_it_name + "\r\n gestor_negocio: "
				+ gestor_negocio + "\r\n gestor_negocio_name: "
				+ gestor_negocio_name + "\r\n coste: " + coste + "\r\n tipo: " + tipo
				+ "\r\n cod_proyecto: " + cod_proyecto + "\r\n producto: " + producto
				+ "\r\n conectividad: " + conectividad
				+ "\r\n fecha_inicio_valoracion: " + fecha_inicio_valoracion
				+ "\r\n str_fecha_inicio_valoracion: "
				+ str_fecha_inicio_valoracion + "\r\n fecha_fin_valoracion: "
				+ fecha_fin_valoracion + "\r\n str_fecha_fin_valoracion: "
				+ str_fecha_fin_valoracion + "\r\n fecha_inicio_viabilidad: "
				+ fecha_inicio_viabilidad + "\r\n str_fecha_inicio_viabilidad: "
				+ str_fecha_inicio_viabilidad + "\r\n fecha_fin_viabilidad: "
				+ fecha_fin_viabilidad + "\r\n str_fecha_fin_viabilidad: "
				+ str_fecha_fin_viabilidad +
				 "\r\n fecha_disponible_conectividad: "
				+ fecha_disponible_conectividad + "\r\n str_fecha_disponible_conectividad: "
				+ str_fecha_disponible_conectividad + "\r\n fecha_plan_trabajo: "
				+ fecha_plan_trabajo + "\r\n str_fecha_plan_trabajo: "
				+ str_fecha_plan_trabajo +"\r\n servicio: " + servicio
				+ "\r\n envioC100: " + envioC100 + "\r\n str_envioC100: "
				+ str_envioC100 + "\r\n okNegocio: " + okNegocio
				+ "\r\n str_OKNegocio: " + str_OKNegocio
				+ "\r\n str_OKNegocioCheck: " + str_OKNegocioCheck
				+ "\r\n url_doc_google_drive: " + url_doc_google_drive + "";
	}
	
}
