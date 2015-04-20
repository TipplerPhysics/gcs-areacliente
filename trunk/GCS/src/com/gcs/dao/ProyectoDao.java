package com.gcs.dao;

import java.text.ParseException;
import java.util.List;

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

public class ProyectoDao {
	
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
			else
				project_number++;
			
			String cod_proyecto = createProjectName(c,p,project_number);			
			p.setCod_proyecto(cod_proyecto);
			
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
			
			if (isNew)
				Utils.writeLog(usermail, "Creó", "Proyecto", p.getCod_proyecto());
			else
				Utils.writeLog(usermail, "Editó", "Proyecto", p.getCod_proyecto());
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
			else
				project_number++;
			
			String cod_proyecto = createProjectName(c,p,project_number);			
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
		
		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> servicios = sDao.getServiciosByProject(p.getKey().getId());	
		
		for (Servicio s:servicios){
			sDao.deleteServicio(s,usermail);
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(p.getClass(), p.getKey().getId()));
		pm.close();
		
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
	
}

