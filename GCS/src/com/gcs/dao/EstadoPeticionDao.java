package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.EstadoPeticion;
import com.gcs.persistence.PMF;

public class EstadoPeticionDao {
	public static EstadoPeticionDao getInstance() {
		return new EstadoPeticionDao();
	}
	
	public synchronized void createEstadoPeticion(EstadoPeticion imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try{
			
			//EstadoPeticionDao impDao = EstadoPeticionDao.getInstance();
		
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		}finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<EstadoPeticion> getAllEstadoPeticion() {

		List<EstadoPeticion> EstadoPeticion;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + EstadoPeticion.class.getName());	
		q.setOrdering("name asc");
		EstadoPeticion = (List<EstadoPeticion>) q.execute();
		
		
		pm.close();

		return EstadoPeticion;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EstadoPeticion> getEstadoPeticionByName(String name) {

		EstadoPeticionDao estPeticionDao = EstadoPeticionDao.getInstance();
		
		List<EstadoPeticion> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + EstadoPeticion.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<EstadoPeticion>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public EstadoPeticion getEstadoPeticionById(long l) {
		
		EstadoPeticion s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		EstadoPeticion EstadoPeticion_temp = pManager.getObjectById(EstadoPeticion.class, l);

		s = pManager.detachCopy(EstadoPeticion_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		EstadoPeticionDao sDao = EstadoPeticionDao.getInstance();
		List<EstadoPeticion> servicios =sDao.getAllEstadoPeticion();
		for (EstadoPeticion s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
	
}
