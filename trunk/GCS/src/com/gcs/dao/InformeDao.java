package com.gcs.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		q.setOrdering("fecha_generado asc");
		informes = (List<Informe>) q.execute();
		
		pm.close();

		return informes;
	}
	
	public List<Informe> getAllInformesC(String Calendada) {

		List<Informe> informes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("SELECT from " + Informe.class.getName()+" WHERE tipo_subida=='"+Calendada+"'");		
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
	public List<Informe> getAllInformesByAnio(String Calendada,String Anio) {

		List<Informe> informes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		String petic = "SELECT from " + Informe.class.getName() + " WHERE anyo_implantacion=='"+Anio+"' &&tipo_subida=='"+Calendada+"'";
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
	public List<Informe> getAllInformesByMes(String Calendada ,String Anio, String Mes) {

		List<Informe> informes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		Query q = pm.newQuery("SELECT from " + Informe.class.getName() + " WHERE anyo_implantacion=='"+Anio+"' && mes_implantacion=='"+Mes+"' &&tipo_subida=='"+Calendada+"'");		
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
		List<Informe> informes;
		if (calendada==null||calendada==""||calendada=="All"||calendada=="all"){
			informes = iDao.getAllInformesByAnio(Anio);
		}else{
			informes = iDao.getAllInformesByAnio(calendada, Anio);
		}
		for (Informe inf : informes){
			String mes =  inf.getMesImplantacion();
			boolean existe = false;
			for (String mesarr:Meses){
				if(mesarr.equals(mes))existe=true;
			}
			if (!existe)Meses.add(mes);
		}	
		
		Collections.sort(Meses);
		//Comparator<String> comparador = Collections.reverseOrder();
		//Collections.sort(Meses, comparador);
		
		return Meses;
	}
	
	public List<String> getDaysForInforme(String calendada, String Anio, String Month){
		
		List <String> Dias = new ArrayList<String>();
		
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> informes;
		
		if (calendada==null||calendada==""||calendada=="All"||calendada=="all"){
			informes = iDao.getAllInformesByMes(Anio, Month);	
		}else{
			informes = iDao.getAllInformesByMes(calendada,Anio, Month);
		}
		
		
		for (Informe inf : informes){
			String dia =  inf.getDiaImplantacion();
			boolean existe = false;
			for (String dialis:Dias){
				if(dialis.equals(dia))existe=true;
			}
			if (!existe)Dias.add(dia);
		}
		
		Collections.sort(Dias);
		//Comparator<String> comparador = Collections.reverseOrder();
		//Collections.sort(Dias, comparador);
		
		return Dias;
	}
	
	public List<String> getYearsForInforme(String calendada){
		
		List <String> Anios = new ArrayList<String>();
		
		InformeDao iDao = InformeDao.getInstance();
		List<Informe> informes;
		
		if (calendada==null||calendada==""||calendada=="All"||calendada=="all"){
			informes = iDao.getAllInformes();	
		}else{
			informes = iDao.getAllInformesC(calendada);
		}
		
		
		for (Informe inf : informes){
			String anio =  inf.getAnyoImplantacion();
			boolean existe = false;
			for (String aniolis:Anios){
				if(aniolis.equals(anio))existe=true;
			}
			if (!existe)Anios.add(anio);
		}
		
		Collections.sort(Anios);
		Comparator<String> comparador = Collections.reverseOrder();
		Collections.sort(Anios, comparador);
		
		return Anios;
	}
	
}
