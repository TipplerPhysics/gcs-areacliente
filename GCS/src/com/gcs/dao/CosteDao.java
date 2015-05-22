package com.gcs.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.beans.Coste;
import com.gcs.beans.Demanda;
import com.gcs.beans.Equipo;
import com.gcs.beans.Proyecto;
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

public class CosteDao {
	
	public static final int DATA_SIZE = 10;
	
	public static CosteDao getInstance(){
		return new CosteDao();
	}
	
	public List<Coste> getCostesByProject(Long id) {

		

		PersistenceManager pManager = PMF.get().getPersistenceManager();
//		Transaction transaction = pManager.currentTransaction();
//		transaction.begin();

		String queryStr = "select from " + Coste.class.getName()
				+ " where projectKey  == :p1";

		List<Coste> costes = (List<Coste>) pManager.newQuery(queryStr).execute(id);

		
//		transaction.commit();
		pManager.close();

		return costes;

	}
	
	public void createCoste(Coste c, String usermail) throws ParseException {
		
		c.setCliente_name(c.getCliente_name().toUpperCase());
		c.setComentarios(c.getComentarios().toUpperCase());
		c.setEquipos(c.getEquipos().toUpperCase());
		c.setGestor_it_name(c.getGestor_it_name().toUpperCase());
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Boolean isNew = false;
		ContadorCosteDao cstDao = ContadorCosteDao.getInstance();
		if (c.getKey()==null)
			isNew=true;
		if(!c.getStr_fecha_recepcion_valoracion().equals("")&&c.getStr_fecha_recepcion_valoracion()!=null){
			c.setFecha_recepcion_valoracion(Utils.dateConverter(c.getStr_fecha_recepcion_valoracion()));
		}
		
		if (c.getKey()==null){
			
			EquipoDao eDao = EquipoDao.getInstance();
			Equipo e = eDao.getEquipoByName(c.getEquipos());
			Date hoy = new Date();
			Integer hoy_anio = hoy.getYear()+1900;
			Integer anio_ultimo = e.getUltima_escritura().getYear()+1900;
			
			if (hoy_anio.equals(anio_ultimo)){
				e.setContador(e.getContador()+1);
			}else{
				e.setContador(0);
			}
			
			e.setUltima_escritura(new Date());
			eDao.createEquipo(e);
		}
		

		
		try {
			pm.makePersistent(c);
			
		} finally {
			pm.close();
			if (isNew){
				Utils.writeLog(usermail, "Creó", "Coste", c.getProject_name());
				cstDao.increaseCont();
			}else{
				Utils.writeLog(usermail, "Editó", "Coste", c.getProject_name());
			}
			
			CosteDao costDao = CosteDao.getInstance();
			List<Coste> costes= costDao.getCostesByProject(c.getProjectKey());
			
			Double d = new Double(0);
			
			ProyectoDao proyectDao = ProyectoDao.getInstance();
			Proyecto proyecto = proyectDao.getProjectbyId(c.getProjectKey());
			
			for (Coste cost:costes){
				if (!"".equals(cost.getCoste_total()))
				d += Double.parseDouble(cost.getCoste_total().replace(",","."));
			}
			String aux = d.toString();
			proyecto.setCoste(aux);
			
			proyectDao.createProjectRaw(proyecto);
			
			
		}
	}
	
	public List<Coste> getAllCostes() {

		List<Coste> costes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("select from " + Coste.class.getName());
		q.setOrdering("fecha_alta desc");
		costes = (List<Coste>) q.execute();

		return costes;
		
	}
	
	public void deleteAllCostes(String usermail){
		CosteDao costDao = CosteDao.getInstance();
		List<Coste> costes = costDao.getAllCostes();
		for(Coste cost : costes){
			costDao.deleteCoste(cost, usermail);
		}
	}

	public void deleteCoste(Coste c, String usermail) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(c.getClass(), c.getKey().getId()));
		pm.close();
		ContadorCosteDao cstDao = ContadorCosteDao.getInstance();
		cstDao.decrementCont();
		Utils.writeLog(usermail, "Eliminó", "Coste", c.getProject_name());

	}
	
	public Coste getCostebyId(long l) {
		
		Coste coste;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Coste coste_temp = pManager.getObjectById(Coste.class, l);

		coste = pManager.detachCopy(coste_temp);
		pManager.close();

		}catch(Exception e){
			coste=null;
		}
		
		return coste;
		
		
	}
	
	public List<Coste> getCosteByAllParam(String fechaEntrada, String nCliente, String nProject, String equipos, String nGestorIt,  Integer page){
		List<Coste> costes= null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Coste");
		List<Filter> finalFilters = new ArrayList<>();
		
		int filters =0;
		if(!fechaEntrada.equals("")){
			filters++;
		}
		if(!nCliente.equals("")){
			filters++;
		}
		if(!nProject.equals("")){
			filters++;
		}
		if(!equipos.equals("")){
			filters++;
		}
		if(!nGestorIt.equals("")){
			filters++;
		}
		
		if(filters<=1){
			if(!fechaEntrada.equals("")){
				finalFilters.add(new FilterPredicate("str_fecha_alta",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
				finalFilters.add(new FilterPredicate("str_fecha_alta",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
			}
			if(!nCliente.equals("")){
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
			}
			if(!nProject.equals("")){
				finalFilters.add(new FilterPredicate("project_name",FilterOperator.GREATER_THAN_OR_EQUAL, nProject));
				finalFilters.add(new FilterPredicate("project_name",FilterOperator.LESS_THAN, nProject+"\ufffd"));
			}
			if(!equipos.equals("")){
				finalFilters.add(new FilterPredicate("equipos",FilterOperator.GREATER_THAN_OR_EQUAL, equipos));
				finalFilters.add(new FilterPredicate("equipos",FilterOperator.LESS_THAN, equipos+"\ufffd"));
			}
			if(!nGestorIt.equals("")){
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.GREATER_THAN_OR_EQUAL, nGestorIt));
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.LESS_THAN, nGestorIt+"\ufffd"));
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
			costes = new ArrayList<>();
			for(Entity result:entities){
				try{
					costes.add(buildCoste(result));
				}catch(Exception exp) {}
			}
			Coste impPage = new Coste();
			impPage.setDetalle("0");
			costes.add(impPage);
		
		}else{
			
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!fechaEntrada.equals("")){
				q = new com.google.appengine.api.datastore.Query("Costes");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("str_fecha_alta",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
				finalFilters.add(new FilterPredicate("str_fecha_alta",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nCliente.equals("")){
				q = new com.google.appengine.api.datastore.Query("Costes");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("cliente_name",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nProject.equals("")){
				q = new com.google.appengine.api.datastore.Query("Costes");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("project_name",FilterOperator.GREATER_THAN_OR_EQUAL, nProject));
				finalFilters.add(new FilterPredicate("project_name",FilterOperator.LESS_THAN, nProject+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!equipos.equals("")){
				q = new com.google.appengine.api.datastore.Query("Costes");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("equipos",FilterOperator.GREATER_THAN_OR_EQUAL, equipos));
				finalFilters.add(new FilterPredicate("equipos",FilterOperator.LESS_THAN, equipos+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nGestorIt.equals("")){
				q = new com.google.appengine.api.datastore.Query("Costes");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.GREATER_THAN_OR_EQUAL, nGestorIt));
				finalFilters.add(new FilterPredicate("gestor_it_name",FilterOperator.LESS_THAN, nGestorIt+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			List<Entity> costesFinal = new ArrayList<>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
		
			costesFinal = Entities.get(lowRowsIndex);
			List<Entity> indexDel = new ArrayList<Entity>();
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int j = 0;
					for (Entity result : costesFinal) {
						if(!Entities.get(i).contains(result)){
							Entity auxEnty = costesFinal.get(j);
							if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
						}
						j++;
					}
				}
			}
			
			for (Entity impborr : indexDel){
				costesFinal.remove(impborr);
			}
			
			costes = new ArrayList<Coste>();
			int costesPages  = costesFinal.size();
			for(int i = page*10; i< (page*10)+10&&i<costesFinal.size();i++){
				try{
					costes.add(buildCoste(costesFinal.get(i)));
				}catch(Exception exp) {}	
			}
			Coste pages = new Coste();
			pages.setDetalle(Integer.toString(costesPages));
			costes.add(pages);
		}
		return costes;
	}

	public List<Coste> getAllCostePagin(Integer page) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Coste");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Coste> costes = new ArrayList<Coste>();	;
		
		for (Entity result : entities){
			try{
				costes.add(buildCoste(result));
			}catch(Exception exp) {}
		}
	
		return costes;
	}
	
	private Coste buildCoste(Entity entity) {
		Coste coste = new Coste();
		
		coste.setKey(entity.getKey());
		
		String clienteName =  getString(entity, "cliente_name");
		if(clienteName != null) {
			coste.setCliente_name(clienteName);
		}
		
		Long clienteKey =  getLong(entity, "clienteKey");
		if(clienteKey != null) {
			coste.setClienteKey(clienteKey);
		}
		
		String comentarios =  getString(entity, "comentarios");
		if(comentarios != null) {
			coste.setComentarios(comentarios);
		}
		
		String costeAnalisis =  getString(entity, "coste_analisis");
		if(costeAnalisis != null) {
			coste.setCoste_analisis(costeAnalisis);
		}
		
		String costeConstruccion =  getString(entity, "coste_construccion");
		if(costeConstruccion != null) {
			coste.setCoste_construccion(costeConstruccion);
		}
		
		String costeDiseño =  getString(entity, "coste_diseño");
		if(costeDiseño != null) {
			coste.setCoste_diseño(costeDiseño);
		}
		
		String costeGestion =  getString(entity, "coste_gestion");
		if(costeGestion != null) {
			coste.setCoste_gestion(costeGestion);
		}
		
		String costePruebas =  getString(entity, "coste_pruebas");
		if(costePruebas != null) {
			coste.setCoste_pruebas(costePruebas);
		}
		
		String costeTotal =  getString(entity, "coste_total");
		if(costeTotal != null) {
			coste.setCoste_total(costeTotal);
		}
		
		String detalle =  getString(entity, "detalle");
		if(detalle != null) {
			coste.setDetalle(detalle);
		}
		
		String equipos =  getString(entity, "equipos");
		if(equipos != null) {
			coste.setEquipos(equipos);
		}
		
		Date fechaAlta = getDate(entity, "fecha_alta");
		if(fechaAlta != null) {
			coste.setFecha_alta(fechaAlta);
		}
		
		Date fechaRecepcionValoracion = getDate(entity, "fecha_recepcion_valoracion");
		if(fechaRecepcionValoracion != null) {
			coste.setFecha_recepcion_valoracion(fechaRecepcionValoracion);
		}
		
		Date fechaSolicitudValoracion = getDate(entity, "fecha_solicitud_valoracion");
		if(fechaSolicitudValoracion != null) {
			coste.setFecha_solicitud_valoracion(fechaSolicitudValoracion);
		}
		
		Long gestorItKey =  getLong(entity, "gestor_it_key");
		if(gestorItKey != null) {
			coste.setGestor_it_key(gestorItKey);
		}
		
		String horasAnalisis =  getString(entity, "horas_analisis");
		if(horasAnalisis != null) {
			coste.setHoras_analisis(horasAnalisis);
		}
		
		String horasConstruccion =  getString(entity, "horas_construccion");
		if(horasConstruccion != null) {
			coste.setHoras_construccion(horasConstruccion);
		}
		
		String horasDiseño =  getString(entity, "horas_diseño");
		if(horasDiseño != null) {
			coste.setHoras_diseño(horasDiseño);
		}
		
		String horasGestion =  getString(entity, "horas_gestion");
		if(horasGestion != null) {
			coste.setHoras_gestion(horasGestion);
		}
		
		String horasPruebas =  getString(entity, "horas_pruebas");
		if(horasPruebas != null) {
			coste.setHoras_pruebas(horasPruebas);
		}
		
		String horasTotal =  getString(entity, "horas_total");
		if(horasTotal != null) {
			coste.setHoras_total(horasTotal);
		}
		
		String numControl =  getString(entity, "num_control");
		if(numControl != null) {
			coste.setNum_control(numControl);
		}
		
		String numValoracion =  getString(entity, "num_valoracion");
		if(numValoracion != null) {
			coste.setNum_valoracion(numValoracion);
		}
		
		String projectName =  getString(entity, "project_name");
		if(projectName != null) {
			coste.setProject_name(projectName);
		}
		
		Long projectKey =  getLong(entity, "projectKey");
		if(projectKey != null) {
			coste.setProjectKey(projectKey);
		}
		
		String 	strFechaAlta =  getString(entity, "str_fecha_alta");
		if(strFechaAlta != null) {
			coste.setStr_fecha_alta(strFechaAlta);
		}
		
		String strFechaRecepcionValoracion =  getString(entity, "str_fecha_recepcion_valoracion");
		if(strFechaRecepcionValoracion != null) {
			coste.setStr_fecha_alta(strFechaRecepcionValoracion);
		}
		
		String strFechaSolicitudValoracion =  getString(entity, "str_fecha_solicitud_valoracion");
		if(strFechaSolicitudValoracion != null) {
			coste.setStr_fecha_solicitud_valoracion(strFechaSolicitudValoracion);
		}
		
		return coste;
		
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
