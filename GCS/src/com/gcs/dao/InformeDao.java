package com.gcs.dao;

import java.text.ParseException;
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
		
		Query q = pm.newQuery("select from " + Informe.class.getName());		
		//q.setOrdering("fecha_generado desc");
		informes = (List<Informe>) q.execute();
		
		pm.close();

		return informes;
	}
}
