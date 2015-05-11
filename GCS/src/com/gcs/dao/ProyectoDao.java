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
import com.gcs.beans.Coste;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
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

public class ProyectoDao {
	
	public static final int DATA_SIZE = 10;
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ProyectoDao getInstance(){
		return new ProyectoDao();
	}
	
	public String getCoste(Proyecto p){
		
		ProyectoDao pDao = ProyectoDao.getInstance();
		CosteDao cDao = CosteDao.getInstance();
		Double d = new Double(0);
		
		List<Coste> costes = cDao.getCostesByProject(p.getKey().getId());
		
		for (Coste c:costes){
			if (!"".equals(c.getCoste_total()))
			d += Double.parseDouble(c.getCoste_total().replace(",","."));
		}
		
		p.setCoste(d.toString());
		try {
			pDao.createProject(p, "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return d.toString();
		
	}
	
	public void createProject(Proyecto p,String usermail) throws ParseException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		ContadorProyectoDao cpDao = ContadorProyectoDao.getInstance();
		
		Boolean isNew = false;
		
		if (p.getKey() == null)
			isNew = true;
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClienteById(p.getClienteKey());
		UserDao uDao = UserDao.getInstance();
		User gestor_it = uDao.getUserbyId(p.getGestor_it());
		User gestor_negocio = uDao.getUserbyId(p.getGestor_negocio());
		
		p.setClienteName(c.getNombre());
		p.setGestor_it_name(gestor_it.getFullName());
		p.setGestor_negocio_name(gestor_negocio.getFullName());
		
		if (p.getStr_envioC100() != null && !"".equals(p.getStr_envioC100()))
			p.setEnvioC100(Utils.dateConverter(p.getStr_envioC100()));
		
		if (p.getStr_OKNegocio() != null && !"".equals(p.getStr_OKNegocio()))
			p.setOkNegocio(Utils.dateConverter(p.getStr_OKNegocio()));
		
		if (p.getStr_fecha_inicio_valoracion() != null && !"".equals(p.getStr_fecha_inicio_valoracion())){
			p.setFecha_inicio_valoracion(Utils.dateConverter(p.getStr_fecha_inicio_valoracion()));
		}
		
		if (p.getStr_fecha_fin_valoracion() != null && !"".equals(p.getStr_fecha_fin_valoracion())){
			p.setFecha_fin_valoracion(Utils.dateConverter(p.getStr_fecha_fin_valoracion()));
		}
		
		if (p.getStr_fecha_inicio_viabilidad() != null && !"".equals(p.getStr_fecha_inicio_viabilidad())){
			p.setFecha_inicio_viabilidad(Utils.dateConverter(p.getStr_fecha_inicio_viabilidad()));
		}
		
		if (p.getStr_fecha_fin_viabilidad() != null && !"".equals(p.getStr_fecha_fin_viabilidad())){
			p.setFecha_fin_viabilidad(Utils.dateConverter(p.getStr_fecha_fin_viabilidad()));
		}
		
		if (p.getStr_fecha_disponible_conectividad() != null && !"".equals(p.getStr_fecha_disponible_conectividad())){
			p.setFecha_disponible_conectividad(Utils.dateConverter(p.getStr_fecha_disponible_conectividad()));
		}
		
		if (p.getStr_fecha_plan_trabajo() != null && !"".equals(p.getStr_fecha_plan_trabajo())){
			p.setFecha_plan_trabajo(Utils.dateConverter(p.getStr_fecha_plan_trabajo()));
		}
		
		
		
		if (p.getKey()==null){
			Integer project_number = c.getProject_number();
			if (project_number==null)
				project_number=1;			
			
				
		
			String cod_proyecto = createProjectName(c,p,project_number);			
			p.setCod_proyecto(cod_proyecto);
			project_number++;
			c.setProject_number(project_number);
			cDao.createCliente(c,"");		
		}
		
		try{
			pm.makePersistent(p);
		}
		finally{
			ServicioDao sDao = ServicioDao.getInstance();
			List<Servicio> servicios = sDao.getServiciosByProject(p.getKey().getId());
			
			for (Servicio s:servicios){
				s.setGestor_it_key(p.getGestor_it());
				s.setGestor_it_name(p.getGestor_it_name());
				s.setGestor_negocio_key(p.getGestor_negocio());
				s.setGestor_negocio_name(p.getGestor_negocio_name());
				
				sDao.createServicio(s, "");
			}
			
			pm.close();
			
			if (isNew){
				Utils.writeLog(usermail, "Creó", "Proyecto", p.getCod_proyecto());
				cpDao.increaseCont();
			}else{
				Utils.writeLog(usermail, "Editó", "Proyecto", p.getCod_proyecto());
			}
		}		
	}
	
	public void createProjectRaw(Proyecto p) throws ParseException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(p);
		}finally{
			pm.close();
		}	
	}
	
	public void createProjectImport(Proyecto p,String usermail) throws ParseException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Boolean isNew = false;
		
		if (p.getKey() == null)
			isNew = true;
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClienteById(p.getClienteKey());
		UserDao uDao = UserDao.getInstance();
		User gestor_it = uDao.getUserbyId(p.getGestor_it());
		User gestor_negocio = uDao.getUserbyId(p.getGestor_negocio());
		
		p.setClienteName(c.getNombre());
		p.setGestor_it_name(gestor_it.getFullName());
		p.setGestor_negocio_name(gestor_negocio.getFullName());
		
		if (p.getStr_envioC100() != null && !"".equals(p.getStr_envioC100()))
			p.setEnvioC100(Utils.dateConverter(p.getStr_envioC100()));
		
		if (p.getStr_OKNegocio() != null && !"".equals(p.getStr_OKNegocio()))
			p.setOkNegocio(Utils.dateConverter(p.getStr_OKNegocio()));
		
		if (p.getStr_fecha_inicio_valoracion() != null && !"".equals(p.getStr_fecha_inicio_valoracion())){
			p.setFecha_inicio_valoracion(Utils.dateConverter(p.getStr_fecha_inicio_valoracion()));
		}
		
		if (p.getStr_fecha_fin_valoracion() != null && !"".equals(p.getStr_fecha_fin_valoracion())){
			p.setFecha_fin_valoracion(Utils.dateConverter(p.getStr_fecha_fin_valoracion()));
		}
		
		if (p.getStr_fecha_inicio_viabilidad() != null && !"".equals(p.getStr_fecha_inicio_viabilidad())){
			p.setFecha_inicio_viabilidad(Utils.dateConverter(p.getStr_fecha_inicio_viabilidad()));
		}
		
		if (p.getStr_fecha_fin_viabilidad() != null && !"".equals(p.getStr_fecha_fin_viabilidad())){
			p.setFecha_fin_viabilidad(Utils.dateConverter(p.getStr_fecha_fin_viabilidad()));
		}
		
		if (p.getStr_fecha_disponible_conectividad() != null && !"".equals(p.getStr_fecha_disponible_conectividad())){
			p.setFecha_disponible_conectividad(Utils.dateConverter(p.getStr_fecha_disponible_conectividad()));
		}
		
		if (p.getStr_fecha_plan_trabajo() != null && !"".equals(p.getStr_fecha_plan_trabajo())){
			p.setFecha_plan_trabajo(Utils.dateConverter(p.getStr_fecha_plan_trabajo()));
		}
		
		
		
		if (p.getKey()==null){
			Integer project_number = c.getProject_number();
			if (project_number==null)
				project_number=1;			
			
			
			
			project_number = 3;
			String cod_proyecto = createProjectName(c,p,project_number);
			p.setCod_proyecto(cod_proyecto);
			project_number++;
			//p.setCod_proyecto(cod_proyecto);
			
			c.setProject_number(project_number);
			cDao.createCliente(c,"");		
		}
		
		try{
			pm.makePersistent(p);
		}
		finally{
			ServicioDao sDao = ServicioDao.getInstance();
			List<Servicio> servicios = sDao.getServiciosByProject(p.getKey().getId());
			
			for (Servicio s:servicios){
				s.setGestor_it_key(p.getGestor_it());
				s.setGestor_it_name(p.getGestor_it_name());
				s.setGestor_negocio_key(p.getGestor_negocio());
				s.setGestor_negocio_name(p.getGestor_negocio_name());
				
				sDao.createServicio(s, "");
			}
			
			pm.close();
		}		
	}
	
	public void deleteProject(Proyecto p, String usermail){
		ContadorProyectoDao cpDao = ContadorProyectoDao.getInstance();
		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> servicios = sDao.getServiciosByProject(p.getKey().getId());	
		
		for (Servicio s:servicios){
			sDao.deleteServicio(s,usermail);
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(p.getClass(), p.getKey().getId()));
		pm.close();
		
		cpDao.decrementCont();
		
		Utils.writeLog(usermail, "Eliminó", "Proyecto", p.getCod_proyecto());
	}
	
	public void deleteAllProject(String usermail){
		
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		List<Proyecto> proyectos = proyectoDao.getAllProjects();
		for(Proyecto proy:proyectos){
			proyectoDao.deleteProject(proy, usermail);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> getAllProjects() {
		
		List<Proyecto> projects;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("select from " + Proyecto.class.getName());		
		q.setOrdering("fecha_alta desc");
		projects = (List<Proyecto>) q.execute();
		
		pm.close();
		/*
		ProyectoDao pDao = ProyectoDao.getInstance();
		Proyecto proyecto = null;
		for(int i = 0; i < projects.size(); i++) {
			proyecto = projects.get(i);
			String coste = pDao.getCoste(proyecto);
			proyecto.setCoste(coste);
		}
*/
		return projects;	
	}
	
public List<Proyecto> getProjectsByClient(Long id){
	
	
	PersistenceManager pManager = PMF.get().getPersistenceManager();
	Transaction transaction = pManager.currentTransaction();
	transaction.begin();


	Query q = pManager.newQuery("select from " + Proyecto.class.getName()
			+ " WHERE clienteKey == :id");

	q.setOrdering("cod_proyecto");
	
	List<Proyecto> projects = (List<Proyecto>) q.execute(id);

	transaction.commit();

	pManager.close();

	return projects;
}
public List<Proyecto> getProjectsByClientByDates(Long id,Date desde, Date hasta){
	
	
	PersistenceManager pManager = PMF.get().getPersistenceManager();
	Transaction transaction = pManager.currentTransaction();
	transaction.begin();


	Query q = pManager.newQuery("select from " + Proyecto.class.getName());

	q.setFilter("fecha_alta >= fechaDesde && fecha_alta <= fechaHasta && clienteKey == id");
	q.declareParameters("java.util.Date fechaDesde , java.util.Date fechaHasta, Long id");
	
	List<Proyecto> projects = (List<Proyecto>) q.execute(desde, hasta, id);

	transaction.commit();

	pManager.close();

	return projects;
}
public List<Proyecto> getProjectsByCode(String id){
	
	
	PersistenceManager pManager = PMF.get().getPersistenceManager();
	Transaction transaction = pManager.currentTransaction();
	transaction.begin();

	String queryStr = "select from " + Proyecto.class.getName()
			+ " WHERE cod_proyecto == :id";

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
		
		project_name = "PROY"+cod_cliente+"_"+String.format("%02d", project_number);
		
		return project_name;
	}
	
	
	public List<Proyecto> getProyectKeyBetweenDatesAndState(String desde, String hasta, String producto) throws ParseException {
		
		Date desdeDate = Utils.dateConverter(desde);
		Date hastaDate = Utils.dateConverter(hasta);

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Proyecto");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();
		
		if(!desde.equals("")){
			finalFilters.add(new FilterPredicate("fecha_alta", FilterOperator.GREATER_THAN_OR_EQUAL, desdeDate));
		}
		if(!hasta.equals("")){
			finalFilters.add(new FilterPredicate("fecha_alta", FilterOperator.LESS_THAN_OR_EQUAL, hastaDate));
		}
		if(!producto.equals("")){
			finalFilters.add(new FilterPredicate("producto", FilterOperator.EQUAL, producto));
		}
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);
		List<Proyecto> proyectoskey = new ArrayList<Proyecto>();
		for(Entity entity : entities){
			proyectoskey.add(buildProyectokey(entity));
		}
		return proyectoskey;
	}
	
	public List<Proyecto> getProyectKeyForState(String producto) throws ParseException {
		


		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Proyecto");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();

		List<Filter> finalFilters = new ArrayList<>();

		if(!producto.equals("")){
			finalFilters.add(new FilterPredicate("producto", FilterOperator.EQUAL, producto));
		}
		
		Filter finalFilter = null;
		if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
		if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
		if(finalFilters.size()!=0)q.setFilter(finalFilter);
		
		entities = datastore.prepare(q).asList(fetchOptions);
		List<Proyecto> proyectoskey = new ArrayList<Proyecto>();
		for(Entity entity : entities){
			proyectoskey.add(buildProyectokey(entity));
		}
		return proyectoskey;
	}
	
	public List<Proyecto> getProyectoByAllParam(String fechaEntrada, String codProyecto, String nCliente, String clasificacion, String tipo, String coste,  Integer page){
		List<Proyecto> proyectos= null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Proyecto");
		List<Filter> finalFilters = new ArrayList<>();
		
		int filters =0;
		if(!fechaEntrada.equals("")){
			filters++;
		}
		if(!codProyecto.equals("")){
			filters++;
		}
		if(!nCliente.equals("")){
			filters++;
		}
		if(!clasificacion.equals("")){
			filters++;
		}
		if(!tipo.equals("")){
			filters++;
		}
		if(!coste.equals("")){
			filters++;
		}
		
		if(filters<=1){
			if(!fechaEntrada.equals("")){
				finalFilters.add(new FilterPredicate("fecha_alta_str",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
				finalFilters.add(new FilterPredicate("fecha_alta_str",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
			}
			if(!codProyecto.equals("")){
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.GREATER_THAN_OR_EQUAL, codProyecto));
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.LESS_THAN, codProyecto+"\ufffd"));
			}
			if(!nCliente.equals("")){
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
			}
			if(!clasificacion.equals("")){
				finalFilters.add(new FilterPredicate("clasificacion",FilterOperator.GREATER_THAN_OR_EQUAL, clasificacion));
				finalFilters.add(new FilterPredicate("clasificacion",FilterOperator.LESS_THAN, clasificacion+"\ufffd"));
			}
			if(!tipo.equals("")){
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.GREATER_THAN_OR_EQUAL, tipo));
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.LESS_THAN, tipo+"\ufffd"));
			}
			if(!coste.equals("")){
				finalFilters.add(new FilterPredicate("coste",FilterOperator.GREATER_THAN_OR_EQUAL, coste));
				finalFilters.add(new FilterPredicate("coste",FilterOperator.LESS_THAN, coste+"\ufffd"));
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
			proyectos = new ArrayList<>();
			for(Entity result:entities){
				proyectos.add(buildProyecto(result));
			}
			Proyecto impPage = new Proyecto();
			impPage.setDetalle("0");
			proyectos.add(impPage);
		
		}else{
			
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!fechaEntrada.equals("")){
				q = new com.google.appengine.api.datastore.Query("Proyecto");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("fecha_alta_str",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
				finalFilters.add(new FilterPredicate("fecha_alta_str",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!codProyecto.equals("")){
				q = new com.google.appengine.api.datastore.Query("Proyecto");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.GREATER_THAN_OR_EQUAL, codProyecto));
				finalFilters.add(new FilterPredicate("cod_proyecto",FilterOperator.LESS_THAN, codProyecto+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nCliente.equals("")){
				q = new com.google.appengine.api.datastore.Query("Proyecto");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
				finalFilters.add(new FilterPredicate("clienteName",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!clasificacion.equals("")){
				q = new com.google.appengine.api.datastore.Query("Proyecto");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("clasificacion",FilterOperator.GREATER_THAN_OR_EQUAL, clasificacion));
				finalFilters.add(new FilterPredicate("clasificacion",FilterOperator.LESS_THAN, clasificacion+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!tipo.equals("")){
				q = new com.google.appengine.api.datastore.Query("Proyecto");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.GREATER_THAN_OR_EQUAL, tipo));
				finalFilters.add(new FilterPredicate("tipo",FilterOperator.LESS_THAN, tipo+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!coste.equals("")){
				q = new com.google.appengine.api.datastore.Query("Proyecto");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("coste",FilterOperator.GREATER_THAN_OR_EQUAL, coste));
				finalFilters.add(new FilterPredicate("coste",FilterOperator.LESS_THAN, coste+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			List<Entity> proyectosFinal = new ArrayList<>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			proyectosFinal = Entities.get(lowRowsIndex);
			List<Entity> indexDel = new ArrayList<Entity>();
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int j = 0;
					for (Entity result : proyectosFinal) {
						if(!Entities.get(i).contains(result)){
							Entity auxEnty = proyectosFinal.get(j);
							if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
						}
						j++;
					}
				}
			}
			
			for (Entity impborr : indexDel){
				proyectosFinal.remove(impborr);
			}
			
			proyectos = new ArrayList<Proyecto>();
			int proyectosPages  = proyectosFinal.size();
			for(int i = page*10; i< (page*10)+10&&i<proyectosFinal.size();i++){
				proyectos.add(buildProyecto(proyectosFinal.get(i)));
			}
			Proyecto pages = new Proyecto();
			pages.setDetalle(Integer.toString(proyectosPages));
			proyectos.add(pages);
		}
		return proyectos;
	}
	
	public List<Proyecto> getAllProyectoPagin(Integer page) {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Proyecto");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		
		for (Entity result : entities){
			proyectos.add(buildProyecto(result));
		}

		return proyectos;
	}

	private Proyecto buildProyectokey(Entity entity) {
		Proyecto proyecto = new Proyecto();
		
		proyecto.setKey(entity.getKey());
		return proyecto;
	}
	private Proyecto buildProyecto(Entity entity) {
		Proyecto proyecto = new Proyecto();
		
		proyecto.setKey(entity.getKey());
		
		Integer clasificacion = getInteger(entity, "clasificacion");
		if(clasificacion != null) {
			proyecto.setClasificacion(clasificacion);
		}
		
		Long clienteKey =  getLong(entity, "clienteKey");
		if(clienteKey != null) {
			proyecto.setClienteKey(clienteKey);
		}
		
		String clienteName =  getString(entity, "clienteName");
		if(clienteName != null) {
			proyecto.setClienteName(clienteName);
		}
		
		String codProyecto =  getString(entity, "cod_proyecto");
		if(codProyecto != null) {
			proyecto.setCod_proyecto(codProyecto);
		}
		
		String conectividad =  getString(entity, "conectividad");
		if(conectividad != null) {
			proyecto.setConectividad(conectividad);
		}
		
		String coste =  getString(entity, "coste");
		if(coste != null) {
			proyecto.setCoste(coste);
		}
		
		String detalle =  getString(entity, "detalle");
		if(detalle != null) {
			proyecto.setDetalle(detalle);
		}
				
		Date envioC100 = getDate(entity, "envioC100");
		if(envioC100 != null) {
			proyecto.setEnvioC100(envioC100);
		}
		
		Date fechaAlta =  getDate(entity, "fecha_alta");
		if(fechaAlta != null) {
			proyecto.setFecha_alta(fechaAlta);
		}
		
		String fechaAltaStr =  getString(entity, "fecha_alta_str");
		if(fechaAltaStr != null) {
			proyecto.setFecha_alta_str(fechaAltaStr);
		}
		
		Date fechaDisponibleConectividad = getDate(entity, "fecha_disponible_conectividad");
		if(fechaDisponibleConectividad != null) {
			proyecto.setFecha_disponible_conectividad(fechaDisponibleConectividad);
		}
		
		Date fechaFinValoracion = getDate(entity, "fecha_fin_valoracion");
		if(fechaFinValoracion != null) {
			proyecto.setFecha_fin_valoracion(fechaFinValoracion);
		}
		
		Date fechaFinViabilidad = getDate(entity, "fecha_fin_viabilidad");
		if(fechaFinViabilidad != null) {
			proyecto.setFecha_fin_viabilidad(fechaFinViabilidad);
		}
		
		Date fechaInicioValoracion = getDate(entity, "fecha_inicio_valoracion");
		if(fechaInicioValoracion != null) {
			proyecto.setFecha_inicio_valoracion(fechaInicioValoracion);
		}
		
		Date fechaInicioViabilidad = getDate(entity, "fecha_inicio_viabilidad");
		if(fechaInicioViabilidad != null) {
			proyecto.setFecha_inicio_viabilidad(fechaInicioViabilidad);
		}
		
		Date fechaPlanTrabajo = getDate(entity, "fecha_plan_trabajo");
		if(fechaPlanTrabajo != null) {
			proyecto.setFecha_plan_trabajo(fechaPlanTrabajo);
		}
		
		Long gestorIt =  getLong(entity, "gestor_it");
		if(gestorIt != null) {
			proyecto.setGestor_it(gestorIt);
		}
		
		String gestorItName =  getString(entity, "gestor_it_name");
		if(gestorItName != null) {
			proyecto.setGestor_it_name(gestorItName);
		}
		
		Long gestorNegocio =  getLong(entity, "gestor_negocio");
		if(gestorNegocio != null) {
			proyecto.setGestor_negocio(gestorNegocio);
		}
		
		String gestorNegocioName =  getString(entity, "gestor_negocio_name");
		if(gestorNegocioName != null) {
			proyecto.setGestor_negocio_name(gestorNegocioName);
		}
		
		Date okNegocio = getDate(entity, "okNegocio");
		if(okNegocio != null) {
			proyecto.setOkNegocio(okNegocio);
		}
		
		String producto =  getString(entity, "producto");
		if(producto != null) {
			proyecto.setProducto(producto);
		}
		
		String servicio =  getString(entity, "servicio");
		if(servicio != null) {
			proyecto.setServicio(servicio);
		}
		
		String strEnvioC100 =  getString(entity, "str_envioC100");
		if(strEnvioC100 != null) {
			proyecto.setStr_envioC100(strEnvioC100);
		}
		
		String strFechaDisponibleConectividad =  getString(entity, "str_fecha_disponible_conectividad");
		if(strFechaDisponibleConectividad != null) {
			proyecto.setStr_fecha_disponible_conectividad(strFechaDisponibleConectividad);
		}
		
		String strFechaFinValoracion =  getString(entity, "str_fecha_fin_valoracion");
		if(strFechaFinValoracion != null) {
			proyecto.setStr_fecha_fin_valoracion(strFechaFinValoracion);
		}
		
		String strFechaFinViabilidad =  getString(entity, "str_fecha_fin_viabilidad");
		if(strFechaFinViabilidad != null) {
			proyecto.setStr_fecha_fin_viabilidad(strFechaFinViabilidad);
		}
		
		String strFechaInicioValoracion =  getString(entity, "str_fecha_inicio_valoracion");
		if(strFechaInicioValoracion != null) {
			proyecto.setStr_fecha_inicio_valoracion(strFechaInicioValoracion);
		}
		
		String strFechaInicioViabilidad =  getString(entity, "str_fecha_inicio_viabilidad");
		if(strFechaInicioViabilidad != null) {
			proyecto.setStr_fecha_inicio_viabilidad(strFechaInicioViabilidad);
		}
		
		String strFechaPlanTrabajo =  getString(entity, "str_fecha_plan_trabajo");
		if(strFechaPlanTrabajo != null) {
			proyecto.setStr_fecha_plan_trabajo(strFechaPlanTrabajo);
		}
		
		String strOKNegocio =  getString(entity, "str_OKNegocio");
		if(strOKNegocio != null) {
			proyecto.setStr_OKNegocio(strOKNegocio);
		}
		
		String strOKNegocioCheck =  getString(entity, "str_OKNegocioCheck");
		if(strOKNegocioCheck != null) {
			proyecto.setStr_OKNegocioCheck(strOKNegocioCheck);
		}
		
		String subtipo =  getString(entity, "subtipo");
		if(subtipo != null) {
			proyecto.setSubtipo(subtipo);
		}
		
		String tipo =  getString(entity, "tipo");
		if(tipo != null) {
			proyecto.setTipo(tipo);
		}
		
		String urlDocGoogleDrive =  getString(entity, "url_doc_google_drive");
		if(urlDocGoogleDrive != null) {
			proyecto.setUrl_doc_google_drive(urlDocGoogleDrive);
		}
		
		return proyecto;
		
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

