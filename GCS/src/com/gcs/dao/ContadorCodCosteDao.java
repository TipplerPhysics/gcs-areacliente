package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;


import com.gcs.beans.ContadorCodCoste;

import com.gcs.persistence.PMF;

public class ContadorCodCosteDao {
	
	public static ContadorCodCosteDao getInstance() {
        return new ContadorCodCosteDao();
    }
	
	public void increaseCont (){
		ContadorCodCoste cc = getContador();
		cc.setNum(cc.getNum()+1);
		createContador(cc); 
	}
	
	public void decrementCont (){
		ContadorCodCoste cc = getContador();
		cc.setNum(cc.getNum()-1);
		createContador(cc); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorCodCoste cc = getContador();
		
		
		if (cc!=null){
			i = cc.getNum();			
		}else{
			ContadorCodCoste contador = new ContadorCodCoste(1);
		}
		
		return i;
	}
	
	public ContadorCodCoste getContador(){
		
		List<ContadorCodCoste> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorCodCoste.class.getName();		
		cd = (List<ContadorCodCoste>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorCodCoste cc){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cc);
		}finally{
			pm.close();
		}
	}
	
}
