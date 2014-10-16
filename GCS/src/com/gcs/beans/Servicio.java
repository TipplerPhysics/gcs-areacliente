package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Servicio {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long gestor_it_key;
	
	@Persistent
	private String gestor_it_name;	

	@Persistent
	private Long gestor_negocio_key;
	
	@Persistent
	private String gestor_negocio_name;	

	@Persistent
	private Long cliente_key;
	
	@Persistent
	private String cliente_name;

	@Persistent
	private String pais;
	
	@Persistent
	private String cod_proyecto;
	
	@Persistent
	private Long id_proyecto;
	
	@Persistent
	private String servicio;
	
	@Persistent
	private String estado;
	
	@Persistent
	private String cod_servicio;
	
	@Persistent
	private String observaciones;
	
	@Persistent
	private String formato_intermedio;
	
	@Persistent
	private String formato_local;
	
	@Persistent
	private String referencia_local1;
	
	@Persistent
	private String referencia_local2;
	
	@Persistent
	private Date fecha_ini_integradas;
	
	@Persistent
	private String str_fecha_ini_integradas;
	
	@Persistent
	private Date fecha_fin_integradas;
	
	@Persistent
	private String str_fecha_fin_integradas;
	
	@Persistent
	private Date fecha_ini_aceptacion;
	
	@Persistent
	private String str_fecha_ini_aceptacion;
	
	@Persistent
	private Date fecha_fin_aceptacion;
	
	@Persistent
	private String str_fecha_fin_aceptacion;
	
	@Persistent
	private Date fecha_ini_validacion;
	
	@Persistent
	private String str_fecha_ini_validacion;
	
	@Persistent
	private Date fecha_fin_validacion;
	
	@Persistent
	private String str_fecha_fin_validacion;
	
	@Persistent
	private Date fecha_implantacion_produccion;
	
	@Persistent
	private String str_fecha_implantacion_produccion;
	
	@Persistent
	private Date fecha_ini_primera_operacion;
	
	@Persistent
	private String str_fecha_ini_primera_operacion;
	
	@Persistent
	private Date fecha_fin_primera_operacion;
	
	@Persistent
	private String str_fecha_fin_primera_operacion;
	
	@Persistent
	private Date fecha_ini_op_cliente;
	
	@Persistent
	private String str_fecha_ini_op_cliente;
	
	@Persistent
	private Date fecha_ANS;
	
	@Persistent
	private String str_fecha_ANS;
	
	@Persistent
	private Date fecha_ini_pruebas;
	
	@Persistent
	private String str_fecha_ini_pruebas;
	
	@Persistent
	private Date fecha_fin_pruebas;
	
	@Persistent
	private String str_fecha_fin_pruebas;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Long getId_proyecto() {
		return id_proyecto;
	}
	
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Long getGestor_it_key() {
		return gestor_it_key;
	}

	public void setGestor_it_key(Long gestor_it_key) {
		this.gestor_it_key = gestor_it_key;
	}

	public String getGestor_it_name() {
		return gestor_it_name;
	}

	public void setGestor_it_name(String gestor_it_name) {
		this.gestor_it_name = gestor_it_name;
	}

	public Long getGestor_negocio_key() {
		return gestor_negocio_key;
	}

	public void setGestor_negocio_key(Long gestor_negocio_key) {
		this.gestor_negocio_key = gestor_negocio_key;
	}

	public String getGestor_negocio_name() {
		return gestor_negocio_name;
	}

	public void setGestor_negocio_name(String gestor_negocio_name) {
		this.gestor_negocio_name = gestor_negocio_name;
	}

	public Long getCliente_key() {
		return cliente_key;
	}

	public void setCliente_key(Long cliente_key) {
		this.cliente_key = cliente_key;
	}

	public String getCliente_name() {
		return cliente_name;
	}

	public void setCliente_name(String cliente_name) {
		this.cliente_name = cliente_name;
	}

	public void setId_proyecto(Long id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

	public String getCod_proyecto() {
		return cod_proyecto;
	}

	public void setCod_proyecto(String cod_proyecto) {
		this.cod_proyecto = cod_proyecto;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCod_servicio() {
		return cod_servicio;
	}

	public void setCod_servicio(String cod_servicio) {
		this.cod_servicio = cod_servicio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFormato_intermedio() {
		return formato_intermedio;
	}

	public void setFormato_intermedio(String formato_intermedio) {
		this.formato_intermedio = formato_intermedio;
	}

	public String getFormato_local() {
		return formato_local;
	}

	public void setFormato_local(String formato_local) {
		this.formato_local = formato_local;
	}

	public String getReferencia_local1() {
		return referencia_local1;
	}

	public void setReferencia_local1(String referencia_local1) {
		this.referencia_local1 = referencia_local1;
	}
	
	public String getReferencia_local2() {
		return referencia_local2;
	}

	public void setReferencia_local2(String referencia_local2) {
		this.referencia_local2 = referencia_local2;
	}

	public Date getFecha_ini_integradas() {
		return fecha_ini_integradas;
	}

	public void setFecha_ini_integradas(Date fecha_ini_integradas) {
		this.fecha_ini_integradas = fecha_ini_integradas;
	}

	public String getStr_fecha_ini_integradas() {
		return str_fecha_ini_integradas;
	}

	public void setStr_fecha_ini_integradas(String str_fecha_ini_integradas) {
		this.str_fecha_ini_integradas = str_fecha_ini_integradas;
	}

	public Date getFecha_fin_integradas() {
		return fecha_fin_integradas;
	}

	public void setFecha_fin_integradas(Date fecha_fin_integradas) {
		this.fecha_fin_integradas = fecha_fin_integradas;
	}

	public String getStr_fecha_fin_integradas() {
		return str_fecha_fin_integradas;
	}

	public void setStr_fecha_fin_integradas(String str_fecha_fin_integradas) {
		this.str_fecha_fin_integradas = str_fecha_fin_integradas;
	}

	public Date getFecha_ini_aceptacion() {
		return fecha_ini_aceptacion;
	}

	public void setFecha_ini_aceptacion(Date fecha_ini_aceptacion) {
		this.fecha_ini_aceptacion = fecha_ini_aceptacion;
	}

	public String getStr_fecha_ini_aceptacion() {
		return str_fecha_ini_aceptacion;
	}

	public void setStr_fecha_ini_aceptacion(String str_fecha_ini_aceptacion) {
		this.str_fecha_ini_aceptacion = str_fecha_ini_aceptacion;
	}

	public Date getFecha_fin_aceptacion() {
		return fecha_fin_aceptacion;
	}

	public void setFecha_fin_aceptacion(Date fecha_fin_aceptacion) {
		this.fecha_fin_aceptacion = fecha_fin_aceptacion;
	}

	public String getStr_fecha_fin_aceptacion() {
		return str_fecha_fin_aceptacion;
	}

	public void setStr_fecha_fin_aceptacion(String str_fecha_fin_aceptacion) {
		this.str_fecha_fin_aceptacion = str_fecha_fin_aceptacion;
	}

	public Date getFecha_ini_validacion() {
		return fecha_ini_validacion;
	}

	public void setFecha_ini_validacion(Date fecha_ini_validacion) {
		this.fecha_ini_validacion = fecha_ini_validacion;
	}

	public String getStr_fecha_ini_validacion() {
		return str_fecha_ini_validacion;
	}

	public void setStr_fecha_ini_validacion(String str_fecha_ini_validacion) {
		this.str_fecha_ini_validacion = str_fecha_ini_validacion;
	}

	public Date getFecha_fin_validacion() {
		return fecha_fin_validacion;
	}

	public void setFecha_fin_validacion(Date fecha_fin_validacion) {
		this.fecha_fin_validacion = fecha_fin_validacion;
	}

	public String getStr_fecha_fin_validacion() {
		return str_fecha_fin_validacion;
	}

	public void setStr_fecha_fin_validacion(String str_fecha_fin_validacion) {
		this.str_fecha_fin_validacion = str_fecha_fin_validacion;
	}

	public Date getFecha_implantacion_produccion() {
		return fecha_implantacion_produccion;
	}

	public void setFecha_implantacion_produccion(Date fecha_implantacion_produccion) {
		this.fecha_implantacion_produccion = fecha_implantacion_produccion;
	}

	public String getStr_fecha_implantacion_produccion() {
		return str_fecha_implantacion_produccion;
	}

	public void setStr_fecha_implantacion_produccion(
			String str_fecha_implantacion_produccion) {
		this.str_fecha_implantacion_produccion = str_fecha_implantacion_produccion;
	}

	public Date getFecha_ini_primera_operacion() {
		return fecha_ini_primera_operacion;
	}

	public void setFecha_ini_primera_operacion(Date fecha_ini_primera_operacion) {
		this.fecha_ini_primera_operacion = fecha_ini_primera_operacion;
	}

	public String getStr_fecha_ini_primera_operacion() {
		return str_fecha_ini_primera_operacion;
	}

	public void setStr_fecha_ini_primera_operacion(
			String str_fecha_ini_primera_operacion) {
		this.str_fecha_ini_primera_operacion = str_fecha_ini_primera_operacion;
	}

	public Date getFecha_fin_primera_operacion() {
		return fecha_fin_primera_operacion;
	}

	public void setFecha_fin_primera_operacion(Date fecha_fin_primera_operacion) {
		this.fecha_fin_primera_operacion = fecha_fin_primera_operacion;
	}

	public String getStr_fecha_fin_primera_operacion() {
		return str_fecha_fin_primera_operacion;
	}

	public void setStr_fecha_fin_primera_operacion(
			String str_fecha_fin_primera_operacion) {
		this.str_fecha_fin_primera_operacion = str_fecha_fin_primera_operacion;
	}

	public Date getFecha_ini_op_cliente() {
		return fecha_ini_op_cliente;
	}

	public void setFecha_ini_op_cliente(Date fecha_ini_op_cliente) {
		this.fecha_ini_op_cliente = fecha_ini_op_cliente;
	}

	public String getStr_fecha_ini_op_cliente() {
		return str_fecha_ini_op_cliente;
	}

	public void setStr_fecha_ini_op_cliente(String str_fecha_ini_op_cliente) {
		this.str_fecha_ini_op_cliente = str_fecha_ini_op_cliente;
	}

	public Date getFecha_ANS() {
		return fecha_ANS;
	}

	public void setFecha_ANS(Date fecha_ANS) {
		this.fecha_ANS = fecha_ANS;
	}

	public String getStr_fecha_ANS() {
		return str_fecha_ANS;
	}

	public void setStr_fecha_ANS(String str_fecha_ANS) {
		this.str_fecha_ANS = str_fecha_ANS;
	}

	public Date getFecha_ini_pruebas() {
		return fecha_ini_pruebas;
	}

	public void setFecha_ini_pruebas(Date fecha_ini_pruebas) {
		this.fecha_ini_pruebas = fecha_ini_pruebas;
	}

	public String getStr_fecha_ini_pruebas() {
		return str_fecha_ini_pruebas;
	}

	public void setStr_fecha_ini_pruebas(String str_fecha_ini_pruebas) {
		this.str_fecha_ini_pruebas = str_fecha_ini_pruebas;
	}

	public Date getFecha_fin_pruebas() {
		return fecha_fin_pruebas;
	}

	public void setFecha_fin_pruebas(Date fecha_fin_pruebas) {
		this.fecha_fin_pruebas = fecha_fin_pruebas;
	}

	public String getStr_fecha_fin_pruebas() {
		return str_fecha_fin_pruebas;
	}

	public void setStr_fecha_fin_pruebas(String str_fecha_fin_pruebas) {
		this.str_fecha_fin_pruebas = str_fecha_fin_pruebas;
	}

	public Date getFecha_migracion_channeling() {
		return fecha_migracion_channeling;
	}

	public void setFecha_migracion_channeling(Date fecha_migracion_channeling) {
		this.fecha_migracion_channeling = fecha_migracion_channeling;
	}

	public String getStr_migracion_channeling() {
		return str_migracion_channeling;
	}

	public void setStr_migracion_channeling(String str_migracion_channeling) {
		this.str_migracion_channeling = str_migracion_channeling;
	}

	public Date getFecha_migracion_infra() {
		return fecha_migracion_infra;
	}

	public void setFecha_migracion_infra(Date fecha_migracion_infra) {
		this.fecha_migracion_infra = fecha_migracion_infra;
	}

	public String getStr_migracion_infra() {
		return str_migracion_infra;
	}

	public void setStr_migracion_infra(String str_migracion_infra) {
		this.str_migracion_infra = str_migracion_infra;
	}

	@Persistent
	private Date fecha_migracion_channeling;
	
	@Persistent
	private String str_migracion_channeling;
	
	@Persistent
	private Date fecha_migracion_infra;
	
	@Persistent
	private String str_migracion_infra;
	
	
}
