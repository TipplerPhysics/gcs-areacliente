package com.gcs.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Pais;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public class ServicioFileDao {
	public static final int DATA_SIZE = 10;
	public static ServicioFileDao getInstance() {
		return new ServicioFileDao();
	}
	
	public synchronized void createServicioFile(ServicioFile imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		Boolean isNew = false;
		if (imp.getKey()==null)
			isNew = true;
		
		try{
			
		
			try {
				
				pm.makePersistent(imp);
			} finally {}
			
			
		} finally{
			pm.close();
			if (isNew){
				ContadorServicioFileDao csfDao = ContadorServicioFileDao.getInstance();
				csfDao.increaseCont();
			}
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
	
public List<ServicioFile> getAllServicesPagin(Integer page) {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("ServicioFile");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<ServicioFile> servicios = new ArrayList<ServicioFile>();	;
		
		for (Entity result : entities){
			servicios.add(buildServicios(result));
		}

		return servicios;
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
	public ServicioFile getServicioFileByNamePais(String name,String pais) {
		
		List<ServicioFile> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PaisDao paisDao = PaisDao.getInstance();
		Pais paisenty = paisDao.getPaisByName(pais);
		
		Query q = pm.newQuery("select from " + ServicioFile.class.getName()+" where name=='"+name+"'&&  paisId=="+paisenty.getKey().getId()+"");
		q.setOrdering("name asc");
		Servicios = (List<ServicioFile>) q.execute();
		
		ServicioFile servicio = Servicios.get(0);
		
		pm.close();

		return servicio;
		
		
	}
	
	public List<ServicioFile> getServicioByAllParam(String nombre, String pais, Integer page){
		List<ServicioFile> servicios= null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("ServicioFile");
		List<Filter> finalFilters = new ArrayList<>();
		
		int filters =0;
		if(!nombre.equals("")){
			nombre = nombre.toUpperCase();
			filters++;
		}
		if(!pais.equals("")){
			pais= pais.toUpperCase();
			filters++;
		}
		
		
		
		if(filters<=1){
			if(!nombre.equals("")){
				finalFilters.add(new FilterPredicate("name",FilterOperator.GREATER_THAN_OR_EQUAL, nombre));
				finalFilters.add(new FilterPredicate("name",FilterOperator.LESS_THAN, nombre+"\ufffd"));
			}
			if(!pais.equals("")){
				finalFilters.add(new FilterPredicate("pais",FilterOperator.GREATER_THAN_OR_EQUAL, pais));
				finalFilters.add(new FilterPredicate("pais",FilterOperator.LESS_THAN, pais+"\ufffd"));
			}
			
			
		
			
			Filter finalFilter = null;
			if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
			if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
			if(finalFilters.size()!=0)q.setFilter(finalFilter);
			
			List<Entity> entities = null;
			FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
			if(page != null) {
				Integer offset = page * DATA_SIZE;
				fetchOptions.limit(DATA_SIZE);	
				fetchOptions.offset(offset);
			}
			
			entities = datastore.prepare(q).asList(fetchOptions);
			servicios = new ArrayList<>();
			for(Entity result:entities){
				servicios.add(buildServicios(result));
			}
			ServicioFile impPage = new ServicioFile();
			impPage.setDetalle("0");
			servicios.add(impPage);
		
		}else{
			
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!nombre.equals("")){
				q = new com.google.appengine.api.datastore.Query("ServicioFile");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("name",FilterOperator.GREATER_THAN_OR_EQUAL, nombre));
				finalFilters.add(new FilterPredicate("name",FilterOperator.LESS_THAN, nombre+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!pais.equals("")){
				q = new com.google.appengine.api.datastore.Query("ServicioFile");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("pais",FilterOperator.GREATER_THAN_OR_EQUAL, pais));
				finalFilters.add(new FilterPredicate("pais",FilterOperator.LESS_THAN, pais+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			
			
			List<Entity> serviciosFinal = new ArrayList<>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			serviciosFinal = Entities.get(lowRowsIndex);
			List<Entity> indexDel = new ArrayList<Entity>();
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int j = 0;
					for (Entity result : serviciosFinal) {
						if(!Entities.get(i).contains(result)){
							Entity auxEnty = serviciosFinal.get(j);
							if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
						}
						j++;
					}
				}
			}
			
			for (Entity impborr : indexDel){
				serviciosFinal.remove(impborr);
			}
			
			servicios = new ArrayList<ServicioFile>();
			int serviciosPages  = serviciosFinal.size();
			for(int i = page*10; i< (page*10)+10&&i<serviciosFinal.size();i++){
				servicios.add(buildServicios(serviciosFinal.get(i)));
			}
			ServicioFile pages = new ServicioFile();
			pages.setDetalle(Integer.toString(serviciosPages));
			servicios.add(pages);
		}
		return servicios;
	}
	
	
	
	public List<ServicioFile> getServiciosFileByNamePais(String name,String pais) {
		
		List<ServicioFile> Servicios = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PaisDao paisDao = PaisDao.getInstance();
		List<Pais> paisenty = paisDao.getPaisesByName(pais);
		if (paisenty.size()==1){
			Query q = pm.newQuery("select from " + ServicioFile.class.getName()+" where name=='"+name+"'&&  paisId=="+paisenty.get(0).getKey().getId()+"");
			q.setOrdering("name asc");
			Servicios = (List<ServicioFile>) q.execute();
		}
		pm.close();

		return Servicios;
		
		
	}
	
	public ServicioFile getServicioFileByNamePais(String name,long pais) {
		
		List<ServicioFile> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();

		
		Query q = pm.newQuery("select from " + ServicioFile.class.getName()+" where name=='"+name+"'&&  paisId=="+pais+"");
		q.setOrdering("name asc");
		Servicios = (List<ServicioFile>) q.execute();
		
		ServicioFile servicio = Servicios.get(0);
		
		pm.close();

		return servicio;
		
		
	}
	
	private ServicioFile buildServicios(Entity entity) {
		ServicioFile servicios = new ServicioFile();
		
		servicios.setKey(entity.getKey());
		
		
		
		String name =  getString(entity, "name");
		if(name != null) {
			servicios.setName(name.toUpperCase());
		}
		
		long paisId =  getLong(entity, "paisId");
		servicios.setPaisId(paisId);
		
		String strPaisId = String.valueOf(paisId);
		if(strPaisId != null) {
		servicios.setStrPaisId(strPaisId);
		}
			
		PaisDao pdao = new PaisDao();
		String paisNombre = pdao.getSPaisById(paisId);
		servicios.setNombrePais(paisNombre);
		
		ArrayList<String> extensiones =  getArray(entity, "extensiones");
		if(extensiones != null) {
			servicios.setExtensiones(extensiones);
		}
		
		String strExtensiones = "";
		for (int i=0;i<extensiones.size();i++){
			   strExtensiones =strExtensiones.concat(extensiones.get(i)+" ");
			}
		if(strExtensiones != null) {
			servicios.setStrExtensiones(strExtensiones.toUpperCase());
			}
	
		return servicios;
		
	}
	
	
	private String getString(Entity e, String field) {
		try {
			return (String) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
	private Long getLong(Entity e, String field) {
		try {
			return (Long) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
	private ArrayList<String> getArray(Entity e, String field) {
		try {
			return (ArrayList<String>) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ServicioFileDao sDao = ServicioFileDao.getInstance();
		List<ServicioFile> servicios =sDao.getAllServicios();
		for (ServicioFile s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}

		pm.close();
	}
}
