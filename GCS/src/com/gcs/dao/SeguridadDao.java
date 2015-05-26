package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Seguridad;
import com.gcs.persistence.PMF;

public class SeguridadDao {
	public static SeguridadDao getInstance() {
		return new SeguridadDao();
	}
	
	public synchronized void createSeguridad(Seguridad imp) {
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
	public List<Seguridad> getAllSeguridad() {

		List<Seguridad> Seguridad;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Seguridad.class.getName());	
		q.setOrdering("name asc");
		Seguridad = (List<Seguridad>) q.execute();
		
		
		pm.close();

		return Seguridad;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Seguridad> getSeguridadByName(String name) {

		SeguridadDao seguridadDao = SeguridadDao.getInstance();
		
		List<Seguridad> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Seguridad.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<Seguridad>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public Seguridad getSeguridadById(long l) {
		
		Seguridad s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Seguridad Seguridad_temp = pManager.getObjectById(Seguridad.class, l);

		s = pManager.detachCopy(Seguridad_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		SeguridadDao sDao = SeguridadDao.getInstance();
		List<Seguridad> servicios =sDao.getAllSeguridad();
		for (Seguridad s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
		pm.close();
	}
	
}
