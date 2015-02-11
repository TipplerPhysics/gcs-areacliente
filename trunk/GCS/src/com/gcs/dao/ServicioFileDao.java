package com.gcs.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Pais;
import com.gcs.beans.ServicioFile;
import com.gcs.persistence.PMF;


public class ServicioFileDao {
	
	public static ServicioFileDao getInstance() {
		return new ServicioFileDao();
	}
	
	public synchronized void createServicioFile(ServicioFile imp) {
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
	public List<ServicioFile> getAllServicios() {

		List<ServicioFile> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + ServicioFile.class.getName());
		q.setOrdering("name asc");
		Servicios = (List<ServicioFile>) q.execute();
		
		
		pm.close();

		return Servicios;
	}
	
	@SuppressWarnings("unchecked")
	public List<ServicioFile> getAllServiciosForPais(Pais pais) {

		PaisDao paisDao = PaisDao.getInstance();
		
		List<ServicioFile> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + ServicioFile.class.getName()+" where paisId=="+pais.getKey().getId());
		q.setOrdering("name asc");
		Servicios = (List<ServicioFile>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public ServicioFile getServicioFileById(long l) {
		
		ServicioFile s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		ServicioFile servicioFile_temp = pManager.getObjectById(ServicioFile.class, l);

		s = pManager.detachCopy(servicioFile_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ServicioFileDao sDao = ServicioFileDao.getInstance();
		List<ServicioFile> servicios =sDao.getAllServicios();
		for (ServicioFile s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}


	}
}
