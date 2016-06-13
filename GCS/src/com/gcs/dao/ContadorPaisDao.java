package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorPais;
import com.gcs.persistence.PMF;


public class ContadorPaisDao {

	public static ContadorPaisDao getInstance() {
	    return new ContadorPaisDao();
	}

	public void increaseCont (){
		ContadorPais cu = getContador();
		cu.setNum(cu.getNum()+1);
		createContador(cu); 
	}

	public void decrementCont (){
		ContadorPais cu = getContador();
		cu.setNum(cu.getNum()-1);
		createContador(cu); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorPais cu = getContador();
		
		
		if (cu!=null){
			i = cu.getNum();			
		}else{
			ContadorPais contador = new ContadorPais(1);
		}
		
		return i;
	}

	public ContadorPais getContador(){
		
		List <ContadorPais> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorPais.class.getName();		
		cd = (List<ContadorPais>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}

	public void  createContador(ContadorPais cd){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}

}
