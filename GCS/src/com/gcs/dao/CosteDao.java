package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Coste;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;

public class CosteDao {
	
	public static CosteDao getInstance(){
		return new CosteDao();
	}
	
	public void createCoste(Coste c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(c);
		} finally {
			pm.close();
		}
	}
	
	public List<Coste> getAllCostes() {

		List<Coste> costes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryStr = "select from " + Coste.class.getName();
		costes = (List<Coste>) pm.newQuery(queryStr).execute();

		return costes;
	}

	public void deleteCoste(Coste c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(c.getClass(), c.getKey().getId()));
		pm.close();

	}
	
public Coste getCostebyId(long l) {
		
		Coste coste;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Coste coste_temp = pManager.getObjectById(Coste.class, l);

		coste = pManager.detachCopy(coste_temp);
		pManager.close();

		}catch(Exception e){
			coste=null;
		}
		
		return coste;
		
		
	}
}
