package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorPagDemanda;
import com.gcs.persistence.PMF;

public class ContadorPagDemandaDao {
	
	public static ContadorPagDemandaDao getInstance() {
        return new ContadorPagDemandaDao();
    }
	
	public void increaseCont (){
		ContadorPagDemanda cd = getContador();
		cd.setNum(cd.getNum()+1);
		createContador(cd); 
	}
	
	public void decrementCont (){
		ContadorPagDemanda cd = getContador();
		cd.setNum(cd.getNum()-1);
		createContador(cd); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorPagDemanda cd = getContador();
		
		if (cd!=null){
			i = cd.getNum();			
		}else{
			ContadorPagDemanda contador = new ContadorPagDemanda(1);
		}
		
		return i;
	}
	
	public ContadorPagDemanda getContador(){
				
		List <ContadorPagDemanda> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorPagDemanda.class.getName();		
		cd = (List<ContadorPagDemanda>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorPagDemanda cd){
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}
	
	public void deleteContador(ContadorPagDemanda cd) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(cd.getClass(), cd.getKey().getId()));
		pm.close();

	}
	
}
