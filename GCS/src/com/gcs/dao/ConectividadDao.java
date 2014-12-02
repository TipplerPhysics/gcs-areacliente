package com.gcs.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;

public class ConectividadDao {
	
	public static ConectividadDao getInstance() {
		return new ConectividadDao();
	}
	
	
	public void createConectividad(Conectividad c, String usermail) throws ParseException {
		
		Boolean isNew = false;
		
		if (c.getKey() == null)
			isNew = true;
		
		if (c.getStr_reglas_firewall()!=null && !"".equals(c.getStr_reglas_firewall())){
			c.setReglas_firewall(Utils.dateConverter(c.getStr_reglas_firewall()));
		}
		
		if (c.getStr_fecha_fin_certificado()!=null && !"".equals(c.getStr_fecha_fin_certificado())){
			c.setFecha_fin_certificado(Utils.dateConverter(c.getStr_fecha_fin_certificado()));
		}
		
		if (c.getStr_fecha_fin_conectividad()!=null && !"".equals(c.getStr_fecha_fin_conectividad())){
			c.setFecha_fin_conectividad(Utils.dateConverter(c.getStr_fecha_fin_conectividad()));
		}
		
		
		if (c.getStr_fecha_fin_infraestructura()!=null && !"".equals(c.getStr_fecha_fin_infraestructura())){
			c.setFecha_fin_infraestructura(Utils.dateConverter(c.getStr_fecha_fin_infraestructura()));
		}
		
		if (c.getStr_fecha_ini_infraestructura()!=null && !"".equals(c.getStr_fecha_ini_infraestructura())){
			c.setFecha_ini_infraestructura(Utils.dateConverter(c.getStr_fecha_ini_infraestructura()));
		}
		
		if (c.getStr_fecha_implantacion()!=null && !"".equals(c.getStr_fecha_implantacion())){
			c.setFecha_implantacion(Utils.dateConverter(c.getStr_fecha_implantacion()));
		}
		
		if (c.getStr_fecha_fin_seguridad()!=null && !"".equals(c.getStr_fecha_fin_seguridad())){
			c.setFecha_fin_seguridad(Utils.dateConverter(c.getStr_fecha_fin_seguridad()));
		}
		
		if (c.getStr_fecha_ini_seguridad()!=null && !"".equals(c.getStr_fecha_ini_seguridad())){
			c.setFecha_ini_seguridad(Utils.dateConverter(c.getStr_fecha_ini_seguridad()));
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try {
			pm.makePersistent(c);
		} finally {
			pm.close();
			
			if (isNew)
				Utils.writeLog(usermail, "Creó", "Conectividad", c.getNombre_proyecto());	
			else
				Utils.writeLog(usermail, "Editó", "Conectividad", c.getNombre_proyecto());
		}
	}
	
	public Conectividad getConectividadByProject(Long id) {

		Conectividad c = new Conectividad();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Conectividad.class.getName()
				+ " where key_proyecto  == :p1";

		List<Conectividad> conectividad = (List<Conectividad>) pManager.newQuery(queryStr).execute(id);

		if (!conectividad.isEmpty()) {
			c = conectividad.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;

	}
	
	public List<Conectividad> getConectividadesByEstado(String estado){
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();
		
		String queryStr = "select from " + Conectividad.class.getName();
				//+ " where estado == ':p1'";
		
		@SuppressWarnings({ "unchecked", "unused" })
		List<Conectividad> conectividades = (List<Conectividad>) pManager.newQuery(queryStr).execute(estado);
		
		if (conectividades.isEmpty()) {
		
			conectividades = null;
		}
		return conectividades;
	}
	
	public List<Conectividad> getConectividadEnCurso(){
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();
		
		String queryStr = "select from " + Conectividad.class.getName()
				//+ " where estadoImplantacion  =="+EstadoImplantacion;
		+ " where estadoImplantacion  == 'Solicitado' || estadoImplantacion  == 'Confirmado'";
		
		@SuppressWarnings({ "unchecked", "unused" })
		List<Conectividad> conectividades = (List<Conectividad>) pManager.newQuery(queryStr).execute();
		
		if (conectividades.isEmpty()) {
		
			conectividades = null;
		}
		return conectividades;
	}

}
