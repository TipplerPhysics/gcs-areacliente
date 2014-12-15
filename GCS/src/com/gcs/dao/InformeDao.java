package com.gcs.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.Informe;
import com.gcs.beans.Servicio;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;

public class InformeDao {

	public static InformeDao getInstance() {
		return new InformeDao();
	}
	
	public Informe getInformeById(long l) {
	       PersistenceManager pManager = PMF.get().getPersistenceManager();
	       Informe informe_temp = pManager.getObjectById(Informe.class, l);	      
	       Informe informe = pManager.detachCopy(informe_temp);
	       pManager.close();
		      
	       return informe;
	}
	
	public void createInforme(Informe i, String usermail){
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		Boolean isNew = false;
		
		if (i.getKey()==null)
			isNew = true;
		
		try{
			pm.makePersistent(i);
			
		} finally{
			pm.close();
			
			if (isNew)
				Utils.writeLog(usermail, "Creó", "Informe", i.getKey().toString());
			else
				Utils.writeLog(usermail, "Editó", "Informe", i.getKey().toString());
		}
		
	}
	
	public List<Informe> getAllInformes() {

		List<Informe> informes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("SELECT from " + Informe.class.getName());		
		//q.setOrdering("fecha_generado desc");
		informes = (List<Informe>) q.execute();
		
		pm.close();

		return informes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Informe> getAllInformesByAnio(String Anio) {

		List<Informe> informes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		String petic = "SELECT from " + Informe.class.getName() + " WHERE anyo_implantacion=='"+Anio+"'";
		Query q = pm.newQuery(petic);		
		//q.setOrdering("fecha_generado desc");
		informes = (List<Informe>) q.execute();
		
		pm.close();

		return informes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Informe> getAllInformesByMes(String Anio, String Mes) {

		List<Informe> informes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		Query q = pm.newQuery("SELECT from " + Informe.class.getName() + " WHERE anyo_implantacion=='"+Anio+"' && mes_implantacion=='"+Mes+"'");		
		//q.setOrdering("fecha_generado desc");
		informes = (List<Informe>) q.execute();
		
		pm.close();

		return informes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Informe> getAllInformesByDate(String Calendada, String Anio, String Mes,String Dia) {

		List<Informe> informes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("SELECT from " + Informe.class.getName() + " WHERE anyo_implantacion=='"+Anio+"' && mes_implantacion=='"+Mes+"' && dia_implantacion=='"+Dia+"' &&tipo_subida=='"+Calendada+"'");
		//q.setOrdering("fecha_generado desc");
		informes = (List<Informe>) q.execute();
		
		pm.close();

		return informes;
	}
	
	public List<String> getMonthsForInforme(String calendada, String Anio){
		
		List <String> Meses = new ArrayList<String>();
		
		InformeDao iDao = InformeDao.getInstance();
		
		List<Informe> informes = iDao.getAllInformesByAnio(Anio);
		
		for (Informe inf : informes){
			String mes =  inf.getMesImplantacion();
			boolean existe = false;
			for (String mesarr:Meses){
				if(mesarr.equals(mes))existe=true;
			}
			if (!existe)Meses.add(mes);
		}	
		
		return Meses;
	}
	
	public List<String> getDaysForInforme(String calendada, String Anio, String Month){
		
		List <String> Dias = new ArrayList<String>();
		
		InformeDao iDao = InformeDao.getInstance();
		
		List<Informe> informes = iDao.getAllInformesByMes(Anio, Month);
		
		for (Informe inf : informes){
			String dia =  inf.getDiaImplantacion();
			boolean existe = false;
			for (String dialis:Dias){
				if(dialis.equals(dia))existe=true;
			}
			if (!existe)Dias.add(dia);
		}
		
		
		return Dias;
	}
	
}
