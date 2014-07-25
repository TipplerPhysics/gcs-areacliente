package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorDemanda;
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
	
	public void  createDemanda(Demanda d){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
		
		try{
			pm.makePersistent(d);
			
			
		}finally{
			pm.close();
			cdDao.increaseCont();
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
	
	@SuppressWarnings("unchecked")
	public String countDemandas(){
		
		ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
		Integer a = cdDao.getContador().getNum();
		
		String str = a.toString();
		
		if (str.length()<=1){
			str = "000"+str;
		}else if (str.length()<=2){
			str = "00"+str;
		}else if (str.length()<=3){
			str = "0"+str;
		}
		
		return str;
		
		
	}
}
