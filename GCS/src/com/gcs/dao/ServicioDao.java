package com.gcs.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
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



public class ServicioDao {
	
			
			
	private static final String SOLICITADO = "SOLICITADO";
	private static final String CONFIRMADO = "CONFIRMADO";
	
	public static final int DATA_SIZE = 10;

	public static ServicioDao getInstance() {
		return new ServicioDao();
	}
	
	public Servicio getServicioById(long l) {
	     PersistenceManager pManager = PMF.get().getPersistenceManager();
	     Servicio servicio_temp = pManager.getObjectById(Servicio.class, l);	      
	     Servicio servicio = pManager.detachCopy(servicio_temp);  
	     pManager.close();
	     return servicio;
	}
	
	public void deleteServicio(Servicio s, String usermail){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( s.getClass(), s.getKey().getId())); 
		pm.close();
		ContadorServicioDao csDao = ContadorServicioDao.getInstance();
		csDao.decrementCont();
		Utils.writeLog(usermail, "Eliminó", "Servicio", s.getCod_servicio());
		
	}
	public void deleteAllServicios(){
		ServicioDao servDao = ServicioDao.getInstance();
		List<Servicio> servicios = servDao.getAllServicios();
		for (Servicio s: servicios){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.deletePersistent( pm.getObjectById( s.getClass(), s.getKey().getId())); 
			pm.close();
		}
	}
	
	public List<Servicio> getAllServicios() {

		List<Servicio> servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("select from " + Servicio.class.getName());		
		//q.setOrdering("nombre asc");
		servicios = (List<Servicio>) q.execute();
		
		pm.close();

		return servicios;
	}
	
	public List<Servicio> getAllServiciosByEstadoPais(String pais, String estado) {

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		String queryStr = "select from " + Servicio.class.getName()
				+ " where estado == '" + estado +  "' && pais=='"+pais+"'";
		
		@SuppressWarnings({ "unchecked", "unused" })
		List<Servicio> servicios = (List<Servicio>) pManager.newQuery(queryStr).execute();
		

		
		pManager.close();
		

		return servicios;
	}
	
	public List<Servicio> getServiciosByProject(Long id) {
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		String queryStr = "select from " + Servicio.class.getName()
				+ " where id_proyecto  == :p1";

		List<Servicio> servicios = (List<Servicio>) pManager.newQuery(queryStr).execute(id);

		pManager.close();

		return servicios;
	}
	
	public void createServicioRaw(Servicio s){

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(s);
		} finally {
			pm.close();
		}
	}
	public void createServicio(Servicio s, String usermail){
		
		if(s.getCliente_name()!=null)s.setCliente_name(s.getCliente_name().toUpperCase());
		if(s.getCod_servicio()!=null)s.setCod_servicio(s.getCod_servicio().toUpperCase());
		if(s.getDetalle()!=null)s.setDetalle(s.getDetalle().toUpperCase());
		if(s.getdetalleSubida()!=null)s.setdetalleSubida(s.getdetalleSubida().toUpperCase());
		if(s.getEstado()!=null)s.setEstado(s.getEstado().toUpperCase());
		if(s.getEstadoImplantacion()!=null)s.setEstadoImplantacion(s.getEstadoImplantacion().toUpperCase());
		if(s.getEstadoSubida()!=null)s.setEstadoSubida(s.getEstadoSubida().toUpperCase());
		if(s.getExtension()!=null)s.setExtension(s.getExtension().toUpperCase());
		if(s.getFormato_intermedio()!=null)s.setFormato_intermedio(s.getFormato_intermedio().toUpperCase());
		if(s.getFormato_local()!=null)s.setFormato_local(s.getFormato_local().toUpperCase());
		if(s.getGestor_it_name()!=null)s.setGestor_it_name(s.getGestor_it_name().toUpperCase());
		if(s.getGestor_pruebas_name()!=null)s.setGestor_pruebas_name(s.getGestor_pruebas_name().toUpperCase());
		if(s.getGestor_pruebas_key()!=null)s.setGestor_pruebas_key(s.getGestor_pruebas_key());
		if(s.getGestor_negocio_name()!=null)s.setGestor_negocio_name(s.getGestor_negocio_name().toUpperCase());
		if(s.getObservaciones()!=null)s.setObservaciones(s.getObservaciones().toUpperCase());
		if(s.getPais()!=null)s.setPais(s.getPais().toUpperCase());
		if(s.getReferencia_local1()!=null)s.setReferencia_local1(s.getReferencia_local1().toUpperCase());
		if(s.getReferencia_local2()!=null)s.setReferencia_local2(s.getReferencia_local2().toUpperCase());
		if(s.getServicio()!=null)s.setServicio(s.getServicio().toUpperCase());
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		Boolean isNew = false;
		ContadorServicioDao csDao = ContadorServicioDao.getInstance();
		
		if (s.getKey()==null)
			isNew = true;
		
		try {
			if (s.getStr_fecha_ini_integradas()!=null && !"".equals(s.getStr_fecha_ini_integradas())){			
				s.setFecha_ini_integradas(Utils.dateConverter(s.getStr_fecha_ini_integradas()));			
			}
			if (s.getStr_fecha_fin_integradas()!=null && !"".equals(s.getStr_fecha_fin_integradas())){			
				s.setFecha_fin_integradas(Utils.dateConverter(s.getStr_fecha_fin_integradas()));			
			}
			if (s.getStr_fecha_ini_aceptacion()!=null && !"".equals(s.getStr_fecha_ini_aceptacion())){			
				s.setFecha_ini_aceptacion(Utils.dateConverter(s.getStr_fecha_ini_aceptacion()));			
			}
			if (s.getStr_fecha_fin_aceptacion()!=null && !"".equals(s.getStr_fecha_fin_aceptacion())){			
				s.setFecha_fin_aceptacion(Utils.dateConverter(s.getStr_fecha_fin_aceptacion()));			
			}
			if (s.getStr_fecha_ini_validacion()!=null && !"".equals(s.getStr_fecha_ini_validacion())){			
				s.setFecha_ini_validacion(Utils.dateConverter(s.getStr_fecha_ini_validacion()));			
			}
			if (s.getStr_fecha_fin_validacion()!=null && !"".equals(s.getStr_fecha_fin_validacion())){			
				s.setFecha_fin_validacion(Utils.dateConverter(s.getStr_fecha_fin_validacion()));			
			}
			if (s.getStr_fecha_implantacion_produccion()!=null && !"".equals(s.getStr_fecha_implantacion_produccion())){			
				s.setFecha_implantacion_produccion(Utils.dateConverter(s.getStr_fecha_implantacion_produccion()));			
			}
			if (s.getStr_fecha_ini_primera_operacion()!=null && !"".equals(s.getStr_fecha_ini_primera_operacion())){			
				s.setFecha_ini_primera_operacion(Utils.dateConverter(s.getStr_fecha_ini_primera_operacion()));			
			}
			if (s.getStr_fecha_fin_primera_operacion()!=null && !"".equals(s.getStr_fecha_fin_primera_operacion())){			
				s.setFecha_fin_primera_operacion(Utils.dateConverter(s.getStr_fecha_fin_primera_operacion()));			
			}
			if (s.getStr_fecha_ini_op_cliente()!=null && !"".equals(s.getStr_fecha_ini_op_cliente())){			
				s.setFecha_ini_op_cliente(Utils.dateConverter(s.getStr_fecha_ini_op_cliente()));			
			}
			if (s.getStr_fecha_ANS()!=null && !"".equals(s.getStr_fecha_ANS())){			
				s.setFecha_ANS(Utils.dateConverter(s.getStr_fecha_ANS()));			
			}
			if (s.getStr_fecha_ini_pruebas()!=null && !"".equals(s.getStr_fecha_ini_pruebas())){			
				s.setFecha_ini_pruebas(Utils.dateConverter(s.getStr_fecha_ini_pruebas()));			
			}
			if (s.getStr_fecha_fin_pruebas()!=null && !"".equals(s.getStr_fecha_fin_pruebas())){			
				s.setFecha_fin_pruebas(Utils.dateConverter(s.getStr_fecha_fin_pruebas()));			
			}
			
		
			/*if (s.getStr_migracion_channeling()!=null && !"".equals(s.getStr_migracion_channeling())){			
				s.setFecha_migracion_channeling(Utils.dateConverter(s.getStr_migracion_channeling()));			
			}
			if (s.getStr_migracion_infra()!=null && !"".equals(s.getStr_migracion_infra())){			
				s.setFecha_migracion_infra(Utils.dateConverter(s.getStr_migracion_infra()));			
			}*/
			
			ProyectoDao pDao = ProyectoDao.getInstance();
			Proyecto p = pDao.getProjectbyId(s.getId_proyecto());
			
			if (p!=null){
				s.setGestor_it_key(p.getGestor_it());
				s.setGestor_it_name(p.getGestor_it_name());
				
				s.setGestor_negocio_key(p.getGestor_negocio());
				s.setGestor_negocio_name(p.getGestor_negocio_name());
				
				s.setCliente_key(p.getClienteKey());
				s.setCliente_name(p.getClienteName());
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			try {
				pm.makePersistent(s);
				
			} finally{
				pm.close();
				
				if (isNew){
					Utils.writeLog(usermail, "Creó", "Servicio", s.getCod_servicio());
					csDao.increaseCont();
				}else{
					Utils.writeLog(usermail, "Editó", "Servicio", s.getCod_servicio());
				}
			}
		}
		
		
	}
	
	public List<Servicio> getServiciosByEstado(String estado){
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		String queryStr = "select from " + Servicio.class.getName()
				+ " where estado == '" + estado +  "'";
		
		@SuppressWarnings({ "unchecked", "unused" })
		List<Servicio> servicios = (List<Servicio>) pManager.newQuery(queryStr).execute();
		
		if (servicios.isEmpty()) {
			servicios = null;
		}
		
		pManager.close();
		
		return servicios;
	}
	
	
	public List<Servicio> getServiciosByService(String servicio){
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		String queryStr = "select from " + Servicio.class.getName()+ " where  servicio== '" + servicio +  "'";
		
		@SuppressWarnings({ "unchecked", "unused" })
		List<Servicio> servicios = (List<Servicio>) pManager.newQuery(queryStr).execute();
		
		pManager.close();
		
		return servicios;
	}
	
	public List<Servicio> getServiciosByGestorIT(String gestor){
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		String queryStr = "select from " + Servicio.class.getName()+ " where  gestor_it_key== '" + gestor +  "'";
		
		@SuppressWarnings({ "unchecked", "unused" })
		List<Servicio> servicios = (List<Servicio>) pManager.newQuery(queryStr).execute();
		
		pManager.close();
		
		return servicios;
	}
	
	public List<Servicio> getServiciosByEstadoImplantacion(String estado){
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		String queryStr = "select from " + Servicio.class.getName()
				+ " where estadoImplantacion == '" + estado +  "'";
		
		@SuppressWarnings({ "unchecked", "unused" })
		List<Servicio> servicios = (List<Servicio>) pManager.newQuery(queryStr).execute();
		
		if (servicios.isEmpty()) {
			servicios = null;
		}
		
		pManager.close();
		
		return servicios;
	}
	
	public Integer countSolicBetweenDates(String desde, String hasta) throws ParseException {
		
		Date desdeDate = Utils.dateConverter(desde);
		Date hastaDate = Utils.dateConverter(hasta);

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();
		
		if(!desde.equals("")){
			finalFilters.add(new FilterPredicate("fecha_implantacion_produccion", FilterOperator.GREATER_THAN_OR_EQUAL, desdeDate));
		}
		if(!hasta.equals("")){
			finalFilters.add(new FilterPredicate("fecha_implantacion_produccion", FilterOperator.LESS_THAN_OR_EQUAL, hastaDate));
		}
		
		
		List<String> estados = new ArrayList<String>();
		
		estados.add("En Penny Test");
		estados.add("Implementado con OK");
		estados.add("Implementado sin OK");
		
		
		finalFilters.add(new FilterPredicate("estado", FilterOperator.IN, estados));
		
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);

		return entities.size();
	}
	
	public Integer countSolicBetweenDatesAndState(String desde, String hasta, String estado) throws ParseException {
		
		Date desdeDate = Utils.dateConverter(desde);
		Date hastaDate = Utils.dateConverter(hasta);

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();
		
		if(!desde.equals("")){
			finalFilters.add(new FilterPredicate("fecha_fin_aceptacion", FilterOperator.GREATER_THAN_OR_EQUAL, desdeDate));
		}
		if(!hasta.equals("")){
			finalFilters.add(new FilterPredicate("fecha_fin_aceptacion", FilterOperator.LESS_THAN_OR_EQUAL, hastaDate));
		}
		if(!estado.equals("")){
			if(!estado.equals("Análisis")){
			finalFilters.add(new FilterPredicate("estado", FilterOperator.EQUAL, estado));
			}else{
				ArrayList<String> estados = new ArrayList<String>();
				estados.add("C100 EN CONFECCIÓN");
				estados.add("PDTE VALORACIÓN IT");
				estados.add("PDTE PLAN DE TRABAJO IT");
				finalFilters.add(new FilterPredicate("estado", FilterOperator.IN, estados));			
			}
		}
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);

		return entities.size();
	}
	
	public Integer countByPais(String pais) throws ParseException {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();
		if(!pais.equals("")){

			finalFilters.add(new FilterPredicate("pais", FilterOperator.EQUAL, pais));

		}
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);

		return entities.size();
	}
	
	public Integer countByProjectAndPais(Long project, String pais) throws ParseException {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();
		if(!pais.equals("")){

			finalFilters.add(new FilterPredicate("pais", FilterOperator.EQUAL, pais));

		}
		
		if(!project.equals("")){

			finalFilters.add(new FilterPredicate("id_proyecto", FilterOperator.EQUAL, project));

		}
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);

		return entities.size();
	}
	

	
		
	public List<Servicio> getServiciosEnCurso(){
		
		List<Servicio> servicioSolicitado = getServiciosByEstadoImplantacion(SOLICITADO);
		List<Servicio> servicioConfirmado = getServiciosByEstadoImplantacion(CONFIRMADO);
		
		if(servicioSolicitado != null) {
			return servicioSolicitado;
		}
		else if(servicioConfirmado != null){
			return servicioConfirmado;
		}
		else {
			return null;
		}
	}
	
	public Servicio getServicioById(String key){

		Long keyAux = Long.parseLong(key);

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Servicio servicio_temp = pManager.getObjectById(Servicio.class, keyAux);
		Servicio servicio = pManager.detachCopy(servicio_temp);
		pManager.close();

		return servicio;		
	}
	
	public List<Servicio> getServicioByAllParam(String codProyeto, String codServicio, String estado, String nGestorIt, String pais, String nCliente,  Integer page){
		List<Servicio> servicios= null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		List<Filter> finalFilters = new ArrayList<>();
		
		int filters =0;
		if(!codProyeto.equals("")){
			codProyeto= codProyeto.toUpperCase();
			filters++;
		}
		if(!codServicio.equals("")){
			codServicio= codServicio.toUpperCase();
			filters++;
		}
		if(!estado.equals("")){
			estado = estado.toUpperCase();
			filters++;
		}
		if(!nGestorIt.equals("")){
			nGestorIt= nGestorIt.toUpperCase();
			filters++;
		}
		if(!pais.equals("")){
			pais= pais.toUpperCase();
			filters++;
		}
		if(!nCliente.equals("")){
			nCliente= nCliente.toUpperCase();
			filters++;
		}
		
		if(filters<=1){
			if(!codProyeto.equals("")){
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.GREATER_THAN_OR_EQUAL, codProyeto));
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.LESS_THAN, codProyeto+"\ufffd"));
			}
			if(!codServicio.equals("")){
				finalFilters.add(new FilterPredicate("servicio",FilterOperator.GREATER_THAN_OR_EQUAL, codServicio));
				finalFilters.add(new FilterPredicate("servicio",FilterOperator.LESS_THAN, codServicio+"\ufffd"));
			}
			if(!estado.equals("")){
				finalFilters.add(new FilterPredicate("estado",FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado",FilterOperator.LESS_THAN, estado+"\ufffd"));
			}
			if(!nGestorIt.equals("")){
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.GREATER_THAN_OR_EQUAL, nGestorIt));
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.LESS_THAN, nGestorIt+"\ufffd"));
			}
			if(!pais.equals("")){
				finalFilters.add(new FilterPredicate("pais",FilterOperator.GREATER_THAN_OR_EQUAL, pais));
				finalFilters.add(new FilterPredicate("pais",FilterOperator.LESS_THAN, pais+"\ufffd"));
			}
			if(!nCliente.equals("")){
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
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
				servicios.add(buildServicio(result));
			}
			Servicio impPage = new Servicio();
			impPage.setDetalle("0");
			servicios.add(impPage);
		
		}else{
			
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!codProyeto.equals("")){
				q = new com.google.appengine.api.datastore.Query("Servicio");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.GREATER_THAN_OR_EQUAL, codProyeto));
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.LESS_THAN, codProyeto+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!codServicio.equals("")){
				q = new com.google.appengine.api.datastore.Query("Servicio");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("servicio",FilterOperator.GREATER_THAN_OR_EQUAL, codServicio));
				finalFilters.add(new FilterPredicate("servicio",FilterOperator.LESS_THAN, codServicio+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!estado.equals("")){
				q = new com.google.appengine.api.datastore.Query("Servicio");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("estado",FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado",FilterOperator.LESS_THAN, estado+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nGestorIt.equals("")){
				q = new com.google.appengine.api.datastore.Query("Servicio");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.GREATER_THAN_OR_EQUAL, nGestorIt));
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.LESS_THAN, nGestorIt+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!pais.equals("")){
				q = new com.google.appengine.api.datastore.Query("Servicio");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("pais",FilterOperator.GREATER_THAN_OR_EQUAL, pais));
				finalFilters.add(new FilterPredicate("pais",FilterOperator.LESS_THAN, pais+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nCliente.equals("")){
				q = new com.google.appengine.api.datastore.Query("Servicio");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
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
			
			servicios = new ArrayList<Servicio>();
			int serviciosPages  = serviciosFinal.size();
			for(int i = page*10; i< (page*10)+10&&i<serviciosFinal.size();i++){
				servicios.add(buildServicio(serviciosFinal.get(i)));
			}
			Servicio pages = new Servicio();
			pages.setDetalle(Integer.toString(serviciosPages));
			servicios.add(pages);
		}
		return servicios;
	}
	
	public List<Servicio> getAllServicioPagin(Integer page) {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Servicio> servicios = new ArrayList<Servicio>();	;
		
		for (Entity result : entities){
			servicios.add(buildServicio(result));
		}

		return servicios;
	}
	
	private Servicio buildServicio(Entity entity) {
		Servicio servicio = new Servicio();
		
		servicio.setKey(entity.getKey());
		
		Long clienteKey =  getLong(entity, "cliente_key");
		if(clienteKey != null) {
			servicio.setCliente_key(clienteKey);
		}
		
		String clienteName =  getString(entity, "cliente_name");
		if(clienteName != null) {
			servicio.setCliente_name(clienteName);
		}
		
		String codProyecto =  getString(entity, "cod_proyecto");
		if(codProyecto != null) {
			servicio.setCod_proyecto(codProyecto);
		}
		
		String codServicio =  getString(entity, "cod_servicio");
		if(codServicio != null) {
			servicio.setCod_servicio(codServicio);
		}
	
		String detalle =  getString(entity, "detalle");
		if(detalle != null) {
			servicio.setDetalle(detalle);
		}
		
		String detalleSubida =  getString(entity, "detalleSubida");
		if(detalleSubida != null) {
			servicio.setdetalleSubida(detalleSubida);
		}
		
		String estado =  getString(entity, "estado");
		if(estado != null) {
			servicio.setEstado(estado);
		}
		
		String estadoImplantacion =  getString(entity, "estadoImplantacion");
		if(estadoImplantacion != null) {
			servicio.setEstadoImplantacion(estadoImplantacion);
		}
		
		String estadoSubida =  getString(entity, "estadoSubida");
		if(estadoSubida != null) {
			servicio.setEstadoSubida(estadoSubida);
		}
		
		String extension =  getString(entity, "extension");
		if(extension != null) {
			servicio.setExtension(extension);
		}
		
		Date fechaANS = getDate(entity, "fecha_ANS");
		if(fechaANS != null) {
			servicio.setFecha_ANS(fechaANS);
		}
		
		Date fechaFinAceptacion = getDate(entity, "fecha_fin_aceptacion");
		if(fechaFinAceptacion != null) {
			servicio.setFecha_fin_aceptacion(fechaFinAceptacion);
		}
		
		Date fechaFinIntegradas = getDate(entity, "fecha_fin_integradas");
		if(fechaFinIntegradas != null) {
			servicio.setFecha_fin_integradas(fechaFinIntegradas);
		}
		
		Date fechaFinPrimeraOperacion = getDate(entity, "fecha_fin_primera_operacion");
		if(fechaFinPrimeraOperacion != null) {
			servicio.setFecha_fin_primera_operacion(fechaFinPrimeraOperacion);
		}
		
		Date fechaFinPruebas = getDate(entity, "fecha_fin_pruebas");
		if(fechaFinPruebas != null) {
			servicio.setFecha_fin_pruebas(fechaFinPruebas);
		}
		
		Date fechaFinValidacion = getDate(entity, "fecha_fin_validacion");
		if(fechaFinValidacion != null) {
			servicio.setFecha_fin_validacion(fechaFinValidacion);
		}
		
		Date fechaImplantacionProduccion = getDate(entity, "fecha_implantacion_produccion");
		if(fechaImplantacionProduccion != null) {
			servicio.setFecha_implantacion_produccion(fechaImplantacionProduccion);
		}
		
		Date fechaIniAceptacion = getDate(entity, "fecha_ini_aceptacion");
		if(fechaIniAceptacion != null) {
			servicio.setFecha_ini_aceptacion(fechaIniAceptacion);
		}
		
		Date fechaIniIntegradas = getDate(entity, "fecha_ini_integradas");
		if(fechaIniIntegradas != null) {
			servicio.setFecha_ini_integradas(fechaIniIntegradas);
		}
		
		Date fechaIniOpCliente = getDate(entity, "fecha_ini_op_cliente");
		if(fechaIniOpCliente != null) {
			servicio.setFecha_ini_op_cliente(fechaIniOpCliente);
		}
		
		Date fechaIniPrimeraOperacion = getDate(entity, "fecha_ini_primera_operacion");
		if(fechaIniPrimeraOperacion != null) {
			servicio.setFecha_ini_primera_operacion(fechaIniPrimeraOperacion);
		}
		
		Date fechaIniPruebas = getDate(entity, "fecha_ini_pruebas");
		if(fechaIniPruebas != null) {
			servicio.setFecha_ini_pruebas(fechaIniPruebas);
		}
		
		Date fechaIniValidacion = getDate(entity, "fecha_ini_validacion");
		if(fechaIniValidacion != null) {
			servicio.setFecha_ini_validacion(fechaIniValidacion);
		}
		
		String formatoIntermedio =  getString(entity, "formato_intermedio");
		if(formatoIntermedio != null) {
			servicio.setFormato_intermedio(formatoIntermedio);
		}
		
		String formatoLocal =  getString(entity, "formato_local");
		if(formatoLocal != null) {
			servicio.setFormato_local(formatoLocal);
		}
		
		Long gestorItKey =  getLong(entity, "gestor_it_key");
		if(gestorItKey != null) {
			servicio.setGestor_it_key(gestorItKey);
		}
		
		String gestorItName =  getString(entity, "gestor_it_name");
		if(gestorItName != null) {
			servicio.setGestor_it_name(gestorItName);
		}
		
		Long gestorPruebasKey =  getLong(entity, "gestor_pruebas_key");
		if(gestorPruebasKey != null) {
			servicio.setGestor_pruebas_key(gestorPruebasKey);
		}
		
		String gestorPruebasName =  getString(entity, "gestor_pruebas_name");
		if(gestorPruebasName != null) {
			servicio.setGestor_pruebas_name(gestorPruebasName);
		}
		
		Long gestorNegocioKey =  getLong(entity, "gestor_negocio_key");
		if(gestorNegocioKey != null) {
			servicio.setGestor_negocio_key(gestorNegocioKey);
		}
		
		String gestorNegocioName =  getString(entity, "gestor_negocio_name");
		if(gestorNegocioName != null) {
			servicio.setGestor_negocio_name(gestorNegocioName);
		}
		
		Long idProyecto =  getLong(entity, "id_proyecto");
		if(idProyecto != null) {
			servicio.setId_proyecto(idProyecto);
		}
		
		String observaciones =  getString(entity, "observaciones");
		if(observaciones != null) {
			servicio.setObservaciones(observaciones);
		}
		
		String pais =  getString(entity, "pais");
		if(pais != null) {
			servicio.setPais(pais);
		}
		
		String referenciaLocal1 =  getString(entity, "referencia_local1");
		if(referenciaLocal1 != null) {
			servicio.setReferencia_local1(referenciaLocal1);
		}
		
		String referenciaLocal2 =  getString(entity, "referencia_local2");
		if(referenciaLocal2 != null) {
			servicio.setReferencia_local2(referenciaLocal2);
		}
		
		String servicioStr =  getString(entity, "servicio");
		if(servicioStr != null) {
			servicio.setServicio(servicioStr);
		}
		
		String strFechaANS =  getString(entity, "str_fecha_ANS");
		if(strFechaANS != null) {
			servicio.setStr_fecha_ANS(strFechaANS);
		}
		
		String strFechaFinAceptacion =  getString(entity, "str_fecha_fin_aceptacion");
		if(strFechaFinAceptacion != null) {
			servicio.setStr_fecha_fin_aceptacion(strFechaFinAceptacion);
		}
		
		String strFechaFinIntegradas =  getString(entity, "str_fecha_fin_integradas");
		if(strFechaFinIntegradas != null) {
			servicio.setStr_fecha_fin_integradas(strFechaFinIntegradas);
		}
		
		String strFechaFinPrimeraOperacion =  getString(entity, "str_fecha_fin_primera_operacion");
		if(strFechaFinPrimeraOperacion != null) {
			servicio.setStr_fecha_fin_primera_operacion(strFechaFinPrimeraOperacion);
		}
		
		String strFechaFinPruebas =  getString(entity, "str_fecha_fin_pruebas");
		if(strFechaFinPruebas != null) {
			servicio.setStr_fecha_fin_pruebas(strFechaFinPruebas);
		}
		
		String strFechaFinValidacion =  getString(entity, "str_fecha_fin_validacion");
		if(strFechaFinValidacion != null) {
			servicio.setStr_fecha_fin_validacion(strFechaFinValidacion);
		}
		

		String strFechaImplantacionProduccion =  getString(entity, "str_fecha_implantacion_produccion");
		if(strFechaImplantacionProduccion != null) {
			servicio.setStr_fecha_implantacion_produccion(strFechaImplantacionProduccion);
		}
		

		String strFechaIniAceptacion =  getString(entity, "str_fecha_ini_aceptacion");
		if(strFechaIniAceptacion != null) {
			servicio.setStr_fecha_ini_aceptacion(strFechaIniAceptacion);
		}
		
		String strFechaIniIntegradas =  getString(entity, "str_fecha_ini_integradas");
		if(strFechaIniIntegradas != null) {
			servicio.setStr_fecha_ini_integradas(strFechaIniIntegradas);
		}
		
		String strFechaIniOpCliente =  getString(entity, "str_fecha_ini_op_cliente");
		if(strFechaIniOpCliente != null) {
			servicio.setStr_fecha_ini_op_cliente(strFechaIniOpCliente);
		}
		
		String strFechaIniPrimeraOperacion =  getString(entity, "str_fecha_ini_primera_operacion");
		if(strFechaIniPrimeraOperacion != null) {
			servicio.setStr_fecha_ini_primera_operacion(strFechaIniPrimeraOperacion);
		}
		
		String strFechaIniPruebas =  getString(entity, "str_fecha_ini_pruebas");
		if(strFechaIniPruebas != null) {
			servicio.setStr_fecha_ini_pruebas(strFechaIniPruebas);
		}
		
		String strFechaIniValidacion =  getString(entity, "str_fecha_ini_validacion");
		if(strFechaIniValidacion != null) {
			servicio.setStr_fecha_ini_validacion(strFechaIniValidacion);
		}
		
		//servicio.setsubidaCalendada((boolean) entity.getProperty("subidaCalendada"));
		
		return servicio;
		
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
	
	
	
	private Date getDate(Entity e, String field) {
		try {
			return (Date) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
public int[] getServiciosForExcelGestor(String gestor){
		
		int[] resultados = new int[7];
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		long a = Long.parseLong(gestor);
		Filter filtro = new FilterPredicate("gestor_it_key", FilterOperator.EQUAL, a);
		q.setFilter(filtro);
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		List<Entity> servicios = datastore.prepare(q).asList(fetchOptions);
		
		
		for(int i=0;i<resultados.length;i++){
			resultados[i]=0;
		}
		
		for(Entity servicio:servicios){
			String estado = (String) servicio.getProperty("estado");
			if(estado.equals("PDTE Doc Alcance en GCS")){
				resultados[0]++;
				
			}else{
				if(estado.equals("PDTE Valoración IT")){
					resultados[1]++;
					
				}else{
					if(estado.equals("En Desarrollo")){
						resultados[2]++;
						
					}else{
						if(estado.equals("En Test - Integraci?n")){
							resultados[3]++;
							
						}else{
							if(estado.equals("En Penny Test")||estado.equals("En Test - Aceptación")||estado.equals("En Test - Conectividad")){
								resultados[4]++;
								
							}
						}
					}
				}
			}
			
			
			String estadoImp = (String) servicio.getProperty("estadoImplantacion");
			
			if(estadoImp!=null&&(estadoImp.equals("SOLICITADO")||estadoImp.equals("CONFIRMADO"))){
				resultados[5]++;
			}
		}
		
		
		
		
		return resultados;
	}

	


	public int[][] getTableImpPorEstad(Integer[][] dates){
		
		int[][] resultados = new int[3][4];
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Servicio");
		
		
		
		///Filter filtro = new FilterPredicate("gestor_it_key", FilterOperator.EQUAL, a);
		
		//q.setFilter(filtro);
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		List<Entity> servicios = datastore.prepare(q).asList(fetchOptions);
		q = new com.google.appengine.api.datastore.Query("Servicio");
		
		servicios = datastore.prepare(q).asList(fetchOptions);
		q = new com.google.appengine.api.datastore.Query("Servicio");
		
		servicios = datastore.prepare(q).asList(fetchOptions);

		return resultados;
	}


}

