package com.gcs.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Coste;
import com.gcs.beans.Demanda;
import com.gcs.beans.Equipo;
import com.gcs.beans.Proyecto;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;

public class CosteDao {
	
	public static CosteDao getInstance(){
		return new CosteDao();
	}
	
	public List<Coste> getCostesByProject(Long id) {

		

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Coste.class.getName()
				+ " where projectKey  == :p1";

		List<Coste> costes = (List<Coste>) pManager.newQuery(queryStr).execute(id);

		
		transaction.commit();
		pManager.close();

		return costes;

	}
	
	public void createCoste(Coste c, String usermail) throws ParseException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Boolean isNew = false;
		
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
			if (isNew)
				Utils.writeLog(usermail, "Creó", "Coste", c.getProject_name());
			else
				Utils.writeLog(usermail, "Editó", "Coste", c.getProject_name());
			
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
}
