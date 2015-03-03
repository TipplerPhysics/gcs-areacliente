package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.TipoPeticion;
import com.gcs.persistence.PMF;

public class TipoPeticionDao {
	public static TipoPeticionDao getInstance() {
		return new TipoPeticionDao();
	}
	
	public synchronized void createTipoPeticion(TipoPeticion imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try{
			
			//TipoPeticionDao impDao = TipoPeticionDao.getInstance();
		
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		}finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoPeticion> getAllTipoPeticion() {

		List<TipoPeticion> TipoPeticion;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + TipoPeticion.class.getName());	
		q.setOrdering("name asc");
		TipoPeticion = (List<TipoPeticion>) q.execute();
		
		
		pm.close();

		return TipoPeticion;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TipoPeticion> getTipoPeticionByName(String name) {

		TipoPeticionDao tipoPeticionDao = TipoPeticionDao.getInstance();
		
		List<TipoPeticion> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + TipoPeticion.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<TipoPeticion>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public TipoPeticion getTipoPeticionById(long l) {
		
		TipoPeticion s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		TipoPeticion TipoPeticion_temp = pManager.getObjectById(TipoPeticion.class, l);

		s = pManager.detachCopy(TipoPeticion_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TipoPeticionDao sDao = TipoPeticionDao.getInstance();
		List<TipoPeticion> servicios =sDao.getAllTipoPeticion();
		for (TipoPeticion s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
	
}
