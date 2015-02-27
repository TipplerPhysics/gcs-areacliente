package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Pais;
import com.gcs.persistence.PMF;

public class PaisDao {
	public static PaisDao getInstance() {
		return new PaisDao();
	}
	
	public synchronized void createPais(Pais imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try{
			
			//ServicioDao impDao = ServicioDao.getInstance();
		
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		}finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pais> getAllPaises() {

		List<Pais> Paises;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Pais.class.getName());	
		q.setOrdering("name asc");
		Paises = (List<Pais>) q.execute();
		
		
		pm.close();

		return Paises;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Pais> getPaisesByName(String name) {

		PaisDao paisDao = PaisDao.getInstance();
		
		List<Pais> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Pais.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<Pais>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	@SuppressWarnings("unchecked")
	public Pais getPaisByName(String name) {

		PaisDao paisDao = PaisDao.getInstance();
		
		List<Pais> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Pais.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<Pais>) q.execute();
		
		
		
		pm.close();

		return Servicios.get(0);
	}
	public Pais getPaisById(long l) {
		
		Pais s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Pais Pais_temp = pManager.getObjectById(Pais.class, l);

		s = pManager.detachCopy(Pais_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PaisDao sDao = PaisDao.getInstance();
		List<Pais> servicios =sDao.getAllPaises();
		for (Pais s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
	
}
