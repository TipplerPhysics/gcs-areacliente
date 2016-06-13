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
import com.google.appengine.api.datastore.Query.SortDirection;

public class PaisDao {
	
	public static final int DATA_SIZE = 10;
	
	public static PaisDao getInstance() {
		return new PaisDao();
	}
	
	public synchronized void createPais(Pais imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		Boolean isNew = false;
		if (imp.getKey()==null)
			isNew = true;
		
			
				try {
					pm.makePersistent(imp);
			
				
			} finally{
			pm.close();
			if (isNew){
				ContadorPaisDao cpDao = ContadorPaisDao.getInstance();
				cpDao.increaseCont();
			}
		}
	}
	

	
	@SuppressWarnings("unchecked")
	public List<Pais> getAllPaises() {

		List<Pais> Paises;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Pais.class.getName());	
		q.setOrdering("name asc");
		Paises = (List<Pais>) q.execute();
		
		
		pm.close();

		return Paises;
	}
	
	public List<Pais> getPaisesByAllParam(String nombre, Integer page){
		List<Pais> paises = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Pais");
		List<Filter> finalFilters = new ArrayList<>();
	
		int filters =0;
		if(!nombre.equals("")){
			nombre = nombre.toUpperCase();
			filters++;
		}
		
		if(filters<=1){
			if(!nombre.equals("")){
				finalFilters.add(new FilterPredicate("name",FilterOperator.GREATER_THAN_OR_EQUAL, nombre));
				finalFilters.add(new FilterPredicate("name",FilterOperator.LESS_THAN, nombre+"\ufffd"));
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
			paises = new ArrayList<>();
			for(Entity result:entities){
				paises.add(buildPais(result));
			}
			Pais impPage = new Pais();
			impPage.setDetalle("0");
			paises.add(impPage);
		}
	return paises;
			
		
	}
	/*
	 * 	public List<ServicioFile> getServicioByAllParam(String nombre, String idpais, Integer page){
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

	 */

	@SuppressWarnings("unchecked")
	public List<Pais> getPaisesByName(String name) {

	    List<Pais> paises = null;
	    
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery("select from " + Pais.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		paises = (List<Pais>) q.execute();
		
		
		
		pm.close();

		return paises;
	}

	
	@SuppressWarnings("unchecked")
	public Pais getPaisByName(String name) {

		PaisDao paisDao = PaisDao.getInstance();
		
		List<Pais> paises;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Pais.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		paises = (List<Pais>) q.execute();
		
		
		
		pm.close();

		return paises.get(0);
	}
	public Pais getPaisById(long l) {
		
		Pais s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Pais Pais_temp = pManager.getObjectById(Pais.class, l);

		s = pManager.detachCopy(Pais_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	
public String getSPaisById(long l) {
		
		String pais = "";
		Pais s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Pais Pais_temp = pManager.getObjectById(Pais.class, l);

		s = pManager.detachCopy(Pais_temp);
		pais = s.getName();
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return pais;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PaisDao sDao = PaisDao.getInstance();
		List<Pais> servicios =sDao.getAllPaises();
		for (Pais s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
		
		pm.close();
	}

	public List<Pais> getAllPaisesPagin(Integer page) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Pais").addSort("name",SortDirection.ASCENDING);;
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Pais> paises = new ArrayList<Pais>();	;
		
		for (Entity result : entities){
			paises.add(buildPais(result));
		}
	
		return paises;
	}
	
	
	private Pais buildPais(Entity entity) {
		Pais pais = new Pais();
		
		pais.setKey(entity.getKey());
		
		String name =  getString(entity, "name");
		if(name != null) {
		 pais.setNme(name.toUpperCase());
		}
		
		return pais;
		
	}
	
	private String getString(Entity e, String field) {
		try {
			return (String) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}

	public void deletePais(Pais p){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
	
		
		pm.deletePersistent(pm.getObjectById(p.getClass(),p.getKey().getId()));
		ContadorPaisDao cpDao = ContadorPaisDao.getInstance();
		cpDao.decrementCont();
		
		pm.close();
	}
	
	
}
