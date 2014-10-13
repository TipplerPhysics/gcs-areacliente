package com.gcs.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Coste;
import com.gcs.beans.Equipo;
import com.gcs.persistence.PMF;

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
	
	public void createCoste(Coste c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
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
		}
	}
	
	public List<Coste> getAllCostes() {

		List<Coste> costes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryStr = "select from " + Coste.class.getName();
		costes = (List<Coste>) pm.newQuery(queryStr).execute();

		return costes;
	}

	public void deleteCoste(Coste c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(c.getClass(), c.getKey().getId()));
		pm.close();

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
