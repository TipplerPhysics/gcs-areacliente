package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ProyectoDao {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ProyectoDao getInstance(){
		return new ProyectoDao();
	}
	
	public void createProject(Proyecto p){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(p);
		}finally{
			pm.close();
		}		
	}
	
	public void deleteProject(Proyecto p){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(p.getClass(), p.getKey().getId()));
		pm.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> getAllProjects() {

		List<Proyecto> proyectos;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryStr = "select from " + Proyecto.class.getName();
		proyectos = (List<Proyecto>) pm.newQuery(queryStr).execute();

		return proyectos;
	}
	
public Proyecto getProjectbyId(long l) {
		
		Proyecto p;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Proyecto project_temp = pManager.getObjectById(Proyecto.class, l);

		p = pManager.detachCopy(project_temp);
		pManager.close();

		}catch(Exception e){
			p=null;
		}
		
		return p;
		
		
	}

}
