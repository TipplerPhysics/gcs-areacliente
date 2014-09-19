package com.gcs.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;

public class ProyectoDao {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ProyectoDao getInstance(){
		return new ProyectoDao();
	}
	
	public void createProject(Proyecto p){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClienteById(p.getClienteKey());
		UserDao uDao = UserDao.getInstance();
		User gestor_it = uDao.getUserbyId(p.getGestor_it());
		User gestor_negocio = uDao.getUserbyId(p.getGestor_negocio());
		
		p.setClienteName(c.getNombre());
		p.setGestor_it_name(gestor_it.getNombre()+ " " + gestor_it.getApellido1() + " " + gestor_it.getApellido2());
		p.setGestor_negocio_name(gestor_negocio.getNombre()+ " " + gestor_negocio.getApellido1() + " " + gestor_negocio.getApellido2());
		
		if (p.getKey()==null){
			Integer project_number = c.getProject_number();
			if (project_number==null)
				project_number=1;			
			else
				project_number++;
			
			String cod_proyecto = createProjectName(c,p,project_number);			
			p.setCod_proyecto(cod_proyecto);
			
			c.setProject_number(project_number);
			cDao.createCliente(c);
			
			
		}
		
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
	
public List<Proyecto> getProjectsByClient(Long id){
	
	
	PersistenceManager pManager = PMF.get().getPersistenceManager();
	Transaction transaction = pManager.currentTransaction();
	transaction.begin();

	String queryStr = "select from " + Proyecto.class.getName()
			+ " WHERE clienteKey == :id";

	List<Proyecto> projects = (List<Proyecto>) pManager.newQuery(queryStr)
			.execute(id);

	transaction.commit();

	pManager.close();

	return projects;
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

	private String createProjectName (Cliente c, Proyecto p, Integer project_number){
		String project_name = "";
				
		String cod_cliente = c.getClientId().split("IDGLOBAL")[1];
		
		project_name = p.getTipo()+cod_cliente+"_"+String.format("%02d", project_number);
		
		return project_name;
	}

}

