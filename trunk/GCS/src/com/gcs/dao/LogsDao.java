package com.gcs.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Log;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;

public class LogsDao {

	public static final int DATA_SIZE = 10;
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static LogsDao getInstance() {
		return new LogsDao();
	}

	public void createLog(Log log){
		Date fecha = new Date();
		String fecha_str = Utils.dateConverterToStr(fecha);
		ContadorAuditoriaDao caDao = ContadorAuditoriaDao.getInstance();
		caDao.increaseCont();
		
		log.setFecha_str(fecha_str);
		log.setFecha(fecha);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(log);
		} finally {
			pm.close();
		}
	}
	
	public List<Log> getAllLogs() {

		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("select from " + Log.class.getName());		
		q.setOrdering("fecha desc");
		logs = (List<Log>) q.execute();
		
		pm.close();

		return logs;
	}
	
	public List<Log> getLogsByLastWeek() {

		Date date = new Date();
		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, -7);
		date.setTime(cal.getTimeInMillis());
		
		Query q = pm.newQuery("select from " + Log.class.getName() + " where fecha > date");		
		q.setOrdering("fecha desc");
		
		q.declareImports("import java.util.Date");
		q.declareParameters("Date date");
		
		
		logs = (List<Log>) q.execute(date);
		
		pm.close();

		return logs;
	}
	
	public List<Log> getLogsByLastMonth() {

		Date date = new Date();
		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, -30);
		date.setTime(cal.getTimeInMillis());
		
		Query q = pm.newQuery("select from " + Log.class.getName() + " where fecha > date");		
		q.setOrdering("fecha desc");
		
		q.declareImports("import java.util.Date");
		q.declareParameters("Date date");
		
		
		logs = (List<Log>) q.execute(date);
		
		pm.close();

		return logs;
	}
	
	public List<Log> getLogsByLast3Months() {

		Date date = new Date();
		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, -91);
		date.setTime(cal.getTimeInMillis());
		
		Query q = pm.newQuery("select from " + Log.class.getName() + " where fecha > date");		
		q.setOrdering("fecha desc");
		
		q.declareImports("import java.util.Date");
		q.declareParameters("Date date");
		
		
		logs = (List<Log>) q.execute(date);
		
		pm.close();

		return logs;
	}
	
	public List<Log> getAllLogPagin(Integer page) {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Log");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Log> log = new ArrayList<Log>();	;
		
		for (Entity result : entities){
			log.add(buildLog(result));
		}

		return log;
	}
	
	private Log buildLog(Entity entity) {
		
		Log log = new Log();
		
		log.setKey(entity.getKey());
		
		String accion =  getString(entity, "accion");
		if(accion != null) {
			log.setAccion(accion);
		}
		
		String detalle =  getString(entity, "detalle");
		if(detalle != null) {
			log.setDetalle(detalle);
		}
		
		String entidad =  getString(entity, "entidad");
		if(entidad != null) {
			log.setEntidad(entidad);
		}
		
		Date fecha = getDate(entity, "fecha");
		if(fecha != null) {
			log.setFecha(fecha);
		}

		String fecha_str =  getString(entity, "fecha_str");
		if(fecha_str != null) {
			log.setFecha_str(fecha_str);
		}
		
		String nombre_entidad =  getString(entity, "nombre_entidad");
		if(nombre_entidad != null) {
			log.setNombre_entidad(nombre_entidad);
		}
		
		String usuario =  getString(entity, "usuario");
		if(usuario != null) {
			log.setUsuario(usuario);
		}
		
		String usuario_mail =  getString(entity, "usuario_mail");
		if(usuario_mail != null) {
			log.setUsuario_mail(usuario_mail);
		}
		
		return log;
		
	}
	
	private String getString(Entity e, String field) {
		try {
			return (String) e.getProperty(field);
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
	

