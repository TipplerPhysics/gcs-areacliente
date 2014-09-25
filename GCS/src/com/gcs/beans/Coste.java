package com.gcs.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Coste {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private String nombre;
	
	@Persistent
	private Long clienteKey;
	
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
	private String num_validacion;
	
	@Persistent
	private String comentarios;
	
	@Persistent
	private Date fecha_solicitud_valoracion;
	
	@Persistent
	private String str_fecha_solicitud_valoracion;
	
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
	private String coste_gestion;
	
	@Persistent
	private String total_horas;
	
	@Persistent
	private String total_coste;
	
	
	
	
	
	

}
