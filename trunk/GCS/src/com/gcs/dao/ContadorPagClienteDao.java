package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.ContadorAuditoria;
import com.gcs.beans.ContadorPagCliente;
import com.gcs.persistence.PMF;

public class ContadorPagClienteDao {
	
	public static ContadorPagClienteDao getInstance() {
        return new ContadorPagClienteDao();
    }
	
	public void increaseCont (){
		ContadorPagCliente cpc = getContador();		
		cpc.setNum(cpc.getNum()+1);
		createContador(cpc); 
	}
	
	public void decrementCont (){
		ContadorPagCliente cpc = getContador();
		cpc.setNum(cpc.getNum()-1);
		createContador(cpc); 
	}

	@SuppressWarnings("unchecked")
	public Integer getContadorValue(){
		
		Integer i = 0;
		
		ContadorPagCliente cpc = getContador();
		
		if (cpc!=null){
			i = cpc.getNum();			
		}else{
			ContadorPagCliente contador = new ContadorPagCliente(1);
		}
		
		return i;
	}
	
	public ContadorPagCliente getContador(){
		
		List <ContadorPagCliente> cd;	
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + ContadorPagCliente.class.getName();		
		cd = (List<ContadorPagCliente>) pm.newQuery(queryStr).execute();
		pm.close();
		
		return cd.get(0);
		
	}
	
	public void  createContador(ContadorPagCliente cd){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(cd);
		}finally{
			pm.close();
		}
	}
	
	
	public void deleteContador(ContadorPagCliente cd) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(cd.getClass(), cd.getKey().getId()));
		pm.close();

	}
	
}
