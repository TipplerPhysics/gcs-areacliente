package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorAuditoria;
import com.gcs.beans.ContadorCoste;
import com.gcs.persistence.PMF;

public class ContadorCosteDao {
	
	public static ContadorCosteDao getInstance() {
        return new ContadorCosteDao();
    }
	
	public void increaseCont (){
		ContadorCoste cc = getContador();
		
		
		cc.setNum(cc.getNum()+1);
		createContador(cc); 
	}
	
	public void decrementCont (){
		ContadorCoste cc = getContador();
		
		
		cc.setNum(cc.getNum()-1);
		createContador(cc); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorCoste cc = getContador();
		
		
		if (cc!=null){
			i = cc.getNum();			
		}else{
			ContadorCoste contador = new ContadorCoste(1);
		}
		
		return i;
	}
	
	public ContadorCoste getContador(){
		
		List <ContadorCoste> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorCoste.class.getName();		
		cd = (List<ContadorCoste>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorCoste cd){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}
	
}
