package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gcs.beans.ProductoProyecto;
import com.gcs.persistence.PMF;

public class ProductoProyectoDao {
	public static ProductoProyectoDao getInstance() {
		return new ProductoProyectoDao();
	}
	
	public synchronized void createProductoProyecto(ProductoProyecto imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try{
			
			//ServicioDao impDao = ServicioDao.getInstance();
		
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		}finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductoProyecto> getAllProductoProyectoes() {

		List<ProductoProyecto> ProductoProyectoes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + ProductoProyecto.class.getName());	
		q.setOrdering("name asc");
		ProductoProyectoes = (List<ProductoProyecto>) q.execute();
		
		
		pm.close();

		return ProductoProyectoes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProductoProyecto> getProductoProyectoesByName(String name) {

		ProductoProyectoDao productoProyectoDao = ProductoProyectoDao.getInstance();
		
		List<ProductoProyecto> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + ProductoProyecto.class.getName()+" where name=='"+name+"'");
		q.setOrdering("name asc");
		Servicios = (List<ProductoProyecto>) q.execute();
		
		
		
		pm.close();

		return Servicios;
	}
	
	public ProductoProyecto getProductoProyectoById(long l) {
		
		ProductoProyecto s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		ProductoProyecto ProductoProyecto_temp = pManager.getObjectById(ProductoProyecto.class, l);

		s = pManager.detachCopy(ProductoProyecto_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ProductoProyectoDao sDao = ProductoProyectoDao.getInstance();
		List<ProductoProyecto> servicios =sDao.getAllProductoProyectoes();
		for (ProductoProyecto s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
		pm.close();
	}
}
