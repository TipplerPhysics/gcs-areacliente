package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorCliente;
import com.gcs.persistence.PMF;

public class ContadorClienteDao {
	
	public static ContadorClienteDao getInstance() {
        return new ContadorClienteDao();
    }
	
	public void increaseCont (){
		ContadorCliente cc = getContador();
		
		
		cc.setNum(cc.getNum()+1);
		createContador(cc); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorCliente cc = getContador();
		
		
		if (cc!=null){
			i = cc.getNum();			
		}else{
			ContadorCliente contador = new ContadorCliente(1);
		}
		
		return i;
	}
	
	public ContadorCliente getContador(){
		
		List <ContadorCliente> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorCliente.class.getName();		
		cd = (List<ContadorCliente>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorCliente cd){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}
	
}
