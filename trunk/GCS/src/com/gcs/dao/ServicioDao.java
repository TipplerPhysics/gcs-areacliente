package com.gcs.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Cliente;
import com.gcs.beans.Demanda;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ServicioDao {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

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
	
	public void deleteServicio(Servicio s){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( s.getClass(), s.getKey().getId())); 
		pm.close();
		
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
	
	
	public void createServicio(Servicio s){
		PersistenceManager pm = PMF.get().getPersistenceManager();	
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
			if (s.getStr_migracion_channeling()!=null && !"".equals(s.getStr_migracion_channeling())){			
				s.setFecha_migracion_channeling(Utils.dateConverter(s.getStr_migracion_channeling()));			
			}
			if (s.getStr_migracion_infra()!=null && !"".equals(s.getStr_migracion_infra())){			
				s.setFecha_migracion_infra(Utils.dateConverter(s.getStr_migracion_infra()));			
			}
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pm.makePersistent(s);
				
			} finally{
				pm.close();
			}
		}
		
		
	}
}
