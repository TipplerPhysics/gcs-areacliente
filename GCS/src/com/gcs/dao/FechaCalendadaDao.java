package com.gcs.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.persistence.PMF;
import com.gcs.beans.FechaCalendada;
import com.gcs.utils.Utils;

public class FechaCalendadaDao {
	
	
	
	
	
	
	
		
		public static FechaCalendadaDao getInstance() {
			return new FechaCalendadaDao();
		}
		
		public synchronized void createFechaCalendada(FechaCalendada imp) throws ParseException {
			PersistenceManager pm = PMF.get().getPersistenceManager();	

			try{
				
				imp.setFecha(Utils.dateConverter(imp.getStr_fecha()));
			
				try {
					pm.makePersistent(imp);
				} finally {}
				
				
			}finally {
				pm.close();
				
			}
		}
		
		@SuppressWarnings("unchecked")
		public List<FechaCalendada> getAllFechas() {

			List<FechaCalendada> Fechas;
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			
			Query q = pm.newQuery("select from " + FechaCalendada.class.getName());
			q.setOrdering("name asc");
			Fechas = (List<FechaCalendada>) q.execute();
			
			
			pm.close();

			return Fechas;
		}
		
		public void deleteAll(){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			FechaCalendadaDao sDao = FechaCalendadaDao.getInstance();
			List<FechaCalendada> fechas =sDao.getAllFechas();
			for (FechaCalendada s :fechas){
				pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
			}


		}
	}


