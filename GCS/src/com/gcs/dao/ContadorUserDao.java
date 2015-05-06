package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorServicio;
import com.gcs.beans.ContadorUser;
import com.gcs.persistence.PMF;

public class ContadorUserDao {
	
	public static ContadorUserDao getInstance() {
        return new ContadorUserDao();
    }
	
	public void increaseCont (){
		ContadorUser cu = getContador();
		cu.setNum(cu.getNum()+1);
		createContador(cu); 
	}
	
	public void decrementCont (){
		ContadorUser cu = getContador();
		cu.setNum(cu.getNum()-1);
		createContador(cu); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorUser cu = getContador();
		
		
		if (cu!=null){
			i = cu.getNum();			
		}else{
			ContadorUser contador = new ContadorUser(1);
		}
		
		return i;
	}
	
	public ContadorUser getContador(){
		
		List <ContadorUser> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorUser.class.getName();		
		cd = (List<ContadorUser>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorUser cd){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}
	
}
