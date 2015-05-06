package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorAuditoria;
import com.gcs.beans.ContadorCoste;
import com.gcs.beans.ContadorProyecto;
import com.gcs.persistence.PMF;

public class ContadorAuditoriaDao {
	
	public static ContadorAuditoriaDao getInstance() {
        return new ContadorAuditoriaDao();
    }
	
	public void increaseCont (){
		ContadorAuditoria ca = getContador();
		
		
		ca.setNum(ca.getNum()+1);
		createContador(ca); 
	}
	
	public void decrementCont (){
		ContadorAuditoria ca = getContador();
		
		
		ca.setNum(ca.getNum()-1);
		createContador(ca); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorAuditoria ca = getContador();
		
		
		if (ca!=null){
			i = ca.getNum();			
		}else{
			ContadorAuditoria contador = new ContadorAuditoria(1);
		}
		
		return i;
	}
	
	public ContadorAuditoria getContador(){
		
		List <ContadorAuditoria> ca;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorAuditoria.class.getName();		
		ca = (List<ContadorAuditoria>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return ca.get(0);
		
	}
	
	public void  createContador(ContadorAuditoria ca){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(ca);
		}finally{
			pm.close();
		}
	}
	
	
}
