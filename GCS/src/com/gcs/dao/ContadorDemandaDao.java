package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorDemanda;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;

public class ContadorDemandaDao {
	
	public static ContadorDemandaDao getInstance() {
        return new ContadorDemandaDao();
    }
	
	public void increaseCont (){
		ContadorDemanda cd = getContador();
		cd.setNum(cd.getNum()+1);
		createContador(cd); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorDemanda cd = getContador();
		
		
		if (cd!=null){
			i = cd.getNum();			
		}else{
			ContadorDemanda contador = new ContadorDemanda(1);
		}
		
		return i;
	}
	
	@SuppressWarnings("unchecked")
	public String countDemandas(){
		
		Integer a = getContador().getNum();
		
		String str = a.toString();
		
		if (str.length()<=1){
			str = "000"+str;
		}else if (str.length()<=2){
			str = "00"+str;
		}else if (str.length()<=3){
			str = "0"+str;
		}
		
		return str;
	}
	
	public ContadorDemanda getContador(){
		
		List <ContadorDemanda> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorDemanda.class.getName();		
		cd = (List<ContadorDemanda>) pm.newQuery(queryStr).execute();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorDemanda cd){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}
	
}
