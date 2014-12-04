package com.gcs.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

public class Informe {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String texto_informe;
	
	@Persistent
	private String anyo_implantacion;
	
	@Persistent
	private String mes_implantacion;
	
	@Persistent
	private String dia_implantacion;
	
	@Persistent
	private String tipo_subida;
	
	public Key getKey() {
		return key;
	}
	
	public String getTextoInforme() {
		return texto_informe;
	}
	
	public void setTextoInforme(String texto_informe) {
		this.texto_informe = texto_informe;
	}
	
	public String getTipoSubida() {
		return tipo_subida;
	}
	
	public void setTipoSubida(String tipo_subida) {
		this.tipo_subida = tipo_subida;
	}
	
	public String getAnyoImplantacion() {
		return anyo_implantacion;
	}
	
	public void setAnyoImplantacion(String anyo_implantacion) {
		this.anyo_implantacion = anyo_implantacion;
	}
	
	public String getMesImplantacion() {
		return mes_implantacion;
	}
	
	public void setMesImplantacion(String mes_implantacion) {
		this.mes_implantacion = mes_implantacion;
	}
	
	public String getDiaImplantacion() {
		return dia_implantacion;
	}
	
	public void setDiaImplantacion(String dia_implantacion) {
		this.dia_implantacion = dia_implantacion;
	}
}
