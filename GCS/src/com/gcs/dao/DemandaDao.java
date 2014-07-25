package com.gcs.dao;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Demanda;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class DemandaDao {
	
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public static DemandaDao getInstance() {
        return new DemandaDao();
    }
	
	public void  createDemanda(Demanda d){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(d);
		}finally{
			pm.close();
		}
	}

}
