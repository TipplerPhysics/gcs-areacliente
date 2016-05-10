package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;


import com.gcs.beans.ContadorServicioFile;
import com.gcs.persistence.PMF;

public class ContadorServicioFileDao {
	
	public static ContadorServicioFileDao getInstance() {
        return new ContadorServicioFileDao();
    }
	
	public void increaseCont (){
		ContadorServicioFile cs = getContador();
		cs.setNum(cs.getNum()+1);
		createContador(cs); 
	}
	
	public void decrementCont (){
		ContadorServicioFile cs = getContador();
		cs.setNum(cs.getNum()-1);
		createContador(cs); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorServicioFile cs = getContador();
		
		
		if (cs!=null){
			i = cs.getNum();			
		}else{
			ContadorServicioFile contador = new ContadorServicioFile(1);
		}
		
		return i;
	}
	
	public ContadorServicioFile getContador(){
		
		List <ContadorServicioFile> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorServicioFile.class.getName();		
		cd = (List<ContadorServicioFile>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorServicioFile cs){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cs);
		}finally{
			pm.close();
		}
	}
	
}
