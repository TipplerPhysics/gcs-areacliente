package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.ConectividadProyecto;
import com.gcs.persistence.PMF;

public class ConectividadProyectoDao {
	public static ConectividadProyectoDao getInstance() {
		return new ConectividadProyectoDao();
	}
	
	public synchronized void createConectividadProyecto(ConectividadProyecto imp) {
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
	public List<ConectividadProyecto> getAllConectividadProyectoes() {

		List<ConectividadProyecto> ConectividadProyectoes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + ConectividadProyecto.class.getName());	
		q.setOrdering("name asc");
		ConectividadProyectoes = (List<ConectividadProyecto>) q.execute();
		
		
		pm.close();

		return ConectividadProyectoes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ConectividadProyecto> getConectividadProyectoesByName(String name) {

		ConectividadProyectoDao conectividadProyectoDao = ConectividadProyectoDao.getInstance();
		
		List<ConectividadProyecto> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + ConectividadProyecto.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<ConectividadProyecto>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public ConectividadProyecto getConectividadProyectoById(long l) {
		
		ConectividadProyecto s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		ConectividadProyecto ConectividadProyecto_temp = pManager.getObjectById(ConectividadProyecto.class, l);

		s = pManager.detachCopy(ConectividadProyecto_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ConectividadProyectoDao sDao = ConectividadProyectoDao.getInstance();
		List<ConectividadProyecto> servicios =sDao.getAllConectividadProyectoes();
		for (ConectividadProyecto s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
}
