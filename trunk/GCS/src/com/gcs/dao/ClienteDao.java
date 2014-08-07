package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.Demanda;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ClienteDao {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}

	public void createCliente(Cliente u) {
		ContadorClienteDao ccDao = ContadorClienteDao.getInstance();
		Integer cont = ccDao.getContadorValue();
		
		if (u.getKey()==null)
			u.setClientId("IDGLOBAL"+ String.format("%04d", cont));
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		

		try {
			pm.makePersistent(u);
		} finally {
			pm.close();
			ccDao.increaseCont();
		}
	}
	
	public Cliente getClienteByName(String name) {

		Cliente c = new Cliente();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Cliente.class.getName()
				+ " where nombre  == :p1";

		List<Cliente> clientes = (List<Cliente>) pManager.newQuery(queryStr).execute(name);

		if (!clientes.isEmpty()) {
			c = clientes.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;
	}

	public Cliente getClienteByRefGlobal(String ref) {

		Cliente c = new Cliente();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Cliente.class.getName()
				+ " where ref_global  == :p1";

		List<Cliente> clientes = (List<Cliente>) pManager.newQuery(queryStr).execute(ref);

		if (!clientes.isEmpty()) {
			c = clientes.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;

	}
	
	public Cliente getClienteByRefLocal(String ref) {

		Cliente c = new Cliente();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Cliente.class.getName()
				+ " where ref_local  == :p1";

		List<Cliente> clientes = (List<Cliente>) pManager.newQuery(queryStr).execute(ref);

		if (!clientes.isEmpty()) {
			c = clientes.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;

	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAllClientes() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryStr = "select from " + Cliente.class.getName();
		clientes = (List<Cliente>) pm.newQuery(queryStr).execute();

		return clientes;
	}
	
	   public Cliente getClienteById(long l) {
           PersistenceManager pManager = PMF.get().getPersistenceManager();
           Cliente cliente_temp = pManager.getObjectById(Cliente.class, l);
          
           Cliente cliente = pManager.detachCopy(cliente_temp);  
            pManager.close();

          
           return cliente;
   }
	   
		public void deleteClient(Cliente c){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.deletePersistent( pm.getObjectById( c.getClass(), c.getKey().getId())); 
			pm.close();
			
		}

}
