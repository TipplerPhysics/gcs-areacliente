package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorProyecto;
import com.gcs.beans.Proyecto;
import com.gcs.persistence.PMF;

public class ContadorProyectoDao {
	
	public static ContadorProyectoDao getInstance() {
        return new ContadorProyectoDao();
    }
	
	public void increaseCont (){
		ContadorProyecto cp = getContador();
		
		
		cp.setNum(cp.getNum()+1);
		createContador(cp); 
	}
	
	public void decrementCont (){
		ContadorProyecto cp = getContador();
		
		
		cp.setNum(cp.getNum()-1);
		createContador(cp); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorProyecto cp = getContador();
		
		
		if (cp!=null){
			i = cp.getNum();			
		}else{
			ContadorProyecto contador = new ContadorProyecto(1);
		}
		
		return i;
	}
	
	public ContadorProyecto getContador(){
		
		List <ContadorProyecto> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorProyecto.class.getName();		
		cd = (List<ContadorProyecto>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorProyecto cd){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}
	
}
