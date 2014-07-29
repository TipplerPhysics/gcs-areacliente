package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Demanda;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DemandaDao {
	
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public static DemandaDao getInstance() {
        return new DemandaDao();
    }
	
	public void deleteDemanda(Demanda d){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( d.getClass(), d.getKey().getId() ) ); 
		pm.close();
		
	}
	
	   public Demanda getDemandaById(long l) {
           PersistenceManager pManager = PMF.get().getPersistenceManager();
           Demanda demanda_temp = pManager.getObjectById(Demanda.class, l);
          
           Demanda demanda = pManager.detachCopy(demanda_temp);  
            pManager.close();

          
           return demanda;
   }
	
	public void  createDemanda(Demanda demanda){
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		try {
			pm.makePersistent(demanda);
			
			// guarda id de peticion
			String codPeticion = "PET_" + String.format("%07d", demanda.getSequence());
			demanda.setCod_peticion(codPeticion);
			pm.makePersistent(demanda);
		} finally{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Demanda> getAllDemandas(){
		
		List<Demanda> demandas;		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + Demanda.class.getName();		
		demandas = (List<Demanda>) pm.newQuery(queryStr).execute();
		
		return demandas;
	}
	
	
}
