package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Estados;
import com.gcs.persistence.PMF;

public class EstadosDao {
	public static EstadosDao getInstance() {
		return new EstadosDao();
	}
	
	public synchronized void createEstados(Estados imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try{
			
			//EstadosDao impDao = EstadosDao.getInstance();
		
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		}finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Estados> getAllEstados() {

		List<Estados> Estados;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Estados.class.getName());	
		q.setOrdering("name asc");
		Estados = (List<Estados>) q.execute();
		
		
		pm.close();

		return Estados;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Estados> getEstadosByName(String name) {

		EstadosDao estadosDao = EstadosDao.getInstance();
		
		List<Estados> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Estados.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<Estados>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public Estados getEstadosById(long l) {
		
		Estados s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Estados Estados_temp = pManager.getObjectById(Estados.class, l);

		s = pManager.detachCopy(Estados_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		EstadosDao sDao = EstadosDao.getInstance();
		List<Estados> servicios =sDao.getAllEstados();
		for (Estados s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
	
}
