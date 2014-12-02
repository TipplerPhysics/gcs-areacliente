package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Cliente;
import com.gcs.beans.Implantacion;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ImplantacionDao {
	
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public static ImplantacionDao getInstance() {
        return new ImplantacionDao();
    }
	
	public void deleteImplantacion(Implantacion d, String usermail){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( d.getClass(), d.getKey().getId())); 
		pm.close();
		
		Utils.writeLog(usermail, "Eliminó", "Implantaci�n", d.getServicio());
	}
	
	   public Implantacion getImplantacionById(long l) {
	       PersistenceManager pManager = PMF.get().getPersistenceManager();
	       Implantacion implantacion_temp = pManager.getObjectById(Implantacion.class, l);	      
	       Implantacion implantacion = pManager.detachCopy(implantacion_temp);  
	       pManager.close();
		      
	       return implantacion;
   }
	   
	public void createImplantacionAndIncreaseCount(Implantacion implantacion, String usermail){
		
			createImplantacion(implantacion,usermail);
			ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();			
			cdDao.increaseCont();
		
		
	}
	
	public void createImplantacion(Implantacion implantacion, String usermail){
		
		Boolean isNew = false;
		
		ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
		Integer count = cdDao.getContadorValue();
		
		// guarda id de implantacion si no existe
		if (implantacion.getKey()==null){
			String codServicio = "IMP_" + String.format("%07d", count);
			implantacion.setServicio(codServicio);
			isNew=true;
		}
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClienteById(implantacion.getClientekey());
		
		implantacion.setClienteName(c.getNombre());
		
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		try {
			
			
			
			pm.makePersistent(implantacion);
			
		
			
		} finally{
			pm.close();
			
			if (isNew)
				Utils.writeLog(usermail, "Creó", "Implantacion", implantacion.getServicio());
			else
				Utils.writeLog(usermail, "Editó", "Implantacion", implantacion.getServicio());

		}
	}

	@SuppressWarnings("unchecked")
	public List<Implantacion> getAllImplantaciones(){
		
		List<Implantacion> implantaciones;		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		Query q = pm.newQuery("select from " + Implantacion.class.getName());		
		q.setOrdering("fecha_entrada_peticion desc");
		implantaciones = (List<Implantacion>) q.execute();
		
		return implantaciones;
	}

}
