package com.gcs.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Cliente;
import com.gcs.beans.ContadorDemanda;
import com.gcs.beans.Demanda;
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

public class DemandaDao {

	public static final int DATA_SIZE = 10;
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public static DemandaDao getInstance() {
        return new DemandaDao();
    }
	
	public void deleteDemanda(Demanda d, String usermail){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( d.getClass(), d.getKey().getId())); 
		pm.close();
		
		//ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();			
		//cdDao.decrementCont();
		
		ContadorPagDemandaDao cpdDao = ContadorPagDemandaDao.getInstance();			
		cpdDao.decrementCont();
		
		Utils.writeLog(usermail, "Eliminó", "Demanda", d.getCod_peticion());
	}
	
	   public Demanda getDemandaById(long l) {
	       PersistenceManager pManager = PMF.get().getPersistenceManager();
	       Demanda demanda_temp = pManager.getObjectById(Demanda.class, l);	      
	       Demanda demanda = pManager.detachCopy(demanda_temp);  
	       pManager.close();
		      
	       return demanda;
   }
	   
	public void createDemandaRaw(Demanda demanda){
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		if(demanda.getCatalogacion()!=null)demanda.setCatalogacion(demanda.getCatalogacion().toUpperCase());
		if(demanda.getComentarios()!=null)demanda.setComentarios(demanda.getComentarios().toUpperCase());
		if(demanda.getDetalle()!=null)demanda.setDetalle(demanda.getDetalle().toUpperCase());
		if(demanda.getEstado()!=null)demanda.setEstado(demanda.getDetalle().toUpperCase());
		if(demanda.getMotivo_catalogacion()!=null)demanda.setMotivo_catalogacion(demanda.getMotivo_catalogacion().toUpperCase());
		
		try{
			pm.makePersistent(demanda);
			
		} finally{
			pm.close();
		}
	}
	   
	public void createDemandaAndIncreaseCount(Demanda demanda, String usermail){
		
			createDemanda(demanda,usermail);
			ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();			
			cdDao.increaseCont();
		
		
	}
	
	public void  createDemanda(Demanda demanda, String usermail){
		
		if(demanda.getCatalogacion()!=null)demanda.setCatalogacion(demanda.getCatalogacion().toUpperCase());
		if(demanda.getComentarios()!=null)demanda.setComentarios(demanda.getComentarios().toUpperCase());
		if(demanda.getDetalle()!=null)demanda.setDetalle(demanda.getDetalle().toUpperCase());
		if(demanda.getEstado()!=null)demanda.setEstado(demanda.getEstado().toUpperCase());
		if(demanda.getMotivo_catalogacion()!=null)demanda.setMotivo_catalogacion(demanda.getMotivo_catalogacion().toUpperCase());
		
		Boolean isNew = false;
		
		ContadorDemandaDao cdDao = ContadorDemandaDao.getInstance();
		Integer count = cdDao.getContadorValue();
		
		ContadorPagDemandaDao cpdDao = ContadorPagDemandaDao.getInstance();
		
		// guarda id de peticion si no existe
		if (demanda.getKey()==null){
			String codPeticion = "PET_" + String.format("%07d", count);
			demanda.setCod_peticion(codPeticion);
			isNew=true;
		}
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClienteById(demanda.getClientekey());
		
		demanda.setClienteName(c.getNombre());
		
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		try {
			
			pm.makePersistent(demanda);
			
		} finally{
			pm.close();
			
			if (isNew){
				Utils.writeLog(usermail, "Creó", "Demanda", demanda.getCod_peticion());
				cpdDao.increaseCont();
			}else
				Utils.writeLog(usermail, "Editó", "Demanda", demanda.getCod_peticion());

		}
	}

	@SuppressWarnings("unchecked")
	public List<Demanda> getAllDemandas(){
		
		List<Demanda> demandas;		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		Query q = pm.newQuery("select from " + Demanda.class.getName());		
		q.setOrdering("fecha_entrada_peticion desc");
		demandas = (List<Demanda>) q.execute();
		
		return demandas;
	}
	
	
	@SuppressWarnings("unchecked")
	public void deleteAllDemandas(){
		
		DemandaDao demDao = DemandaDao.getInstance();
		List<Demanda> demandas = demDao.getAllDemandas();
		for(Demanda dem : demandas){
			demDao.deleteDemanda(dem, "");
		}

	}
	
	public List<Demanda> getDemandaByAllParam(String fechaEntrada, String nCliente, String tipo, String estado,  String codPeticion, Integer page){
		List<Demanda> demandas= null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Demanda");
		List<Filter> finalFilters = new ArrayList<>();
		
		int filters =0;
		if(!fechaEntrada.equals("")){
			filters++;
		}
		if(!nCliente.equals("")){
			filters++;
		}
		if(!tipo.equals("")){
			filters++;
		}
		if(!estado.equals("")){
			filters++;
		}
		if(!codPeticion.equals("")){
			filters++;
		}
		
		
		if(filters<=1){
			if(!fechaEntrada.equals("")){
				finalFilters.add(new FilterPredicate("str_fecha_entrada_peticion",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
				finalFilters.add(new FilterPredicate("str_fecha_entrada_peticion",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
			}
			if(!nCliente.equals("")){
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
			}
			if(!tipo.equals("")){
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.GREATER_THAN_OR_EQUAL, tipo));
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.LESS_THAN, tipo+"\ufffd"));
			}
			if(!estado.equals("")){
				finalFilters.add(new FilterPredicate("estado",FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado",FilterOperator.LESS_THAN, estado+"\ufffd"));
			}
			if(!codPeticion.equals("")){
				finalFilters.add(new FilterPredicate("cod_peticion",FilterOperator.GREATER_THAN_OR_EQUAL, codPeticion));
				finalFilters.add(new FilterPredicate("cod_peticion",FilterOperator.LESS_THAN, codPeticion+"\ufffd"));
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
			demandas = new ArrayList<>();
			for(Entity result:entities){
				try{
					demandas.add(buildDemanda(result));
				}catch(Exception exp) {}
			}
			Demanda impPage = new Demanda();
			impPage.setDetalle("0");
			demandas.add(impPage);
		
		}else{
			
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!fechaEntrada.equals("")){
				q = new com.google.appengine.api.datastore.Query("Demanda");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("str_fecha_entrada_peticion",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
				finalFilters.add(new FilterPredicate("str_fecha_entrada_peticion",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nCliente.equals("")){
				q = new com.google.appengine.api.datastore.Query("Demanda");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!tipo.equals("")){
				q = new com.google.appengine.api.datastore.Query("Demanda");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.GREATER_THAN_OR_EQUAL, tipo));
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.LESS_THAN, tipo+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!estado.equals("")){
				q = new com.google.appengine.api.datastore.Query("Demanda");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("estado",FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado",FilterOperator.LESS_THAN, estado+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!codPeticion.equals("")){
				q = new com.google.appengine.api.datastore.Query("Demanda");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("cod_peticion",FilterOperator.GREATER_THAN_OR_EQUAL, codPeticion));
				finalFilters.add(new FilterPredicate("cod_peticion",FilterOperator.LESS_THAN, codPeticion+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			List<Entity> demandasFinal = new ArrayList<>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			demandasFinal = Entities.get(lowRowsIndex);
			List<Entity> indexDel = new ArrayList<Entity>();
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int j = 0;
					for (Entity result : demandasFinal) {
						if(!Entities.get(i).contains(result)){
							Entity auxEnty = demandasFinal.get(j);
							if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
						}
						j++;
					}
				}
			}
			
			for (Entity impborr : indexDel){
				demandasFinal.remove(impborr);
			}
			
			demandas = new ArrayList<Demanda>();
			int demandasPages  = demandasFinal.size();
			for(int i = page*10; i< (page*10)+10&&i<demandasFinal.size();i++){
				try{
					demandas.add(buildDemanda(demandasFinal.get(i)));
				}catch(Exception exp) {}
			}
			Demanda pages = new Demanda();
			pages.setDetalle(Integer.toString(demandasPages));
			demandas.add(pages);
		}
		return demandas;
	}
	
	public List<Demanda> getAllDemandaPagin(Integer page) {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Demanda");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Demanda> demandas = new ArrayList<Demanda>();	;
		
		for (Entity result : entities){
			try{
				demandas.add(buildDemanda(result));
			}catch(Exception exp) {}
		}

		return demandas;
	}
	
	public Integer countSolicBetweenDates(String desde, String hasta) throws ParseException {
		
		Date desdeDate = Utils.dateConverter(desde);
		Date hastaDate = Utils.dateConverter(hasta);

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Demanda");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();
		
		if(!desde.equals("")){
			finalFilters.add(new FilterPredicate("fecha_entrada_peticion", FilterOperator.GREATER_THAN_OR_EQUAL, desdeDate));
		}
		if(!hasta.equals("")){
			finalFilters.add(new FilterPredicate("fecha_entrada_peticion", FilterOperator.LESS_THAN_OR_EQUAL, hastaDate));
		}
		
		
		
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);
		

		
		

		return entities.size();
	}
	
public Integer countSolicBetweenDatesEstado(String desde, String hasta,String tipo) throws ParseException {
		
		Date desdeDate = Utils.dateConverter(desde);
		Date hastaDate = Utils.dateConverter(hasta);

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Demanda");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();
		
		if(!desde.equals("")){
			finalFilters.add(new FilterPredicate("fecha_entrada_peticion", FilterOperator.GREATER_THAN_OR_EQUAL, desdeDate));
		}
		if(!hasta.equals("")){
			finalFilters.add(new FilterPredicate("fecha_entrada_peticion", FilterOperator.LESS_THAN_OR_EQUAL, hastaDate));
		}
		
		if(!tipo.equals("")){
			finalFilters.add(new FilterPredicate("tipo", FilterOperator.EQUAL, tipo));
		}
		
		
		
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);

		return entities.size();
	}
	
	
	private Demanda buildDemanda(Entity entity) {
		Demanda demanda = new Demanda();
		
		demanda.setKey(entity.getKey());
		
		String catalogacion =  getString(entity, "catalogacion");
		if(catalogacion != null) {
			demanda.setCatalogacion(catalogacion);
		}
		
		Long clientekey =  getLong(entity, "clientekey");
		if(clientekey != null) {
			demanda.setClientekey(clientekey);
		}
		
		String clienteName =  getString(entity, "clienteName");
		if(clienteName != null) {
			demanda.setClienteName(clienteName);
		}
		
		String codPeticion =  getString(entity, "cod_peticion");
		if(codPeticion != null) {
			demanda.setCod_peticion(codPeticion);
		}
		
		String comentarios =  getString(entity, "comentarios");
		if(comentarios != null) {
			demanda.setComentarios(comentarios);
		}
		
		String detalle =  getString(entity, "detalle");
		if(detalle != null) {
			demanda.setDetalle(detalle);
		}
		
		demanda.setDevuelta((boolean) entity.getProperty("devuelta"));
		
		String estado = getString(entity, "estado");
		if(estado != null) {
			demanda.setEstado(estado);
		}
		
		Date fechaComunicacion = getDate(entity, "fecha_comunicacion");
		if(fechaComunicacion != null) {
			demanda.setFecha_comunicacion(fechaComunicacion);
		}
		
		Date fechaComunicacionAsignacion = getDate(entity, "fecha_comunicacion_asignacion");
		if(fechaComunicacionAsignacion != null) {
			demanda.setFecha_comunicacion_asignacion(fechaComunicacionAsignacion);
		}
		
		Date fechaEntradaPeticion = getDate(entity, "fecha_entrada_peticion");
		if(fechaEntradaPeticion != null) {
			demanda.setFecha_entrada_peticion(fechaEntradaPeticion);
		}
		
		Date fechaSolicitudAsignacion = getDate(entity, "fecha_solicitud_asignacion");
		if(fechaSolicitudAsignacion != null) {
			demanda.setFecha_solicitud_asignacion(fechaSolicitudAsignacion);
		}
		
		Long gestorIt =  getLong(entity, "gestor_it");
		if(gestorIt != null) {
			demanda.setGestor_it(gestorIt);
		}
		
		Long gestorNegocio =  getLong(entity, "gestor_negocio");
		if(gestorNegocio != null) {
			demanda.setGestor_negocio(gestorNegocio);
		}
		
		String horaComunicacion = getString(entity, "hora_comunicacion");
		if(horaComunicacion != null) {
			demanda.setHora_comunicacion(horaComunicacion);
		}
		
		String horaComunicacionAsignacion = getString(entity, "hora_comunicacion_asignacion");
		if(horaComunicacionAsignacion != null) {
			demanda.setHora_comunicacion_asignacion(horaComunicacionAsignacion);
		}
		
		String horaEntradaPeticion = getString(entity, "hora_entrada_peticion");
		if(horaEntradaPeticion != null) {
			demanda.setHora_entrada_peticion(horaEntradaPeticion);
		}
		
		String horaSolicitudAsignacion = getString(entity, "hora_solicitud_asignacion");
		if(horaSolicitudAsignacion != null) {
			demanda.setHora_solicitud_asignacion(horaSolicitudAsignacion);
		}
		
		String motivoCatalogacion = getString(entity, "motivo_catalogacion");
		if(motivoCatalogacion != null) {
			demanda.setMotivo_catalogacion(motivoCatalogacion);
		}
		
		Long sequence =  getLong(entity, "sequence");
		if(sequence != null) {
			demanda.setSequence(sequence);
		}
		
		String strFechaComunicacion = getString(entity, "str_fecha_comunicacion");
		if(strFechaComunicacion != null) {
			demanda.setStr_fecha_comunicacion(strFechaComunicacion);
		}
		
		String strFechaComunicacionAsignacion = getString(entity, "str_fecha_comunicacion_asignacion");
		if(strFechaComunicacionAsignacion != null) {
			demanda.setStr_fecha_comunicacion_asignacion(strFechaComunicacionAsignacion);
		}
		
		String strFechaEntradaPeticion = getString(entity, "str_fecha_entrada_peticion");
		if(strFechaEntradaPeticion != null) {
			demanda.setStr_fecha_entrada_peticion(strFechaEntradaPeticion);
		}
		
		String strFechaSolicitudAsignacion = getString(entity, "str_fecha_solicitud_asignacion");
		if(strFechaSolicitudAsignacion != null) {
			demanda.setStr_fecha_solicitud_asignacion(strFechaSolicitudAsignacion);
		}
		
		String tipo = getString(entity, "tipo");
		if(tipo != null) {
			demanda.setTipo(tipo);
		}
		
		return demanda;
		
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
	
	
	private Integer getInteger(Entity e, String field) {
		try {
			return (Integer) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
	private Date getDate(Entity e, String field) {
		try {
			return (Date) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}

	
	
}
