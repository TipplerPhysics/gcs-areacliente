package com.gcs.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ClienteDao {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}

	public void createCliente(Cliente c) {
		ContadorClienteDao ccDao = ContadorClienteDao.getInstance();
		Integer cont = ccDao.getContadorValue();
		
		String nombre = c.getNombre();
		String letra = nombre.substring(0,1).toUpperCase();
		String nombreMayus = letra + nombre.substring(1,nombre.length());
		
		c.setNombre(nombreMayus);
		
		boolean isNewClient = false;
		if (c.getKey()==null){
			c.setClientId("IDGLOBAL"+ String.format("%04d", cont));
			isNewClient = true;
			c.setErased(false);
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		

		try {
			pm.makePersistent(c);
		} finally {
			pm.close();
			if (isNewClient)
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
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAllClientes() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("select from " + Cliente.class.getName());		
		q.setOrdering("nombre asc");
		clientes = (List<Cliente>) q.execute();
		
		pm.close();

		return clientes;
	}
	
public List<Cliente> getAllNonDeletedClients(){
		

		List<Cliente> clients_jdo = getAllClientes();
		List<Cliente> clients = new ArrayList<Cliente>();
		
		
		for (Cliente c:clients_jdo)
			if (!c.isErased())
				clients.add(c);

		return clients;
	}

	public void logicalDelete(Cliente c){
		c.setErased(true);
		createCliente(c);
	}
	
	   public Cliente getClienteById(long l) {
           PersistenceManager pManager = PMF.get().getPersistenceManager();
           Cliente cliente_temp = pManager.getObjectById(Cliente.class, l);
          
           Cliente cliente = pManager.detachCopy(cliente_temp);  
            pManager.close();

          
           return cliente;
   }
	   
		public void deleteClient(Cliente c){
			ProyectoDao pDao = ProyectoDao.getInstance();
			List<Proyecto> projects = pDao.getProjectsByClient(c.getKey().getId());
			
			for (Proyecto p:projects){
				pDao.deleteProject(p);
			}
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.deletePersistent( pm.getObjectById( c.getClass(), c.getKey().getId())); 
			pm.close();
			
		}

}
