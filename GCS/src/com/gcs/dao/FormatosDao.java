package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Formatos;
import com.gcs.beans.Servicio;
import com.gcs.persistence.PMF;

public class FormatosDao {
	
	public static FormatosDao getInstance() {
        return new FormatosDao();
    }
	
	public List<Formatos> getAllFormatos() {

		List<Formatos> formatos;
		PersistenceManager pm = PMF.get().getPersistenceManager();
	
		
		Query q = pm.newQuery("select formato_name from " + Formatos.class.getName());		
		q.setOrdering("formato_name asc");
		formatos = (List<Formatos>) q.execute();
		
		
		pm.close();

		return formatos;
	}
	
	

	
	
	
	
	
}
