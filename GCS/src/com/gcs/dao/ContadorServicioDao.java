package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorProyecto;
import com.gcs.beans.ContadorServicio;
import com.gcs.persistence.PMF;

public class ContadorServicioDao {
	
	public static ContadorServicioDao getInstance() {
        return new ContadorServicioDao();
    }
	
	public void increaseCont (){
		ContadorServicio cs = getContador();
		cs.setNum(cs.getNum()+1);
		createContador(cs); 
	}
	
	public void decrementCont (){
		ContadorServicio cs = getContador();
		cs.setNum(cs.getNum()-1);
		createContador(cs); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorServicio cs = getContador();
		
		
		if (cs!=null){
			i = cs.getNum();			
		}else{
			ContadorServicio contador = new ContadorServicio(1);
		}
		
		return i;
	}
	
	public ContadorServicio getContador(){
		
		List <ContadorServicio> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorServicio.class.getName();		
		cd = (List<ContadorServicio>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorServicio cs){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cs);
		}finally{
			pm.close();
		}
	}
	
}
