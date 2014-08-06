package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Cliente;
import com.gcs.beans.ContadorDemanda;
import com.gcs.beans.Demanda;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class DemandaDao {
	
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public static DemandaDao getInstance() {
        return new DemandaDao();
    }
	
	public void deleteDemanda(Demanda d){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( d.getClass(), d.getKey().getId())); 
		pm.close();
		
	}
	
	   public Demanda getDemandaById(long l) {
	       PersistenceManager pManager = PMF.get().getPersistenceManager();
	       Demanda demanda_temp = pManager.getObjectById(Demanda.class, l);	      
	       Demanda demanda = pManager.detachCopy(demanda_temp);  
	       pManager.close();
		      
	       return demanda;
   }
	   
	public void createDemandaAndIncreaseCount(Demanda demanda){
		
			createDemanda(demanda);
			ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();			
			cdDao.increaseCont();
		
		
	}
	
	public void  createDemanda(Demanda demanda){
		
		ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
		Integer count = cdDao.getContadorValue();
		
		// guarda id de peticion si no existe
		if (demanda.getKey()==null){
			String codPeticion = "PET_" + String.format("%07d", count);
			demanda.setCod_peticion(codPeticion);
		}
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClienteById(demanda.getClientekey());
		
		demanda.setClienteName(c.getNombre());
		
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		try {
			
			
			
			pm.makePersistent(demanda);
			
		
			
		} finally{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Demanda> getAllDemandas(){
		
		List<Demanda> demandas;		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		Query q = pm.newQuery("select from " + Demanda.class.getName());		
		q.setOrdering("fecha_entrada_peticion desc");
		demandas = (List<Demanda>) q.execute();
		
		return demandas;
	}
	
	
}
