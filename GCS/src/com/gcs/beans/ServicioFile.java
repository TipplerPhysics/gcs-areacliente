package com.gcs.beans;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class ServicioFile {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private long paisId;
	
	@Persistent
	private ArrayList<String> extensiones;
	
	@Persistent
	private String nombrePais;
	
	@Persistent
	private String strPaisId;
	
	@Persistent
	private String strExtensiones;
	
	
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public ArrayList<String> getExtensiones() {
		return extensiones;
	}

	public void setExtensiones(ArrayList<String> extensiones) {
		this.extensiones = extensiones;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public long getPaisId() {
		return paisId;
	}

	public void setPaisId(long id) {
		this.paisId = id;
	}
	
	private String detalle;
	
	public String getDetalle() {
		return detalle;
	}
	
	public void setDetalle(String det) {
		this.detalle = det;
	}

	public String getStrPaisId() {
		return strPaisId;
	}

	public void setStrPaisId(String strPaisId) {
		this.strPaisId = strPaisId;
	}

	public String getStrExtensiones() {
		return strExtensiones;
	}

	public void setStrExtensiones(String strExtensiones) {
		this.strExtensiones = strExtensiones;
	}

	

	
	
	
	
	
}