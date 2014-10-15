package com.gcs.dao;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Servicio;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ServiciosDao {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ServiciosDao getInstance() {
		return new ServiciosDao();
	}
	
	public void deleteServicio(Servicio s){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( s.getClass(), s.getKey().getId())); 
		pm.close();
		
	}
	
	public void createServicio(Servicio s){
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		try {
			pm.makePersistent(s);
			
		} finally{
			pm.close();
		}
	}
}
