package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Coste;
import com.gcs.beans.Formatos;
import com.gcs.beans.Servicio;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.Transaction;

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
	
	public List<Coste> select() {

		List<Coste> coste;
		PersistenceManager pm = PMF.get().getPersistenceManager();
	
		
		Query q = pm.newQuery("select from " + Coste.class.getName());		
		coste = (List<Coste>) q.execute();
		
		
		pm.close();

		return coste;
	}
	
	
	public void update( List<Coste> coste) {

		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		 try {
			 String values = "INN_2015027,INN_2015028";
			 for(int i=0;i<coste.size();i++){
				 
				 String num_control = coste.get(i).getNum_control();
				 if (values.contains(num_control)){
					 Coste c = pm.getObjectById(Coste.class, coste.get(i).getKey().getId());
			         c.setControl_presupuestario("2015"); 
			         pm.makePersistent(c);
				 } 
			 
			 
			
			 }
	        } finally {
	            pm.close();
	        }
	
		
		pm.close();

	}
	
	
	
	
	
}
