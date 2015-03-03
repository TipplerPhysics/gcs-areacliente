package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Departamentos;
import com.gcs.persistence.PMF;

public class DepartamentosDao {
	public static DepartamentosDao getInstance() {
		return new DepartamentosDao();
	}
	
	public synchronized void createDepartamentos(Departamentos imp) {
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
	public List<Departamentos> getAllDepartamentos() {

		List<Departamentos> Departamentos;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Departamentos.class.getName());	
		q.setOrdering("name asc");
		Departamentos = (List<Departamentos>) q.execute();
		
		
		pm.close();

		return Departamentos;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Departamentos> getDepartamentosByName(String name) {

		DepartamentosDao departamentosDao = DepartamentosDao.getInstance();
		
		List<Departamentos> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Departamentos.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<Departamentos>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public Departamentos getDepartamentosById(long l) {
		
		Departamentos s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Departamentos Departamentos_temp = pManager.getObjectById(Departamentos.class, l);

		s = pManager.detachCopy(Departamentos_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DepartamentosDao sDao = DepartamentosDao.getInstance();
		List<Departamentos> servicios =sDao.getAllDepartamentos();
		for (Departamentos s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
	
}
